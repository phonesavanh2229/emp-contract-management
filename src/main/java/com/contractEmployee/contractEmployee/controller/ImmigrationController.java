package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.services.ImmigrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImmigrationController {

    private final ImmigrationService immigrationService;

    // ----------------------- 📌 IMMIGRATION -----------------------
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllImmigrations(HttpServletRequest request) {
        List<EmployeeDto> all = immigrationService.getImmigrationAll();
        return ResponseEntity.ok(
                ApiResponse.success("Immigrations fetched successfully", all, request.getRequestURI())
        );
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getImmigrationById(
            HttpServletRequest request,
            @PathVariable Integer employeeId) {
        EmployeeDto employee = immigrationService.getImmigrationByEmployeeId(employeeId);
        return ResponseEntity.ok(
                ApiResponse.success("Immigration fetched successfully", employee, request.getRequestURI())
        );
    }

    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> saveImmigration(
            HttpServletRequest request,
            @PathVariable Integer employeeId,
            @RequestBody ImmigrationRequest immigrationRequest) {
        EmployeeDto dto = immigrationService.saveImmigration(employeeId, immigrationRequest);
        return ResponseEntity.ok(
                ApiResponse.success("Immigration saved successfully", dto, request.getRequestURI())
        );
    }

    // ----------------------- 📌 PASSPORT CRUD -----------------------
    @PostMapping("/employees/{employeeId}/passports")
    public ResponseEntity<ApiResponse<Passport>> savePassport(
            HttpServletRequest request,
            @PathVariable Integer employeeId,
            @RequestBody PassportDto passportDto) {
        Passport passport = immigrationService.savePassport(employeeId, passportDto);
        return ResponseEntity.ok(
                ApiResponse.success("Passport saved successfully", passport, request.getRequestURI())
        );
    }

    @GetMapping("/passports/{passportId}")
    public ResponseEntity<ApiResponse<Passport>> getPassport(
            HttpServletRequest request,
            @PathVariable Integer passportId) {
        Passport passport = immigrationService.getPassport(passportId);
        return ResponseEntity.ok(
                ApiResponse.success("Passport fetched successfully", passport, request.getRequestURI())
        );
    }

    @GetMapping("/employees/{employeeId}/passports")
    public ResponseEntity<ApiResponse<List<Passport>>> getPassportsByEmployee(
            HttpServletRequest request,
            @PathVariable Integer employeeId) {
        List<Passport> passports = immigrationService.getPassportsByEmployee(employeeId);
        return ResponseEntity.ok(
                ApiResponse.success("Passports fetched successfully", passports, request.getRequestURI())
        );
    }

    @PutMapping("/passports/{passportId}")
    public ResponseEntity<ApiResponse<Passport>> updatePassport(
            HttpServletRequest request,
            @PathVariable Integer passportId,
            @RequestBody PassportDto passportDto) {
        Passport updated = immigrationService.updatePassport(passportId, passportDto);
        return ResponseEntity.ok(
                ApiResponse.success("Passport updated successfully", updated, request.getRequestURI())
        );
    }

    @DeleteMapping("/passports/{passportId}")
    public ResponseEntity<ApiResponse<Void>> deletePassport(
            HttpServletRequest request,
            @PathVariable Integer passportId) {
        immigrationService.deletePassport(passportId);
        return ResponseEntity.ok(
                ApiResponse.success("Passport deleted successfully", null, request.getRequestURI())
        );
    }

    // ----------------------- 📌 VISA CRUD -----------------------
    @PostMapping("/passports/{passportId}/visas")
    public ResponseEntity<ApiResponse<Visa>> saveVisa(
            HttpServletRequest request,
            @PathVariable Integer passportId,
            @RequestBody VisaDto visaDto) {
        Visa visa = immigrationService.saveVisa(passportId, visaDto);
        return ResponseEntity.ok(
                ApiResponse.success("Visa saved successfully", visa, request.getRequestURI())
        );
    }

    @GetMapping("/visas/{visaId}")
    public ResponseEntity<ApiResponse<Visa>> getVisa(
            HttpServletRequest request,
            @PathVariable Integer visaId) {
        Visa visa = immigrationService.getVisa(visaId);
        return ResponseEntity.ok(
                ApiResponse.success("Visa fetched successfully", visa, request.getRequestURI())
        );
    }

    @GetMapping("/passports/{passportId}/visas")
    public ResponseEntity<ApiResponse<List<Visa>>> getVisasByPassport(
            HttpServletRequest request,
            @PathVariable Integer passportId) {
        List<Visa> visas = immigrationService.getVisasByPassport(passportId);
        return ResponseEntity.ok(
                ApiResponse.success("Visas fetched successfully", visas, request.getRequestURI())
        );
    }

    @PutMapping("/visas/{visaId}")
    public ResponseEntity<ApiResponse<Visa>> updateVisa(
            HttpServletRequest request,
            @PathVariable Integer visaId,
            @RequestBody VisaDto visaDto) {
        Visa updated = immigrationService.updateVisa(visaId, visaDto);
        return ResponseEntity.ok(
                ApiResponse.success("Visa updated successfully", updated, request.getRequestURI())
        );
    }

    @DeleteMapping("/visas/{visaId}")
    public ResponseEntity<ApiResponse<Void>> deleteVisa(
            HttpServletRequest request,
            @PathVariable Integer visaId) {
        immigrationService.deleteVisa(visaId);
        return ResponseEntity.ok(
                ApiResponse.success("Visa deleted successfully", null, request.getRequestURI())
        );
    }

    // ----------------------- 📌 RENTAL CRUD -----------------------
    @PostMapping("/visas/{visaId}/rentals")
    public ResponseEntity<ApiResponse<RentalCertificate>> saveRental(
            HttpServletRequest request,
            @PathVariable Integer visaId,
            @RequestBody RentalCertificateDto rentalDto) {
        RentalCertificate rental = immigrationService.saveRental(visaId, rentalDto);
        return ResponseEntity.ok(
                ApiResponse.success("Rental certificate saved successfully", rental, request.getRequestURI())
        );
    }

    @GetMapping("/rentals/{rentalId}")
    public ResponseEntity<ApiResponse<RentalCertificate>> getRental(
            HttpServletRequest request,
            @PathVariable Integer rentalId) {
        RentalCertificate rental = immigrationService.getRental(rentalId);
        return ResponseEntity.ok(
                ApiResponse.success("Rental fetched successfully", rental, request.getRequestURI())
        );
    }

    @GetMapping("/visas/{visaId}/rentals")
    public ResponseEntity<ApiResponse<List<RentalCertificate>>> getRentalsByVisa(
            HttpServletRequest request,
            @PathVariable Integer visaId) {
        List<RentalCertificate> rentals = immigrationService.getRentalsByVisa(visaId);
        return ResponseEntity.ok(
                ApiResponse.success("Rentals fetched successfully", rentals, request.getRequestURI())
        );
    }

    @PutMapping("/rentals/{rentalId}")
    public ResponseEntity<ApiResponse<RentalCertificate>> updateRental(
            HttpServletRequest request,
            @PathVariable Integer rentalId,
            @RequestBody RentalCertificateDto rentalDto) {
        RentalCertificate updated = immigrationService.updateRental(rentalId, rentalDto);
        return ResponseEntity.ok(
                ApiResponse.success("Rental updated successfully", updated, request.getRequestURI())
        );
    }

    @DeleteMapping("/rentals/{rentalId}")
    public ResponseEntity<ApiResponse<Void>> deleteRental(
            HttpServletRequest request,
            @PathVariable Integer rentalId) {
        immigrationService.deleteRental(rentalId);
        return ResponseEntity.ok(
                ApiResponse.success("Rental deleted successfully", null, request.getRequestURI())
        );
    }

    // ----------------------- 📌 SUMMARY -----------------------
    @GetMapping("/passport-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getPassportSummary(HttpServletRequest request) {
        List<SummaryDto> summary = immigrationService.getPassportSummary();
        return ResponseEntity.ok(
                ApiResponse.success("Passport summary fetched successfully", summary, request.getRequestURI())
        );
    }

    @GetMapping("/visa-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getVisaSummary(HttpServletRequest request) {
        List<SummaryDto> summary = immigrationService.getVisaSummary();
        return ResponseEntity.ok(
                ApiResponse.success("Visa summary fetched successfully", summary, request.getRequestURI())
        );
    }

    @GetMapping("/rental-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getRentalSummary(HttpServletRequest request) {
        List<SummaryDto> summary = immigrationService.getRentalCertificateSummary();
        return ResponseEntity.ok(
                ApiResponse.success("Rental certificate summary fetched successfully", summary, request.getRequestURI())
        );
    }

    // ----------------------- 📌 RENEW -----------------------
    @PostMapping("/renew-passport/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewPassport(
            HttpServletRequest request,
            @PathVariable Integer employeeId,
            @RequestBody PassportDto passportDto) {
        EmployeeDto dto = immigrationService.renewPassport(employeeId, passportDto);
        return ResponseEntity.ok(
                ApiResponse.success("Passport renewed successfully", dto, request.getRequestURI())
        );
    }

    @PostMapping("/renew-visa/{passportId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewVisa(
            HttpServletRequest request,
            @PathVariable Integer passportId,
            @RequestBody VisaDto visaDto) {
        EmployeeDto dto = immigrationService.renewVisa(passportId, visaDto);
        return ResponseEntity.ok(
                ApiResponse.success("Visa renewed successfully", dto, request.getRequestURI())
        );
    }

    @PostMapping("/renew-rental/{rentalId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewRental(
            HttpServletRequest request,
            @PathVariable Integer rentalId,
            @RequestBody RentalCertificateDto rentalDto) {
        EmployeeDto dto = immigrationService.renewRental(rentalId, rentalDto);
        return ResponseEntity.ok(
                ApiResponse.success("Rental certificate renewed successfully", dto, request.getRequestURI())
        );
    }
}
