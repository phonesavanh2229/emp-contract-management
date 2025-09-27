package com.contractEmployee.contractEmployee.dto.request;

import com.contractEmployee.contractEmployee.entity.Gender;

import java.time.LocalDate;
import java.util.List;

public class EmployeePassportDto {
    private Long id;
    private String staffCode;
    private String firstName;
    private String lastName;
    private Gender gender;  //fix
    private LocalDate birthday;
    private String phone;
    private String phoneTwo;
    private String email;
    private LocalDate startWorking;
    private String status;
    private String province;
    private String  district;
    private String village;

    private String branch;
    private DepartmentBranchDto departmentBranch;

    // immigration fields
    private List<PassportDto> passports;
}
