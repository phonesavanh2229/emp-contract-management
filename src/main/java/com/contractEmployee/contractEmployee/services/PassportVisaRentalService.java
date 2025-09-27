// com.contractEmployee.contractEmployee.services.PassportVisaRentalService.java
package com.contractEmployee.contractEmployee.services;

import com.contractEmployee.contractEmployee.dto.request.EmployeeDto;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.mapper.EmployeePVRMapper;
import com.contractEmployee.contractEmployee.rep.EmployeeRepository;
import com.contractEmployee.contractEmployee.search.EmployeeSpecification;
import com.contractEmployee.contractEmployee.search.FilterState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassportVisaRentalService {

    private final EmployeeRepository employeeRepository;
    private final EmployeePVRMapper mapper;

    @Transactional(readOnly = true)
    public Page<EmployeeDto> getImmigrationAll(
            FilterState passportFilter,
            FilterState visaFilter,
            FilterState rentalFilter,
            int expiringDays,
            Pageable pageable
    ) {
        int days = (expiringDays > 0) ? expiringDays : 30;

        Specification<Employee> spec = Specification.allOf(
                EmployeeSpecification.passportFilter(passportFilter, days),
                EmployeeSpecification.visaFilter(visaFilter, days),
                EmployeeSpecification.rentalFilter(rentalFilter, days)
        );

        return employeeRepository.findAll(spec, pageable)
                .map(mapper::toFullDto);
    }
}
