package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {
@Query("SELECT p FROM Passport p WHERE p.status = 'ACTIVE'")
    List<Passport> findActivePassports();
    List<Passport> findByExpiryDateBetweenAndStatus(LocalDate start, LocalDate end, String status);
    List<Passport> findByExpiryDateLessThanEqual(LocalDate date);
    // ✅ คืน passport ล่าสุดแค่ 1 อัน
@Query("SELECT p FROM Passport p " +
        "WHERE p.employee.id = :employeeId AND p.isCurrent = true " +
        "ORDER BY p.id DESC")
Optional<Passport> findCurrentPassportByEmployeeId(@Param("employeeId") Long employeeId);
    List<Passport> findAllByEmployeeIdAndIsCurrentTrue(Long employeeId);

    List<Passport> findByEmployeeId(Long employeeId);

    List<Passport> findByExpiryDateBeforeAndStatus(LocalDate today, String status);

    @Query("SELECT COUNT(p) FROM Passport p WHERE p.expiryDate < :today OR p.status = 'INACTIVE'")
    long countExpired(@Param("today") LocalDate today);

    @Query("SELECT COUNT(p) FROM Passport p WHERE p.expiryDate BETWEEN :today AND :next180Days AND p.status = 'ACTIVE'")
    long countExpiring(@Param("today") LocalDate today, @Param("next180Days") LocalDate next180Days);

    long countByStatus(String Status);

}
