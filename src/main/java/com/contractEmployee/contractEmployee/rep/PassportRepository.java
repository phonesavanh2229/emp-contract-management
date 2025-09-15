package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Integer> {
    Optional<Passport> findByEmployeeId(Integer employeeId);
}
