// com.contractEmployee.contractEmployee.mapper.EmployeePVRMapper.java
package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import com.contractEmployee.contractEmployee.dto.request.RentalCertificateDto;
import com.contractEmployee.contractEmployee.dto.request.VisaDto;
import com.contractEmployee.contractEmployee.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeePVRMapper {

    public EmployeeDto toFullDto(Employee e) {
        EmployeeDto dto = EmployeeMapper.toDto(e); // ใช้ตัว map หลักของคุณ

        if (e.getPassports() != null) {
            dto.setPassports(
                    e.getPassports().stream().map(passport -> {
                        PassportDto pd = new PassportDto();
                        pd.setId(passport.getId());
                        pd.setPassportNumber(passport.getPassportNo());
                        pd.setPassportType(passport.getPassportType());
                        pd.setCountry(passport.getCountryCode());
                        pd.setIssuePlace(passport.getIssuePlace());
                        pd.setIssueDate(passport.getIssueDate());
                        pd.setExpiryDate(passport.getExpiryDate());
                        pd.setStatus(passport.getStatus());

                        if (passport.getVisas() != null) {
                            pd.setVisas(passport.getVisas().stream().map(visa -> {
                                VisaDto vd = new VisaDto();
                                vd.setId(visa.getId());
                                vd.setVisaNumber(visa.getVisaNumber());
                                vd.setVisaType(visa.getVisaType());
                                vd.setVisaPurpose(visa.getVisaPurpose());
                                vd.setCountry(visa.getCountryCode());
                                vd.setIssuePlace(visa.getIssuePlace());
                                vd.setIssueDate(visa.getIssueDate());
                                vd.setExpiryDate(visa.getExpiryDate());
                                vd.setEntries(visa.getEntries());
                                vd.setStatus(visa.getStatus());

                                if (visa.getRentals() != null) {
                                    vd.setRentalCertificates(
                                            visa.getRentals().stream().map(r -> {
                                                RentalCertificateDto rd = new RentalCertificateDto();
                                                rd.setId(r.getId());
                                                rd.setCertificateNumber(r.getCertificateNumber());
                                                rd.setRentalType(r.getRentalType());
                                                rd.setAddress(r.getAddress());
                                                rd.setLandlordName(r.getLandlordName());
                                                rd.setLandlordContact(r.getLandlordContact());
                                                rd.setStartDate(r.getStartDate());
                                                rd.setEndDate(r.getEndDate());
                                                rd.setStatus(r.getStatus());
                                                return rd;
                                            }).toList()
                                    );
                                }
                                return vd;
                            }).toList());
                        }
                        return pd;
                    }).toList()
            );
        }

        return dto;
    }
}
