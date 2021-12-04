package com.we3j.demo.wallet;

import com.we3j.demo.utils.Environment;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/4
 * *
 */
public class Web3jInfo {

    /**
     * 必须先连接节点才能进行后续线上操作
     *
     * ***/
    public static Web3j connect(){
        return Web3j.build(new HttpService(Environment.RPC_URL));// TODO: 2018/4/10 节点更改为自己的或者主网
    }

    /***
     * 根据本地节点连接web3
     * **/
    public static Web3j connect(String localPointUrl){
        return Web3j.build(new HttpService(localPointUrl));
    }
}
