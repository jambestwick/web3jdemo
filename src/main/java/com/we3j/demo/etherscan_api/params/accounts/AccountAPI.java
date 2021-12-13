package com.we3j.demo.etherscan_api.params.accounts;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.we3j.demo.etherscan_api.params.Sort;
import com.we3j.demo.etherscan_api.response.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [document-account] https://docs.etherscan.io/api-endpoints/accounts}
 * *
 */
@RetrofitClient(baseUrl = "${base.url}")
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
    @GET("api")
    ApiResponse getSingleAddressBalance(@Query("module") String api, @Query("action") String action, @Query("address") String address, @Query("tag") String tag, @Query("apikey") String apiKey);

    @GET("api")
    String getMultipleAddressBalance(@Query("module") String api, @Query("action") String action, @Query("address") String addresses, @Query("tag") String tag, @Query("apikey") String apiKey);


    /**
     * @return records maximum of 10000 records only
     */
    @GET("api")
    String getListNormalTransactionByAddress(@Query("module") String api, @Query("action") String action, @Query("address") String address, @Query("startblock") long startBlock, @Query("endblock") long endBlock, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    /**
     * @return records maximum of 10000 records only
     */
    @GET("api")
    String getListInternalTransactionByAddress(@Query("module") String api, @Query("action") String action, @Query("address") String address, @Query("startblock") long startBlock, @Query("endblock") long endBlock, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getListInternalTransactionByHash(@Query("module") String api, @Query("action") String action, @Query("txhash") String transHash, @Query("apikey") String apiKey);

    @GET("api")
    String getListInternalTransactionByBlockRange(@Query("module") String api, @Query("action") String action, @Query("startblock") long startBlock, @Query("endblock") long endBlock, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getContractTransactionByAddress(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("address") String walletAddress, @Query("startblock") long startBlock, @Query("endblock") long endBlock, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getNFTTransactionByAddress(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("address") String walletAddress, @Query("startblock") long startBlock, @Query("endblock") long endBlock, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getListMinedByAddress(@Query("module") String api, @Query("action") String action, @Query("address") String walletAddress, @Query("blocktype") String blockType, @Query("apikey") String apiKey);

    @GET("api")
    String getHistoricalBalanceByBlockNoAddress(@Query("module") String api, @Query("action") String action, @Query("blockno") long blockNo, @Query("address") String walletAddress, @Query("apikey") String apiKey);


}
