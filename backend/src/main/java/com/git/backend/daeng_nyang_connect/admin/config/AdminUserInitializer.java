package com.git.backend.daeng_nyang_connect.admin.config;

import com.git.backend.daeng_nyang_connect.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {
    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        adminService.admin();
    }
}
