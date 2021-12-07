package com.we3j.demo.etherscan_api.params.tokens;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [tokens-api] https://docs.etherscan.io/api-endpoints/tokens}
 * *
 */
public interface TokensAPI {

    String getTokenTotalSupplyByContractAddress(String api, String contractAddress);

    String getTokenAccountBalanceForTokenContractAddress(String api, String contractAddress, String address);

    String getHistoricalTokenTotalSupplyByContractAddressAndBlockNo(String api, String contractAddress, long blockNo);

    String getHistoricalTokenAccountBalanceByContractAddressAndBlockNo(String api, String contractAddress, long blockNo, String address);

    String getTokenInfoByContractAddress(String api, String contractAddress);


}
