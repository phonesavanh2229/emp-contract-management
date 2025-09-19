package com.contractEmployee.contractEmployee.dto.response;

import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.Visa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithVisaDto {
    private Integer id;
    private String staffCode;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String province;
    private String village;
    private List<Visa> visas;
}
