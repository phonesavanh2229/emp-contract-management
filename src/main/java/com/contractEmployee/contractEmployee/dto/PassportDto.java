// PassportDto.java
package com.contractEmployee.contractEmployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
    private Integer id;
    private String passportNumber;
    private String passportType;
    private String country;     // map -> country_code
    private String issuePlace;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;

    private List<RentalCertificateDto> rentalCertificates;

}
