package com.we3j.demo.etherscan_api.params.logs;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [log-api] https://docs.etherscan.io/api-endpoints/logs}
 * *
 */
public interface logAPI {


    String getSampleLog(String api, long fromBlock, long toBlock, String topic, String address);

}
