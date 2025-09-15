package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.rep.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepo;

    public long getTotalContracts() {
        return contractRepo.countAllContracts();
    }

    public long getExpiringContracts() {
        LocalDate next30Days = LocalDate.now().plusDays(30);
        return contractRepo.countExpiringContracts(next30Days);
    }

    public long getExpiredContracts() {
        return contractRepo.countExpiredContracts();
    }
}
