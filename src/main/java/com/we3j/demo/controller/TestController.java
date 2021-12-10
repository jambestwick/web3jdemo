package com.we3j.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.we3j.demo.etherscan_api.key.ApiKey;
import com.we3j.demo.etherscan_api.params.BaseAPI;
import com.we3j.demo.etherscan_api.params.accounts.AccountAPI;
import com.we3j.demo.etherscan_api.params.transactions.TransactionsAPI;
import com.we3j.demo.etherscan_api.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/8
 * *
 */
@RestController
@RequestMapping("/test")
public class TestController {
    //    @Resource
//    AccountAPI accountAPI;
    @Resource
    TransactionsAPI transactionsAPI;


    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse batchUpdateLabelGPS() {
        try {
            //String result = accountAPI.getTest();

            for (int i = 0; i < 9999; i++) {
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {

                        for (int j = 0; j < 10000; j++) {
                            try {
                                transactionsAPI.test();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }


//
//            ApiResponse result = accountAPI.getSingleAddressBalance("account", "balance", "0x661a60cdC8434611E65f51065EC246Bf0bA31EbF", "latest", ApiKey.KEY);
//            System.out.println("res:" + result);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
