package com.git.backend.daeng_nyang_connect.oauth.service;

import com.git.backend.daeng_nyang_connect.user.entity.MyPage;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.MyPageRepository;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import com.git.backend.daeng_nyang_connect.user.role.Role;
import com.git.backend.daeng_nyang_connect.user.service.UserService;
import jakarta.persistence.Cacheable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Cacheable
public class OAuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final MyPageRepository myPageRepository;

    @Value("${naverIdEc2}")
    private String client_id;

    @Value("${naverSecretEc2}")
    private String client_secret;

    public ResponseEntity<?> naverLogin(HttpServletRequest request, HttpServletResponse response){
        // 네이버에서 전달해준 code, state 값 가져오기
        String code = request.getParameter("code");
        String state = request.getParameter("state");

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
                MyPage myPage = userService.myPageEntity(naverUser);
                myPageRepository.save(myPage);
                userService.socialLogin(naverUser.getEmail(),request,response);
                String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/api/tips/getAll").build().encode().toUriString();
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                redirectStrategy.sendRedirect(request, response, targetUrl);
                return ResponseEntity.ok(response);


            } else {
                User user = byEmail.get();
                userService.socialLogin(user.getEmail(),request,response);
                String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/api/tips/getAll").build().encode().toUriString();
                RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
                redirectStrategy.sendRedirect(request, response, targetUrl);
                return ResponseEntity.ok(response);
            }
        } catch (RestClientException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> rs = new HashMap<>();
        rs.put("message", "알 수 없는 오류가 발생했습니다");
        rs.put("http_status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rs);
    }





}

