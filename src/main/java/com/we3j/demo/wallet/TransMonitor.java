package com.we3j.demo.wallet;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import rx.Subscription;
import rx.functions.Action1;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/5
 * *
 * 交易监听器
 */
public class TransMonitor {
    private static TransMonitor instance;
    private Web3j web3j;

    public static TransMonitor getInstance() {
        if (instance == null) {
            synchronized (TransMonitor.class) {
                if (instance == null) {
                    instance = new TransMonitor();
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

    public Subscription subscribeBlock(final Action1<? super EthBlock> onNext) {
        if (this.web3j == null) return null;
        return this.web3j.blockObservable(true).subscribe(onNext);
    }

    public void unsubscribeBlock(Subscription subscription) {
        if (this.web3j == null) return;
        subscription.unsubscribe();
    }

    /**
     * 监听新交易事件
     * 已经交易的事件
     **/
    public Subscription subscribeHasTrans(final Action1<? super Transaction> onNext) {
        if (this.web3j == null) return null;
        return web3j.transactionObservable().subscribe(onNext);
    }

    /**
     * 取消订阅信息
     **/
    public void unsubscribeHasTrans(Subscription subscription) {
        if (this.web3j == null) return;
        subscription.unsubscribe();
    }

    /**
     * 监听待定交易
     */
    public Subscription subscribePendingTrans(final Action1<? super Transaction> onNext) {
        if (this.web3j == null) return null;
        return web3j.pendingTransactionObservable().subscribe(onNext);
    }

    /**
     * 取消订阅信息
     **/
    public void unsubscribePendingTrans(Subscription subscription) {
        if (this.web3j == null) return;
        subscription.unsubscribe();
    }


    /**
     * 监听合约的交易事件
     **/
    public Subscription subscribeContract(String contractAddress, final Action1<? super Log> onNext) {
        if (this.web3j == null) return null;

        // 要监听的合约事件 交易
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.valueOf(13763721L)),
                DefaultBlockParameterName.LATEST,
                contractAddress);
        Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        },

                        new TypeReference<Uint256>(false) {
                        }));

        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).subscribe(onNext);
    }

    public void unsubscribeContract(Subscription subscription) {
        if (this.web3j == null) return;
        subscription.unsubscribe();
    }


}
