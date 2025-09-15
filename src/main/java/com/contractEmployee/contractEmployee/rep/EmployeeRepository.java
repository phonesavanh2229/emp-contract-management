package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import com.contractEmployee.contractEmployee.entity.Contract;


import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByStaffCode(String staffCode);

}

