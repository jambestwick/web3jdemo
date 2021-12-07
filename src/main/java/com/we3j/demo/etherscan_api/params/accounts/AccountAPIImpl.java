package com.we3j.demo.etherscan_api.params.accounts;

import com.we3j.demo.etherscan_api.params.Sort;
import com.we3j.demo.utils.OKHttpUtil;

import java.util.List;

/**
 * @Author jambestwick
 * @create 2021/12/7 0007  23:20
 * @email jambestwick@126.com
 */
public class AccountAPIImpl implements AccountAPI {
    @Override
    public String getSingleAddressBalance(String api, String address) {
        return OKHttpUtil.builder().url(api)
                  // 有参数的话添加参数，可多个
                  .addParam("module", "account")
                  .addParam("action", "balance")
                  .addParam("address", address)
                  .addParam("tag", "latest")
                  .get()
                  .sync();
    }

    @Override
    public String getMultipleAddressBalance(String api, String... address) {
        return null;
    }

    @Override
    public String getMultipleAddressBalance(String api, List<String>... address) {
        return null;
    }

    @Override
    public String getListTransactionByAddress(String api, String address) {
        return null;
    }

    @Override
    public String getListNormalTransactionByAddress(String api, int records, String address, Sort sort) {
        return null;
    }

    @Override
    public String getListInternalTransactionByAddress(String api, int records, String address, Sort sort) {
        return null;
    }

    @Override
    public String getListInternalTransactionByHash(String api, String transHash) {
        return null;
    }

    @Override
    public String getListInternalTransactionByBlockRange(String api, long startBlock, long endBlock, Sort sort) {
        return null;
    }

    @Override
    public String getContractTransactionByAddress(String api, String contractAddress, String walletAddress, long startBlock, long endBlock, Sort sort) {
        return null;
    }

    @Override
    public String getNFTTransactionByAddress(String api, String contractAddress, String walletAddress, long startBlock, long endBlock, Sort sort) {
        return null;
    }

    @Override
    public String getListMinedByAddress(String api, String walletAddress) {
        return null;
    }

    @Override
    public String getHistoricalBalanceByBlockNoAddress(String api, long blockNo, String walletAddress) {
        return null;
    }
}
