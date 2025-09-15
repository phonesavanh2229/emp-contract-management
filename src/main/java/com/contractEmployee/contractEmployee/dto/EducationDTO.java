package com.contractEmployee.contractEmployee.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationDTO {
    private Long id;
    private String institute;
    private String degree;
    private String fieldOfStudy;
    private LocalDate startDate;
    private LocalDate endDate;

}