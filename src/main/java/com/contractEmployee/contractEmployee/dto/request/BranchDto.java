package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;

@Data
public class BranchDto {
    private Integer id;
    private String name;
    private Boolean mainBranch;

    private DistrictDto district; // district ที่ branch นี้อยู่
}