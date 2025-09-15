package com.contractEmployee.contractEmployee.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
public class ContractAmendmentRequest {
    private Long contractId;
    private LocalDate amendmentDate;
    private LocalDate effectiveDate;
    private String changeType;
    private String reason;
    private String changedBy;
    private String approvedBy;
    private String source;
    private String status;
}
