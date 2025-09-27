package com.contractEmployee.contractEmployee.dto.request;

import com.contractEmployee.contractEmployee.entity.Gender;
import lombok.AllArgsConstructor;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;
    private String staffCode;
    private String firstName;
    private String lastName;
    private Gender  gender;  //fix
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
    @Builder.Default
    private List<PassportDto> passports = new ArrayList<>();

//    private PassportDto passport;
//    @Builder.Default
//    private List<VisaDto> visas = new ArrayList<>();

}
