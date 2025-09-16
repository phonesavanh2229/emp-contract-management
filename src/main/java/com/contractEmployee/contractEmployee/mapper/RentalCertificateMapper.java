// RentalCertificateMapper.java
package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.RentalCertificateDto;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;

public class RentalCertificateMapper {
    public static RentalCertificateDto toRentalDto(RentalCertificate r) {
        RentalCertificateDto dto = new RentalCertificateDto();
        dto.setId(r.getId());
        dto.setCertificateNumber(r.getCertificateNumber());
        dto.setRentalType(r.getRentalType());
        dto.setAddress(r.getAddress());
        dto.setLandlordName(r.getLandlordName());
        dto.setLandlordContact(r.getLandlordContact());
        dto.setStartDate(r.getStartDate());
        dto.setEndDate(r.getEndDate());
        dto.setStatus(r.getStatus());
        return dto;
    }
}
