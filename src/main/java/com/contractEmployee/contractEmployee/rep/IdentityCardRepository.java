package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.IdentityCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {
    boolean existsByCardNumber(String cardNumber);
    List<IdentityCard> findByEmployeeEmployeeId(Long employeeId);
}
