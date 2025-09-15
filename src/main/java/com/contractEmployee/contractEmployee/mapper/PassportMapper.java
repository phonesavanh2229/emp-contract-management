package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.*;
import com.contractEmployee.contractEmployee.entity.*;

import java.util.stream.Collectors;

public class PassportMapper {

    public static PassportDto toDto(Passport p) {
        PassportDto dto = new PassportDto();
        dto.setPassportNumber(p.getPassportNo());
        dto.setPassportType(p.getPassportType());
        dto.setCountry(p.getCountryCode());
        dto.setIssuePlace(p.getIssuePlace());
        dto.setIssueDate(p.getIssueDate());
        dto.setExpiryDate(p.getExpiryDate());
        dto.setStatus(p.getStatus());

        // map visas
//        dto.setVisas(p.getVisas().stream()
//                .map(v -> {
//                    VisaDto vd = new VisaDto();
//                    vd.setVisaNumber(v.getVisaNumber());
//                    vd.setVisaType(v.getVisaType());
//                    vd.setVisaPurpose(v.getVisaPurpose());
//                    vd.setCountry(v.getCountryCode());
//                    vd.setIssuePlace(v.getIssuePlace());
//                    vd.setIssueDate(v.getIssueDate());
//                    vd.setExpiryDate(v.getExpiryDate());
//                    vd.setEntries(v.getEntries());
//                    vd.setStatus(v.getVisaStatus());
//                    return vd;
//                }).collect(Collectors.toList()));

        // map rentals (ผ่าน visa → rentals)
        dto.setRentalCertificates(
                p.getVisas().stream()
                        .flatMap(v -> v.getRentals().stream())
                        .map(r -> {
                            RentalCertificateDto rd = new RentalCertificateDto();
                            rd.setCertificateNumber(r.getCertificateNumber());
                            rd.setRentalType(r.getRentalType());
                            rd.setAddress(r.getAddress());
                            rd.setLandlordName(r.getLandlordName());
                            rd.setLandlordContact(r.getLandlordContact());
                            rd.setStartDate(r.getStartDate());
                            rd.setEndDate(r.getEndDate());
                            rd.setStatus(r.getStatus());
                            return rd;
                        }).collect(Collectors.toList())
        );

        return dto;
    }
}
