package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.RentalCertificateDto;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.services.PassportVisaRentalCertificateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RentalCertificate {
    private final PassportVisaRentalCertificateService passportVisaRentalCertificateService;

    @PostMapping("/visas/{visaId}/rentals")
    public ResponseEntity<ApiResponse<com.contractEmployee.contractEmployee.entity.RentalCertificate>> saveRental(
            HttpServletRequest request,
            @PathVariable Long visaId,
            @RequestBody RentalCertificateDto rentalDto) {
        com.contractEmployee.contractEmployee.entity.RentalCertificate rental = passportVisaRentalCertificateService.saveRental(visaId, rentalDto);
        return ResponseEntity.ok(
                ApiResponse.success("Rental certificate saved successfully", rental, request.getRequestURI())
        );
    }

    @GetMapping("/rentals/{rentalId}")
    public ResponseEntity<ApiResponse<com.contractEmployee.contractEmployee.entity.RentalCertificate>> getRental(
            HttpServletRequest request,
            @PathVariable Long rentalId) {
        com.contractEmployee.contractEmployee.entity.RentalCertificate rental = passportVisaRentalCertificateService.getRental(rentalId);
        return ResponseEntity.ok(
                ApiResponse.success("Rental fetched successfully", rental, request.getRequestURI())
        );
    }

    @GetMapping("/visas/{visaId}/rentals")
    public ResponseEntity<ApiResponse<List<com.contractEmployee.contractEmployee.entity.RentalCertificate>>> getRentalsByVisa(
            HttpServletRequest request,
            @PathVariable Long visaId) {
        List<com.contractEmployee.contractEmployee.entity.RentalCertificate> rentals = passportVisaRentalCertificateService.getRentalsByVisa(visaId);
        return ResponseEntity.ok(
                ApiResponse.success("Rentals fetched successfully", rentals, request.getRequestURI())
        );
    }

    @PutMapping("/rentals/{rentalId}")
    public ResponseEntity<ApiResponse<com.contractEmployee.contractEmployee.entity.RentalCertificate>> updateRental(
            HttpServletRequest request,
            @PathVariable Long rentalId,
            @RequestBody RentalCertificateDto rentalDto) {
        com.contractEmployee.contractEmployee.entity.RentalCertificate updated = passportVisaRentalCertificateService.updateRental(rentalId, rentalDto);
        return ResponseEntity.ok(
                ApiResponse.success("Rental updated successfully", updated, request.getRequestURI())
        );
    }

    @DeleteMapping("/rentals/{rentalId}")
    public ResponseEntity<ApiResponse<Void>> deleteRental(
            HttpServletRequest request,
            @PathVariable Long rentalId) {
        passportVisaRentalCertificateService.deleteRental(rentalId);
        return ResponseEntity.ok(
                ApiResponse.success("Rental deleted successfully", null, request.getRequestURI())
        );
    }
    @GetMapping("/rental-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getRentalSummary(HttpServletRequest request) {
        List<SummaryDto> summary = passportVisaRentalCertificateService.getRentalCertificateSummary();
        return ResponseEntity.ok(
                ApiResponse.success("Rental certificate summary fetched successfully", summary, request.getRequestURI())
        );
    }
    @PostMapping("/renew-rental/{rentalId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewRental(
            HttpServletRequest request,
            @PathVariable Long rentalId,
            @RequestBody RentalCertificateDto rentalDto) {
        EmployeeDto dto = passportVisaRentalCertificateService.renewRental(rentalId, rentalDto);
        return ResponseEntity.ok(
                ApiResponse.success("Rental certificate renewed successfully", dto, request.getRequestURI())
        );
    }

}
