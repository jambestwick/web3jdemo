package com.we3j.demo.test;

import com.we3j.demo.utils.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.ContractUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @auth jambestwick
 * <p>
 * 合约测试
 ***/

public class ContractDemo {

    private static final Logger log = LoggerFactory.getLogger(ContractDemo.class);
    private Web3j web3j;
    private Credentials credentials;

    public static void main(String[] args) {
        new ContractDemo().run();
    }

    private void run() {


//        try {
//            connectETHClient();
//            listenContract("0x4c1ae77bc2df45fb68b13fa1b4f000305209b0cb");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }


    /*******连接以太坊客户端**************/
    private void connectETHClient() throws IOException {
        //连接方式1：使用infura 提供的客户端
        //mainnet https://mainnet.infura.io/v3/2b86c426683f4a6095fd175fe931d799
        web3j = Web3j.build(new HttpService(Environment.RPC_URL));// TODO: 2018/4/10 节点更改为自己的或者主网
        //连接方式2：使用本地客户端
        //web3j = Web3j.build(new HttpService("127.0.0.1:7545"));
        //测试是否连接成功
        String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        log.info("version=" + web3ClientVersion);
    }

    /********根据私钥加载钱包 **********/
    private void loadWalletByPrivateKey(String inputPrivateKey) {
        credentials = Credentials.create(inputPrivateKey);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        log.info("address=" + address);
        log.info("public key=" + publicKey);
        log.info("private key=" + privateKey);
    }


    /**
     * *****
     * *******创建一个智能合约******
     * <p>
     * nonce 当nonce太小，交易会被直接拒绝。
     * 当nonce太大，交易会一直处于队列之中，这也就是导致我们上面描述的问题的原因；
     * 当发送一个比较大的nonce值，然后补齐开始nonce到那个值之间的nonce，那么交易依旧可以被执行。
     * 当交易处于queue中时停止geth客户端，那么交易queue中的交易会被清除掉。
     * 解决这个问题的方法是自己维护Nonce，同一个账户连续发送俩笔交易时，通过递增Nonce来防止Nonce重复。
     * @param from  the address to create Contract
     ***********/
    public void createContract(String from) throws InterruptedException, ExecutionException {
// using a raw transaction
        if (web3j == null) return;


        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                from, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        //Generate a smart contract address. This enables you to identify what address a
        //     *  smart contract will be deployed to on the network.
        String result = ContractUtils.generateContractAddress(from, nonce);

        log.info("createContract web3j" + result);
    }


    public void createContract(String from, BigInteger ethAmount) throws InterruptedException, ExecutionException, IOException {
        if (web3j == null) return;

        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                from, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        // using a raw transaction
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        //BigInteger gasLimit =getTransactionGasLimit(web3j,null);//每笔交易的gas最少21000
        BigInteger gasLimit = new BigInteger(String.valueOf(21000L));//每笔交易的gas最少21000
        RawTransaction rawTransaction = RawTransaction.createContractTransaction(nonce, gasPrice, gasLimit, ethAmount, "0x <compiled smart contract code>");
// send...

    }

    public static BigInteger getTransactionGasLimit(Web3j web3j, org.web3j.protocol.core.methods.request.Transaction transaction) {
        try {
            EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
            if (ethEstimateGas.hasError()) {
                throw new RuntimeException(ethEstimateGas.getError().getMessage());
            }
            return ethEstimateGas.getAmountUsed();
        } catch (IOException e) {
            throw new RuntimeException("net error");
        }
    }

    /************获取账户ETH余额***************/
    public static BigDecimal getBalance(Web3j web3j, String address) {
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            return Convert.fromWei(new BigDecimal(ethGetBalance.getBalance()), Convert.Unit.ETHER);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*********根据交易Hash获取合约地址***********/
    private String getContractAddress(String transactionHash) throws IOException {
        // get contract address
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).send();

        if (transactionReceipt.getTransactionReceipt().isPresent()) {
            return transactionReceipt.getResult().getContractAddress();
        }
        return null;
    }


//    private void deploy() {
//
//        Web3j web3j = Web3j.build(new HttpService(Environment.RPC_URL));
//        RemoteCall<TokenERC20> deploy = TokenERC20.deploy(web3j, credentials,
//                Convert.toWei("10", Convert.Unit.GWEI).toBigInteger(),
//                BigInteger.valueOf(3000000),
//                BigInteger.valueOf(5201314),
//                "my token", "mt");
//        try {
//            TokenERC20 tokenERC20 = deploy.send();
//            tokenERC20.isValid();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /***合约监听***/
    private void listenContract(String contractAddress) {

        /**
         * 监听ERC20 token 交易
         */
        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                contractAddress);
        org.web3j.abi.datatypes.Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<?>[]{new TypeReference<Address>(true) {
                        }, new TypeReference<Address>(true) {
                        }, new TypeReference<Uint256>(false) {
                        }})
        );

        String topicData = EventEncoder.encode(event);
        filter.addSingleTopic(topicData);
        System.out.println(topicData);

        web3j.ethLogObservable(filter).subscribe(log -> {
            System.out.println(log.getBlockNumber());
            System.out.println(log.getTransactionHash());
            List<String> topics = log.getTopics();
            for (String topic : topics) {
                System.out.println(topic);
            }
        });
    }

}
