package com.we3j.demo.etherscan_api.params.blocks;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.we3j.demo.etherscan_api.params.BaseAPI;
import com.we3j.demo.etherscan_api.params.Sort;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [blocks-api] https://docs.etherscan.io/api-endpoints/blocks}
 * *
 */
@RetrofitClient(baseUrl = "${base.url}")
public interface BlocksAPI extends BaseAPI {
    @GET("api")
    String getBlockAndUncleRewardsByBlockNo(@Query("module") String api, @Query(value = "action") String action, @Query("blockno") long blockNo, @Query("apikey") String apiKey);

    @GET("api")
    String getEstimatedBlockCountdownTimeByBlockNo(@Query("module") String api, @Query(value = "action") String action, @Query("blockno") long blockNo, @Query("apikey") String apiKey);

    /**
     * @param timestamp blockOrder before or after timestamp
     **/
    @GET("api")
    String getBlockNumberByTimestamp(@Query("module") String api, @Query(value = "action") String action, @Query("timestamp") long timestamp, @Query("closest") BlockOrder closest, @Query("apikey") String apiKey);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    @GET("api")
    String getDailyAverageBlockSize(@Query("module") String api, @Query(value = "action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    @GET("api")
    String getDailyBlockCountAndRewards(@Query("module") String api, @Query(value = "action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    @GET("api")
    String getDailyBlockRewards(@Query("module") String api, @Query(value = "action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    @GET("api")
    String getDailyAverageBlockTime(@Query("module") String api, @Query(value = "action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    @GET("api")
    String getDailyUncleBlockCountAndRewards(@Query("module") String api, @Query(value = "action") String action, @Query("startdate") String startDate, @Query("enddate") String endDate, @Query("sort") Sort sort, @Query("apikey") String apiKey);


}
