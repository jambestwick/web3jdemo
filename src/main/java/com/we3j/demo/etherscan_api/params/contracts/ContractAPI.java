package com.we3j.demo.etherscan_api.params.contracts;

import com.we3j.demo.etherscan_api.params.BaseAPI;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [contract-api] https://docs.etherscan.io/api-endpoints/contracts}
 * *
 */
public interface ContractAPI extends BaseAPI {
    @GET("api")
    String getContractABI(@Query("module") String api, @Query("action") String action, @Query("address") String contractAddress, @Query("apikey") String apiKey);

    @GET("api")
    String getContractSourceCode(@Query("module") String api, @Query("action") String action, @Query("address") String contractAddress, @Query("apikey") String apiKey);

}
