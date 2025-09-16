package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationResponse;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.mapper.PassportMapper;
import com.contractEmployee.contractEmployee.mapper.RentalCertificateMapper;
import com.contractEmployee.contractEmployee.mapper.VisaMapper;
import com.contractEmployee.contractEmployee.rep.EmployeeRepository;
import com.contractEmployee.contractEmployee.rep.PassportRepository;
import com.contractEmployee.contractEmployee.rep.RentalCertificateRepository;
import com.contractEmployee.contractEmployee.rep.VisaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ImmigrationServiceImpl implements ImmigrationService {

    private final EmployeeRepository employeeRepository;
    private final PassportRepository passportRepository;
    private final VisaRepository visaRepository;
    private final RentalCertificateRepository rentalRepository;

    @Override
    @Transactional(readOnly = true)
    public ApiResponse getImmigrationAll() {
        List<EmployeeDto> employeeDtos = employeeRepository.findAll()
                .stream()
                .map(this::mapEmployeeWithImmigration)
                .collect(Collectors.toList());

        return  ApiResponse.success("get Data successfully",employeeDtos); // ✅ wrap
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<EmployeeDto>> getImmigrationByEmployeeId(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        EmployeeDto dto = mapEmployeeWithImmigration(employee);
        return ApiResponse.success("get Data successfully", List.of(dto)); // ✅ wrap เป็น List
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<VisaSummaryDto> getVisaSummary() {
        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        long active = visaRepository.countByVisaStatus("ACTIVE");
        long expired = visaRepository.countByVisaStatus("EXPIRED");
        long expiring = visaRepository.countByExpiryDateBetween(today, next30Days);
        VisaSummaryDto dto = new VisaSummaryDto(active, expired, expiring);
        return ApiResponse.success("Visa summary fetched successfully", dto);
    }

    @Override
    @Transactional
    public ApiResponse<EmployeeDto> saveImmigration(Integer employeeId, ImmigrationRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        // ✅ Save Passport
        PassportDto passportDto = request.getPassport();
        Passport passport = new Passport();
        passport.setEmployee(employee);
        passport.setPassportNo(passportDto.getPassportNumber());
        passport.setPassportType(passportDto.getPassportType());
        passport.setCountryCode(passportDto.getCountry());
        passport.setIssuePlace(passportDto.getIssuePlace());
        passport.setStatus(passportDto.getStatus());
        passport.setIssueDate(passportDto.getIssueDate());
        passport.setExpiryDate(passportDto.getExpiryDate());
        passport = passportRepository.save(passport);

        // ✅ Save Visas
        if (request.getVisas() != null) {
            for (VisaDto v : request.getVisas()) {
                Visa visa = new Visa();
                visa.setPassport(passport);
                visa.setVisaNumber(v.getVisaNumber());
                visa.setVisaType(v.getVisaType());
                visa.setVisaPurpose(v.getVisaPurpose());
                visa.setCountryCode(v.getCountry());
                visa.setIssuePlace(v.getIssuePlace());
                visa.setEntries(v.getEntries());
                visa.setVisaStatus(v.getStatus());
                visa.setIssueDate(v.getIssueDate());
                visa.setExpiryDate(v.getExpiryDate());
                Visa savedVisa = visaRepository.save(visa);

                // ✅ Save Rentals for each Visa
                if (request.getRentalCertificates() != null) {
                    for (RentalCertificateDto r : request.getRentalCertificates()) {
                        RentalCertificate rc = new RentalCertificate();
                        rc.setVisa(savedVisa);
                        rc.setCertificateNumber(r.getCertificateNumber());
                        rc.setRentalType(r.getRentalType());
                        rc.setAddress(r.getAddress());
                        rc.setLandlordName(r.getLandlordName());
                        rc.setLandlordContact(r.getLandlordContact());
                        rc.setStartDate(r.getStartDate());
                        rc.setEndDate(r.getEndDate());
                        rc.setStatus(r.getStatus());
                        rentalRepository.save(rc);
                    }
                }
            }
        }

        // ✅ Return mapped EmployeeDto with immigration
        EmployeeDto dto = mapEmployeeWithImmigration(employee);
        return ApiResponse.success("Immigration data saved successfully", dto);
    }

    // ------------------ Mapper ------------------
    private EmployeeDto mapEmployeeWithImmigration(Employee e) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(e.getId());
        dto.setStaffCode(e.getStaffCode());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setPhone(e.getPhone());
        dto.setEmail(e.getEmail());
        dto.setProvince(e.getProvince());
        dto.setVillage(e.getVillage());

        passportRepository.findByEmployeeId(e.getId()).ifPresent(passport -> {
            PassportDto passportDto = new PassportDto();
            passportDto.setId(passport.getId());
            passportDto.setPassportNumber(passport.getPassportNo());
            passportDto.setPassportType(passport.getPassportType());
            passportDto.setCountry(passport.getCountryCode());
            passportDto.setIssuePlace(passport.getIssuePlace());
            passportDto.setStatus(passport.getStatus());
            passportDto.setIssueDate(passport.getIssueDate());
            passportDto.setExpiryDate(passport.getExpiryDate());

            List<VisaDto> visaDtos = visaRepository.findByPassportId(passport.getId())
                    .stream()
                    .map(visa -> {
                        VisaDto visaDto = new VisaDto();
                        visaDto.setId(visa.getId());
                        visaDto.setVisaNumber(visa.getVisaNumber());
                        visaDto.setVisaType(visa.getVisaType());
                        visaDto.setVisaPurpose(visa.getVisaPurpose());
                        visaDto.setCountry(visa.getCountryCode());
                        visaDto.setIssuePlace(visa.getIssuePlace());
                        visaDto.setEntries(visa.getEntries());
                        visaDto.setStatus(visa.getVisaStatus());
                        visaDto.setIssueDate(visa.getIssueDate());
                        visaDto.setExpiryDate(visa.getExpiryDate());

                        List<RentalCertificateDto> rentals = rentalRepository.findByVisaId(visa.getId())
                                .stream()
                                .map(r -> {
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
                                }).collect(Collectors.toList());

                        visaDto.setRentalCertificates(rentals);
                        return visaDto;
                    }).collect(Collectors.toList());

            passportDto.setVisas(visaDtos);
            dto.setPassport(passportDto);
        });

        return dto;
    }
}
//    // ------------------ Mapping Helpers ------------------
//    private EmployeeDto mapEmployeeWithImmigration(Employee e) {
//        EmployeeDto dto = new EmployeeDto();
//        dto.setId(e.getId());
//        dto.setStaffCode(e.getStaffCode());
//        dto.setFirstName(e.getFirstName());
//        dto.setLastName(e.getLastName());
//        dto.setPhone(e.getPhone());
//        dto.setEmail(e.getEmail());
//        dto.setProvince(e.getProvince());
//        dto.setVillage(e.getVillage());
//
//        passportRepository.findByEmployeeId(e.getId()).ifPresent(passport -> {
//            PassportDto passportDto = PassportMapper.toPassportDto(passport);
//
//            List<VisaDto> visaDtos = visaRepository.findByPassportId(passport.getId())
//                    .stream()
//                    .map(visa -> {
//                        VisaDto visaDto = VisaMapper.toVisaDto(visa);
//
//                        List<RentalCertificateDto> rentalDtos = rentalRepository.findByVisaId(visa.getId())
//                                .stream()
//                                .map(RentalCertificateMapper::toRentalDto)
//                                .collect(Collectors.toList());
//
//                        visaDto.setRentalCertificates(rentalDtos);
//                        return visaDto;
//                    })
//                    .collect(Collectors.toList());
//
//            passportDto.setVisas(visaDtos);
//            dto.setPassport(passportDto);
//        });
//
//        return dto;
//    }
//}
