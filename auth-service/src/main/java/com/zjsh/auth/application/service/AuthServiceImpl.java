package com.zjsh.auth.application.service;

import com.zjsh.auth.application.UserApp;
import com.zjsh.auth.application.command.AuthInfoCommand;
import com.zjsh.auth.application.converter.AuthConverter;
import com.zjsh.auth.sdk.dto.AuthInfoDto;
import com.zjsh.auth.sdk.dto.request.UserInfoReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserApp userApp;

    @Override
    public AuthInfoDto getUserById(UserInfoReq req) {
        AuthInfoCommand command = AuthConverter.toCommand(req);
        //getAuthInfo
        //todo: 在这调repository查数据
        AuthInfoDto authInfoDto = new AuthInfoDto();
        authInfoDto.setId(command.getId());
        authInfoDto.setUsername("test111");
        return authInfoDto;
    }
}
