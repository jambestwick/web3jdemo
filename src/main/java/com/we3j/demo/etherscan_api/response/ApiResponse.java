package com.we3j.demo.etherscan_api.response;

import java.io.Serializable;

/**
 * * Created by jambestwick@126.com
 * * on 2021/12/8
 * *
 */
public class ApiResponse<T> implements Serializable {
    private String status;
    private String message;
    private T result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
