package com.we3j.demo.wallet;

import com.we3j.demo.utils.NormalUtil;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author jambestwick
 * @create 2021/12/5 0005  21:04
 * @email jambestwick@126.com
 * <p>
 * ERC20的代币
 */
public class TokenClient {

    private static String emptyAddress = "0x0000000000000000000000000000000000000000";

    /**
     * 查询代币发行总量
     *
     * @param web3j
     * @param contractAddress
     * @return
     */
    public static BigInteger getTokenTotalSupply(Web3j web3j, String contractAddress) {
        if (web3j == null) return null;
        String methodName = "totalSupply";
        String fromAddr = emptyAddress;
        BigInteger totalSupply = BigInteger.ZERO;
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            totalSupply = (BigInteger) results.get(0).getValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return totalSupply;
    }

    /**
     * 查询指定账户
     * 指定 ERC-20 余额
     **/
    public static BigInteger getAddressBalanceOf(Web3j web3j, String contractAddress, String address) {
        if (web3j == null) return null;
        String methodName = "balanceOf";
        String fromAddr = emptyAddress;
        BigInteger tokenBalance = BigInteger.ZERO;

        List<Type> inputParameters = new ArrayList<>();
        Address userAddress = new Address(address);
        inputParameters.add(userAddress);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            tokenBalance = (BigInteger) results.get(0).getValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return tokenBalance;
    }


    /***
     * ERC20的授权操作
     * 1.先调用授权 2.再调用transfer
     * approve授权
     *
     * **/
    public static boolean approve(Web3j web3j, Credentials credentials, String contractAddress, String address, BigInteger value) {
        if (web3j == null) return false;
        if (credentials == null) return false;
        String methodName = "approve";
        String fromAddr = emptyAddress;
        boolean approveFlag = false;
        Function function = new Function(
                methodName,
                Arrays.asList(new Address(address), new Uint256(value)),
                Arrays.asList(new TypeReference<Type>() {
                }));


        //获取nonce，交易笔数
        try {
            BigInteger nonce = NormalUtil.getNonce(web3j, address);
            //get gasPrice
            BigInteger gasPrice = NormalUtil.requestCurrentGasPrice(web3j);
            BigInteger gasLimit = Contract.GAS_LIMIT;

            String encodedFunction = FunctionEncoder.encode(function);

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                    gasPrice,
                    gasLimit,
                    contractAddress, encodedFunction);


            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //异步发送交易
            EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            approveFlag = true;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return approveFlag;
    }


    /**
     * Returns the amount which _spender is still allowed to withdraw from _owner.
     **/
    public static BigInteger allowance(Web3j web3j, String contractAddress, String owner, String spender) {
        if (web3j == null) return null;
        String methodName = "allowance";
        String fromAddr = emptyAddress;
        BigInteger allowedAmount = BigInteger.ZERO;

        List<Type> inputParameters = new ArrayList<>();
        Address userAddress = new Address(owner);
        inputParameters.add(userAddress);
        Address spenderAddress = new Address(spender);
        inputParameters.add(spenderAddress);

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            allowedAmount = (BigInteger) results.get(0).getValue();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return allowedAmount;
    }


    /***********
     * A允许B从A那里转多少钱给C，
     * 先用A调用approve方法，传入B账户；
     * 然后调用transferFrom方法，from参数传A账户，to参数传B账户，最后账户地址要改为B的账户来调用
     * ****************/
    public static boolean transferForm(Web3j web3j,
                                       Credentials credentials,
                                       String from,
                                       String to,
                                       BigInteger value,
                                       String contractAddress) throws ExecutionException, IOException, InterruptedException {
        //获取nonce，交易笔数
        BigInteger nonce = NormalUtil.getNonce(web3j, from);
        //get gasPrice
        BigInteger gasPrice = NormalUtil.requestCurrentGasPrice(web3j);
        BigInteger gasLimit = Contract.GAS_LIMIT;
        //创建RawTransaction交易对象
        try {
            Function function = new Function(
                    "transferForm",
                    Arrays.asList(new Address(from), new Address(to), new Uint256(value)),
                    Arrays.asList(new TypeReference<Type>() {
                    }));

            String encodedFunction = FunctionEncoder.encode(function);

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                    gasPrice,
                    gasLimit,
                    contractAddress, encodedFunction);


            //签名Transaction，这里要对交易做签名
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //异步发送交易
            EthSendTransaction send = web3j.ethSendRawTransaction(hexValue).sendAsync().get();

            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


    /****
     * 调用claim方法抢NFT Token
     * ***/
    public static boolean claimNFT(Web3j web3j, String contractAddress, Integer tokenId) {
        if (web3j == null) return false;
        String methodName = "claim";
        String fromAddr = emptyAddress;
        Function function = new Function(
                methodName,
                Arrays.asList(new Uint256(tokenId)),
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
