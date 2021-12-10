package com.we3j.demo.etherscan_api.params.transactions;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import com.we3j.demo.etherscan_api.params.BaseAPI;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import javax.xml.transform.Result;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [transactions-api] https://docs.etherscan.io/api-endpoints/stats}
 * *
 */
@RetrofitClient(baseUrl = "${base.url}")
public interface TransactionsAPI extends BaseAPI {

    @GET("api")
    String checkContractExecutionStatus(@Query("module") String api, @Query("action") String action, @Query("txhash") String txHash, @Query("apikey") String apiKey);

    @GET("api")
    String checkTransactionReceiptStatus(@Query("module") String api, @Query("action") String action, @Query("txhash") String txHash, @Query("apikey") String apiKey);

    @GET("badge?page_id=jambestwick.jambestwick")
    Result test ();
}
