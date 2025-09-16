// CreateFullPassportRequest.java
package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateFullPassportRequest {
    private String passportNumber;
    private String placeOfIssue;
    private LocalDate issuedDate;
    private LocalDate expiryDate;

    private List<VisaDto> visas;
    private List<RentalCertificateDto> rentalCertificates;
}
