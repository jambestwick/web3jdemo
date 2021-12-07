package com.we3j.demo.etherscan_api.params.contracts;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * {@link [contract-api] https://docs.etherscan.io/api-endpoints/contracts}
 * *
 */
public interface ContractAPI {
    String getContractABI(String api, String contractAddress);

    String getContractSourceCode(String api, String contractAddress);

}
