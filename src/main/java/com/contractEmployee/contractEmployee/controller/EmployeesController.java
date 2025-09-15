package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.*;
import com.contractEmployee.contractEmployee.services.ContractService;
import com.contractEmployee.contractEmployee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeeService employeeService;
    private final ContractService contractService;

    /**
     * Create full employee with contract, ID card, education, and files.
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/full")
    public ResponseEntity<ApiResponse<EmployeeResponse>> createFullEmployee(
            @RequestBody CompositeEmployeeRequest request
    ) {
        EmployeeResponse saved = employeeService.createFullEmployee(request);
        return ResponseEntity.ok(ApiResponse.success("Employee with contract, ID card, and education saved", saved));
    }

    /**
     * Get all employees with basic and extended info.
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        List<EmployeeResponse> all = employeeService.getAllEmployees();
        return ResponseEntity.ok(ApiResponse.success("List of all employees", all));
    }

    /**
     * Get employee by ID.
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse dto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.success("Employee found", dto));
    }

    /**
     * Get contract summary stats.
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/contract-summary")
    public ResponseEntity<ApiResponse<ContractStatsDto>> getContractStats() {
        ContractStatsDto stats = new ContractStatsDto(
                contractService.getTotalContracts(),
                contractService.getExpiringContracts(),
                contractService.getExpiredContracts()
        );
        return ResponseEntity.ok(ApiResponse.success("Contract summary fetched", stats));
    }

    /**
     * Delete an employee by ID.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted", null));
    }

    /**
     * Update basic employee info (excluding contract, files, etc).
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request
    ) {
        EmployeeResponse updated = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(ApiResponse.success("Employee updated", updated));
    }
}
