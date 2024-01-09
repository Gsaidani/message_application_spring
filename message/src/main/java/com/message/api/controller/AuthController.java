package com.message.api.controller;

import com.message.api.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public @ResponseBody Map<String, Object> login(String grantType, String username, String password, boolean withRefreshToken, String refreshToken) {

        return authenticationService.authenticate(grantType, username, password, withRefreshToken, refreshToken);

    }
}
