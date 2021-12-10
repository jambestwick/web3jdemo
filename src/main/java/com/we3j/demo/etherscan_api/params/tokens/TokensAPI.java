package com.we3j.demo.etherscan_api.params.tokens;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.we3j.demo.etherscan_api.params.BaseAPI;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [tokens-api] https://docs.etherscan.io/api-endpoints/tokens}
 * *
 */
@RetrofitClient(baseUrl = "${base.url}")
public interface TokensAPI extends BaseAPI {

    @GET("api")
    String getTokenTotalSupplyByContractAddress(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("apikey") String apiKey);

    @GET("api")
    String getTokenAccountBalanceForTokenContractAddress(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("address") String address, @Query("apikey") String apiKey);

    @GET("api")
    String getHistoricalTokenTotalSupplyByContractAddressAndBlockNo(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("blockno") long blockNo, @Query("apikey") String apiKey);

    @GET("api")
    String getHistoricalTokenAccountBalanceByContractAddressAndBlockNo(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("address") String address, @Query("blockno") long blockNo, @Query("apikey") String apiKey);

    @GET("api")
    String getTokenInfoByContractAddress(@Query("module") String api, @Query("action") String action, @Query("contractaddress") String contractAddress, @Query("apikey") String apiKey);


}
