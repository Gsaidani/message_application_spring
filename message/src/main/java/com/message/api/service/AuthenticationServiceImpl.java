package com.message.api.service;

import com.message.api.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    public AuthenticationServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public Map<String,Object> authenticate(String grantType, String username, String password, boolean withRefreshToken, String refreshToken){
        String subject=null;
        String scope=null;
        if(grantType.equals("password")){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            subject=authentication.getName();
            scope=authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
        } else if (grantType.equals("refreshToken")) {
            Jwt decodeJWT = null;
            try {
                if(refreshToken==null){
                    throw new ApplicationRuntimeException(HttpStatus.UNAUTHORIZED.toString(), "Refresh Token is required");
                }
                decodeJWT = jwtDecoder.decode(refreshToken);
            } catch (JwtException e) {
                throw new ApplicationRuntimeException(HttpStatus.UNAUTHORIZED.toString(), e.getMessage());
            }
            subject=decodeJWT.getSubject();
            UserDetails userDetails= userDetailsService.loadUserByUsername(subject);
            Collection<? extends GrantedAuthority> authorities= userDetails.getAuthorities();
            scope=authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        }

        Map<String,Object> idToken= new HashMap<>();
        Instant instant=Instant.now();
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(withRefreshToken?1:2, ChronoUnit.MINUTES))
                .issuer("security-service")
                .claim("scope",scope)
                .build();
        String jwtAccesToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("accessToken",jwtAccesToken);
        if(withRefreshToken){
            JwtClaimsSet jwtClaimsSetRefresh=JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(2 , ChronoUnit.MINUTES))
                    .issuer("security-service")
                    .build();
            String jwtRefreshToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
            idToken.put("refreshToken",jwtRefreshToken);
        }
        return idToken;
    }

    //    public Map<String,Object> login(Authentication authentication) {
//        Map<String,Object> idToken= new HashMap<>();
//        Instant instant=Instant.now();
//        String scope= authentication.getAuthorities()
//                .stream().map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
//                .subject(authentication.getName())
//                .issuedAt(instant)
//                .expiresAt(instant.plus(5, ChronoUnit.MINUTES))
//                .issuer("security-service")
//                .claim("scope",scope)
//                .build();
//        String jwtAccesToken=jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
//        idToken.put("accessToken",jwtAccesToken);
//        return idToken;
//    }
}
