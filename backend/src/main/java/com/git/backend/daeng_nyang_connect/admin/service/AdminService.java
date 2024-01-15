package com.git.backend.daeng_nyang_connect.admin.service;

import com.git.backend.daeng_nyang_connect.admin.dto.UsersDto;
import com.git.backend.daeng_nyang_connect.user.entity.User;
import com.git.backend.daeng_nyang_connect.user.repository.UserRepository;
import com.git.backend.daeng_nyang_connect.user.role.Role;
import lombok.RequiredArgsConstructor;
//import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${adminEmail}")
    private String adminEmail;

    @Value("${adminPassword}")
    private String adminPassword;

    public void admin(){
        if (!userRepository.existsByEmail(adminEmail)) {
            User adminUser = User.builder()
                    .name("관리자")
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .nickname("관리자")
                    .gender('X')
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(adminUser);
        }
    }


}
