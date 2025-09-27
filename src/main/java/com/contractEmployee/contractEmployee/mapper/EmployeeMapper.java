package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.entity.Branch;
import com.contractEmployee.contractEmployee.entity.DepartmentBranch;
import com.contractEmployee.contractEmployee.entity.District;
import com.contractEmployee.contractEmployee.entity.Employee;
import org.springframework.web.bind.annotation.Mapping;

public class EmployeeMapper {

    public static EmployeeDto toDto(Employee e) {
        if (e == null) return null;

        EmployeeDto dto = new EmployeeDto();
        dto.setId(e.getId());
        dto.setStaffCode(e.getStaffCode());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setGender(e.getGender());
        dto.setBirthday(e.getBirthday());
        dto.setPhone(e.getPhone());
        dto.setPhoneTwo(e.getPhoneTwo());
        dto.setEmail(e.getEmail());
        dto.setStartWorking(e.getStartWorking());
        dto.setStatus(e.getStatus() != null ? e.getStatus().name() : null);
        dto.setProvince(e.getProvince());
        dto.setVillage(e.getVillage());
        dto.setDistrict(e.getDistircts());
        // ✅ map District -> only set needed values (avoid infinite recursion)
//        District district = e.getDistrict();
//        if (district != null) {
//            dto.setDistrict(district.getDistrictName());
//        }

        // ✅ map Branch -> only set needed values
        Branch branch = e.getBranch();
        if (branch != null) {
            dto.setBranch(branch.getName());
        }

        DepartmentBranch departmentBranch = e.getDepartmentBranch();
        if (departmentBranch != null) {
            DepartmentBranchDto deptDto = new DepartmentBranchDto();
            deptDto.setId(departmentBranch.getId());

            // map Department -> DepartmentDto
            if (departmentBranch.getDepartment() != null) {
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setId(departmentBranch.getDepartment().getId());
                departmentDto.setDepartmentName(departmentBranch.getDepartment().getDepartmentName());
                departmentDto.setLocation(departmentBranch.getDepartment().getLocation());
                deptDto.setDepartment(departmentDto);
            }

            // map Branch -> BranchDto
            if (departmentBranch.getBranch() != null) {
                BranchDto branchDto = new BranchDto();
                branchDto.setId(departmentBranch.getBranch().getId());
                branchDto.setName(departmentBranch.getBranch().getName());
                branchDto.setMainBranch(departmentBranch.getBranch().isMainBranch());
                deptDto.setBranch(branchDto);
            }

            dto.setDepartmentBranch(deptDto);
        }

        return dto;
    }


}
