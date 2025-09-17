// VisaMapper.java
package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.VisaDto;
import com.contractEmployee.contractEmployee.entity.Visa;
import java.util.stream.Collectors;

public class VisaMapper {
    public static VisaDto toVisaDto(Visa v) {
        VisaDto dto = new VisaDto();
        dto.setId(v.getId());
        dto.setVisaNumber(v.getVisaNumber());
        dto.setVisaType(v.getVisaType());
        dto.setVisaPurpose(v.getVisaPurpose());
        dto.setCountry(v.getCountryCode());
        dto.setIssuePlace(v.getIssuePlace());
        dto.setIssueDate(v.getIssueDate());
        dto.setExpiryDate(v.getExpiryDate());
        dto.setEntries(v.getEntries());
        dto.setStatus(v.getStatus());
        return dto;
    }
}
