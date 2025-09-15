package com.contractEmployee.contractEmployee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractRequest {
    @NotNull
    private Long employeeId;
    private String contractNumber;
    private String contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long previousContractId;


}