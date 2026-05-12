package com.zjsh.auth.application.converter;

import com.zjsh.auth.application.command.AuthInfoCommand;
import com.zjsh.auth.domain.entity.AuthInfo;
import com.zjsh.auth.sdk.dto.AuthInfoDto;
import com.zjsh.auth.sdk.dto.request.UserInfoReq;

public class AuthConverter {

    public static AuthInfoDto toDto(AuthInfo authInfo) {
        AuthInfoDto dto = new AuthInfoDto();
        dto.setId(authInfo.getId());
        dto.setUsername(authInfo.getUsername());
        return dto;
    }

    public static AuthInfoCommand toCommand(UserInfoReq req) {
        return AuthInfoCommand.builder()
                .id(req.getId())
                .build();
    }
}
