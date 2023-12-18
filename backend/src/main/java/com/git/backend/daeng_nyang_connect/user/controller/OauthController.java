package com.git.backend.daeng_nyang_connect.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.config.security.SecurityConfig;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import com.git.backend.daeng_nyang_connect.user.role.Role;
import com.git.backend.daeng_nyang_connect.user.service.UserService;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OauthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Value("${naverIdEc2}")
    private String client_id;

    @Value("${naverSecretEc2}")
    private String client_secret;

    @RequestMapping("/naver_redirect")
    public ResponseEntity<?> naver_redirect(HttpServletRequest request, HttpServletResponse response) {
        // 네이버에서 전달해준 code, state 값 가져오기
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 세션에 저장해둔 state값 가져오기
        String session_state = String.valueOf(request.getSession().getAttribute("state"));


        String tokenURL = "https://nid.naver.com/oauth2.0/token";

        // body data 생성
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("grant_type", "authorization_code");
        parameter.add("client_id", client_id);
        parameter.add("client_secret", client_secret);
        parameter.add("code", code);
        parameter.add("state", state);

        // request header 설정
        HttpHeaders headers = new HttpHeaders();
        // Content-type을 application/x-www-form-urlencoded 로 설정
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // header 와 body로 Request 생성
        HttpEntity<?> entity = new HttpEntity<>(parameter, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            // 응답 데이터(json)를 Map 으로 받을 수 있도록 관련 메시지 컨버터 추가
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Post 방식으로 Http 요청
            // 응답 데이터 형식은 Hashmap 으로 지정
            ResponseEntity<HashMap> result = restTemplate.postForEntity(tokenURL, entity, HashMap.class);
            Map<String, String> resMap = result.getBody();
            log.info("resMap" + resMap);

            // 리턴받은 access_token 가져오기
            String access_token = resMap.get("access_token");

            String userInfoURL = "https://openapi.naver.com/v1/nid/me";
            // Header에 access_token 삽입
            headers.set("Authorization", "Bearer " + access_token);

            // Request entity 생성
            HttpEntity<?> userInfoEntity = new HttpEntity<>(headers);

            // Post 방식으로 Http 요청
            // 응답 데이터 형식은 Hashmap 으로 지정
            ResponseEntity<HashMap> userResult = restTemplate.postForEntity(userInfoURL, userInfoEntity, HashMap.class);
            Map<String, String> userResultMap = userResult.getBody();

            HashMap<String, Object> responseMap = (HashMap<String, Object>) userResult.getBody().get("response");
            String email = (String) responseMap.get("email");
            String name = (String) responseMap.get("name");
            String nickname = (String) responseMap.get("nickname");
            String mobile = (String) responseMap.get("mobile");

            Optional<User> byEmail = userRepository.findByEmail(email);

            if (byEmail.isEmpty()) {
                User naverUser = new User();
                naverUser.setEmail(email);
                naverUser.setRole(Role.USER);
                naverUser.setName(name);
                naverUser.setNickname(nickname);
                naverUser.setMobile(mobile);
                userRepository.save(naverUser);
                return userService.socialLogin(naverUser.getEmail(), naverUser.getPassword(), response);
            } else {
                User user = byEmail.get();
                return userService.socialLogin(user.getEmail(), user.getPassword(), response);
            }
        } catch (RestClientException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("로그인 실패");
    }

}

