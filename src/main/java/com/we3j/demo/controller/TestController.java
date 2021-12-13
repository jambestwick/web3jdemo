package com.we3j.demo.controller;

import com.we3j.demo.etherscan_api.key.ApiKey;
import com.we3j.demo.etherscan_api.params.accounts.AccountAPI;
import com.we3j.demo.etherscan_api.params.tokens.TokensAPI;
import com.we3j.demo.etherscan_api.params.transactions.TransactionsAPI;
import com.we3j.demo.etherscan_api.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/8
 * *
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    AccountAPI accountAPI;
    @Resource
    TransactionsAPI transactionsAPI;
    @Resource
    TokensAPI tokensAPI;


    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse batchUpdateLabelGPS() {
        try {
            ApiResponse result = accountAPI.getSingleAddressBalance("account", "balance", "0x661a60cdC8434611E65f51065EC246Bf0bA31EbF", "latest", ApiKey.KEY);
            System.out.println("res:" + result);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
