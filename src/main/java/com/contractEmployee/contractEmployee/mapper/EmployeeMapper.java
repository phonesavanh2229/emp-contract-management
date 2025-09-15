//package com.contractEmployee.contractEmployee.mapper;
//
//import com.contractEmployee.contractEmployee.dto.*;
//import com.contractEmployee.contractEmployee.entity.Employee;
//import com.contractEmployee.contractEmployee.entity.EmployeeStatus;
//import com.contractEmployee.contractEmployee.entity.Gender;
//import lombok.experimental.UtilityClass;
//
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@UtilityClass
//public class EmployeeMapper {
//
//    // ✅ Convert Entity to Response DTO (with relations)
//    public static EmployeeResponse toDto(Employee e) {
//        if (e == null) return null;
//
//        EmployeeResponse dto = new EmployeeResponse();
//        dto.setEmployeeId(e.getId());
//        dto.setStaffCode(e.getStaffCode());
//        dto.setFirstName(e.getFirstName());
//        dto.setLastName(e.getLastName());
//        dto.setGender(e.getGender() != null ? e.getGender().name() : null);
//        dto.setBirthday(e.getBirthday());
//        dto.setPhone(e.getPhone());
//        dto.setPhoneTwo(e.getPhoneTwo());
//        dto.setEmail(e.getEmail());
//        dto.setStartWorking(e.getStartWorking());
//        dto.setStatus(e.getStatus() != null ? e.getStatus().name() : null);
//        dto.setProvince(e.getProvince());
//        dto.setDistrict(e.getDistrict());
//        dto.setVillage(e.getVillage());
//
//
//        if (e.getContracts() != null) {
//            dto.setContracts(e.getContracts().stream()
//                    .map(ContractMapper::toDto)
//                    .collect(Collectors.toList()));
//        }
//
//        if (e.getEducations() != null) {
//            dto.setEducations(
//                    e.getEducations().stream()
//                            .map(EducationMapper::toDto)
//                            .collect(Collectors.toList())
//            );
//        }
//
//
//        if (e.getIdentityCard() != null) {
//            dto.setIdentityCard(IdentityCardMapper.toDto(e.getIdentityCard()));
//        }
//
//
//        return dto;
//    }
//
//    // ✅ Convert Create Request -> Entity
//    public static Employee toEntity(EmployeeRequest req) {
//        if (req == null) return null;
//
//        return Employee.builder()
//                .staffCode(generateStaffCodeIfEmpty(req.getStaffCode()))
//                .firstName(req.getFirstName())
//                .lastName(req.getLastName())
//                .gender(parseGender(String.valueOf(req.getGender())))
//                .birthday(req.getBirthday())
//                .phone(req.getPhone())
//                .phoneTwo(req.getPhoneTwo())
//                .email(req.getEmail())
//                .startWorking(req.getStartWorking())
//                .province(req.getProvince())
//                .district(req.getDistrict())
//                .village(req.getVillage())
//                .status(parseStatus(req.getStatus()))
//                .build();
//    }
//
//    // ✅ Convert Entity -> Basic Request DTO
//    public static EmployeeRequest toRequest(Employee e) {
//        if (e == null) return null;
//
//        EmployeeRequest req = new EmployeeRequest();
//        req.setStaffCode(e.getStaffCode());
//        req.setFirstName(e.getFirstName());
//        req.setLastName(e.getLastName());
//        req.setGender(e.getGender() != null ? valueOf(e.getGender().name()) : null);
//        req.setBirthday(e.getBirthday());
//        req.setPhone(e.getPhone());
//        req.setPhoneTwo(e.getPhoneTwo());
//        req.setEmail(e.getEmail());
//        req.setStartWorking(e.getStartWorking());
//        req.setProvince(e.getProvince());
//        req.setDistrict(e.getDistrict());
//        req.setVillage(e.getVillage());
//        req.setStatus(e.getStatus() != null ? e.getStatus().name() : null);
//        return req;
//    }
//
//    // Helpers
//    private static String generateStaffCodeIfEmpty(String code) {
//        return (code == null || code.isBlank())
//                ? "EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
//                : code;
//    }
//
//    private static Gender parseGender(String gender) {
//        try {
//            return gender != null ? Gender.valueOf(gender.toUpperCase()) : null;
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//    }
//
//    private static EmployeeStatus parseStatus(String status) {
//        try {
//            return status != null ? EmployeeStatus.valueOf(status.toUpperCase()) : null;
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//    }
//}
