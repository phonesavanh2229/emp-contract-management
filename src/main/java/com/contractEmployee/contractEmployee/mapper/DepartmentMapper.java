package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.DepartmentDto;
import com.contractEmployee.contractEmployee.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto toDto(Department entity) {
        if (entity == null) return null;

        DepartmentDto dto = new DepartmentDto();
        dto.setId(entity.getId());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setLocation(entity.getLocation());

        return dto;
    }
}
