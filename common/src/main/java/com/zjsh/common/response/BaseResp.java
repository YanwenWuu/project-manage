package com.zjsh.common.response;

import lombok.Data;

@Data
public class BaseResp<T> {

    private Integer code;

    private String message;

    private T data;

    public void setSuccess(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    public void setFail(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}