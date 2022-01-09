package com.we3j.demo.utils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/3
 * *
 */
public class NormalUtil {

    /***********查询指定地址的余额***********/
    public static String getBalanceOfString(Web3j web3j, String address) throws IOException {
        BigInteger balance = getBalanceOf(web3j, address);
        if (balance != null) {
            return Convert.fromWei(balance.toString(), Convert.Unit.ETHER).toPlainString();
        }
        return null;
    }

    /***********查询指定地址的余额***********/
    public static BigInteger getBalanceOf(Web3j web3j, String address) throws IOException {
        if (web3j == null) return null;
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        //格式转化 wei-ether 10`18 =1 ETH
        return balance.getBalance();
    }

    /***********查询多个地址的余额***********/
    public static Map<String, BigInteger> getListBalanceOf(Web3j web3j, String... addresses) {
        Map<String, BigInteger> balanceMap = new HashMap<>();
        for (String address : addresses) {
            BigInteger addressBalance = null;
            try {
                addressBalance = getBalanceOf(web3j, address);
                balanceMap.put(address, addressBalance);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return balanceMap;
    }

    /****************查询多个地址的余额*******************/
    public static Map<String, BigInteger> getListBalanceOf(Web3j web3j, List<String> addresses) {
        Map<String, BigInteger> balanceMap = new HashMap<>();
        for (String address : addresses) {
            BigInteger addressBalance = null;
            try {
                addressBalance = getBalanceOf(web3j, address);
                balanceMap.put(address, addressBalance);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return balanceMap;
    }

    /**
     * 获取nonce，交易笔数
     *
     * @param from address
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static BigInteger getNonce(Web3j web3j, String from) throws ExecutionException, InterruptedException {
        if (web3j == null) return null;
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).sendAsync().get();
        return transactionCount.getTransactionCount();
    }
    /**
     * Return the current gas price from the ethereum node.
     * <p>
     *     Note: this method was previously called {@code getGasPrice} but was renamed to
     *     distinguish it when a bean accessor method on {@link org.web3j.tx.Contract} was added with that name.
     *     If you have a Contract subclass that is calling this method (unlikely since those
     *     classes are usually generated and until very recently those generated subclasses were
     *     marked {@code final}), then you will need to change your code to call this method
     *     instead, if you want the dynamic behavior.
     * </p>
     * @return the current gas price, determined dynamically at invocation
     * @throws IOException if there's a problem communicating with the ethereum node
     */
    public static BigInteger requestCurrentGasPrice(Web3j web3j) throws IOException {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        return ethGasPrice.getGasPrice();
    }


}
