package com.we3j.demo.service.etherscan_api.params.stats;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.we3j.demo.service.etherscan_api.params.Sort;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author jambestwick
 * @create 2021/12/7 0007  21:26
 * @email jambestwick@126.com
 * * {@link [stats-api] https://docs.etherscan.io/api-endpoints/stats-1}
 */
@RetrofitClient(baseUrl = "${base.url}")
public interface StatsAPI {

    @GET("api")
    String getTotalSupplyEther(@Query("module") String api, @Query("action") String action, @Query("apikey") String apiKey);

    @GET("api")
    String getTotalSupplyEther2(@Query("module") String api, @Query("action") String action, @Query("apikey") String apiKey);

    @GET("api")
    String getEtherLastPrice(@Query("module") String api, @Query("action") String action, @Query("apikey") String apiKey);

    @GET("api")
    String getEtherNodesSize(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("clienttype") String clientType, @Query("syncmode") String syncMode, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getTotalNodesCount(@Query("module") String api, @Query("action") String action, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyTxnFee(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyNewAddressCount(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyNetUtilization(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyAverageHashRate(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyTxCount(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getDailyAverageNetDifficulty(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    @GET("api")
    String getEtherDailyPrice(@Query("module") String api, @Query("action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);


}
