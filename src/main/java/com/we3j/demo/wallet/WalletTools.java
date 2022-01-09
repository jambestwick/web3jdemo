package com.we3j.demo.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we3j.demo.utils.FileUtil;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/4
 * * about some method create and load wallet
 */
public class WalletTools {

    public static boolean createWallet(String filePath, String destFile) throws CipherException, IOException {
        FileUtil.createOrExistsDir(new File(filePath));
        Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet("", new File(filePath));//助记词钱包生成
        bip39Wallet.getMnemonic();
        System.out.println("生成助记词, generate mnemonic:" + bip39Wallet.getMnemonic());
        return FileUtil.writeFileFromLineString(new File(destFile), bip39Wallet.getMnemonic(), true);
    }


    /******************
     * **批量生成钱包（助记词）到指定文件中
     * @param destFile eg. "d:\\keystore"
     * @param count 需要生成的数量
     * *********************/
    public static void createWalletBatch(int count, String filePath, String destFile) throws CipherException, IOException {
        for (int i = 0; i < count; i++) {
            createWallet(filePath, destFile);
        }

    }

    /**
     * return fileName
     *
     **/
    public static String createWalletFull(String password, String destFile) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        FileUtil.createOrExistsDir(new File(destFile));
        return WalletUtils.generateFullNewWalletFile(password, new File(destFile));
    }

    /**
     * return fileName
     ***/
    public static String createWalletLight(String password, String destFile) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        FileUtil.createOrExistsDir(new File(destFile));
        return WalletUtils.generateLightNewWalletFile(password, new File(destFile));
    }

    /**
     *根据私钥文件路径加载钱包
     *
     **/
    public static Credentials loadWallet(String password, String walletFilePath) throws IOException, CipherException {
        // FIXME: 2018/4/15 替换为自己的钱包路径
        //String walletFilePath = "/Users/jambestwick/MyGitHub/z_wallet_temp/UTC--2018-04-10T02-51-24.815000000Z--12571f46ec3f81f7ebe79112be5883194d683787.json";
        //String password = "123456";
        Credentials credentials = WalletUtils.loadCredentials(password, walletFilePath);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        System.out.println("address=" + address);
        System.out.println("public key=" + publicKey);
        System.out.println("private key=" + privateKey);
        return credentials;
    }

    /********根据助记词加载钱包 **********/
    public static Credentials loadWalletByMnemonic(String password, String mnemonic) {
        Credentials credentials = WalletUtils.loadBip39Credentials(password, mnemonic);//no need password
//        credentials = WalletUtils.loadBip39Credentials(password,
//                keyWords);//"cherry type collect echo derive shy balcony dog concert picture kid february"
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        System.out.println("address=" + address);
        System.out.println("public key=" + publicKey);
        System.out.println("private key=" + privateKey);
        return credentials;
    }

    /**
     * 根据私钥加载钱包
     *
     * **/
    public static Credentials loadWalletByPrivateKey(String inputPrivateKey) {
        Credentials credentials = Credentials.create(inputPrivateKey);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        System.out.println("address=" + address);
        System.out.println("public key=" + publicKey);
        System.out.println("private key=" + privateKey);
        return credentials;
    }

    /**
     * 解密keystore 得到私钥
     *
     * @param keystore
     * @param password
     */
    public static String decryptWallet(String keystore, String password) {
        String privateKey = null;
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            ECKeyPair ecKeyPair = null;
            ecKeyPair = Wallet.decrypt(password, walletFile);
            privateKey = ecKeyPair.getPrivateKey().toString(16);
            System.out.println(privateKey);
        } catch (CipherException e) {
            if ("Invalid password provided".equals(e.getMessage())) {
                System.out.println("密码错误");
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return privateKey;
    }


}
