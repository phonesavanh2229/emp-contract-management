// ImmigrationRequest.java
package com.contractEmployee.contractEmployee.dto;

import lombok.Data;
import java.util.List;

@Data
public class ImmigrationRequest {
    private PassportDto passport;
    private List<VisaDto> visas;
    private List<RentalCertificateDto> rentalCertificates;
}
