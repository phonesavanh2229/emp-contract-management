package com.contractEmployee.contractEmployee.rep;

import com.contractEmployee.contractEmployee.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByEmployeeEmployeeId(Long employeeId);
}