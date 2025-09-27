package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;

@Data
public class DepartmentBranchDto {
    private Long id;

    private DepartmentDto  department;
    private BranchDto branch;
}
