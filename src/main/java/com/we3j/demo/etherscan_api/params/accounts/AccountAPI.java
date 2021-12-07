package com.we3j.demo.etherscan_api.params.accounts;

import com.we3j.demo.etherscan_api.params.Sort;

import java.util.List;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [document-account] https://docs.etherscan.io/api-endpoints/accounts}
 * *
 */
public interface AccountAPI {
    /**
     * eg.
     * "module=account\n" +
     * "   &action=balance\n" +
     * "   &address=0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae\n" +
     * "   &tag=latest\n" +
     * "   &apikey=YourApiKeyToken";
     */
    //public static final String Single_Address_Ether_Balance ;
    String getSingleAddressBalance(String api, String address);

    String getMultipleAddressBalance(String api, String... address);

    String getMultipleAddressBalance(String api, List<String>... address);

    String getListTransactionByAddress(String api, String address);

    /**
     * @param records maximum of 10000 records only
     */
    String getListNormalTransactionByAddress(String api, int records, String address, Sort sort);

    /**
     * @param records maximum of 10000 records only
     */
    String getListInternalTransactionByAddress(String api, int records, String address, Sort sort);


    String getListInternalTransactionByHash(String api, String transHash);

    String getListInternalTransactionByBlockRange(String api, long startBlock, long endBlock,Sort sort);

    String getContractTransactionByAddress(String api, String contractAddress, String walletAddress,long startBlock, long endBlock,Sort sort);

    String getNFTTransactionByAddress(String api, String contractAddress, String walletAddress,long startBlock, long endBlock,Sort sort);

    String getListMinedByAddress(String api, String walletAddress);

    String getHistoricalBalanceByBlockNoAddress(String api, long blockNo, String walletAddress);


}
