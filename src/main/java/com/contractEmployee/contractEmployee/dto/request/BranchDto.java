package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;

@Data
public class BranchDto {
    private Long id;
    private String name;
    private Boolean mainBranch;

    private DistrictDto district;
}