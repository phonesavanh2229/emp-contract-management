package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.request.LoginRequest;
import com.contractEmployee.contractEmployee.dto.request.RegisterRequest;
import com.contractEmployee.contractEmployee.dto.response.AuthResponse;
import com.contractEmployee.contractEmployee.entity.User;
import com.contractEmployee.contractEmployee.rep.UserRepository;
import com.contractEmployee.contractEmployee.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepo;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam("token") String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @GetMapping("/me")
    public User me(@AuthenticationPrincipal UserDetails principal) {
        return userRepo.findByUsername(principal.getUsername()).orElseThrow();
    }

    // ✅ ADMIN-only endpoints
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> allUsers() {
        return userRepo.findAll();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public AuthResponse createUser(@Valid @RequestBody RegisterRequest req) {
        return authService.register(req);
    }
}
