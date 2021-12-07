package com.we3j.demo.etherscan_api.params.transactions;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [transactions-api] https://docs.etherscan.io/api-endpoints/stats}
 * *
 */
public interface TransactionsAPI {
    String checkContractExecutionStatus(String api, String contractAddress);

    String checkTransactionReceiptStatus(String api, String txHash);

}
