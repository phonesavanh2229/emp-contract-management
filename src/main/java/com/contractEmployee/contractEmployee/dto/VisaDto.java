// VisaDto.java
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
public class VisaDto {
    private Integer id;
    private String visaNumber;
    private String visaType;
    private String visaPurpose;
    private String country;     // map -> country_code
    private String issuePlace;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Integer entries;
    private String status;
//    private List<VisaDto> visas = new ArrayList<>();

}
