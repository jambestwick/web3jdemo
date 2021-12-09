package com.we3j.demo.wallet;

import com.we3j.demo.bean.bo.ReceiveAccount;
import com.we3j.demo.test.WalletDemo;
import com.we3j.demo.utils.NormalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.Contract;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/3
 * 转账
 * *
 */
public class TransferToken {
    private static final Logger log = LoggerFactory.getLogger(TransferToken.class);


    /****************转账****************/
    public static boolean transfer(Web3j web3j, Credentials credentials, String toAddress, BigDecimal amount)
            throws Exception {
        if (web3j == null) return false;
        if (credentials == null) return false;
        if (!WalletUtils.isValidAddress(toAddress) || !WalletUtils.isValidAddress(credentials.getAddress())) {
            log.info("invalid address");
            return false;
        }
        BigInteger balance = NormalUtil.getBalanceOf(web3j, credentials.getAddress());
        if (balance == null) {
            log.info("can't find wallet balance");
            return false;
        }
        if (balance.compareTo(amount.toBigInteger()) <= 0) {//wallet balance must greater than trans amount
            log.info("wallet balance is less than trans amount");
            return false;
        }

        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, toAddress, amount, Convert.Unit.WEI).send();
        log.info("trans hash=" + send.getTransactionHash());
        log.info("from :" + send.getFrom());
        log.info("to:" + send.getTo());
        log.info("gas used=" + send.getGasUsed());
        log.info("status: " + send.getStatus());
        return true;
    }

    /****************转账****************/
    public static void transferAsync(Web3j web3j, Credentials credentials, String toAddress, BigDecimal amount)
            throws InterruptedException, IOException, TransactionException, ExecutionException {
        if (web3j == null) return;
        if (credentials == null) return;
        if (!WalletUtils.isValidAddress(toAddress) || !WalletUtils.isValidAddress(credentials.getAddress())) {
            log.info("invalid address");
            return;
        }
        BigInteger balance = NormalUtil.getBalanceOf(web3j, credentials.getAddress());
        if (balance == null) {
            log.info("can't find wallet balance");
            return;
        }
        if (balance.compareTo(amount.toBigInteger()) <= 0) {//wallet balance must greater than trans amount
            log.info("wallet balance is less than trans amount");
            return;
        }
        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, toAddress, amount, Convert.Unit.WEI).sendAsync().get();
        log.info("trans hash=" + send.getTransactionHash());
        log.info("from :" + send.getFrom());
        log.info("to:" + send.getTo());
        log.info("gas used=" + send.getGasUsed());
        log.info("status: " + send.getStatus());
    }


    /***************向多地址异步批量转账*****************/
    public static void transferBatch(Web3j web3j, Credentials credentials, List<ReceiveAccount> receiveAccountList) throws InterruptedException, TransactionException, IOException, ExecutionException {
        for (ReceiveAccount receiveAccount : receiveAccountList) {
            transferAsync(web3j, credentials, receiveAccount.getAddress(), receiveAccount.getSendAmount());
        }
    }


    /***********转账ERC20协议的代币*****************/
    public static EthSendTransaction transferERC20(Web3j web3j,
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
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(value)),
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

        log.info("trans hash=" + send.getTransactionHash());
        log.info("rawResponse :" + send.getRawResponse());
        log.info("result:" + send.getResult());
        return send;
    }


    /************************** 批量转账ERC20 Token***************************************/
    public static void transferERC20Batch(Web3j web3j,
                                          Credentials credentials,
                                          String from,
                                          String contractAddress,
                                          List<ReceiveAccount> receiveAccountList) throws InterruptedException, ExecutionException, IOException {

        for (ReceiveAccount receiveAccount : receiveAccountList) {
            transferERC20(web3j, credentials, from, receiveAccount.getAddress(), receiveAccount.getSendAmount().toBigInteger(), contractAddress);
        }
    }

    /**
     * 批量转账ERC20 Token
     *
     * @param destAddresses 目的地址的集合
     * @param sendAmount    每个地址要发送的金额
     ***********************************/
    public static void transferERC20Batch(Web3j web3j,
                                          Credentials credentials,
                                          String from,
                                          String contractAddress,
                                          List<String> destAddresses
            , BigInteger sendAmount) throws InterruptedException, ExecutionException, IOException {

        for (String dest : destAddresses) {
            transferERC20(web3j, credentials, from, dest, sendAmount, contractAddress);
        }
    }

    /**
     * 根据hash值获取交易
     *
     * @param hash
     * @return
     * @throws IOException
     */
    public static EthTransaction getTransactionByHash(Web3j web3j, String hash) throws IOException {
        Request<?, EthTransaction> request = web3j.ethGetTransactionByHash(hash);
        return request.send();
    }

    /**
     * 获得ethblock
     *
     * @param blockNumber 根据区块编号
     * @return
     * @throws IOException
     */
    public static EthBlock getBlockEthBlock(Web3j web3j, Integer blockNumber) throws IOException {
        DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(blockNumber);
        Request<?, EthBlock> request = web3j.ethGetBlockByNumber(defaultBlockParameter, true);
        EthBlock ethBlock = request.send();
        return ethBlock;
    }

}
