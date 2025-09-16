package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import com.contractEmployee.contractEmployee.dto.request.VisaDto;
import com.contractEmployee.contractEmployee.dto.request.RentalCertificateDto;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeImmigrationMapper {

    public static EmployeeDto toEmployeeImmigrationDto(
            Employee e,
            Passport passport,
            List<Visa> visas,
            List<RentalCertificate> rentals
    ) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(e.getId());
        dto.setStaffCode(e.getStaffCode());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setPhone(e.getPhone());
        dto.setEmail(e.getEmail());
        dto.setProvince(e.getProvince());
        dto.setVillage(e.getVillage());

        if (passport != null) {
            PassportDto passportDto = PassportMapper.toPassportDto(passport);

            List<VisaDto> visaDtos = visas.stream()
                    .map(v -> {
                        VisaDto visaDto = VisaMapper.toVisaDto(v);

                        List<RentalCertificateDto> rentalDtos = rentals.stream()
                                .filter(r -> r.getVisa().getId().equals(v.getId()))
                                .map(RentalCertificateMapper::toRentalDto)
                                .collect(Collectors.toList());

                        visaDto.setRentalCertificates(rentalDtos);
                        return visaDto;
                    })
                    .collect(Collectors.toList());

            passportDto.setVisas(visaDtos);
            dto.setPassport(passportDto);
        }

        return dto;
    }
}
