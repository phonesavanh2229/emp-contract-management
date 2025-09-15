package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RentalCertificateRepository extends JpaRepository<RentalCertificate, Integer> {
    List<RentalCertificate> findByVisa_Passport_Id(Integer passportId);
}