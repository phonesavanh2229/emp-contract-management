package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithPassportDto;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithVisaDto;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassportVisaRentalCertificateService {

    List<EmployeeWithPassportDto> getEmployeesWithActivePassports();
    List<EmployeeWithPassportDto> getEmployeesWithExpiringPassports();
    List<EmployeeWithPassportDto> getEmployeesWithExpiredPassports();
    List<EmployeeWithVisaDto> getEmployeesWithActiveVisas();
    List<EmployeeWithVisaDto> getEmployeesWithExpiredVisas();

    // Immigration aggregate
    Page<EmployeeDto> getImmigrationAll(Pageable pageable);
    EmployeeDto getImmigrationByEmployeeId(Long employeeId);
    EmployeeDto saveImmigration(Long employeeId, ImmigrationRequest request);

    // Summary
    List<SummaryDto> getSummary();
    List<SummaryDto> getPassportSummary();
    List<SummaryDto> getVisaSummary();
    List<SummaryDto> getRentalCertificateSummary();

    // Renew
    EmployeeDto renewPassport(Long employeeId, PassportDto passportDto);
    EmployeeDto renewVisa(Long passportId, VisaDto visaDto);
    EmployeeDto renewRental(Long rentalId, RentalCertificateDto rentalDto);

    // Passport
    Passport savePassport(Long employeeId, PassportDto passportDto);
    Passport getPassport(Long passportId);
    List<Passport> getPassportsByEmployee(Long employeeId);
    Passport updatePassport(Long passportId, PassportDto passportDto);
    void deletePassport(Long passportId);

    // Visa
    Visa saveVisa(Long passportId, VisaDto visaDto);
    List<Visa> getVisa(Long visaId);
    List<Visa> getVisasByPassport(Long passportId);
    Visa updateVisa(Long visaId, VisaDto visaDto);
    void deleteVisa(Long visaId);

    // Rental
    RentalCertificate saveRental(Long visaId, RentalCertificateDto rentalDto);
    RentalCertificate getRental(Long rentalId);
    List<RentalCertificate> getRentalsByVisa(Long visaId);
    RentalCertificate updateRental(Long rentalId, RentalCertificateDto rentalDto);
    void deleteRental(Long rentalId);
}
