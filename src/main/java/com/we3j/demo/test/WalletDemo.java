package com.we3j.demo.test;

import com.we3j.demo.mona.BuildInviteCodeRequest;
import com.we3j.demo.mona.Constants;
import com.we3j.demo.mona.RandomUtil;
import com.we3j.demo.mona.RequestUtil;
import com.we3j.demo.utils.Environment;
import com.we3j.demo.wallet.WalletTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * @Author jambestwick
 * @create 2021/11/30 0030  0:09
 * @email jambestwick1988@gmail.com
 */

public class WalletDemo {

    private static final Logger log = LoggerFactory.getLogger(WalletDemo.class);
    private Web3j web3j;
    private Credentials credentials;
    String tempAddress;
    private HttpService httpService;

    public static void main(String[] args) throws Exception {
        new WalletDemo().run1();
    }


    private void run1() throws Exception {
        log.info("hello eth,hello web3j");

//        for (int i = 0; i < 100; i++) {
//            Executors.newCachedThreadPool().execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        WalletTools.createWalletBatch(100000,"d:\\keystore","d:\\keystore\\abc.txt");
//                    } catch (CipherException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//

        connectETHClient();//连接以太坊客户端
        //creatAccount();//创建冷钱包
        String privateKey = "";
        String keyStoreDir = WalletUtils.getDefaultKeyDirectory();
        System.out.println("生成keyStore文件的默认目录：" + keyStoreDir);
        //通过密码及keystore目录生成钱包
        //Bip39Wallet wallet = WalletUtils.generateBip39Wallet("123456", new File(keyStoreDir));
        //keyStore文件名
//        System.out.println(wallet.getFilename());
//        //12个单词的助记词
//        System.out.println(wallet.getMnemonic());

        Scanner scanner = new Scanner(System.in);
        privateKey = scanner.next();
        //
        loadWalletByPrivateKey(privateKey);//加载钱包
        //先获取Sign码

        //getBlanceOf();//查询账户余额
        //transto();//转账到指定地址
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


    /*************创建一个钱包文件**************/
    private void creatAccount() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String walletFileName0 = "";//文件名
        String walletFilePath0 = "D:\\JavaWorkSpace\\we3jdemo\\src\\main\\java\\com\\we3j\\demo";//钱包文件保持路径，请替换位自己的某文件夹路径
        //String walletFilePath0 = "/Users/jam/MyGitHub/z_wallet_temp";//钱包文件保持路径，请替换位自己的某文件夹路径

        walletFileName0 = WalletUtils.generateNewWalletFile("123456", new File(walletFilePath0), false);//根据密码生成钱包到地址路径
        //walletFileName0 = WalletUtils.generateLightNewWalletFile("password1", new File(walletFilePath0));//
        //WalletUtils.generateLightNewWalletFile("password2",new File(walletFilePath0));
        //WalletUtils.generateBip39Wallet("password2",new File(walletFilePath0));//助记词钱包生成
        log.info("walletName: " + walletFileName0);
    }

//
//    /**
//     * 创建bip39钱包
//     *
//     * @param password 钱包密码
//     * @return CommonWallet
//     * @throws CipherException e
//     */
//    public Bip39Wallet2 generateBip39Wallet(String password) throws CipherException {
//        byte[] initialEntropy = new byte[16];
//        SecureRandom.getInstance().nextBytes(initialEntropy);
//        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
//        byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
//        ECKeyPair ecKeyPair = ECKeyPair.create(Hash.sha256(seed));
//        WalletFile walletFile = WalletUtils.generateWalletFile(password, ecKeyPair, false,false);
//        return new Bip39Wallet2(walletFile.getAddress(), mnemonic, password, JSON.toJSONString(walletFile), ecKeyPair.getPrivateKey(), ecKeyPair.getPublicKey());
//    }


    /********根据密码，钱包文件加载钱包文件**********/
    private void loadWallet(String password, String walletFilePath) throws IOException, CipherException {
        // FIXME: 2018/4/15 替换为自己的钱包路径
        //String walletFilePath = "/Users/jambestwick/MyGitHub/z_wallet_temp/UTC--2018-04-10T02-51-24.815000000Z--12571f46ec3f81f7ebe79112be5883194d683787.json";
        //String password = "123456";
        credentials = WalletUtils.loadCredentials(password, walletFilePath);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        log.info("address=" + address);
        log.info("public key=" + publicKey);
        log.info("private key=" + privateKey);
    }


    /********根据助记词加载钱包 **********/
    private void loadWalletByPrimaryKeyPassword(String password, String mnemonic) {
        WalletUtils.loadBip39Credentials(password, mnemonic);//no need password
//        credentials = WalletUtils.loadBip39Credentials(password,
//                keyWords);//"cherry type collect echo derive shy balcony dog concert picture kid february"
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        log.info("address=" + address);
        log.info("public key=" + publicKey);
        log.info("private key=" + privateKey);
        tempAddress = address;

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
        tempAddress = address;

    }


    /****************交易*****************/
    private void transto() throws Exception {
        if (web3j == null) return;
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_to = "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87";
        //转账时最好判断地址的有效性,谨防由于错行少字，金额丢失
        if (!WalletUtils.isValidAddress(address_to)) {
            return;
        }
        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, address_to, BigDecimal.ONE, Convert.Unit.FINNEY).send();

        log.info("Transaction complete:");
        log.info("trans hash=" + send.getTransactionHash());
        log.info("from :" + send.getFrom());
        log.info("to:" + send.getTo());
        log.info("gas used=" + send.getGasUsed());
        log.info("status: " + send.getStatus());
    }

    /********对指定信息签名**********/
    private String sign(String message) throws Exception {
        if (web3j == null) return null;
        if (credentials == null) return null;

        byte[] hexMessage = Hash.sha3(message.getBytes());

        Sign.SignatureData signatureData = Sign.signMessage(hexMessage, credentials.getEcKeyPair());//例如metamask 登录的签名信息
        //EthSign ethSign = web3j.ethSign(address, Hash.sha3(message)).send();

        String resultSignature = signatureData.toString();
        log.info("sign hash=" + resultSignature);
        return resultSignature;
    }

    /***********查询指定地址的余额***********/
    private void getBlanceOf() throws IOException {

        if (web3j == null) return;
        String address = "0x12571f46ec3f81f7ebe79112be5883194d683787";//等待查询余额的地址
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        //格式转化 wei-ether
        String blanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString().concat(" ether");
        log.info(blanceETH);
    }


    /***********查询指定地址的余额***********/



    /**
     * 初始化admin级别操作的对象
     *
     * @return Admin
     */
    private Admin initAdmin() {
        return Admin.build(getService());
    }


    private HttpService getService() {
        if (httpService == null) {
            httpService = new HttpService("https://mainnet.infura.io/v3/9eb78bae70c34116a2b28db3fdb96dd0");
        }
        return httpService;
    }


}
