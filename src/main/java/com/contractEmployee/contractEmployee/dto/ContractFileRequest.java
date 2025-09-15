package com.contractEmployee.contractEmployee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContractFileRequest {
    @NotNull
    private Long contractId;
    private Long employeeId;
    private String fileName;
    private String filePath;
    private String fileType;
}