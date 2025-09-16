package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.DistrictDto;
import com.contractEmployee.contractEmployee.entity.District;

import java.util.stream.Collectors;

public class DistrictMapper {

    // ✅ ป้องกัน loop โดยไม่ map Branches
    public static DistrictDto toDto(District entity) {
        if (entity == null) return null;

        DistrictDto dto = new DistrictDto();
        dto.setId(entity.getId());
        dto.setDistrictName(entity.getDistrictName());
        dto.setCode(entity.getCode());

        // province → shallow mapping
        if (entity.getProvince() != null) {
            dto.setProvince(ProvinceMapper.toDto(entity.getProvince()));
        }

        return dto;

    }

    public static DistrictDto toDtoWithBranches(District entity) {
        if (entity == null) return null;

        DistrictDto dto = toDto(entity);

        if (entity.getBranches() != null) {
            dto.setBranches(entity.getBranches()
                    .stream()
                    .map(BranchMapper::toDtoWithoutDistrict)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
