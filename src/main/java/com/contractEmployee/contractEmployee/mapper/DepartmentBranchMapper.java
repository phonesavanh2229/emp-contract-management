package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.DepartmentBranchDto;
import com.contractEmployee.contractEmployee.dto.request.DepartmentDto;
import com.contractEmployee.contractEmployee.entity.DepartmentBranch;

public class DepartmentBranchMapper {

    public static DepartmentBranchDto toDto(DepartmentBranch entity) {
        if (entity == null) return null;

        DepartmentBranchDto dto = new DepartmentBranchDto();
        dto.setId(entity.getId());

        // map department -> DepartmentDto
        if (entity.getDepartment() != null) {
            DepartmentDto deptDto = new DepartmentDto();
            deptDto.setId(entity.getDepartment().getId());
            deptDto.setDepartmentName(entity.getDepartment().getDepartmentName());
            deptDto.setLocation(entity.getDepartment().getLocation());
            dto.setDepartment(deptDto);
        }

        // map branch -> BranchDto
        if (entity.getBranch() != null) {
            dto.setBranch(BranchMapper.toDto(entity.getBranch()));
        }

        return dto;
    }
}

