package com.contractEmployee.contractEmployee.dto;

import com.contractEmployee.contractEmployee.entity.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EmployeeRequest {
    private String staffCode;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;
    private String phone;
    private String phoneTwo;
    private String email;
    private LocalDate startWorking;
    private String status;
    private String province;
    private String district;
    private String village;
}
