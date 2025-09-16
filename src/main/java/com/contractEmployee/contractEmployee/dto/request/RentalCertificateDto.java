// RentalCertificateDto.java
package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RentalCertificateDto {
    private Integer id;
    private String certificateNumber;
    private String rentalType;
    private String address;
    private String landlordName;
    private String landlordContact;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
