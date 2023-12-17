package com.git.backend.daeng_nyang_connect.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private ObjectMapper object;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        // 사용자 이메일을 가져온다
        String email = oAuth2User.getAttribute("email");
        // 프로바이더를 가져옴
        String provider = oAuth2User.getAttribute("provider");

        // CustomOAuth2userService에서 셋팅한 로그인한 회원 존재 여부를 가져온다.
        boolean isExist = oAuth2User.getAttribute("exist");
        // Role 얻어오기
        String role = oAuth2User.getAuthorities().stream().findFirst()
                .orElseThrow(IllegalAccessError::new) // 존재하지 않을 시 예외를 던진다.
                .getAuthority(); // ROLE 가져옴

        // 회원이 있는 경우
        if (isExist) {
            // 회원이 존재하면 jwt 토큰 발행
            String accessToken = tokenProvider.createAccessToken(email);
            String refreshToken = tokenProvider.createRefreshToken(email);
            Map<String, String> rs = new HashMap<>();
//            rs.put("message", "로그인 되었습니다");
//            rs.put("access_token", accessToken);
//            rs.put("refresh_token", refreshToken);
//            rs.put("http_status", HttpStatus.OK.toString());
//            log.info("access_token : " + accessToken);

            // 이 부분에서 토큰을 반환하도록 처리
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().write(object.writeValueAsString(rs));

            response.setHeader("access_token", accessToken);
            log.info("response" + response.getHeader(accessToken));
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/api/tips/getAll")
                    .queryParam("access_token", accessToken)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }else{
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/register")
                    .queryParam("email", (String) oAuth2User.getAttribute("email"))
                    .queryParam("provider", provider)
                    .build()
                    .encode(StandardCharsets.UTF_8)
                    .toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }
}
