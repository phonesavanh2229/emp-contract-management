//package com.contractEmployee.contractEmployee.controller;
//
//import com.contractEmployee.contractEmployee.dto.ApiResponse;
//import com.contractEmployee.contractEmployee.dto.RenewContractRequest;
//import com.contractEmployee.contractEmployee.dto.ContractDto;
//import com.contractEmployee.contractEmployee.services.ContractRenewalService;
//
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/contracts")
//public class ContractRenewalController {
//
//    private final ContractRenewalService contractRenewalService;
//
//    public ContractRenewalController(ContractRenewalService contractRenewalService) {
//        this.contractRenewalService = contractRenewalService;
//    }
//
//    @PostMapping("/renew")
//    public ApiResponse<ContractDto> renewContract(@RequestBody RenewContractRequest request) {
//        ContractDto contractDto = contractRenewalService.renewContract(request);
//        return ApiResponse.success("Contract renewed successfully", contractDto);
//    }
//}
