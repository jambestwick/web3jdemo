package demo;

import mona.BuildInviteCodeRequest;
import mona.Constants;
import mona.RandomUtil;
import mona.RequestUtil;
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
import java.util.Scanner;

/**
 * @Author jambestwick
 * @create 2021/11/30 0030  0:09
 * @email jambestwick@126.com
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
        conectETHclient();//连接以太坊客户端
        //creatAccount();//创建冷钱包
        String privateKey = "";
        String wechat = "";
        String keyStoreDir = WalletUtils.getDefaultKeyDirectory();
        System.out.println("生成keyStore文件的默认目录：" + keyStoreDir);
        //通过密码及keystore目录生成钱包
        //Bip39Wallet wallet = WalletUtils.generateBip39Wallet("123456", new File(keyStoreDir));
        //keyStore文件名
//        System.out.println(wallet.getFilename());
//        //12个单词的助记词
//        System.out.println(wallet.getMnemonic());

        System.out.println("离线工具，放心使用,源码在github.com/jambestwick");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入作者的微信号，方能使用:");
        if (scanner.hasNext()) {
            wechat = scanner.nextLine();
        }
        if (wechat == null) {
            System.out.println("对不起，你不认识作者！");
            return;
        }
        if (!wechat.equals("jambestwick")) {
            System.out.println("对不起，你不认识作者！");
            return;
        }
        System.out.println("离线工具，放心使用,输入私钥/offLine tools  safe, input privateKey：");

        privateKey = scanner.nextLine();

        loadWalletByPrimaryKeyPassword(privateKey);//加载钱包
        //先获取Sign码

        while (true) {
            String monaMessage = RequestUtil.requestGet(Constants.GET_MESSAGE);
            if (monaMessage == null) {
                //未获取到Message
                continue;
            }
            //不断尝试随机生成code码
            String signMessage = sign(tempAddress, monaMessage);
            if (signMessage == null) {
                continue;
            }
            //随机生成邀请码
            String inviteCode = RandomUtil.randomNumDigest();
            String response = RequestUtil.requestPost(Constants.POST_INVITE_CODE
                    , BuildInviteCodeRequest.buildInviteParam(tempAddress, inviteCode, signMessage)
                    , BuildInviteCodeRequest.buildHead("application/json, text/plain, */*"
                            , "gzip, deflate, br"
                            , "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7"
                            , "application/json"
                            , "https://monaconft.io"
                            , "https://monaconft.io/"
                            , "\"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\""
                            , "?1"
                            , "Android"
                            , "empty"
                            , "cors"
                            , "same-site"
                            , ""
                            , "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Mobile Safari/537.36")
            );

            if (response != null && !response.contains("wrong invite code or has been taken")) {//邀请码成功，不继续
                System.out.println("恭喜你，成功获取到了邀请码，进入Mona:" + response.toLowerCase());
                System.out.println("Congratulations，Success get the invite code，Mona login");

                break;
            }
        }

        //getBlanceOf();//查询账户余额
        //transto();//转账到指定地址
    }


    /*******连接以太坊客户端**************/
    private void conectETHclient() throws IOException {
        //连接方式1：使用infura 提供的客户端
        //mainnet https://mainnet.infura.io/v3/2b86c426683f4a6095fd175fe931d799
        web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/9eb78bae70c34116a2b28db3fdb96dd0"));// TODO: 2018/4/10 token更改为自己的或者主网
        //连接方式2：使用本地客户端
        //web3j = Web3j.build(new HttpService("127.0.0.1:7545"));
        //测试是否连接成功
        String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        log.info("version=" + web3ClientVersion);
    }


    /*************创建一个钱包文件**************/
    private void creatAccount() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String walletFileName0 = "";//文件名
        String walletFilePath0 = "/Users/jam/MyGitHub/z_wallet_temp";//钱包文件保持路径，请替换位自己的某文件夹路径

        walletFileName0 = WalletUtils.generateNewWalletFile("123456", new File(walletFilePath0), false);
        //WalletUtils.generateFullNewWalletFile("password1",new File(walleFilePath1));
        //WalletUtils.generateLightNewWalletFile("password2",new File(walleFilePath2));
        log.info("walletName: " + walletFileName0);
    }

    /********加载钱包文件**********/
    private void loadWallet() throws IOException, CipherException {
        // FIXME: 2018/4/15 替换为自己的钱包路径
        String walleFilePath = "/Users/yepeng/MyGitHub/z_wallet_temp/UTC--2018-04-10T02-51-24.815000000Z--12571f46ec3f81f7ebe79112be5883194d683787.json";
        String passWord = "123456";
        credentials = WalletUtils.loadCredentials(passWord, walleFilePath);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        log.info("address=" + address);
        log.info("public key=" + publicKey);
        log.info("private key=" + privateKey);
    }

    /**
     * 根据助记词连接
     **/

    private void loadWalletByPrimaryKeyPassword(String privateKeys) throws IOException, CipherException {

        credentials = Credentials.create(privateKeys);
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

    /****************交易*****************/
    private void transto() throws Exception {
        if (web3j == null) return;
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_to = "0x41F1dcbC0794BAD5e94c6881E7c04e4F98908a87";
        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, address_to, BigDecimal.ONE, Convert.Unit.FINNEY).send();

        log.info("Transaction complete:");
        log.info("trans hash=" + send.getTransactionHash());
        log.info("from :" + send.getFrom());
        log.info("to:" + send.getTo());
        log.info("gas used=" + send.getGasUsed());
        log.info("status: " + send.getStatus());
    }

    /********签名**********/
    private String sign(String address, String message) throws Exception {
        if (web3j == null) return null;
        if (credentials == null) return null;

        byte[] hexMessage = Hash.sha3(message.getBytes());

        Sign.SignatureData signatureData = Sign.signMessage(hexMessage, credentials.getEcKeyPair());
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
