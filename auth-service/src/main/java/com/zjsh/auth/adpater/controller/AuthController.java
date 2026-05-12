package com.zjsh.auth.adpater.controller;


import com.zjsh.auth.application.service.AuthService;
import com.zjsh.auth.sdk.dto.AuthInfoDto;
import com.zjsh.auth.sdk.dto.request.UserInfoReq;
import com.zjsh.auth.sdk.dto.response.UserInfoResp;
import com.zjsh.common.response.BaseResp;
import com.zjsh.common.utils.BaseController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends BaseController {

    @Resource
    private AuthService authService;

    @GetMapping("/auth/user/get")
    public UserInfoResp getUser( UserInfoReq req) {

        return exec(
                () -> authService.getUserById(req),
                new UserInfoResp()
        );
    }
}

