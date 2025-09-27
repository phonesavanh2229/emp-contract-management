package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.PagedResponse;
import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.ImmigrationRequest;
import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithPassportDto;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.search.EmployeeSpecification;
import com.contractEmployee.contractEmployee.search.FilterState;
import com.contractEmployee.contractEmployee.services.PassportVisaRentalCertificateService;
import com.contractEmployee.contractEmployee.services.PassportVisaRentalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PassportController {

    private final PassportVisaRentalCertificateService passportVisaRentalCertificateService;
    private final PassportVisaRentalService service;
    private final PassportVisaRentalService passportVisaRentalService;
    // ---------------- IMMIGRATION ----------------

    @GetMapping("/passport")
    public ResponseEntity<ApiResponse<Page<EmployeeDto>>> getAllImmigrations(
            HttpServletRequest request,
            @RequestParam(required = false) String passportFilter, // ACTIVE|EXPIRING|EXPIRED|ANY
            @RequestParam(required = false) String visaFilter,
            @RequestParam(required = false) String rentalFilter,
            @RequestParam(defaultValue = "30") int expiringDays,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            // optional sorting (ตัวอย่าง: sortBy=lastName&dir=ASC)
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String dir
    ) {

        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC,
                sortBy
        );
        Pageable pageable = PageRequest.of(page, size, sort);

        // parse string -> FilterState
        FilterState pf = EmployeeSpecification.parse(passportFilter);
        FilterState vf = EmployeeSpecification.parse(visaFilter);
        FilterState rf = EmployeeSpecification.parse(rentalFilter);

        Page<EmployeeDto> result = passportVisaRentalService.getImmigrationAll(
                pf, vf, rf, expiringDays, pageable
        );

        return ResponseEntity.ok(
                ApiResponse.success("passport fetched successfully", result, request.getRequestURI())
        );
    }

    private String mapSortField(String sortBy) {
        switch (sortBy) {
            case "passportExpiry": return "passports.expiryDate";
            case "visaExpiry": return "passports.visas.expiryDate";
            case "rentalExpiry": return "passports.visas.rentals.endDate";
            default: return "id";
        }
    }


    @GetMapping("/employees/{employeeId}/immigration")
    public ResponseEntity<ApiResponse<EmployeeDto>> getImmigrationByEmployee(
            HttpServletRequest request,
            @PathVariable Long employeeId) {
        EmployeeDto employee = passportVisaRentalCertificateService.getImmigrationByEmployeeId(employeeId);
        return ResponseEntity.ok(ApiResponse.success(
                "Immigration fetched successfully", employee, request.getRequestURI()
        ));
    }

    @PostMapping("/empassports/{employeeId}/")
    public ResponseEntity<ApiResponse<EmployeeDto>> saveImmigration(
            HttpServletRequest request,
            @PathVariable Long employeeId,
            @Valid @RequestBody ImmigrationRequest immigrationRequest) {
        EmployeeDto dto = passportVisaRentalCertificateService.saveImmigration(employeeId, immigrationRequest);
        return ResponseEntity.ok(ApiResponse.success(
                "Immigration saved successfully", dto, request.getRequestURI()
        ));
    }

    // ---------------- PASSPORT CRUD ----------------

    @PostMapping("/em_passport/{employeeId}")
    public ResponseEntity<ApiResponse<Passport>> savePassport(
            HttpServletRequest request,
            @PathVariable Long employeeId,
            @Valid @RequestBody PassportDto passportDto) {
        Passport passport = passportVisaRentalCertificateService.savePassport(employeeId, passportDto);
        return ResponseEntity.ok(ApiResponse.success(
                "Passport saved successfully", passport, request.getRequestURI()
        ));
    }

    @GetMapping("/employees/{employeeId}/passports")
    public ResponseEntity<ApiResponse<List<Passport>>> getPassportsByEmployee(
            HttpServletRequest request,
            @PathVariable Long employeeId) {
        List<Passport> passports = passportVisaRentalCertificateService.getPassportsByEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success(
                "Passports fetched successfully", passports, request.getRequestURI()
        ));
    }

    @GetMapping("/passports/{passportId}")
    public ResponseEntity<ApiResponse<Passport>> getPassportById(
            HttpServletRequest request,
            @PathVariable Long passportId) {
        Passport passport = passportVisaRentalCertificateService.getPassport(passportId);
        return ResponseEntity.ok(ApiResponse.success(
                "Passport fetched successfully", passport, request.getRequestURI()
        ));
    }

    @PutMapping("/passports/{passportId}")
    public ResponseEntity<ApiResponse<Passport>> updatePassport(
            HttpServletRequest request,
            @PathVariable Long passportId,
            @Valid @RequestBody PassportDto passportDto) {
        Passport updated = passportVisaRentalCertificateService.updatePassport(passportId, passportDto);
        return ResponseEntity.ok(ApiResponse.success(
                "Passport updated successfully", updated, request.getRequestURI()
        ));
    }

    @DeleteMapping("/passports/{passportId}")
    public ResponseEntity<ApiResponse<Void>> deletePassport(
            HttpServletRequest request,
            @PathVariable Long passportId) {
        passportVisaRentalCertificateService.deletePassport(passportId);
        return ResponseEntity.ok(ApiResponse.success(
                "Passport deleted successfully", null, request.getRequestURI()
        ));
    }

    // ---------------- PASSPORT RENEW ----------------

    @PostMapping("/employees/{employeeId}/passports/renew")
    public ResponseEntity<ApiResponse<EmployeeDto>> renewPassport(
            HttpServletRequest request,
            @PathVariable Long employeeId,
            @Valid @RequestBody PassportDto passportDto) {
        EmployeeDto dto = passportVisaRentalCertificateService.renewPassport(employeeId, passportDto);
        return ResponseEntity.ok(ApiResponse.success(
                "Passport renewed successfully", dto, request.getRequestURI()
        ));
    }

    // ---------------- PASSPORT SUMMARY ----------------

    @GetMapping("/passports/summary")
    public ResponseEntity<ApiResponse<List<SummaryDto>>> getPassportSummary(HttpServletRequest request) {
        List<SummaryDto> summary = passportVisaRentalCertificateService.getPassportSummary();
        return ResponseEntity.ok(ApiResponse.success(
                "Passport summary fetched successfully", summary, request.getRequestURI()
        ));
    }

    @GetMapping("/passports/active")
    public ResponseEntity<ApiResponse<List<EmployeeWithPassportDto>>> getEmployeesWithActivePassports(
            HttpServletRequest request) {
        List<EmployeeWithPassportDto> result = passportVisaRentalCertificateService.getEmployeesWithActivePassports();
        return ResponseEntity.ok(ApiResponse.success(
                "Employees with ACTIVE passports fetched successfully", result, request.getRequestURI()
        ));
    }

    @GetMapping("/passports/expiring")
    public ResponseEntity<ApiResponse<List<EmployeeWithPassportDto>>> getEmployeesWithExpiringPassports(
            HttpServletRequest request) {
        List<EmployeeWithPassportDto> employees = passportVisaRentalCertificateService.getEmployeesWithExpiringPassports();
        return ResponseEntity.ok(ApiResponse.success(
                "Employees with expiring passports fetched successfully", employees, request.getRequestURI()
        ));
    }

    @GetMapping("/passports/expired")
    public ResponseEntity<ApiResponse<List<EmployeeWithPassportDto>>> getEmployeesWithExpiredPassports(
            HttpServletRequest request) {
        List<EmployeeWithPassportDto> employees = passportVisaRentalCertificateService.getEmployeesWithExpiredPassports();
        return ResponseEntity.ok(ApiResponse.success(
                "Employees with expired passports fetched successfully", employees, request.getRequestURI()
        ));
    }
}
