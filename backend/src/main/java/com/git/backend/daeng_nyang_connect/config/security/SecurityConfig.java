package com.git.backend.daeng_nyang_connect.config.security;


import com.git.backend.daeng_nyang_connect.config.jwt.TokenProvider;
import com.git.backend.daeng_nyang_connect.filter.JwtAuthenticationFilter;
import com.git.backend.daeng_nyang_connect.oauth.handler.MyAuthenticationSuccessHandler;
import com.git.backend.daeng_nyang_connect.oauth.service.CustomOAuth2UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
      //          .headers((headerConfig -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)))
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/", "/css/**", "/images/**", "/js/**","/index", "/resources/static/**").permitAll()
                                .requestMatchers("/api", "/api/signup", "api/IdCheck","api/NicknameCheck","/api/login","/api/logout", "api/findPassword","api/findId","api/myPage",
                                        "api/tips/search","api/tips/getBoard","api/tips/getAll",
                                        "api/animal/all/**", "api/animal/kind/**", "api/animal/city/**", "api/animal/adoptionStatus/**",
                                        "api/review/all", "api/review", "api/review/comment", "api/lost/getAll", "api/lost/one",
                                        "api/my_pet/getAll","/api/mate/**", "/api/my_pet/**","/api/mate/all", "/api/my_pet/all", "/login/oauth2/**").permitAll()
                                .requestMatchers("/api/tips/**", "api/myPage/**", "api/animal/**", "api/review/**", "api/review/comment/**","api/lost/**").hasRole("USER")
                                .requestMatchers( "/api/mate/my_board", "/api/my_pet/my_board").authenticated()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> {
                    oauth2
                            .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                            .successHandler(myAuthenticationSuccessHandler);

                })

                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:3001","http://localhost:8080"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}