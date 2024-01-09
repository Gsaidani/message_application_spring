package com.message.api.service;

import org.springframework.stereotype.Service;

import java.util.Map;
public interface AuthenticationService {
    Map<String,Object> authenticate(String grantType, String username, String password, boolean withRefreshToken, String refreshToken);

}
