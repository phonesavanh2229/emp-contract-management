package com.contractEmployee.contractEmployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContractStatsDto {
    private long totalContracts;
    private long expiredContracts;
    private long expiringSoonContracts;
}
