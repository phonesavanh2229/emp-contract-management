package com.contractEmployee.contractEmployee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationRequest {
    @NotNull
    private Long employeeId;
    private String institute;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;
}
