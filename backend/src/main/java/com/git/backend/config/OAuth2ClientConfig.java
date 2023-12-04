package com.git.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2ClientConfig {
    @Value("${security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${security.oauth2.client.registration.google.scope}")
    private String scope;

    @Value("${security.oauth2.client.registration.google.authorizationUri}")
    private String authorizationUri;

//    1. ClientRegistrationRepository :  클라이언트 등록 정보를 관리
        @Bean
        public ClientRegistrationRepository clientRegistrationRepository() {
            return new InMemoryClientRegistrationRepository(clientRegistration());
        }
    //    2. ClientRegistration : 클라이언트의 설정을 정의
        private ClientRegistration clientRegistration() {
            return ClientRegistration.withRegistrationId("google")
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(redirectUri)
                    .scope(scope)
                    .authorizationUri(authorizationUri)
                    .build();
        }
}

