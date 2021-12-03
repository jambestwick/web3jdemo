package com.we3j.demo.bean.bo;

import com.we3j.demo.bean.Account;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/3
 * *
 */
public class ReceiveAccount extends Account implements Serializable {
    private BigDecimal sendAmount;

    public BigDecimal getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(BigDecimal sendAmount) {
        this.sendAmount = sendAmount;
    }
}
