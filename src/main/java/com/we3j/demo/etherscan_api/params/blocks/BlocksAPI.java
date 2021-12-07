package com.we3j.demo.etherscan_api.params.blocks;

import com.we3j.demo.etherscan_api.params.Sort;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [blocks-api] https://docs.etherscan.io/api-endpoints/blocks}
 * *
 */
public interface BlocksAPI {
    String getBlockAndUncleRewardsByBlockNo(String api, long blockNo);

    String getEstimatedBlockCountdownTimeByBlockNo(String api, long blockNo);

    /**
     * @param timestamp
     * @param blockOrder before or after timestamp
     **/
    String getBlockNumberByTimestamp(String api, long timestamp, BlockOrder blockOrder);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    String getDailyAverageBlockSize(String api, String startDate, String endDate, Sort sort);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    String getDailyBlockCountAndRewards(String api, String startDate, String endDate, Sort sort);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    String getDailyBlockRewards(String api, String startDate, String endDate, Sort sort);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    String getDailyAverageBlockTime(String api, String startDate, String endDate, Sort sort);

    /**
     * @param startDate format eg.2019-02-01
     * @param endDate   format eg.2019-02-15
     **/
    String getDailyUncleBlockCountAndRewards(String api, String startDate, String endDate, Sort sort);


}
