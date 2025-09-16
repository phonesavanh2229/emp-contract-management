package com.contractEmployee.contractEmployee.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
