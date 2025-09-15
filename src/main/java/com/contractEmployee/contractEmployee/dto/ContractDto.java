package com.contractEmployee.contractEmployee.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractDto {
    private long id;
    private Long employeeId;
    private String contractNumber;
    private String contractType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long previousContractId;
}