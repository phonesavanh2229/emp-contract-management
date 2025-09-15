package com.contractEmployee.contractEmployee.dto;

import lombok.Data;

import java.util.List;
@Data
public class CompositeEmployeeRequest {
    private EmployeeRequest employee;
    private ContractRequest contract;
    private List<EducationRequest> educations;
    private List<ContractFileRequest> contractFiles;
    private IdentityCardDto identityCard;
}
