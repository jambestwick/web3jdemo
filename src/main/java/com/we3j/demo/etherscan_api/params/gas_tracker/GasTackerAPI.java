package com.we3j.demo.etherscan_api.params.gas_tracker;

import com.we3j.demo.etherscan_api.params.Sort;

/**
 * @Author jambestwick
 * @create 2021/12/7 0007  21:04
 * @email jambestwick@126.com
 *  * {@link [gas-api] https://docs.etherscan.io/api-endpoints/gas-tracker}
 */
public interface GasTackerAPI {

    String getEstimationConfirmationTime(String api, long gasPrice);//The result is returned in seconds.


    String getGasOracle(String api);

    String getDailyAverageGasLimit(String api,String startDate, String endDate, Sort sort);

    String getDailyTotalGasUsed(String api,String startDate, String endDate, Sort sort);

    String getDailyAverageGasPrice(String api,String startDate, String endDate, Sort sort);

}
