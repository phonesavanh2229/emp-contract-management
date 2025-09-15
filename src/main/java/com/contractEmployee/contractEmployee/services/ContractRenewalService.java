//package com.contractEmployee.contractEmployee.services;
//
//import com.contractEmployee.contractEmployee.dto.*;
//import com.contractEmployee.contractEmployee.entity.*;
//import com.contractEmployee.contractEmployee.mapper.ContractMapper;
//import com.contractEmployee.contractEmployee.rep.*;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Service
//public class ContractRenewalService {
//
//    private final ContractRepository contractRepository;
//    private final ContractAmendmentRepository amendmentRepository;
//    private final ContractFileRepository contractFileRepository;
//    private final EmployeeRepository employeeRepository;
//
//    public ContractRenewalService(
//            ContractRepository contractRepository,
//            ContractAmendmentRepository amendmentRepository,
//            ContractFileRepository contractFileRepository,
//            EmployeeRepository employeeRepository
//    ) {
//        this.contractRepository = contractRepository;
//        this.amendmentRepository = amendmentRepository;
//        this.contractFileRepository = contractFileRepository;
//        this.employeeRepository = employeeRepository;
//    }
//
//    @Transactional
//    public ContractDto renewContract(RenewContractRequest request) {
//        // 1. Validate employee
//        Employee employee = employeeRepository.findById(request.getEmployeeId())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        // 2. Find and deactivate old contract
//        Contract previousContract = contractRepository.findById(request.getPreviousContractId())
//                .orElseThrow(() -> new RuntimeException("Previous contract not found"));
//        previousContract.setStatus("INACTIVE");
//        contractRepository.save(previousContract);
//
//        // 3. Create new contract
//        Contract newContract = Contract.builder()
//                .employee(employee)
//                .contractNumber(request.getNewContract().getContractNumber())
//                .contractType(request.getNewContract().getContractType())
//                .startDate(request.getNewContract().getStartDate())
//                .endDate(request.getNewContract().getEndDate())
//                .status(request.getNewContract().getStatus())
//                .previousContract(previousContract)
//                .build();
//
//        Contract savedContract = contractRepository.save(newContract);
//
//        // 4. Create amendment
//        ContractAmendment amendment = ContractAmendment.builder()
//                .contract(savedContract)
//                .amendmentDate(request.getAmendment().getAmendmentDate())
//                .effectiveDate(request.getAmendment().getEffectiveDate())
//                .changeType(request.getAmendment().getChangeType())
//                .reason(request.getAmendment().getReason())
//                .changedBy(request.getAmendment().getChangedBy())
//                .approvedBy(request.getAmendment().getApprovedBy())
//                .source(request.getAmendment().getSource())
//                .status(request.getAmendment().getStatus())
//                .build();
//
//        amendmentRepository.save(amendment);
//
//        // 5. Add contract files
//        if (request.getContractFiles() != null) {
//            request.getContractFiles().forEach(fileDto -> {
//                ContractFile file = ContractFile.builder()
//                        .contract(savedContract)
//                        .fileName(fileDto.getFileName())
//                        .filePath(fileDto.getFilePath())
//                        .fileType(fileDto.getFileType())
//                        .build();
//                contractFileRepository.save(file);
//            });
//        }
//
//        // 6. Return DTO
//        return ContractMapper.toDto(savedContract);
//    }
//
//}
