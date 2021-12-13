package com.we3j.demo.service.etherscan_api.params;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/7
 * *
 */
public enum Sort {
    ASC("asc"),
    DESC("desc");
    private String sort;

    Sort(String sort) {
        this.sort = sort;
    }
}
