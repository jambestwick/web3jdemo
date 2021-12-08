package com.we3j.demo.etherscan_api.params.gas_tracker;

import com.we3j.demo.etherscan_api.params.BaseAPI;
import com.we3j.demo.etherscan_api.params.Sort;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author jambestwick
 * @create 2021/12/7 0007  21:04
 * @email jambestwick@126.com
 * * {@link [gas-api] https://docs.etherscan.io/api-endpoints/gas-tracker}
 */
public interface GasTackerAPI extends BaseAPI {
    @GET("api")
    String getEstimationConfirmationTime(@Query("module") String api, @Query("action") String action, @Query("gasprice") long gasPrice, @Query("apikey") String apiKey);//The result is returned in seconds.

    @GET("api")
    String getGasOracle(@Query("module") String api, @Query("action") String action, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyAverageGasLimit(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyTotalGasUsed(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyAverageGasPrice(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

}
