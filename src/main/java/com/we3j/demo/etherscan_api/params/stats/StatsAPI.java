package com.we3j.demo.etherscan_api.params.stats;

import com.we3j.demo.etherscan_api.params.Sort;

/**
 * @Author jambestwick
 * @create 2021/12/7 0007  21:26
 * @email jambestwick@126.com
 * * {@link [stats-api] https://docs.etherscan.io/api-endpoints/stats-1}
 */
public interface StatsAPI {


    String getTotalSupplyEther(String api);

    String getTotalSupplyEther2(String api);

    String getEtherLastPrice(String api);

    String getEtherNodesSize(String api, String startDate, String endDate, Sort sort);

    String getTotalNodesCount(String api);

    String getDailyTxnFee(String api, String startDate, String endDate, Sort sort);

    String getDailyNewAddressCount(String api, String startDate, String endDate, Sort sort);

    String getDailyNetUtilization(String api, String startDate, String endDate, Sort sort);

    String getDailyAverageHashRate(String api, String startDate, String endDate, Sort sort);

    String getDailyTxCount(String api, String startDate, String endDate, Sort sort);

    String getDailyAverageNetDifficulty(String api, String startDate, String endDate, Sort sort);

    String getEtherDailyPrice(String api, String startDate, String endDate, Sort sort);


}
