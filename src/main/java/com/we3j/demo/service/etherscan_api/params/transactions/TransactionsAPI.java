package com.we3j.demo.service.etherscan_api.params.transactions;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [transactions-api] https://docs.etherscan.io/api-endpoints/stats}
 * *
 */
@RetrofitClient(baseUrl = "${base.url}")
public interface TransactionsAPI  {

    @GET("api")
    String checkContractExecutionStatus(@Query("module") String api, @Query("action") String action, @Query("txhash") String txHash, @Query("apikey") String apiKey);

    @GET("api")
    String checkTransactionReceiptStatus(@Query("module") String api, @Query("action") String action, @Query("txhash") String txHash, @Query("apikey") String apiKey);

}
