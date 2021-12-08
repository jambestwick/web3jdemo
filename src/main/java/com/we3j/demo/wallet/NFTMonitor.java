package com.we3j.demo.wallet;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import rx.Subscription;
import rx.functions.Action1;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/8
 * *
 */
public class NFTMonitor {
    private static NFTMonitor instance;
    private Web3j web3j;

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

    /**
     * 监听合约的交易事件
     **/
    public Subscription subscribeClaim(String contractAddress, final Action1<? super Log> onNext) {
        if (this.web3j == null) return null;
        // 要监听的合约事件 交易
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(0L)),
                DefaultBlockParameterName.LATEST,
                contractAddress);
        Event eventClaim = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },

                        new TypeReference<Uint256>(false) {//tokenId如果有10000个NFT默认从1往上1加
                        }));

        filter.addSingleTopic(EventEncoder.encode(eventClaim));
        return web3j.ethLogObservable(filter).subscribe(onNext);
    }

    public void unsubscribeClaim(Subscription subscription) {
        if (this.web3j == null) return;
        subscription.unsubscribe();
    }



}
