package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.*;
import com.contractEmployee.contractEmployee.entity.*;
import com.contractEmployee.contractEmployee.mapper.EmployeeMapper;
import com.contractEmployee.contractEmployee.rep.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final ContractRepository contractRepo;
    private final ContractFileRepository contractFileRepo;
    private final IdentityCardRepository identityCardRepo;
    private final EducationRepository educationRepo;

    /**
     * Create employee with related entities (contract, files, ID card, education)
     */
    @Transactional
    public EmployeeResponse createFullEmployee(CompositeEmployeeRequest req) {
        // ✅ Save Employee
        Employee employee = saveEmployee(req.getEmployee());

        // ✅ Save Contract
        Contract contract = saveContract(req.getContract(), employee);

        // ✅ Save Contract Files
        if (req.getContractFiles() != null) {
            for (ContractFileRequest fileReq : req.getContractFiles()) {
                ContractFile file = ContractFile.builder()
                        .contract(contract)
                        .fileName(fileReq.getFileName())
                        .filePath(fileReq.getFilePath())
                        .fileType(fileReq.getFileType())
                        .build();
                contractFileRepo.save(file);
            }
        }

        // ✅ Save Identity Card
        if (req.getIdentityCard() != null) {
            IdentityCard card = IdentityCard.builder()
                    .employee(employee)
                    .cardNumber(req.getIdentityCard().getCardNumber())
                    .type(req.getIdentityCard().getType())
                    .issuedDate(LocalDate.parse(req.getIdentityCard().getIssuedDate()))
                    .expiryDate(LocalDate.parse(req.getIdentityCard().getExpiryDate()))
                    .placeOfIssue(req.getIdentityCard().getPlaceOfIssue())
                    .build();
            identityCardRepo.save(card);
        }

        // ✅ Save Educations
        if (req.getEducations() != null) {
            for (EducationRequest eduReq : req.getEducations()) {
                Education edu = Education.builder()
                        .employee(employee)
                        .institute(eduReq.getInstitute())
                        .degree(eduReq.getDegree())
                        .fieldOfStudy(eduReq.getFieldOfStudy())
                        .startDate(eduReq.getStartDate())
                        .endDate(eduReq.getEndDate())
                        .build();
                educationRepo.save(edu);
            }
        }

        return EmployeeMapper.toDto(employee);
    }

    // 🔁 Update basic employee info (not full update)
    @Transactional
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest updated) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Employee not found with ID: " + id));

        emp.setFirstName(updated.getFirstName());
        emp.setLastName(updated.getLastName());
        emp.setGender(parseGender(String.valueOf(updated.getGender())));
        emp.setBirthday(updated.getBirthday());
        emp.setPhone(updated.getPhone());
        emp.setPhoneTwo(updated.getPhoneTwo());
        emp.setEmail(updated.getEmail());
        emp.setProvince(updated.getProvince());
        emp.setDistrict(updated.getDistrict());
        emp.setVillage(updated.getVillage());
        emp.setStartWorking(updated.getStartWorking());
        emp.setStatus(parseStatus(updated.getStatus()));

        return EmployeeMapper.toDto(employeeRepo.save(emp));
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepo.findAll().stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }
//   ..
    public EmployeeResponse getEmployeeById(Long id) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Employee not found with ID: " + id));
        return EmployeeMapper.toDto(emp);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Employee not found with ID: " + id));
        employeeRepo.delete(emp);
    }

    // ====================
    // 🔽 PRIVATE HELPERS 🔽
    // ====================
    private Employee saveEmployee(EmployeeRequest req) {
        return employeeRepo.save(Employee.builder()
                .staffCode(req.getStaffCode())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .gender(parseGender(String.valueOf(req.getGender())))
                .birthday(req.getBirthday())
                .province(req.getProvince())
                .district(req.getDistrict())
                .village(req.getVillage())
                .phone(req.getPhone())
                .phoneTwo(req.getPhoneTwo())
                .email(req.getEmail())
                .startWorking(req.getStartWorking())
                .status(parseStatus(req.getStatus()))
                .build());
    }

    private Contract saveContract(ContractRequest req, Employee employee) {
        return contractRepo.save(Contract.builder()
                .employee(employee)
                .contractNumber(req.getContractNumber())
                .contractType(Contract.ContractType.valueOf(req.getContractType().toUpperCase()))
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .status(Contract.ContractStatus.valueOf(req.getStatus().toUpperCase()))
                .build());
    }

    private Gender parseGender(String genderStr) {
        try {
            return Gender.valueOf(genderStr.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender value: " + genderStr);
        }
    }

    private Employee.EmployeeStatus parseStatus(String statusStr) {
        try {
            return Employee.EmployeeStatus.valueOf(statusStr.toUpperCase());
        } catch (Exception e) {

            throw new IllegalArgumentException("Invalid status value: " + statusStr);
        }
    }
}
