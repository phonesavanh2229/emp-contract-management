package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.RentalCertificateDto;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;

public class RentalCertificateMapper {
    public static RentalCertificateDto toDto(RentalCertificate rc) {
        if (rc == null) return null;

        RentalCertificateDto dto = new RentalCertificateDto();
        dto.setId(rc.getId());
        dto.setCertificateNumber(rc.getCertificateNumber());
        dto.setRentalType(rc.getRentalType());
        dto.setAddress(rc.getAddress());
        dto.setLandlordName(rc.getLandlordName());
        dto.setLandlordContact(rc.getLandlordContact());
        dto.setStartDate(rc.getStartDate());
        dto.setEndDate(rc.getEndDate());
        dto.setStatus(rc.getStatus());
        return dto;
    }
}
