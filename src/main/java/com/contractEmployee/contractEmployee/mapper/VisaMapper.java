package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.VisaDto;
import com.contractEmployee.contractEmployee.entity.Visa;

public class VisaMapper {
    public static VisaDto toDto(Visa visa) {
        if (visa == null) return null;

        VisaDto dto = new VisaDto();
        dto.setId(visa.getId());
        dto.setVisaNumber(visa.getVisaNumber());
        dto.setVisaType(visa.getVisaType());
        dto.setVisaPurpose(visa.getVisaPurpose());
        dto.setCountry(visa.getCountryCode());
        dto.setIssuePlace(visa.getIssuePlace());
        dto.setEntries(visa.getEntries());
        dto.setStatus(visa.getStatus());
        dto.setIssueDate(visa.getIssueDate());
        dto.setExpiryDate(visa.getExpiryDate());
        return dto;
    }
}
