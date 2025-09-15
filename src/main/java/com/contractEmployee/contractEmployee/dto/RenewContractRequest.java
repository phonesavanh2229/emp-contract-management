package com.contractEmployee.contractEmployee.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RenewContractRequest {
    private Long employeeId;
    private ContractRequest newContract;
    private ContractAmendmentRequest amendment;
    private List<ContractFileRequest> contractFiles;
    private Long previousContractId;
}
