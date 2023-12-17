package com.git.backend.daeng_nyang_connect.oauth.service;

import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.config.security.SecurityConfig;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import com.git.backend.daeng_nyang_connect.user.role.Role;
import com.git.backend.daeng_nyang_connect.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;




    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //기본 OAUTH2USERService 객체 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        //OAuth2UserService를 사용하여 OAuth2User 정보를 가져온다
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        //클라이언트에 등록한 ID(구글, 네이버 ,카카오) 와 사용자 이름 속성을 가져온다
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 사용하여 가져온 OAuth2User 정보로 OAuth2Attribute 객체를 만든다
        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        //OAuth2Attribute의 속성값들을 MAP으로 반환 받는다.
        Map<String, Object> userAttribute = oAuth2Attribute.converToMap();

        //사용자 email (또는 id) 정보를 가져온다
        String email = (String) userAttribute.get("email");
        //이매일로 가입된 회원인지 조회
        Optional<User> findUser = userRepository.findByEmail(email);

        if(findUser.isEmpty()) {
            //회원이 존재하지 않을 경우 exist 값을 false 로 넣어줌
            userAttribute.put("exits", false);
            User oauthUser = new User();
            oauthUser.setEmail(email);
            oauthUser.setPassword("fptmql12");
            oauthUser.setRole(Role.USER);
            userRepository.save(oauthUser);
            return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")),
                    userAttribute, "email");

        }
        userAttribute.put("exists", true);
        String accessToken = tokenProvider.createAccessToken(email);
        log.info(accessToken);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(findUser.get().getRole().toString())),
                userAttribute, "email");
        }


    }

