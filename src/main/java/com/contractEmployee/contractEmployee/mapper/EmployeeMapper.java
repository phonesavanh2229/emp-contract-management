// EmployeeMapper.java
package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee e) {
        if (e == null) return null;
        EmployeeDto dto = new EmployeeDto();
        dto.setId(e.getId());
        dto.setStaffCode(e.getStaffCode());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setGender(String.valueOf(e.getGender()));
        dto.setBirthday(e.getBirthday());
        dto.setPhone(e.getPhone());
        dto.setEmail(e.getEmail());
        dto.setProvince(e.getProvince());
        dto.setDistrict(e.getDistrict().toString());
        dto.setVillage(e.getVillage());
        dto.setStatus(String.valueOf(e.getStatus()));
        dto.setBranch(BranchMapper.toDto(e.getBranch()));
        dto.setDepartmentBranch(DepartmentBranchMapper.toDto(e.getDepartmentBranch()));
        return dto;
    }
}
