package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.CreateFullPassportRequest;
import com.contractEmployee.contractEmployee.dto.request.PassportDto;

import java.util.List;

public interface PassportService {
    PassportDto createPassport(Integer employeeId, CreateFullPassportRequest request);

    List<PassportDto> getPassportsByEmployee(Integer employeeId);
}
