package com.zjsh.auth.application;

import com.zjsh.auth.domain.entity.AuthInfo;
import org.springframework.stereotype.Service;

@Service
public class UserApp {


    public AuthInfo getAuthInfo(Long userId) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.setId(userId);
        authInfo.setUsername("admin");
        return authInfo;

    }
}
