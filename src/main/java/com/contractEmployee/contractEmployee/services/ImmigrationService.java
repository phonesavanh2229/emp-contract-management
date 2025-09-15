package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.ImmigrationRequest;
import com.contractEmployee.contractEmployee.dto.ImmigrationResponse;
import com.contractEmployee.contractEmployee.dto.VisaSummaryDto;

public interface ImmigrationService {
    ImmigrationResponse saveImmigration(Integer employeeId, ImmigrationRequest request);
    ImmigrationResponse getImmigration(Integer employeeId);
    VisaSummaryDto getVisaSummary();
}
