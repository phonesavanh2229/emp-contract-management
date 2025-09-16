package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.ProvinceDto;
import com.contractEmployee.contractEmployee.entity.Province;

public class ProvinceMapper {
    public static ProvinceDto toDto(Province entity) {
        if (entity == null) return null;

        ProvinceDto dto = new ProvinceDto();
        dto.setId(entity.getId());
        dto.setProvinceName(entity.getProvinceName());
        dto.setCode(entity.getCode());

        return dto;
    }
}
