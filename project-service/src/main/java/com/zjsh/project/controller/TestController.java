package com.zjsh.project.controller;


import com.zjsh.auth.sdk.dto.AuthInfoDto;
import com.zjsh.auth.sdk.dto.request.UserInfoReq;
import com.zjsh.auth.sdk.feign.AuthServiceApi;
import com.zjsh.project.wrapper.AuthWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class TestController {

    @Resource
    private AuthWrapper authWrapper;

    @GetMapping("/project/test")
    public AuthInfoDto test() {
        UserInfoReq req = new UserInfoReq();
        req.setId(1L);

        return authWrapper.test();
    }
}