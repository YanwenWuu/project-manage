package com.zjsh.common.utils;

import com.zjsh.common.exception.BusinessException;
import com.zjsh.common.response.BaseResp;

import java.util.function.Supplier;

import java.util.function.Supplier;

public abstract class BaseController {

    protected <T, R extends BaseResp<T>> R exec(
            Supplier<T> supplier,
            R resp) {

        try {

            T data = supplier.get();

            resp.setCode(0);
            resp.setMessage("success");
            resp.setData(data);

        } catch (BusinessException e) {

            resp.setCode(e.getCode());
            resp.setMessage(e.getMessage());

        } catch (Exception e) {

            resp.setCode(50000);
            resp.setMessage("系统异常");
        }

        return resp;
    }
}