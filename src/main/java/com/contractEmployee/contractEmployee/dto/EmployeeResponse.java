package com.contractEmployee.contractEmployee.dto;

//import com.contractEmployee.contractEmployee.entity.Education;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.*;
//import com.contractEmployee.contractEmployee.entity.IdentityCard;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private Long employeeId;
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
//    private List<ContractDto> contracts;
//    private List<EducationDTO> educations;
//
//    private IdentityCardDto identityCard;


}
