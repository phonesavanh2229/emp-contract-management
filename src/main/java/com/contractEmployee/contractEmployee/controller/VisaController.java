package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.VisaDto;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithVisaDto;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.services.PassportVisaRentalCertificateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class VisaController {

    private final PassportVisaRentalCertificateService passportVisaRentalCertificateService;
    @PostMapping("/passports/{passportId}/visas")
    public ResponseEntity<ApiResponse<Visa>> saveVisa(
            HttpServletRequest request,
            @PathVariable Long passportId,
            @RequestBody VisaDto visaDto) {
        Visa visa = passportVisaRentalCertificateService.saveVisa(passportId, visaDto);
        return ResponseEntity.ok(
                ApiResponse.success("Visa saved successfully", visa, request.getRequestURI())
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getImmigrationSummary(HttpServletRequest request) {
        List<SummaryDto> summary = passportVisaRentalCertificateService.getSummary();
        return ResponseEntity.ok(
                ApiResponse.success("Summary fetched successfully", summary, request.getRequestURI())
        );
    }

    @GetMapping("/visas/{visaId}")
    public ResponseEntity<ApiResponse<List<Visa>>> getVisa(
            HttpServletRequest request,
            @PathVariable Long visaId) {
        List<Visa> visa = passportVisaRentalCertificateService.getVisa(visaId);
        return ResponseEntity.ok(
                ApiResponse.success("Visa fetched successfully", visa, request.getRequestURI())
        );
    }

    @GetMapping("/passports/{passportId}/visas")
    public ResponseEntity<ApiResponse<List<Visa>>> getVisasByPassport(
            HttpServletRequest request,
            @PathVariable Long passportId) {
        List<Visa> visas = passportVisaRentalCertificateService.getVisasByPassport(passportId);
        return ResponseEntity.ok(
                ApiResponse.success("Visas fetched successfully", visas, request.getRequestURI())
        );
    }

    @PutMapping("/visas/{visaId}")
    public ResponseEntity<ApiResponse<Visa>> updateVisa(
            HttpServletRequest request,
            @PathVariable Long visaId,
            @RequestBody VisaDto visaDto) {
        Visa updated = passportVisaRentalCertificateService.updateVisa(visaId, visaDto);
        return ResponseEntity.ok(
                ApiResponse.success("Visa updated successfully", updated, request.getRequestURI())
        );
    }

    @DeleteMapping("/visas/{visaId}")
    public ResponseEntity<ApiResponse<Void>> deleteVisa(
            HttpServletRequest request,
            @PathVariable Long visaId) {
        passportVisaRentalCertificateService.deleteVisa(visaId);
        return ResponseEntity.ok(
                ApiResponse.success("Visa deleted successfully", null, request.getRequestURI())
        );
    }
    @PostMapping("/renew-visa/{passportId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewVisa(
            HttpServletRequest request,
            @PathVariable Long passportId,
            @RequestBody VisaDto visaDto) {
        EmployeeDto dto = passportVisaRentalCertificateService.renewVisa(passportId, visaDto);
        return ResponseEntity.ok(
                ApiResponse.success("Visa renewed successfully", dto, request.getRequestURI())
        );
    }
    @GetMapping("/visa-summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getVisaSummary(HttpServletRequest request) {
        List<SummaryDto> summary = passportVisaRentalCertificateService.getVisaSummary();
        return ResponseEntity.ok(
                ApiResponse.success("Visa summary fetched successfully", summary, request.getRequestURI())
        );
    }
    @GetMapping("/visas-active")
    public ResponseEntity<ApiResponse<List<EmployeeWithVisaDto>>> getEmployeesWithActiveVisas(HttpServletRequest request) {
        List<EmployeeWithVisaDto> employees = passportVisaRentalCertificateService.getEmployeesWithActiveVisas();
        return ResponseEntity.ok(
                ApiResponse.success("Employees with ACTIVE visas fetched successfully", employees, request.getRequestURI())
        );
    }

    @GetMapping("/visas-expired")
    public ResponseEntity<ApiResponse<List<EmployeeWithVisaDto>>> getExpiredVisas(HttpServletRequest request) {
        List<EmployeeWithVisaDto> result = passportVisaRentalCertificateService.getEmployeesWithExpiredVisas();
        return ResponseEntity.ok(
                ApiResponse.success("Employees with expired visas fetched successfully", result, request.getRequestURI())
        );
    }
}
