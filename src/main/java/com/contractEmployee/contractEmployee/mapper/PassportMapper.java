package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import com.contractEmployee.contractEmployee.entity.Passport;

public class PassportMapper {
    public static PassportDto toDto(Passport passport) {
        if (passport == null) return null;

        PassportDto dto = new PassportDto();
        dto.setId(passport.getId());
        dto.setPassportNumber(passport.getPassportNo());
        dto.setPassportType(passport.getPassportType());
        dto.setCountry(passport.getCountryCode());
        dto.setIssuePlace(passport.getIssuePlace());
        dto.setStatus(passport.getStatus());
        dto.setIssueDate(passport.getIssueDate());
        dto.setExpiryDate(passport.getExpiryDate());
        return dto;
    }
}
