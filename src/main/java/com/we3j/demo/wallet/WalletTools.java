package com.we3j.demo.wallet;

import com.we3j.demo.utils.FileUtil;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/4
 * *
 */
public class WalletTools {

    public static boolean createWallet(String filePath, String destFile) throws CipherException, IOException {
        FileUtil.createOrExistsDir(new File(filePath));
        Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet("", new File(filePath));//助记词钱包生成
        bip39Wallet.getMnemonic();
        System.out.println("生成助记词:" + bip39Wallet.getMnemonic());
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
}
