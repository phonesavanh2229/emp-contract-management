package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.ContractDto;
import com.contractEmployee.contractEmployee.dto.ContractRequest;
import com.contractEmployee.contractEmployee.entity.Contract;
import com.contractEmployee.contractEmployee.entity.Employee;

public class ContractMapper {

    // 1. Convert Contract entity to ContractDto
    public static ContractDto toDto(Contract c) {
        ContractDto dto = new ContractDto();
        dto.setId(c.getContractId());
        dto.setEmployeeId(c.getEmployee() != null ? c.getEmployee().getId() : null);
        dto.setContractNumber(c.getContractNumber());
        dto.setContractType(String.valueOf(c.getContractType()));
        dto.setStartDate(c.getStartDate());
        dto.setEndDate(c.getEndDate());
        dto.setStatus(String.valueOf(c.getStatus()));
        dto.setPreviousContractId(c.getPreviousContract() != null ? c.getPreviousContract().getContractId() : null);
        return dto;
    }

    // 2. Convert ContractRequest to Contract entity (with previous contract)
    public static Contract toEntity(ContractRequest req, Employee emp, Contract prev) {
        Contract c = new Contract();
        c.setEmployee(emp);
        c.setContractNumber(req.getContractNumber());
        c.setContractType(Contract.ContractType.valueOf(req.getContractType()));
        c.setStartDate(req.getStartDate());
        c.setEndDate(req.getEndDate());
        c.setStatus(Contract.ContractStatus.valueOf(req.getStatus()));
        c.setPreviousContract(prev);
        return c;
    }

    // 3. Overloaded version without previous contract (default null)
    public static Contract toEntity(ContractRequest req, Employee emp) {
        return toEntity(req, emp, null);
    }
}
