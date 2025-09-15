package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.CreateFullPassportRequest;
import com.contractEmployee.contractEmployee.dto.PassportDto;

import java.util.List;

public interface PassportService {
    PassportDto createPassport(Integer employeeId, CreateFullPassportRequest request);

    List<PassportDto> getPassportsByEmployee(Integer employeeId);
}
