//package com.contractEmployee.contractEmployee.services;
//
//import com.contractEmployee.contractEmployee.dto.request.*;
//import com.contractEmployee.contractEmployee.dto.response.ApiResponse;
//import com.contractEmployee.contractEmployee.entity.Employee;
//import com.contractEmployee.contractEmployee.entity.Passport;
//import com.contractEmployee.contractEmployee.entity.RentalCertificate;
//import com.contractEmployee.contractEmployee.entity.Visa;
//import com.contractEmployee.contractEmployee.rep.EmployeeRepository;
//import com.contractEmployee.contractEmployee.rep.PassportRepository;
//import com.contractEmployee.contractEmployee.rep.RentalCertificateRepository;
//import com.contractEmployee.contractEmployee.rep.VisaRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class ImmigrationServiceImplBK implements ImmigrationService {
//
//    private final EmployeeRepository employeeRepository;
//    private final PassportRepository passportRepository;
//    private final VisaRepository visaRepository;
//    private final RentalCertificateRepository rentalRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse getImmigrationAll() {
//        List<EmployeeDto> employeeDtos = employeeRepository.findAll()
//                .stream()
//                .map(this::mapEmployeeWithImmigration)
//                .collect(Collectors.toList());
//
//        return  ApiResponse.success("get Data successfully",employeeDtos); // ✅ wrap
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<List<EmployeeDto>> getImmigrationByEmployeeId(Integer employeeId) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));
//
//        EmployeeDto dto = mapEmployeeWithImmigration(employee);
//        return ApiResponse.success("get Data successfully", List.of(dto));
//    }
//    @Override
//    @Transactional
//    public ApiResponse<List<SummaryDto>> getPassportSummary() {
//        LocalDate today = LocalDate.now();
//        LocalDate next180Days = today.plusDays(180);
//
//        // 1️⃣ อัปเดต passport ที่หมดอายุแล้ว → INACTIVE
//        List<Passport> expiredPassports = passportRepository.findByExpiryDateBeforeAndStatus(today, "ACTIVE");
//        expiredPassports.forEach(p -> p.setStatus("INACTIVE"));
//        passportRepository.saveAll(expiredPassports);
//
//        // 2️⃣ นับจำนวน
//        long active = passportRepository.countByStatus("ACTIVE");
//        long expiring = passportRepository.countExpiring(today, next180Days);
//        long expired = passportRepository.countExpired(today);
//
//        SummaryDto summary = new SummaryDto(active, expiring, expired);
//        return ApiResponse.success("Passport summary fetched successfully", List.of(summary));
//    }
//
//
//
//    @Transactional
//    public ApiResponse<List<SummaryDto>> getVisaSummary() {
//        LocalDate today = LocalDate.now();
//        LocalDate next30Days = today.plusDays(30);
//        List<Visa> expiredVisas = visaRepository.findByExpiryDateBeforeAndStatus(today, "ACTIVE");
//        expiredVisas.forEach(v -> {
//            v.setStatus("INACTIVE");
//            v.setIsCurrent(false);
//        });
//        visaRepository.saveAll(expiredVisas);
//
//        // 2️⃣ Count correctly
//        long active = visaRepository.countActive(today);
//        long expiring = visaRepository.countExpiring(today, next30Days);
//        long expired = visaRepository.countExpired(today); // ✅ FIXED
//
//        SummaryDto summary = new SummaryDto(active, expiring, expired);
//        return ApiResponse.success("Visa summary fetched successfully", List.of(summary));
//    }
//
//    @Override
//    @Transactional
//    public ApiResponse<List<SummaryDto>> getRentalCertificateSummary() {
//        LocalDate today = LocalDate.now();
//        LocalDate next30Days = today.plusDays(30);
//
//        // 1️⃣ update expired rental certificate → EXPIRED
//        List<RentalCertificate> expiredRentals =
//                rentalRepository.findByEndDateBeforeAndStatus(today, "ACTIVE");
//        expiredRentals.forEach(r -> r.setStatus("EXPIRED"));
//        rentalRepository.saveAll(expiredRentals);
//
//        // 2️⃣ count summary
//        long active = rentalRepository.countActive(today);
//        long expiring = rentalRepository.countExpiring(today, next30Days);
//        long expired = rentalRepository.countExpired(today);
//
//        SummaryDto summary = new SummaryDto(active, expiring, expired);
//        return ApiResponse.success("Rental certificate summary fetched successfully", List.of(summary));
//    }
//
//
//
//    @Override
//    @Transactional
//    public ApiResponse<EmployeeDto> saveImmigration(Integer employeeId, ImmigrationRequest request) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));
//        PassportDto passportDto = request.getPassport();
//        Passport passport = new Passport();
//        passport.setEmployee(employee);
//        passport.setPassportNo(passportDto.getPassportNumber());
//        passport.setPassportType(passportDto.getPassportType());
//        passport.setCountryCode(passportDto.getCountry());
//        passport.setIssuePlace(passportDto.getIssuePlace());
//        passport.setStatus(passportDto.getStatus());
//        passport.setIssueDate(passportDto.getIssueDate());
//        passport.setExpiryDate(passportDto.getExpiryDate());
//        passport = passportRepository.save(passport);
//
//
//        if (request.getVisas() != null) {
//            for (VisaDto v : request.getVisas()) {
//                Visa visa = new Visa();
//                visa.setPassport(passport);
//                visa.setVisaNumber(v.getVisaNumber());
//                visa.setVisaType(v.getVisaType());
//                visa.setVisaPurpose(v.getVisaPurpose());
//                visa.setCountryCode(v.getCountry());
//                visa.setIssuePlace(v.getIssuePlace());
//                visa.setEntries(v.getEntries());
//                visa.setStatus(v.getStatus());
//                visa.setIssueDate(v.getIssueDate());
//                visa.setExpiryDate(v.getExpiryDate());
//                Visa savedVisa = visaRepository.save(visa);
//
//                // ✅ Save Rentals for each Visa
//                if (request.getRentalCertificates() != null) {
//                    for (RentalCertificateDto r : request.getRentalCertificates()) {
//                        RentalCertificate rc = new RentalCertificate();
//                        rc.setVisa(savedVisa);
//                        rc.setCertificateNumber(r.getCertificateNumber());
//                        rc.setRentalType(r.getRentalType());
//                        rc.setAddress(r.getAddress());
//                        rc.setLandlordName(r.getLandlordName());
//                        rc.setLandlordContact(r.getLandlordContact());
//                        rc.setStartDate(r.getStartDate());
//                        rc.setEndDate(r.getEndDate());
//                        rc.setStatus(r.getStatus());
//                        rentalRepository.save(rc);
//                    }
//                }
//            }
//        }
//
//
//        EmployeeDto dto = mapEmployeeWithImmigration(employee);
//        return ApiResponse.success("Immigration data saved successfully", dto);
//    }
////    --------------------------Passport------------
//    @Override
//    @Transactional
//    public ApiResponse<PassportDto> savePassport(Integer employeeId, PassportDto passportDto) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));
//
//        Passport passport = new Passport();
//        passport.setEmployee(employee);
//        passport.setPassportNo(passportDto.getPassportNumber());
//        passport.setPassportType(passportDto.getPassportType());
//        passport.setCountryCode(passportDto.getCountry());
//        passport.setIssuePlace(passportDto.getIssuePlace());
//        passport.setStatus(passportDto.getStatus());
//        passport.setIssueDate(passportDto.getIssueDate());
//        passport.setExpiryDate(passportDto.getExpiryDate());
//
//        passportRepository.save(passport);
//        return ApiResponse.success("Passport saved successfully", passportDto);
//    }
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<PassportDto> getPassport(Integer passportId) {
//        Passport passport = passportRepository.findById(passportId)
//                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));
//        return ApiResponse.success("Passport fetched successfully", mapPassport(passport));
//    }
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<List<PassportDto>> getPassportsByEmployee(Integer employeeId) {
//        List<Passport> passports = passportRepository.findByEmployeeId(employeeId);
//        return ApiResponse.success(
//                "Passports fetched successfully",
//                passports.stream().map(this::mapPassport).toList()
//        );
//    }
//
//
//    @Override
//    @Transactional
//    public ApiResponse<PassportDto> updatePassport(Integer passportId, PassportDto passportDto) {
//        Passport passport = passportRepository.findById(passportId)
//                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));
//
//        passport.setPassportNo(passportDto.getPassportNumber());
//        passport.setPassportType(passportDto.getPassportType());
//        passport.setCountryCode(passportDto.getCountry());
//        passport.setIssuePlace(passportDto.getIssuePlace());
//        passport.setStatus(passportDto.getStatus());
//        passport.setIssueDate(passportDto.getIssueDate());
//        passport.setExpiryDate(passportDto.getExpiryDate());
//
//        passportRepository.save(passport);
//        return ApiResponse.success("Passport updated successfully", mapPassport(passport));
//    }
//
//    @Override
//    @Transactional
//    public ApiResponse<Void> deletePassport(Integer passportId) {
//        if (!passportRepository.existsById(passportId)) {
//            throw new EntityNotFoundException("Passport not found with ID: " + passportId);
//        }
//
//        passportRepository.deleteById(passportId);
//        return ApiResponse.success("Passport deleted successfully", null);
//    }
//
//    @Override
//
//    @Transactional
//    public ApiResponse<EmployeeDto> renewPassport(Integer employeeId, PassportDto dto) {
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));
//
//        // หา current passport เก่า
//        List<Passport> currents = passportRepository.findAllByEmployeeIdAndIsCurrentTrue(employeeId);
//
//        Passport previous = null;
//        if (!currents.isEmpty()) {
//            // เลือกอันแรก (กรณีปกติควรมีแค่ 1)
//            previous = currents.get(0);
//
//            // ปิดของเก่า
//            previous.setIsCurrent(false);
//            previous.setStatus("INACTIVE");
//            passportRepository.save(previous);
//
//            // ถ้ามีเกิน 1 ก็ปิดให้หมด
//            if (currents.size() > 1) {
//                for (int i = 1; i < currents.size(); i++) {
//                    Passport old = currents.get(i);
//                    old.setIsCurrent(false);
//                    old.setStatus("INACTIVE");
//                    passportRepository.save(old);
//                }
//            }
//        }
//
//        // สร้าง passport ใหม่
//        Passport newPassport = new Passport();
//        newPassport.setEmployee(employee);
//        newPassport.setPassportNo(dto.getPassportNumber());
//        newPassport.setPassportType(dto.getPassportType());
//        newPassport.setCountryCode(dto.getCountry());
//        newPassport.setIssuePlace(dto.getIssuePlace());
//        newPassport.setIssueDate(dto.getIssueDate());
//        newPassport.setExpiryDate(dto.getExpiryDate());
//        newPassport.setStatus("ACTIVE");
//        newPassport.setIsCurrent(true);
//
//        // ✅ set previous_passport_id
//        if (previous != null) {
//            newPassport.setPreviousPassport(previous);
//        }
//
//        passportRepository.save(newPassport);
//
//        return ApiResponse.success("Passport renewed successfullImmigrationServiceImply",
//                mapEmployeeWithImmigration(employee));
//    }
//
//
//
//    @Override
//    @Transactional
//    public ApiResponse<VisaDto> saveVisa(Integer passportId, VisaDto visaDto) {
//        Passport passport = passportRepository.findById(passportId)
//                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));
//
//        Visa visa = new Visa();
//        visa.setPassport(passport);
//        visa.setVisaNumber(visaDto.getVisaNumber());
//        visa.setVisaType(visaDto.getVisaType());
//        visa.setVisaPurpose(visaDto.getVisaPurpose());
//        visa.setCountryCode(visaDto.getCountry());
//        visa.setIssuePlace(visaDto.getIssuePlace());
//        visa.setEntries(visaDto.getEntries());
//        visa.setStatus(visaDto.getStatus());
//        visa.setIssueDate(visaDto.getIssueDate());
//        visa.setExpiryDate(visaDto.getExpiryDate());
//
//        visaRepository.save(visa);
//        return ApiResponse.success("Visa saved successfully", visaDto);
//    }
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<VisaDto> getVisa(Integer visaId) {
//        Visa visa = visaRepository.findById(visaId)
//                .orElseThrow(() -> new EntityNotFoundException("Visa not found: " + visaId));
//        return ApiResponse.success("Visa fetched successfully", mapVisa(visa));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<List<VisaDto>> getVisasByPassport(Integer passportId) {
//        List<Visa> visas = visaRepository.findByPassportId(passportId);
//        return ApiResponse.success("Visas fetched successfully",
//                visas.stream().map(this::mapVisa).toList());
//    }
//
//    // ---------------------- Renew Visa ----------------------
//    @Transactional
//    public ApiResponse<EmployeeDto> renewVisa(Integer passportId, VisaDto dto) {
//        Passport passport = passportRepository.findById(passportId)
//                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));
//
//        // expire old visa
//        visaRepository.findByPassportIdAndIsCurrentTrue(passportId)
//                .forEach(old -> {
//                    old.setIsCurrent(false);
//                    old.setStatus("EXPIRED");
//                    visaRepository.save(old);
//                });
//
//        // create new visa
//        Visa newVisa = new Visa();
//        newVisa.setPassport(passport);
//        newVisa.setVisaNumber(dto.getVisaNumber());
//        newVisa.setVisaType(dto.getVisaType());
//        newVisa.setVisaPurpose(dto.getVisaPurpose());
//        newVisa.setCountryCode(dto.getCountry());
//        newVisa.setIssuePlace(dto.getIssuePlace());
//        newVisa.setIssueDate(dto.getIssueDate());
//        newVisa.setExpiryDate(dto.getExpiryDate());
//        newVisa.setStatus("ACTIVE");
//        newVisa.setIsCurrent(true);
//
//        visaRepository.save(newVisa);
//
//        return ApiResponse.success("Visa renewed successfully",
//                mapEmployeeWithImmigration(passport.getEmployee()));
//    }
//
//    @Override
//    @Transactional
//    public ApiResponse<VisaDto> updateVisa(Integer visaId, VisaDto visaDto) {
//        Visa visa = visaRepository.findById(visaId)
//                .orElseThrow(() -> new EntityNotFoundException("Visa not found: " + visaId));
//
//        visa.setVisaNumber(visaDto.getVisaNumber());
//        visa.setVisaType(visaDto.getVisaType());
//        visa.setVisaPurpose(visaDto.getVisaPurpose());
//        visa.setCountryCode(visaDto.getCountry());
//        visa.setIssuePlace(visaDto.getIssuePlace());
//        visa.setEntries(visaDto.getEntries());
//        visa.setStatus(visaDto.getStatus());
//        visa.setIssueDate(visaDto.getIssueDate());
//        visa.setExpiryDate(visaDto.getExpiryDate());
//
//        visaRepository.save(visa);
//        return ApiResponse.success("Visa updated successfully", mapVisa(visa));
//    }
//
//    @Override
//    @Transactional
//    public ApiResponse<Void> deleteVisa(Integer visaId) {
//        visaRepository.deleteById(visaId);
//        return ApiResponse.success("Visa deleted successfully", null);
//    }
//
//    // ---------------------- Renew Rental ----------------------
//
//    @Override
//    @Transactional
//    public ApiResponse<RentalCertificateDto> saveRental(Integer visaId, RentalCertificateDto rentalDto) {
//        Visa visa = visaRepository.findById(visaId)
//                .orElseThrow(() -> new EntityNotFoundException("Visa not found: " + visaId));
//
//        RentalCertificate rc = new RentalCertificate();
//        rc.setVisa(visa);
//        rc.setCertificateNumber(rentalDto.getCertificateNumber());
//        rc.setRentalType(rentalDto.getRentalType());
//        rc.setAddress(rentalDto.getAddress());
//        rc.setLandlordName(rentalDto.getLandlordName());
//        rc.setLandlordContact(rentalDto.getLandlordContact());
//        rc.setStartDate(rentalDto.getStartDate());
//        rc.setEndDate(rentalDto.getEndDate());
//        rc.setStatus(rentalDto.getStatus());
//
//        rentalRepository.save(rc);
//        return ApiResponse.success("Rental Certificate saved successfully", rentalDto);
//    }
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<RentalCertificateDto> getRental(Integer rentalId) {
//        RentalCertificate rc = rentalRepository.findById(rentalId)
//                .orElseThrow(() -> new EntityNotFoundException("Rental not found: " + rentalId));
//        return ApiResponse.success("Rental fetched successfully", mapRental(rc));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ApiResponse<List<RentalCertificateDto>> getRentalsByVisa(Integer visaId) {
//        List<RentalCertificate> rentals = rentalRepository.findByVisaId(visaId);
//        return ApiResponse.success("Rentals fetched successfully",
//                rentals.stream().map(this::mapRental).toList());
//    }
//    @Override
//    @Transactional
//    public ApiResponse<RentalCertificateDto> updateRental(Integer rentalId, RentalCertificateDto rentalDto) {
//        RentalCertificate rc = rentalRepository.findById(rentalId)
//                .orElseThrow(() -> new EntityNotFoundException("Rental not found: " + rentalId));
//
//        rc.setCertificateNumber(rentalDto.getCertificateNumber());
//        rc.setRentalType(rentalDto.getRentalType());
//        rc.setAddress(rentalDto.getAddress());
//        rc.setLandlordName(rentalDto.getLandlordName());
//        rc.setLandlordContact(rentalDto.getLandlordContact());
//        rc.setStartDate(rentalDto.getStartDate());
//        rc.setEndDate(rentalDto.getEndDate());
//        rc.setStatus(rentalDto.getStatus());
//
//        rentalRepository.save(rc);
//        return ApiResponse.success("Rental updated successfully", mapRental(rc));
//    }
//
//    @Override
//    @Transactional
//    public ApiResponse<Void> deleteRental(Integer rentalId) {
//        rentalRepository.deleteById(rentalId);
//        return ApiResponse.success("Rental deleted successfully", null);
//    }
//
//    @Transactional
//    public ApiResponse<EmployeeDto> renewRental(Integer rentalId, RentalCertificateDto dto) {
//        // หา rental เก่า
//        RentalCertificate oldRental = rentalRepository.findById(rentalId)
//                .orElseThrow(() -> new EntityNotFoundException("Rental not found: " + rentalId));
//
//        Visa visa = oldRental.getVisa();
//
//        // expire rental เก่า
//        oldRental.setIsCurrent(false);
//        oldRental.setStatus("INACTIVE");
//        rentalRepository.save(oldRental);
//
//        // create rental ใหม่
//        RentalCertificate newRental = new RentalCertificate();
//        newRental.setVisa(visa);
//        newRental.setCertificateNumber(dto.getCertificateNumber());
//        newRental.setRentalType(dto.getRentalType());
//        newRental.setAddress(dto.getAddress());
//        newRental.setLandlordName(dto.getLandlordName());
//        newRental.setLandlordContact(dto.getLandlordContact());
//        newRental.setStartDate(dto.getStartDate());
//        newRental.setEndDate(dto.getEndDate());
//        newRental.setStatus("ACTIVE");
//        newRental.setIsCurrent(true);
//
//        // อ้างอิง rental เก่า
//        newRental.setPreviousRental(oldRental);
//
//        rentalRepository.save(newRental);
//
//        return ApiResponse.success("Rental certificate renewed successfully",
//                mapEmployeeWithImmigration(visa.getPassport().getEmployee()));
//    }
//
//
//    private PassportDto mapPassport(Passport passport) {
//        PassportDto dto = new PassportDto();
//        dto.setPassportNumber(passport.getPassportNo());
//        dto.setPassportType(passport.getPassportType());
//        dto.setCountry(passport.getCountryCode());
//        dto.setIssuePlace(passport.getIssuePlace());
//        dto.setStatus(passport.getStatus());
//        dto.setIssueDate(passport.getIssueDate());
//        dto.setExpiryDate(passport.getExpiryDate());
//        return dto;
//    }
//    private VisaDto mapVisa(Visa visa) {
//        VisaDto dto = new VisaDto();
//        dto.setVisaNumber(visa.getVisaNumber());
//        dto.setVisaType(visa.getVisaType());
//        dto.setVisaPurpose(visa.getVisaPurpose());
//        dto.setCountry(visa.getCountryCode());
//        dto.setIssuePlace(visa.getIssuePlace());
//        dto.setEntries(visa.getEntries());
//        dto.setStatus(visa.getStatus());
//        dto.setIssueDate(visa.getIssueDate());
//        dto.setExpiryDate(visa.getExpiryDate());
//        return dto;
//    }
//    private RentalCertificateDto mapRental(RentalCertificate rc) {
//        RentalCertificateDto dto = new RentalCertificateDto();
//        dto.setCertificateNumber(rc.getCertificateNumber());
//        dto.setRentalType(rc.getRentalType());
//        dto.setAddress(rc.getAddress());
//        dto.setLandlordName(rc.getLandlordName());
//        dto.setLandlordContact(rc.getLandlordContact());
//        dto.setStartDate(rc.getStartDate());
//        dto.setEndDate(rc.getEndDate());
//        dto.setStatus(rc.getStatus());
//        return dto;
//    }
//
//    private EmployeeDto mapEmployeeWithImmigration(Employee e) {
//        EmployeeDto dto = new EmployeeDto();
//        dto.setId(e.getId());
//        dto.setStaffCode(e.getStaffCode());
//        dto.setFirstName(e.getFirstName());
//        dto.setLastName(e.getLastName());
//        dto.setPhone(e.getPhone());
//        dto.setEmail(e.getEmail());
//        dto.setProvince(e.getProvince());
//        dto.setVillage(e.getVillage());
//
//        // ✅ พนักงานมีได้หลาย passport
//        List<PassportDto> passportDtos = passportRepository.findByEmployeeId(e.getId())
//                .stream()
//                .map(passport -> {
//                    PassportDto passportDto = new PassportDto();
//                    passportDto.setId(passport.getId());
//                    passportDto.setPassportNumber(passport.getPassportNo());
//                    passportDto.setPassportType(passport.getPassportType());
//                    passportDto.setCountry(passport.getCountryCode());
//                    passportDto.setIssuePlace(passport.getIssuePlace());
//                    passportDto.setStatus(passport.getStatus());
//                    passportDto.setIssueDate(passport.getIssueDate());
//                    passportDto.setExpiryDate(passport.getExpiryDate());
//
//                    // ✅ map visas
//                    List<VisaDto> visaDtos = visaRepository.findByPassportId(passport.getId())
//                            .stream()
//                            .map(visa -> {
//                                VisaDto visaDto = new VisaDto();
//                                visaDto.setId(visa.getId());
//                                visaDto.setVisaNumber(visa.getVisaNumber());
//                                visaDto.setVisaType(visa.getVisaType());
//                                visaDto.setVisaPurpose(visa.getVisaPurpose());
//                                visaDto.setCountry(visa.getCountryCode());
//                                visaDto.setIssuePlace(visa.getIssuePlace());
//                                visaDto.setEntries(visa.getEntries());
//                                visaDto.setStatus(visa.getStatus());
//                                visaDto.setIssueDate(visa.getIssueDate());
//                                visaDto.setExpiryDate(visa.getExpiryDate());
//
//                                // ✅ map rentals
//                                List<RentalCertificateDto> rentals = rentalRepository.findByVisaId(visa.getId())
//                                        .stream()
//                                        .map(r -> {
//                                            RentalCertificateDto rd = new RentalCertificateDto();
//                                            rd.setId(r.getId());
//                                            rd.setCertificateNumber(r.getCertificateNumber());
//                                            rd.setRentalType(r.getRentalType());
//                                            rd.setAddress(r.getAddress());
//                                            rd.setLandlordName(r.getLandlordName());
//                                            rd.setLandlordContact(r.getLandlordContact());
//                                            rd.setStartDate(r.getStartDate());
//                                            rd.setEndDate(r.getEndDate());
//                                            rd.setStatus(r.getStatus());
//                                            return rd;
//                                        }).toList();
//
//                                visaDto.setRentalCertificates(rentals);
//                                return visaDto;
//                            }).toList();
//
//                    passportDto.setVisas(visaDtos);
//                    return passportDto;
//                })
//                .toList();
//
//        // ✅ เก็บหลาย passport
//        dto.setPassports(passportDtos);
//
//        return dto;
//    }
//
//}
//
