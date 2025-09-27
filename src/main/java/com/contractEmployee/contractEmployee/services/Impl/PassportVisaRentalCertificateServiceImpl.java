package com.contractEmployee.contractEmployee.services.Impl;

import com.contractEmployee.contractEmployee.dto.request.*;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithPassportDto;
import com.contractEmployee.contractEmployee.dto.response.EmployeeWithVisaDto;
import com.contractEmployee.contractEmployee.dto.response.SummaryDto;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.Passport;
import com.contractEmployee.contractEmployee.entity.RentalCertificate;
import com.contractEmployee.contractEmployee.entity.Visa;
import com.contractEmployee.contractEmployee.mapper.EmployeeMapper;
import com.contractEmployee.contractEmployee.mapper.PassportMapper;
import com.contractEmployee.contractEmployee.mapper.RentalCertificateMapper;
import com.contractEmployee.contractEmployee.mapper.VisaMapper;
import com.contractEmployee.contractEmployee.rep.EmployeeRepository;
import com.contractEmployee.contractEmployee.rep.PassportRepository;
import com.contractEmployee.contractEmployee.rep.RentalCertificateRepository;
import com.contractEmployee.contractEmployee.rep.VisaRepository;
import com.contractEmployee.contractEmployee.services.PassportVisaRentalCertificateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassportVisaRentalCertificateServiceImpl implements PassportVisaRentalCertificateService {

    private final EmployeeRepository employeeRepository;
    private final PassportRepository passportRepository;
    private final VisaRepository visaRepository;
    private final RentalCertificateRepository rentalRepository;
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWithVisaDto> getEmployeesWithActiveVisas() {
        LocalDate today = LocalDate.now();
        List<Visa> activeVisas = visaRepository.findByStatus("ACTIVE");

        // group by employee
        return activeVisas.stream()
                .collect(Collectors.groupingBy(v -> v.getPassport().getEmployee()))
                .entrySet()
                .stream()
                .map(entry -> {
                    Employee e = entry.getKey();
                    List<Visa> visas = entry.getValue();

                    return new EmployeeWithVisaDto(
                            e.getId(),
                            e.getStaffCode(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getPhone(),
                            e.getEmail(),
                            e.getProvince(),
                            e.getVillage(),
                            visas
                    );
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWithVisaDto> getEmployeesWithExpiredVisas() {
        LocalDate today = LocalDate.now();

        // visas ที่หมดอายุแล้ว
        List<Visa> expiredVisas = visaRepository.findByExpiryDateLessThanEqualAndStatus(today, "ACTIVE");

        return expiredVisas.stream()
                .map(Visa::getPassport)
                .map(Passport::getEmployee)
                .distinct()
                .map(e -> {
                    List<Visa> onlyExpired = e.getPassports().stream()
                            .flatMap(p -> p.getVisas().stream())
                            .filter(v -> v.getExpiryDate() != null &&
                                    (v.getExpiryDate().isBefore(today) ||
                                            v.getExpiryDate().isEqual(today)))
                            .toList();
                    return new EmployeeWithVisaDto(
                            e.getId(),
                            e.getStaffCode(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getPhone(),
                            e.getEmail(),
                            e.getProvince(),
                            e.getVillage(),
                            onlyExpired
                    );
                })
                .toList();
    }



    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWithPassportDto> getEmployeesWithExpiredPassports() {
        LocalDate today = LocalDate.now();

        // ดึง passport ที่หมดอายุแล้ว (<= วันนี้)
        List<Passport> expiredPassports = passportRepository.findByExpiryDateLessThanEqual(today);

        return expiredPassports.stream()
                .map(Passport::getEmployee)
                .distinct()
                .map(e -> {
                    EmployeeWithPassportDto dto = new EmployeeWithPassportDto();
                    dto.setId(e.getId());
                    dto.setStaffCode(e.getStaffCode());
                    dto.setFirstName(e.getFirstName());
                    dto.setLastName(e.getLastName());
                    dto.setPhone(e.getPhone());
                    dto.setEmail(e.getEmail());
                    dto.setProvince(e.getProvince());
                    dto.setVillage(e.getVillage());

                    // ให้เฉพาะ passport ที่ expired
                    List<Passport> onlyExpired = e.getPassports().stream()
                            .filter(p -> p.getExpiryDate() != null &&
                                    (p.getExpiryDate().isBefore(today) ||
                                            p.getExpiryDate().isEqual(today)))
                            .map(p -> {
                                Passport pd = new Passport();
                                pd.setId(p.getId());
                                pd.setPassportNo(p.getPassportNo());
                                pd.setPassportType(p.getPassportType());
                                pd.setCountryCode(p.getCountryCode());
                                pd.setIssuePlace(p.getIssuePlace());
                                pd.setStatus(p.getStatus());
                                pd.setIssueDate(p.getIssueDate());
                                pd.setExpiryDate(p.getExpiryDate());
                                return pd;
                            })
                            .toList();

                    dto.setPassports(onlyExpired);
                    return dto;
                })
                .toList();
    }



    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWithPassportDto> getEmployeesWithExpiringPassports() {
        LocalDate today = LocalDate.now();
        LocalDate next180Days = today.plusDays(180);

        // ดึง passport ที่กำลังจะหมดอายุ (ระหว่างวันนี้ - 180 วันข้างหน้า)
        List<Passport> expiringPassports =
                passportRepository.findByExpiryDateBetweenAndStatus(today, next180Days, "ACTIVE");

        return expiringPassports.stream()
                .map(Passport::getEmployee)
                .distinct()
                .map(e -> {
                    EmployeeWithPassportDto dto = new EmployeeWithPassportDto();
                    dto.setId(e.getId());
                    dto.setStaffCode(e.getStaffCode());
                    dto.setFirstName(e.getFirstName());
                    dto.setLastName(e.getLastName());
                    dto.setPhone(e.getPhone());
                    dto.setEmail(e.getEmail());
                    dto.setProvince(e.getProvince());
                    dto.setVillage(e.getVillage());

                    // ให้เฉพาะ passport ที่กำลังจะหมดอายุ
                    List<Passport> onlyExpiring = e.getPassports().stream()
                            .filter(p -> "ACTIVE".equalsIgnoreCase(p.getStatus())
                                    && p.getExpiryDate() != null
                                    && (!p.getExpiryDate().isBefore(today) &&
                                    !p.getExpiryDate().isAfter(next180Days)))
                            .map(p -> {
                                Passport pd = new Passport();
                                pd.setId(p.getId());
                                pd.setPassportNo(p.getPassportNo());
                                pd.setPassportType(p.getPassportType());
                                pd.setCountryCode(p.getCountryCode());
                                pd.setIssuePlace(p.getIssuePlace());
                                pd.setStatus(p.getStatus());
                                pd.setIssueDate(p.getIssueDate());
                                pd.setExpiryDate(p.getExpiryDate());
                                return pd;
                            })
                            .toList();

                    dto.setPassports(onlyExpiring);
                    return dto;
                })
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmployeeWithPassportDto> getEmployeesWithActivePassports() {
        List<Passport> activePassports = passportRepository.findActivePassports();

        // group by employee
        return activePassports.stream()
                .collect(Collectors.groupingBy(Passport::getEmployee))
                .entrySet()
                .stream()
                .map(entry -> {
                    var e = entry.getKey();
                    return new EmployeeWithPassportDto(
                            e.getId(),
                            e.getStaffCode(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getPhone(),
                            e.getEmail(),
                            e.getProvince(),
                            e.getVillage(),
                            entry.getValue() // active passports
                    );
                })
                .toList();
    }

    // ---------- Immigration ----------
//    @Override
//    @Transactional(readOnly = true)
//    public List<EmployeeDto> getImmigrationAll() {
//        return employeeRepository.findAll()
//                .stream()
//                .map(this::mapEmployeeWithImmigration)
//                .toList();
//    }
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> getImmigrationAll(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(this::mapEmployeeWithImmigration);
    }



    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getImmigrationByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));
        return mapEmployeeWithImmigration(employee);
    }

    @Override
    @Transactional
    public EmployeeDto saveImmigration(Long employeeId, ImmigrationRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        // ✅ Save Passport
        PassportDto passportDto = request.getPassport();
        Passport passport = new Passport();
        passport.setEmployee(employee);
        passport.setPassportNo(passportDto.getPassportNumber());
        passport.setPassportType(passportDto.getPassportType());
        passport.setCountryCode(passportDto.getCountry());
        passport.setIssuePlace(passportDto.getIssuePlace());
        passport.setStatus(passportDto.getStatus());
        passport.setIssueDate(passportDto.getIssueDate());
        passport.setExpiryDate(passportDto.getExpiryDate());
        passportRepository.save(passport);

        // ✅ Save Visa + Rental
        if (request.getVisas() != null) {
            for (VisaDto v : request.getVisas()) {
                Visa visa = new Visa();
                visa.setPassport(passport);
                visa.setVisaNumber(v.getVisaNumber());
                visa.setVisaType(v.getVisaType());
                visa.setVisaPurpose(v.getVisaPurpose());
                visa.setCountryCode(v.getCountry());
                visa.setIssuePlace(v.getIssuePlace());
                visa.setEntries(v.getEntries());
                visa.setStatus(v.getStatus());
                visa.setIssueDate(v.getIssueDate());
                visa.setExpiryDate(v.getExpiryDate());
                Visa savedVisa = visaRepository.save(visa);

                if (request.getRentalCertificates() != null) {
                    for (RentalCertificateDto r : request.getRentalCertificates()) {
                        RentalCertificate rc = new RentalCertificate();
                        rc.setVisa(savedVisa);
                        rc.setCertificateNumber(r.getCertificateNumber());
                        rc.setRentalType(r.getRentalType());
                        rc.setAddress(r.getAddress());
                        rc.setLandlordName(r.getLandlordName());
                        rc.setLandlordContact(r.getLandlordContact());
                        rc.setStartDate(r.getStartDate());
                        rc.setEndDate(r.getEndDate());
                        rc.setStatus(r.getStatus());
                        rentalRepository.save(rc);
                    }
                }
            }
        }

        return mapEmployeeWithImmigration(employee);
    }
    @Override
    @Transactional(readOnly = true)
    public List<SummaryDto> getSummary() {
        SummaryDto passport = new SummaryDto("Passport",
                getPassportSummary().get(0).getTotalActive(),
                getPassportSummary().get(0).getTotalExpiring(),
                getPassportSummary().get(0).getTotalExpired());

        SummaryDto visa = new SummaryDto("visa",
                getVisaSummary().get(0).getTotalActive(),
                getVisaSummary().get(0).getTotalExpiring(),
                getVisaSummary().get(0).getTotalExpired());

        SummaryDto rental = new SummaryDto("Rental",
                getRentalCertificateSummary().get(0).getTotalActive(),
                getRentalCertificateSummary().get(0).getTotalExpiring(),
                getRentalCertificateSummary().get(0).getTotalExpired());

        return List.of(passport, visa, rental);
    }


    @Override
    @Transactional
    public List<SummaryDto> getPassportSummary() {
        LocalDate today = LocalDate.now();
        LocalDate next180Days = today.plusDays(180);

        // อัปเดต passport ที่หมดอายุแล้ว → INACTIVE
        List<Passport> expiredPassports = passportRepository.findByExpiryDateBeforeAndStatus(today, "ACTIVE");
        expiredPassports.forEach(p -> p.setStatus("INACTIVE"));
        passportRepository.saveAll(expiredPassports);

        // นับจำนวน
        long active = passportRepository.countByStatus("ACTIVE");
        long expiring = passportRepository.countExpiring(today, next180Days);
        long expired = passportRepository.countExpired(today);

        return List.of(new SummaryDto("Passport",active, expiring, expired));
    }

    @Override
    @Transactional
    public List<SummaryDto> getVisaSummary() {
        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        // update expired visas
        List<Visa> expiredVisas = visaRepository.findByExpiryDateBeforeAndStatus(today, "ACTIVE");
        expiredVisas.forEach(v -> {
            v.setStatus("INACTIVE");
            v.setIsCurrent(false);
        });
        visaRepository.saveAll(expiredVisas);

        // count visas
        long active = visaRepository.countActive(today);
        long expiring = visaRepository.countExpiring(today, next30Days);
        long expired = visaRepository.countExpired(today);

        return List.of(new SummaryDto("Visa",active, expiring, expired));
    }

    @Override
    @Transactional
    public List<SummaryDto> getRentalCertificateSummary() {
        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        // update expired rentals
        List<RentalCertificate> expiredRentals = rentalRepository.findByEndDateBeforeAndStatus(today, "ACTIVE");
        expiredRentals.forEach(r -> r.setStatus("EXPIRED"));
        rentalRepository.saveAll(expiredRentals);

        // count rentals
        long active = rentalRepository.countActive(today);
        long expiring = rentalRepository.countExpiring(today, next30Days);
        long expired = rentalRepository.countExpired(today);

        return List.of(new SummaryDto("Rental Certificate",active, expiring, expired));
    }


    @Override
    @Transactional
    public EmployeeDto renewPassport(Long employeeId, PassportDto dto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        // หา current passport
        List<Passport> currents = passportRepository.findAllByEmployeeIdAndIsCurrentTrue(employeeId);

        Passport previous = null;
        if (!currents.isEmpty()) {
            previous = currents.get(0);
            previous.setIsCurrent(false);
            previous.setStatus("INACTIVE");
            passportRepository.save(previous);

            for (int i = 1; i < currents.size(); i++) {
                Passport old = currents.get(i);
                old.setIsCurrent(false);
                old.setStatus("INACTIVE");
                passportRepository.save(old);
            }
        }

        Passport newPassport = new Passport();
        newPassport.setEmployee(employee);
        newPassport.setPassportNo(dto.getPassportNumber());
        newPassport.setPassportType(dto.getPassportType());
        newPassport.setCountryCode(dto.getCountry());
        newPassport.setIssuePlace(dto.getIssuePlace());
        newPassport.setIssueDate(dto.getIssueDate());
        newPassport.setExpiryDate(dto.getExpiryDate());
        newPassport.setStatus("ACTIVE");
        newPassport.setIsCurrent(true);

        if (previous != null) {
            newPassport.setPreviousPassport(previous);
        }

        passportRepository.save(newPassport);

        return mapEmployeeWithImmigration(employee);
    }


    @Override
    @Transactional
    public EmployeeDto renewVisa(Long passportId, VisaDto dto) {
        Passport passport = passportRepository.findById(passportId)
                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));

        // expire old visa
        visaRepository.findByPassportIdAndIsCurrentTrue(passportId)
                .forEach(old -> {
                    old.setIsCurrent(false);
                    old.setStatus("EXPIRED");
                    visaRepository.save(old);
                });

        // create new visa
        Visa newVisa = new Visa();
        newVisa.setPassport(passport);
        newVisa.setVisaNumber(dto.getVisaNumber());
        newVisa.setVisaType(dto.getVisaType());
        newVisa.setVisaPurpose(dto.getVisaPurpose());
        newVisa.setCountryCode(dto.getCountry());
        newVisa.setIssuePlace(dto.getIssuePlace());
        newVisa.setIssueDate(dto.getIssueDate());
        newVisa.setExpiryDate(dto.getExpiryDate());
        newVisa.setStatus("ACTIVE");
        newVisa.setIsCurrent(true);

        visaRepository.save(newVisa);

        return mapEmployeeWithImmigration(passport.getEmployee());
    }


    @Override
    @Transactional
    public EmployeeDto renewRental(Long rentalId, RentalCertificateDto dto) {
        RentalCertificate oldRental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found: " + rentalId));

        Visa visa = oldRental.getVisa();

        oldRental.setIsCurrent(false);
        oldRental.setStatus("INACTIVE");
        rentalRepository.save(oldRental);

        RentalCertificate newRental = new RentalCertificate();
        newRental.setVisa(visa);
        newRental.setCertificateNumber(dto.getCertificateNumber());
        newRental.setRentalType(dto.getRentalType());
        newRental.setAddress(dto.getAddress());
        newRental.setLandlordName(dto.getLandlordName());
        newRental.setLandlordContact(dto.getLandlordContact());
        newRental.setStartDate(dto.getStartDate());
        newRental.setEndDate(dto.getEndDate());
        newRental.setStatus("ACTIVE");
        newRental.setIsCurrent(true);
        newRental.setPreviousRental(oldRental);

        rentalRepository.save(newRental);

        return mapEmployeeWithImmigration(visa.getPassport().getEmployee());
    }


    // ---------- Passport ----------
    @Override
    @Transactional
    public Passport savePassport(Long employeeId, PassportDto passportDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + employeeId));

        Passport passport = new Passport();
        passport.setEmployee(employee);
        passport.setPassportNo(passportDto.getPassportNumber());
        passport.setPassportType(passportDto.getPassportType());
        passport.setCountryCode(passportDto.getCountry());
        passport.setIssuePlace(passportDto.getIssuePlace());
        passport.setStatus(passportDto.getStatus());
        passport.setIssueDate(passportDto.getIssueDate());
        passport.setExpiryDate(passportDto.getExpiryDate());

        return passportRepository.save(passport);
    }

    @Override
    @Transactional(readOnly = true)
    public Passport getPassport(Long passportId) {
        return passportRepository.findById(passportId)
                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Passport> getPassportsByEmployee(Long employeeId) {
        return passportRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional
    public Passport updatePassport(Long passportId, PassportDto passportDto) {
        Passport passport = passportRepository.findById(passportId)
                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));

        passport.setPassportNo(passportDto.getPassportNumber());
        passport.setPassportType(passportDto.getPassportType());
        passport.setCountryCode(passportDto.getCountry());
        passport.setIssuePlace(passportDto.getIssuePlace());
        passport.setStatus(passportDto.getStatus());
        passport.setIssueDate(passportDto.getIssueDate());
        passport.setExpiryDate(passportDto.getExpiryDate());

        return passportRepository.save(passport);
    }

    @Override
    @Transactional
    public void deletePassport(Long passportId) {
        if (!passportRepository.existsById(passportId)) {
            throw new EntityNotFoundException("Passport not found: " + passportId);
        }
        passportRepository.deleteById(passportId);
    }

    // ---------- Visa ----------
    @Override
    @Transactional
    public Visa saveVisa(Long passportId, VisaDto visaDto) {
        Passport passport = passportRepository.findById(passportId)
                .orElseThrow(() -> new EntityNotFoundException("Passport not found: " + passportId));

        Visa visa = new Visa();
        visa.setPassport(passport);
        visa.setVisaNumber(visaDto.getVisaNumber());
        visa.setVisaType(visaDto.getVisaType());
        visa.setVisaPurpose(visaDto.getVisaPurpose());
        visa.setCountryCode(visaDto.getCountry());
        visa.setIssuePlace(visaDto.getIssuePlace());
        visa.setEntries(visaDto.getEntries());
        visa.setStatus(visaDto.getStatus());
        visa.setIssueDate(visaDto.getIssueDate());
        visa.setExpiryDate(visaDto.getExpiryDate());

        return visaRepository.save(visa);
    }

    @Override
    @Transactional(readOnly = true)
    public  List<Visa> getVisa(Long visaId) {
        Visa  visa =visaRepository.findById(visaId)
                .orElseThrow(() -> new EntityNotFoundException("Visa not found: " + visaId));
        return List.of(visa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Visa> getVisasByPassport(Long passportId) {
        return visaRepository.findByPassportId(passportId);
    }

    @Override
    @Transactional
    public Visa updateVisa(Long visaId, VisaDto visaDto) {
        Visa visa = visaRepository.findById(visaId)
                .orElseThrow(() -> new EntityNotFoundException("Visa not found: " + visaId));

        visa.setVisaNumber(visaDto.getVisaNumber());
        visa.setVisaType(visaDto.getVisaType());
        visa.setVisaPurpose(visaDto.getVisaPurpose());
        visa.setCountryCode(visaDto.getCountry());
        visa.setIssuePlace(visaDto.getIssuePlace());
        visa.setEntries(visaDto.getEntries());
        visa.setStatus(visaDto.getStatus());
        visa.setIssueDate(visaDto.getIssueDate());
        visa.setExpiryDate(visaDto.getExpiryDate());

        return visaRepository.save(visa);
    }

    @Override
    @Transactional
    public void deleteVisa(Long visaId) {
        visaRepository.deleteById(visaId);
    }

    // ---------- Rental ----------
    @Override
    @Transactional
    public RentalCertificate saveRental(Long visaId, RentalCertificateDto rentalDto) {
        Visa visa = visaRepository.findById(visaId)
                .orElseThrow(() -> new EntityNotFoundException("Visa not found: " + visaId));

        RentalCertificate rc = new RentalCertificate();
        rc.setVisa(visa);
        rc.setCertificateNumber(rentalDto.getCertificateNumber());
        rc.setRentalType(rentalDto.getRentalType());
        rc.setAddress(rentalDto.getAddress());
        rc.setLandlordName(rentalDto.getLandlordName());
        rc.setLandlordContact(rentalDto.getLandlordContact());
        rc.setStartDate(rentalDto.getStartDate());
        rc.setEndDate(rentalDto.getEndDate());
        rc.setStatus(rentalDto.getStatus());

        return rentalRepository.save(rc);
    }

    @Override
    @Transactional(readOnly = true)
    public RentalCertificate getRental(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found: " + rentalId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalCertificate> getRentalsByVisa(Long visaId) {
        return rentalRepository.findByVisaId(visaId);
    }

    @Override
    @Transactional
    public RentalCertificate updateRental(Long rentalId, RentalCertificateDto rentalDto) {
        RentalCertificate rc = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found: " + rentalId));

        rc.setCertificateNumber(rentalDto.getCertificateNumber());
        rc.setRentalType(rentalDto.getRentalType());
        rc.setAddress(rentalDto.getAddress());
        rc.setLandlordName(rentalDto.getLandlordName());
        rc.setLandlordContact(rentalDto.getLandlordContact());
        rc.setStartDate(rentalDto.getStartDate());
        rc.setEndDate(rentalDto.getEndDate());
        rc.setStatus(rentalDto.getStatus());

        return rentalRepository.save(rc);
    }

    @Override
    @Transactional
    public void deleteRental(Long rentalId) {
        rentalRepository.deleteById(rentalId);
    }

    // ---------- Utility Mapping ----------
    private EmployeeDto mapEmployeeWithImmigration(Employee e) {
        EmployeeDto dto = EmployeeMapper.toDto(e);

        dto.setPassports(
                passportRepository.findByEmployeeId(e.getId())
                        .stream()
                        .map(passport -> {
                            PassportDto passportDto = PassportMapper.toDto(passport);

                            passportDto.setVisas(
                                    visaRepository.findByPassportId(passport.getId())
                                            .stream()
                                            .map(visa -> {
                                                VisaDto visaDto = VisaMapper.toDto(visa);

                                                visaDto.setRentalCertificates(
                                                        rentalRepository.findByVisaId(visa.getId())
                                                                .stream()
                                                                .map(RentalCertificateMapper::toDto)
                                                                .toList()
                                                );
                                                return visaDto;
                                            })
                                            .toList()
                            );

                            return passportDto;
                        })
                        .toList()
        );

        return dto;
    }
}
