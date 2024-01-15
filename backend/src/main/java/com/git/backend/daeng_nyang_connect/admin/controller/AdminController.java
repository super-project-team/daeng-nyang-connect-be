package com.git.backend.daeng_nyang_connect.admin.controller;

import com.git.backend.daeng_nyang_connect.admin.dto.UsersDto;
import com.git.backend.daeng_nyang_connect.admin.service.AdminService;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Slf4j
@Cacheable
public class AdminController {
    private final AdminService adminService;


}
