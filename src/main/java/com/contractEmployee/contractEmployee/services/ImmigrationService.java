package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.dto.request.ImmigrationRequest;
import com.contractEmployee.contractEmployee.dto.request.VisaSummaryDto;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationResponse;


import java.util.List;
public interface ImmigrationService {
    ApiResponse<List<EmployeeDto>> getImmigrationAll();
    ApiResponse<List<EmployeeDto>> getImmigrationByEmployeeId(Integer employeeId);
    ApiResponse<EmployeeDto> saveImmigration(Integer employeeId, ImmigrationRequest request);
//    VisaSummaryDto getVisaSummary();
    ApiResponse<VisaSummaryDto>getVisaSummary();
}
