package com.we3j.demo.utils;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/3
 * 账号管理
 * 对账号的锁定与解锁
 * 保证安全
 * *
 */
public class AccountUtil {

    /*******需要账户交易时才解锁账号*********/
    public static boolean unlockAccount(String address, String passphrase) throws ExecutionException, InterruptedException {
        Admin admin = Admin.build(new HttpService(Environment.RPC_URL));  // defaults to http://localhost:8545/
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address, passphrase).sendAsync().get();
        return personalUnlockAccount.accountUnlocked();
    }

    /********************锁定账号后无法进行交易，保证安全***************************/
    public static boolean lockAccount(String address) throws IOException {
        Geth geth = Geth.build(new HttpService(Environment.RPC_URL));
        BooleanResponse result = geth.personalLockAccount(address).send();
        return result.success();
    }
}
