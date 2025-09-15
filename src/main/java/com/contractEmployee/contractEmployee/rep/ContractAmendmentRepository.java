package com.contractEmployee.contractEmployee.rep;


import com.contractEmployee.contractEmployee.entity.ContractAmendment;
import org.springframework.data.jpa.repository.JpaRepository;
//
import java.util.List;

public interface ContractAmendmentRepository extends JpaRepository<ContractAmendment, Long> {
    List<ContractAmendment> findByContractContractId(Long contractId);
}