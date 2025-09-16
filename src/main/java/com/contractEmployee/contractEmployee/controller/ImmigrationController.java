package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.ImmigrationRequest;
import com.contractEmployee.contractEmployee.dto.request.VisaSummaryDto;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationListResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationResponse;
import com.contractEmployee.contractEmployee.services.ImmigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class ImmigrationController {

    private final ImmigrationService immigrationService;
    @PostMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> saveImmigration(
            @PathVariable Integer employeeId,
            @RequestBody ImmigrationRequest request) {

        return ResponseEntity.ok(
                immigrationService.saveImmigration(employeeId, request)
        );
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllImmigrations() {
        return ResponseEntity.ok(immigrationService.getImmigrationAll());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getImmigrationById(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(immigrationService.getImmigrationByEmployeeId(employeeId));
    }
    @GetMapping("/visa-summary")
    public ResponseEntity<ApiResponse<VisaSummaryDto>> getVisaSummary() {
        return ResponseEntity.ok(immigrationService.getVisaSummary());
    }
}
