package com.zjsh.auth.domain.entity;

import lombok.Data;

@Data
public class AuthInfo {
    private Long id;
    private String username;
    private String password;
}
