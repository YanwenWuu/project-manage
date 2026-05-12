package com.zjsh.common.exception;

import lombok.Getter;

@Getter
public enum BusinessExceptionEnum {

    // 通用异常 10000 开头
    SYSTEM_ERROR(10000, "系统内部异常"),
    PARAM_ERROR(10001, "请求参数非法"),
    DATA_NOT_EXIST(10002, "数据不存在"),
    REQUEST_TIMEOUT(10003, "请求超时"),

    // 权限认证类 20000 开头
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_EXPIRE(20002, "登录已失效，请重新登录"),
    NO_PERMISSION(20003, "暂无操作权限"),

    // 远程调用/微服务调用 30000 开头
    AUTH_SERVICE_CALL_FAIL(30001, "认证服务调用失败"),
    EXTERNAL_DB_SERVICE_CALL_FAIL(30002, "第三方数据服务调用失败");


    /** 错误码 */
    private final Integer code;
    /** 错误信息 */
    private final String msg;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}