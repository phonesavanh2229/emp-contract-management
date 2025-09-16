package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.CreateFullPassportRequest;
import com.contractEmployee.contractEmployee.dto.request.PassportDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PassportServiceImpl implements PassportService {
    @Override
    public PassportDto createPassport(Integer employeeId, CreateFullPassportRequest request) {
        // TODO: Implement actual logic
        return new PassportDto();
    }

    @Override
    public List<PassportDto> getPassportsByEmployee(Integer employeeId) {
        // TODO: Implement actual logic
        return Collections.emptyList();
    }
}

