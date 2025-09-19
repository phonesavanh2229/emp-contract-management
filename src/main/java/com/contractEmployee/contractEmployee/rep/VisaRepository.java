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
    List<Visa> findByPassportIdAndIsCurrentTrue(Integer passportId);
    List<Visa>  findByExpiryDateLessThanEqual(LocalDate today);
    List<Visa> findByExpiryDateLessThanEqualAndStatus(LocalDate date, String status);

    //    List<Visa>  findByExpiryDateBeforeAndStatusNot(LocalDate today, LocalDate todays,String status);
    // ✅ Active visas
//    List<Visa> findByExpiryDateBeforeAndStatusNot(LocalDate today, String status);
//    Optional<Visa> findFirstByPassportIdAndIsCurrentTrue(Integer passportId);
//    List<Visa> findAllByPassportIdAndIsCurrentTrue(Integer passportId);
    // ✅ Active visas
    @Query("SELECT COUNT(v) FROM Visa v WHERE v.expiryDate >= :today AND v.status = 'ACTIVE'")
    long countActive(@Param("today") LocalDate today);
    // Find expired visas that are still ACTIVE
    List<Visa> findByExpiryDateBeforeAndStatus(LocalDate today, String status);
    List<Visa> findByStatus(String status);

    // ✅ Expiring within 30 days
    @Query("SELECT COUNT(v) FROM Visa v WHERE v.status = 'ACTIVE' AND v.expiryDate BETWEEN :today AND :next30Days")
    long countExpiring(@Param("today") LocalDate today, @Param("next30Days") LocalDate next30Days);
    // ✅ Expired
    @Query("SELECT COUNT(v) FROM Visa v WHERE v.expiryDate < :today OR v.status = 'INACTIVE'")
    long countExpired(@Param("today") LocalDate today);

}
