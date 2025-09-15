// ImmigrationResponse.java
package com.contractEmployee.contractEmployee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImmigrationResponse {
    private PassportDto passport;
    private List<VisaDto> visas;
    private List<RentalCertificateDto> rentalCertificates;
}
