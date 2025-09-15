package com.contractEmployee.contractEmployee.controller;

import com.contractEmployee.contractEmployee.dto.ApiResponse;
import com.contractEmployee.contractEmployee.dto.ImmigrationRequest;
import com.contractEmployee.contractEmployee.dto.ImmigrationResponse;
import com.contractEmployee.contractEmployee.dto.VisaSummaryDto;
import com.contractEmployee.contractEmployee.services.ImmigrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/immigration")
@RequiredArgsConstructor
public class ImmigrationController {

    private final ImmigrationService immigrationService;

    @PostMapping("/{employeeId}")
    public ResponseEntity<ImmigrationResponse> saveImmigration(
            @PathVariable Integer employeeId,
            @RequestBody ImmigrationRequest request) {
        return ResponseEntity.ok(immigrationService.saveImmigration(employeeId, request));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ImmigrationResponse> getImmigration(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(immigrationService.getImmigration(employeeId));
    }
    @GetMapping("/visa-summary")
    public ResponseEntity<ApiResponse<VisaSummaryDto>> getVisaSummary() {
        VisaSummaryDto summary = immigrationService.getVisaSummary();
        ApiResponse<VisaSummaryDto> response = new ApiResponse<>(
                true,
                "Visa summary fetched successfully",
                summary
        );
        return ResponseEntity.ok(response);
    }
}
