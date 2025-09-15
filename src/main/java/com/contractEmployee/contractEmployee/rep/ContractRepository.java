package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

//@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByEmployeeEmployeeId(Long employeeId);
    long countByEndDateBefore(LocalDate date);
    long countByEndDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT COUNT(c) FROM Contract c")
    long countAllContracts();

    @Query("SELECT COUNT(c) FROM Contract c WHERE c.endDate BETWEEN CURRENT_DATE AND :next30Days")
    long countExpiringContracts(@Param("next30Days") LocalDate next30Days);

    @Query("SELECT COUNT(c) FROM Contract c WHERE c.endDate < CURRENT_DATE")
    long countExpiredContracts();
}
