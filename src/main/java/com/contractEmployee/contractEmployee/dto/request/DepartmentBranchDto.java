package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;

@Data
public class DepartmentBranchDto {
    private Integer id;

    private DepartmentDto department;
    private BranchDto branch;
}
