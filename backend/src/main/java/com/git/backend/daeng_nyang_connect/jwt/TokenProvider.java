package com.git.backend.daeng_nyang_connect.jwt;

import com.git.backend.daeng_nyang_connect.user.repository.CustomerUserDetails;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.openid.connect.sdk.claims.ClaimsSet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    @Value("${security.jwt.secret}")
    private String secretKey;
    private long accesstokenValidSecond = 500L * 60 * 60; //30분
    private long refreshtokenValidSecond = 1000L * 60 * 60; //1시간

    private final CustomerUserDetails customerUserDetails;

    @PostConstruct
    protected void init(){
        secretKey  = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private String getString(String email, long accesstokenValidSecond){
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date accessValidate = new Date(now.getTime() + accesstokenValidSecond);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(accessValidate)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }




}
