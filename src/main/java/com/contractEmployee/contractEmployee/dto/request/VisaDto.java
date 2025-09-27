// VisaDto.java
package com.contractEmployee.contractEmployee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisaDto {
    private Long id;
    private String visaNumber;
    private String visaType;
    private String visaPurpose;
    private String country;
    private String issuePlace;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Long entries;
    private String status;
    private List<RentalCertificateDto> rentalCertificates;

}
