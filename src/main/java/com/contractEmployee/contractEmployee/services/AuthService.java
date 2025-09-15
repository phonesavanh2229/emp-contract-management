package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.*;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.Role;
import com.contractEmployee.contractEmployee.entity.User;
import com.contractEmployee.contractEmployee.exception.BadRequestException;
import com.contractEmployee.contractEmployee.rep.EmployeeRepository;
import com.contractEmployee.contractEmployee.rep.UserRepository;
import com.contractEmployee.contractEmployee.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwt;
    private final EmployeeRepository employeeRepo;
    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new BadRequestException("Username already taken");
        }

        // ✅ if no role provided, default to ROLE_USER
        Role role = req.getRole() != null ? req.getRole() : Role.ROLE_USER;

        // ✅ Find employee
        Employee employee = employeeRepo.findById(req.getEmployeeId())
                .orElseThrow(() -> new BadRequestException("Employee not found with ID: " + req.getEmployeeId()));

        // ✅ Build user
        var user = User.builder()
                .username(req.getUsername())
                .password(encoder.encode(req.getPassword()))
                .role(role)
                .employee(employee) // ✅ Add this line to associate employee
                .build();

        userRepo.save(user);

        String access = jwt.generateAccessToken(user.getUsername(), user.getRole().name());
        String refresh = jwt.generateRefreshToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .tokenType("Bearer")
                .expiresIn(3600)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }


    public AuthResponse login(LoginRequest req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }

        var user = userRepo.findByUsername(req.getUsername()).orElseThrow();
        String access = jwt.generateAccessToken(user.getUsername(), user.getRole().name());
        String refresh = jwt.generateRefreshToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .tokenType("Bearer")
                .expiresIn(3600)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse refresh(String refreshToken) {
        if (!jwt.validateRefreshToken(refreshToken)) {
            throw new BadRequestException("Invalid refresh token");
        }
        String username = jwt.extractUsernameFromRefresh(refreshToken);
        var user = userRepo.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        String newAccess = jwt.generateAccessToken(user.getUsername(), user.getRole().name());
        String newRefresh = jwt.generateRefreshToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(newAccess)
                .refreshToken(newRefresh)
                .tokenType("Bearer")
                .expiresIn(3600)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
