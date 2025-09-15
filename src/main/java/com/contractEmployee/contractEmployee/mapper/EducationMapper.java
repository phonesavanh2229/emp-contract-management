package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.EducationDTO;
import com.contractEmployee.contractEmployee.dto.EducationRequest;
import com.contractEmployee.contractEmployee.entity.Education;
import com.contractEmployee.contractEmployee.entity.Employee;

public class EducationMapper {

    // ใช้ตอนสร้าง Education ใหม่จาก Request
    public static Education toEntity(EducationRequest req, Employee employee) {
        Education e = new Education();
        e.setEmployee(employee);
        e.setInstitute(req.getInstitute());
        e.setDegree(req.getDegree());
        e.setFieldOfStudy(req.getFieldOfStudy());
        e.setStartDate(req.getStartDate());
        e.setEndDate(req.getEndDate());
        return e;
    }

    // ใช้ตอนดึงข้อมูล Education กลับเป็น DTO
    public static EducationDTO toDto(Education e) {
        EducationDTO dto = new EducationDTO();
        dto.setId(e.getEducationId());
        dto.setInstitute(e.getInstitute());
        dto.setDegree(e.getDegree());
        dto.setFieldOfStudy(e.getFieldOfStudy());
        dto.setStartDate(e.getStartDate());
        dto.setEndDate(e.getEndDate());
        return dto;
    }
}
