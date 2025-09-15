package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.*;
import com.contractEmployee.contractEmployee.entity.*;
import com.contractEmployee.contractEmployee.rep.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImmigrationServiceImpl implements ImmigrationService {

    private final EmployeeRepository employeeRepository;
    private final PassportRepository passportRepository;
    private final VisaRepository visaRepository;
    private final RentalCertificateRepository rentalRepository;

    @Override
    @Transactional
    public ImmigrationResponse saveImmigration(Integer employeeId, ImmigrationRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        ImmigrationResponse response = new ImmigrationResponse();

        // 1) Save Passport
        Passport passport = null;
        if (request.getPassport() != null) {
            PassportDto p = request.getPassport();

            passport = passportRepository.findByEmployeeId(employeeId).orElse(new Passport());
            passport.setEmployee(employee);
            passport.setPassportNo(p.getPassportNumber());
            passport.setPassportType(p.getPassportType());
            passport.setCountryCode(p.getCountry());
            passport.setIssuePlace(p.getIssuePlace());
            passport.setIssueDate(p.getIssueDate());
            passport.setExpiryDate(p.getExpiryDate());
            passport.setStatus(p.getStatus());

            passport = passportRepository.save(passport);

            response.setPassport(toPassportDto(passport));
        } else {
            passport = passportRepository.findByEmployeeId(employeeId).orElse(null);
        }

        // 2) Save Visas
        if (request.getVisas() != null && !request.getVisas().isEmpty()) {
            if (passport == null) {
                throw new IllegalStateException("Create passport before adding visas.");
            }
            var savedVisaDtos = new ArrayList<VisaDto>();
            for (VisaDto v : request.getVisas()) {
                Visa visa = new Visa();
                visa.setPassport(passport);
                visa.setVisaNumber(v.getVisaNumber());
                visa.setVisaType(v.getVisaType());
                visa.setVisaPurpose(v.getVisaPurpose());
                visa.setCountryCode(v.getCountry());
                visa.setIssuePlace(v.getIssuePlace());
                visa.setIssueDate(v.getIssueDate());
                visa.setExpiryDate(v.getExpiryDate());
                visa.setEntries(v.getEntries());
                visa.setVisaStatus(v.getStatus());

                Visa savedVisa = visaRepository.save(visa);
                savedVisaDtos.add(toVisaDto(savedVisa));
            }
            response.setVisas(savedVisaDtos);
        }

        // 3) Save Rental Certificates
        if (request.getRentalCertificates() != null && !request.getRentalCertificates().isEmpty()) {
            if (passport == null) {
                throw new IllegalStateException("Create passport/visa before adding rental certificates.");
            }
            Visa latestVisa = visaRepository.findTopByPassportIdOrderByCreatedAtDesc(passport.getId())
                    .orElseThrow(() -> new IllegalStateException("No visa found for this passport."));

            var savedRentalDtos = new ArrayList<RentalCertificateDto>();
            for (RentalCertificateDto r : request.getRentalCertificates()) {
                RentalCertificate rc = new RentalCertificate();
                rc.setVisa(latestVisa); // ✅ only visa, no passport
                rc.setCertificateNumber(r.getCertificateNumber());
                rc.setRentalType(r.getRentalType());
                rc.setAddress(r.getAddress());
                rc.setLandlordName(r.getLandlordName());
                rc.setLandlordContact(r.getLandlordContact());
                rc.setStartDate(r.getStartDate());
                rc.setEndDate(r.getEndDate());
                rc.setStatus(r.getStatus());

                RentalCertificate savedRc = rentalRepository.save(rc);
                savedRentalDtos.add(toRentalDto(savedRc));
            }
            response.setRentalCertificates(savedRentalDtos);
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ImmigrationResponse getImmigration(Integer employeeId) {
        ImmigrationResponse resp = new ImmigrationResponse();

        var passportOpt = passportRepository.findByEmployeeId(employeeId);
        if (passportOpt.isEmpty()) return resp;

        var passport = passportOpt.get();
        resp.setPassport(toPassportDto(passport));

        resp.setVisas(
                visaRepository.findByPassportId(passport.getId())
                        .stream().map(this::toVisaDto)
                        .collect(Collectors.toList())
        );

        resp.setRentalCertificates(
                rentalRepository.findByVisa_Passport_Id(passport.getId())
                        .stream().map(this::toRentalDto)
                        .collect(Collectors.toList())
        );

        return resp;
    }
    @Override
    @Transactional(readOnly = true)
    public VisaSummaryDto getVisaSummary() {
        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        long active = visaRepository.countByVisaStatus("ACTIVE");
        long expired = visaRepository.countByVisaStatus("EXPIRED");
        long expiring = visaRepository.countByExpiryDateBetween(today, next30Days);

        VisaSummaryDto dto = new VisaSummaryDto();
        dto.setTotalActive(active);
        dto.setTotalExpired(expired);
        dto.setTotalExpiring(expiring);
        return dto;
    }




    // ✅ Mapping Helpers
    private PassportDto toPassportDto(Passport p) {
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

    private VisaDto toVisaDto(Visa v) {
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
        dto.setStatus(v.getVisaStatus());
        return dto;
    }

    private RentalCertificateDto toRentalDto(RentalCertificate r) {
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
