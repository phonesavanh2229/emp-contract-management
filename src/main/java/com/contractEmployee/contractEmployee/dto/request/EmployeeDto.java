package com.contractEmployee.contractEmployee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String staffCode;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthday;
    private String phone;
    private String phoneTwo;
    private String email;
    private LocalDate startWorking;
    private String status;
    private String province;
    private String district;
    private String village;

    private BranchDto branch;
    private DepartmentBranchDto departmentBranch;

    // immigration fields
    private List<PassportDto> passports;

//    private PassportDto passport;
    private List<VisaDto> visas;

}
