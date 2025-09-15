package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisaRepository extends JpaRepository<Visa, Integer> {
    List<Visa> findByPassportId(Integer passportId);
    Optional<Visa> findTopByPassportIdOrderByCreatedAtDesc(Integer passportId);
    List<Visa> findByExpiryDateAfter(LocalDate date);   // Active
    List<Visa> findByExpiryDateBefore(LocalDate date);  // Expired
    List<Visa> findByExpiryDateBetween(LocalDate start, LocalDate end);
    @Query("SELECT COUNT(v) FROM Visa v WHERE v.expiryDate BETWEEN :today AND :next30Days")
    long countExpiringBetween(@Param("today") LocalDate today, @Param("next30Days") LocalDate next30Days);
    long countByVisaStatus(String visaStatus);

    long countByExpiryDateBefore(LocalDate today);

    long countByExpiryDateAfter(LocalDate today);

    long countByExpiryDateBetween(LocalDate start, LocalDate end);

}
