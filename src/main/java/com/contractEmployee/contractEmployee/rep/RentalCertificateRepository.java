package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RentalCertificateRepository extends JpaRepository<RentalCertificate, Integer> {
    List<RentalCertificate> findAllByVisaIdAndIsCurrentTrue(Integer visaId);
    List<RentalCertificate> findByVisa_Passport_Id(Integer passportId);

    List<RentalCertificate> findByVisaId(Integer visaId);

    List<RentalCertificate> findByVisaIdAndIsCurrentTrue(Integer visaId);

    long countByStatus(String status);

    // ✅ ใช้ endDate แทน expiryDate
    List<RentalCertificate> findByEndDateBeforeAndStatus(LocalDate today, String status);

    // ถ้าจะนับช่วงวันที่ ใช้ startDate หรือ endDate ให้ตรงกับ entity
    long countByStartDateBetween(LocalDate start, LocalDate end);

    // ✅ Active
    @Query("SELECT COUNT(r) FROM RentalCertificate r WHERE r.endDate >= :today AND r.status = 'ACTIVE'")
    long countActive(@Param("today") LocalDate today);

    // ✅ Expiring
    @Query("SELECT COUNT(r) FROM RentalCertificate r WHERE r.endDate BETWEEN :today AND :next30Days AND r.status = 'ACTIVE'")
    long countExpiring(@Param("today") LocalDate today, @Param("next30Days") LocalDate next30Days);

    // ✅ Expired
    @Query("SELECT COUNT(r) FROM RentalCertificate r WHERE r.endDate < :today OR r.status = 'EXPIRED'")
    long countExpired(@Param("today") LocalDate today);
}
