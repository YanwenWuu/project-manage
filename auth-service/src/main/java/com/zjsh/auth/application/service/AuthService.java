package com.zjsh.auth.application.service;

import com.zjsh.auth.sdk.dto.AuthInfoDto;
import com.zjsh.auth.sdk.dto.request.UserInfoReq;
import com.zjsh.auth.sdk.dto.response.UserInfoResp;
import com.zjsh.common.response.BaseResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthService {
    @GetMapping("/auth/user/get")
    AuthInfoDto getUserById(@RequestParam("id") UserInfoReq req);
}

