package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationListResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationResponse;
import com.contractEmployee.contractEmployee.services.ImmigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImmigrationController {

    private final ImmigrationService immigrationService;

    // ---------- Passport ----------
    @PostMapping("/employees/{employeeId}/passports")
    public ApiResponse<PassportDto> savePassport(@PathVariable Integer employeeId,
                                                 @RequestBody PassportDto passportDto) {
        return immigrationService.savePassport(employeeId, passportDto);
    }

    @PutMapping("/passports/{passportId}")
    public ApiResponse<PassportDto> updatePassport(@PathVariable Integer passportId,
                                                   @RequestBody PassportDto passportDto) {
        return immigrationService.updatePassport(passportId, passportDto);
    }

    @GetMapping("/passports/{passportId}")
    public ApiResponse<PassportDto> getPassport(@PathVariable Integer passportId) {
        return immigrationService.getPassport(passportId);
    }

    @GetMapping("/employees/{employeeId}/passports")
    public ApiResponse<List<PassportDto>> getPassportsByEmployee(@PathVariable Integer employeeId) {
        return immigrationService.getPassportsByEmployee(employeeId);
    }

    @DeleteMapping("/passports/{passportId}")
    public ApiResponse<Void> deletePassport(@PathVariable Integer passportId) {
        return immigrationService.deletePassport(passportId);
    }

    // ---------- Visa ----------
    @PostMapping("/passports/{passportId}/visas")
    public ApiResponse<VisaDto> saveVisa(@PathVariable Integer passportId,
                                         @RequestBody VisaDto visaDto) {
        return immigrationService.saveVisa(passportId, visaDto);
    }

    @GetMapping("/visas/{visaId}")
    public ApiResponse<VisaDto> getVisa(@PathVariable Integer visaId) {
        return immigrationService.getVisa(visaId);
    }

    @GetMapping("/passports/{passportId}/visas")
    public ApiResponse<List<VisaDto>> getVisasByPassport(@PathVariable Integer passportId) {
        return immigrationService.getVisasByPassport(passportId);
    }

    @PutMapping("/visas/{visaId}")
    public ApiResponse<VisaDto> updateVisa(@PathVariable Integer visaId,
                                           @RequestBody VisaDto visaDto) {
        return immigrationService.updateVisa(visaId, visaDto);
    }

    @DeleteMapping("/visas/{visaId}")
    public ApiResponse<Void> deleteVisa(@PathVariable Integer visaId) {
        return immigrationService.deleteVisa(visaId);
    }

    // ---------- Rental ----------
    @PostMapping("/visas/{visaId}/rentals")
    public ApiResponse<RentalCertificateDto> saveRental(@PathVariable Integer visaId,
                                                        @RequestBody RentalCertificateDto rentalDto) {
        return immigrationService.saveRental(visaId, rentalDto);
    }

    @GetMapping("/rentals/{rentalId}")
    public ApiResponse<RentalCertificateDto> getRental(@PathVariable Integer rentalId) {
        return immigrationService.getRental(rentalId);
    }

    @GetMapping("/visas/{visaId}/rentals")
    public ApiResponse<List<RentalCertificateDto>> getRentalsByVisa(@PathVariable Integer visaId) {
        return immigrationService.getRentalsByVisa(visaId);
    }

    @PutMapping("/rentals/{rentalId}")
    public ApiResponse<RentalCertificateDto> updateRental(@PathVariable Integer rentalId,
                                                          @RequestBody RentalCertificateDto rentalDto) {
        return immigrationService.updateRental(rentalId, rentalDto);
    }

    @DeleteMapping("/rentals/{rentalId}")
    public ApiResponse<Void> deleteRental(@PathVariable Integer rentalId) {
        return immigrationService.deleteRental(rentalId);
    }

    // ---------- Immigration aggregate ----------
    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> saveImmigration(
            @PathVariable Integer employeeId,
            @RequestBody ImmigrationRequest request) {
        return ResponseEntity.ok(immigrationService.saveImmigration(employeeId, request));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllImmigrations() {
        return ResponseEntity.ok(immigrationService.getImmigrationAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getImmigrationById(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(immigrationService.getImmigrationByEmployeeId(employeeId));
    }

    // ---------- Summary ----------
    @GetMapping("/passport-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getPassportSummary() {
        return ResponseEntity.ok(immigrationService.getPassportSummary());
    }

    @GetMapping("/visa-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getVisaSummary() {
        return ResponseEntity.ok(immigrationService.getVisaSummary());
    }

    @GetMapping("/rentalcertificate-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getRentalCertificateSummary() {
        return ResponseEntity.ok(immigrationService.getRentalCertificateSummary());
    }

    // ---------- Renewal ----------
    @PostMapping("/renew-passport/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewPassport(@PathVariable Integer employeeId,
                                                                  @RequestBody PassportDto passportDto) {
        return ResponseEntity.ok(immigrationService.renewPassport(employeeId, passportDto));
    }

    @PostMapping("/renew-visa/{passportId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewVisa(@PathVariable Integer passportId,
                                                              @RequestBody VisaDto visaDto) {
        return ResponseEntity.ok(immigrationService.renewVisa(passportId, visaDto));
    }

    @PostMapping("/renew-rental/{visaId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewRental(@PathVariable Integer visaId,
                                                                @RequestBody RentalCertificateDto rentalDto) {
        return ResponseEntity.ok(immigrationService.renewRental(visaId, rentalDto));
    }
}

