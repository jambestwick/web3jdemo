import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Uint;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
import java.util.List;

/**
 * 本案例作为web3j的示例项目
 * 1 连接以太坊客户端
 * 2 创建冷钱包
 * 3 加载钱包文件
 * 4 转账
 * 5 部署合约
 * 6 读取&更新 智能合约的数据
 * 7 观察智能合约event
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private Web3j web3j;
    private Credentials credentials;

    public static void main(String[] args) throws Exception {
        new Application().run1();
    }

    private void run1() throws Exception {
        log.info("hello eth,hello web3j");
        conectETHclient();//连接以太坊客户端
        //creatAccount();//创建冷钱包
        loadWallet();//加载钱包
        //getBlanceOf();//查询账户余额
        transto();//转账到指定地址
    }


    /*******连接以太坊客户端**************/
    private void conectETHclient() throws IOException {
        //连接方式1：使用infura 提供的客户端
        web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/zmd7VgRt9go0x6qlJ2Mk"));// TODO: 2018/4/10 token更改为自己的
        //连接方式2：使用本地客户端
        //web3j = Web3j.build(new HttpService("127.0.0.1:7545"));
        //测试是否连接成功
        String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        log.info("version=" + web3ClientVersion);
    }


    /*************创建一个钱包文件**************/
    private void creatAccount() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String walletFileName0 = "";//文件名
        String walletFilePath0 = "/Users/yepeng/MyGitHub/z_wallet_temp";//钱包文件保持路径，请替换位自己的某文件夹路径

        walletFileName0 = WalletUtils.generateNewWalletFile("123456", new File(walletFilePath0), false);
        //WalletUtils.generateFullNewWalletFile("password1",new File(walleFilePath1));
        //WalletUtils.generateLightNewWalletFile("password2",new File(walleFilePath2));
        log.info("walletName: " + walletFileName0);
    }

    /********加载钱包文件**********/
    private void loadWallet() throws IOException, CipherException {
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

}
