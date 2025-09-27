// com.contractEmployee.contractEmployee.rep.EmployeeRepository.java
package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    boolean existsByStaffCode(String staffCode);

    // ใช้กับ findAll(spec, pageable) เพื่อดึง passports -> visas -> rentals ครบชุด
    @EntityGraph(attributePaths = {
            "passports",
            "passports.visas",
            "passports.visas.rentals"
    })
    Page<Employee> findAll(org.springframework.data.jpa.domain.Specification<Employee> spec,
                           Pageable pageable);
}
