package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.ContractFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractFileRepository extends JpaRepository<ContractFile, Long> {
    List<ContractFile> findByContractContractId(Long contractId);
}