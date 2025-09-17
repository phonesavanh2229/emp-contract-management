package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
import com.contractEmployee.contractEmployee.dto.response.ImmigrationResponse;


import java.util.List;
public interface ImmigrationService {
    ApiResponse<List<EmployeeDto>> getImmigrationAll();
    ApiResponse<List<EmployeeDto>> getImmigrationByEmployeeId(Integer employeeId);
    ApiResponse<EmployeeDto> saveImmigration(Integer employeeId, ImmigrationRequest request);
    ApiResponse<List<SummaryDto>>getPassportSummary();
    ApiResponse<List<SummaryDto>>getVisaSummary();
    ApiResponse<List<SummaryDto>>getRentalCertificateSummary();
    ApiResponse<EmployeeDto> renewPassport(Integer employeeId, PassportDto passportDto);
    ApiResponse<EmployeeDto> renewVisa(Integer passportId, VisaDto visaDto);
    ApiResponse<EmployeeDto> renewRental(Integer visaId, RentalCertificateDto rentalDto);



    // Passport
    ApiResponse<PassportDto> savePassport(Integer employeeId, PassportDto passportDto);
    ApiResponse<PassportDto> getPassport(Integer passportId);
    ApiResponse<List<PassportDto>> getPassportsByEmployee(Integer employeeId);
    ApiResponse<PassportDto> updatePassport(Integer passportId, PassportDto passportDto);
    ApiResponse<Void> deletePassport(Integer passportId);

    // Visa
    ApiResponse<VisaDto> saveVisa(Integer passportId, VisaDto visaDto);
    ApiResponse<VisaDto> getVisa(Integer visaId);
    ApiResponse<List<VisaDto>> getVisasByPassport(Integer passportId);
    ApiResponse<VisaDto> updateVisa(Integer visaId, VisaDto visaDto);
    ApiResponse<Void> deleteVisa(Integer visaId);

    // Rental
    ApiResponse<RentalCertificateDto> saveRental(Integer visaId, RentalCertificateDto rentalDto);
    ApiResponse<RentalCertificateDto> getRental(Integer rentalId);
    ApiResponse<List<RentalCertificateDto>> getRentalsByVisa(Integer visaId);
    ApiResponse<RentalCertificateDto> updateRental(Integer rentalId, RentalCertificateDto rentalDto);
    ApiResponse<Void> deleteRental(Integer rentalId);
}
