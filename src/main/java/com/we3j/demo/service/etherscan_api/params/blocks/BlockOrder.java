package com.we3j.demo.service.etherscan_api.params.blocks;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * *
 */
public enum BlockOrder {
    BEFORE("before"),
    AFTER("after");
    private String key;

    BlockOrder(String key) {
        this.key = key;
    }
}
