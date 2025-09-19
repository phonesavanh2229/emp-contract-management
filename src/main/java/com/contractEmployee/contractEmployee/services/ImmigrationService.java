package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithPassportDto;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithVisaDto;
import com.contractEmployee.contractEmployee.dto.response.SummaryPVRDto;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;

import java.util.List;

public interface ImmigrationService {

    List<EmployeeWithPassportDto> getEmployeesWithActivePassports();
    List<EmployeeWithPassportDto> getEmployeesWithExpiringPassports();
    List<EmployeeWithPassportDto> getEmployeesWithExpiredPassports();
    List<EmployeeWithVisaDto> getEmployeesWithActiveVisas();
    List<EmployeeWithVisaDto> getEmployeesWithExpiredVisas();

//    List<EmployeeWithVisaDto> getEmployeesWithExpiringVisas();
    // Immigration aggregate
    List<EmployeeDto> getImmigrationAll();
    EmployeeDto getImmigrationByEmployeeId(Integer employeeId);
    EmployeeDto saveImmigration(Integer employeeId, ImmigrationRequest request);

    // Summary
    List<SummaryDto> getSummary();
    List<SummaryDto> getPassportSummary();
    List<SummaryDto> getVisaSummary();
    List<SummaryDto> getRentalCertificateSummary();

    // Renew
    EmployeeDto renewPassport(Integer employeeId, PassportDto passportDto);
    EmployeeDto renewVisa(Integer passportId, VisaDto visaDto);
    EmployeeDto renewRental(Integer rentalId, RentalCertificateDto rentalDto);

    // Passport
    Passport savePassport(Integer employeeId, PassportDto passportDto);
    Passport getPassport(Integer passportId);
    List<Passport> getPassportsByEmployee(Integer employeeId);
    Passport updatePassport(Integer passportId, PassportDto passportDto);
    void deletePassport(Integer passportId);

    // Visa
    Visa saveVisa(Integer passportId, VisaDto visaDto);
    List<Visa> getVisa(Integer visaId);
    List<Visa> getVisasByPassport(Integer passportId);
    Visa updateVisa(Integer visaId, VisaDto visaDto);
    void deleteVisa(Integer visaId);

    // Rental
    RentalCertificate saveRental(Integer visaId, RentalCertificateDto rentalDto);
    RentalCertificate getRental(Integer rentalId);
    List<RentalCertificate> getRentalsByVisa(Integer visaId);
    RentalCertificate updateRental(Integer rentalId, RentalCertificateDto rentalDto);
    void deleteRental(Integer rentalId);
}
