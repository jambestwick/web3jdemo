package com.we3j.demo.wallet;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.Log;
import rx.Subscription;
import rx.functions.Action1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * * Created by jambestwick
 * * on 2021/12/8
 * *
 */
public class NFTMonitor {
    private static NFTMonitor instance;
    private Web3j web3j;
    private Credentials credentials;

    public static NFTMonitor getInstance() {
        if (instance == null) {
            synchronized (NFTMonitor.class) {
                if (instance == null) {
                    instance = new NFTMonitor();
                }
            }
        }
        return instance;
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * 监听合约的交易事件
     **/
    public Subscription subscribeClaim(String contractAddress, final Action1<? super Log> onNext) {
        if (this.web3j == null) return null;
        // 要监听的合约事件 交易
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(0L)),//监听从0区块
                DefaultBlockParameterName.LATEST,//到最新的区块
                contractAddress);
        Event eventClaim = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },

                        new TypeReference<Uint256>(false) {
                        }));

        filter.addSingleTopic(EventEncoder.encode(eventClaim));
        return web3j.ethLogObservable(filter).subscribe(onNext);
    }

    public void unsubscribeClaim(Subscription subscription) {
        if (this.web3j == null) return;
        subscription.unsubscribe();
    }

    private static String emptyAddress = "0x0000000000000000000000000000000000000000";

    /**
     * 调用NFT的Mint方法根据合约有两种
     * Function: mint(uint256 _mintAmount)
     * ，即铸造生成NFT到自己的钱包
     *
     * @param amount 你要mint的NFT数量
     ***/
    public  boolean mintNFTByAmount(Web3j web3j, String contractAddress, int amount) {
        if (web3j == null) return false;
        String methodName = "mint";
        String fromAddr = emptyAddress;
        Function function = new Function(
                methodName,
                Arrays.asList(new Uint256(amount)),
                Arrays.asList(new TypeReference<Type>() {
                }));

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            //List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 调用NFT的Mint方法根据合约有两种
     * Function: mint(address _to, uint256 _mintAmount)
     ***/
    public  boolean mintNFTByAmountAndAddress(Web3j web3j, Credentials credentials, String contractAddress, int amount) {
        if (web3j == null) return false;
        if (credentials == null) return false;
        String methodName = "mint";
        String fromAddr = emptyAddress;
        Function function = new Function(
                methodName,
                Arrays.asList(new Address(credentials.getAddress()), new Uint256(amount)),
                Arrays.asList(new TypeReference<Type>() {
                }));

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            //List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }




}
