package com.contractEmployee.contractEmployee.dto;

import com.contractEmployee.contractEmployee.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must contain only English letters and numbers")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Password must contain only English letters and numbers")
    private String password;

    private Role role; // Optional: default to ROLE_USER in service
}
