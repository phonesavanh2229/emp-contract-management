package com.contractEmployee.contractEmployee.dto.response;

//import com.contractEmployee.contractEmployee.entity.Education;
import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import com.contractEmployee.contractEmployee.dto.request.VisaDto;
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

    private PassportDto passport;
    private List<VisaDto> visas;

}
