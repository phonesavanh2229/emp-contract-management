package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.DepartmentBranchDto;
import com.contractEmployee.contractEmployee.entity.DepartmentBranch;

public class DepartmentBranchMapper {

    public static DepartmentBranchDto toDto(DepartmentBranch entity) {
        if (entity == null) return null;

        DepartmentBranchDto dto = new DepartmentBranchDto();
        dto.setId(entity.getId());

        // map department
        if (entity.getDepartment() != null) {
            dto.setDepartment(DepartmentMapper.toDto(entity.getDepartment()));
        }

        // map branch
        if (entity.getBranch() != null) {
            dto.setBranch(BranchMapper.toDto(entity.getBranch()));
        }

        return dto;
    }
}
