// ImmigrationRequest.java
package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class ImmigrationRequest {
    private Integer id;
    private String staffCode;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String province;
    private String village;

    private PassportDto passport;
    private List<VisaDto> visas;
    private List<RentalCertificateDto> rentalCertificates;
}
