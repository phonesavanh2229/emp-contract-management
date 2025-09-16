package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.request.BranchDto;
import com.contractEmployee.contractEmployee.entity.Branch;

public class BranchMapper {
    public static BranchDto toDto(Branch entity) {
        if (entity == null) return null;

        BranchDto dto = new BranchDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setMainBranch(entity.isMainBranch());

        if (entity.getDistrict() != null) {
            dto.setDistrict(DistrictMapper.toDto(entity.getDistrict()));
        }

        return dto;
    }

    public static BranchDto toDtoWithoutDistrict(Branch entity) {
        if (entity == null) return null;

        BranchDto dto = new BranchDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setMainBranch(entity.isMainBranch());
        return dto;
    }
}
