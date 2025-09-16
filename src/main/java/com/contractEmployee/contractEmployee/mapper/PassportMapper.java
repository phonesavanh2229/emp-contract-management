package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import com.contractEmployee.contractEmployee.dto.request.RentalCertificateDto;
import com.contractEmployee.contractEmployee.dto.request.VisaDto;
import com.contractEmployee.contractEmployee.entity.*;

import java.util.stream.Collectors;

public class PassportMapper {
    public static PassportDto toPassportDto(Passport p) {
        PassportDto dto = new PassportDto();
        dto.setId(p.getId());
        dto.setPassportNumber(p.getPassportNo());
        dto.setPassportType(p.getPassportType());
        dto.setCountry(p.getCountryCode());
        dto.setIssuePlace(p.getIssuePlace());
        dto.setIssueDate(p.getIssueDate());
        dto.setExpiryDate(p.getExpiryDate());
        dto.setStatus(p.getStatus());
        return dto;
    }
}
