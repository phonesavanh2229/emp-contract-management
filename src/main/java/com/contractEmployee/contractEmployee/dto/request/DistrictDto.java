package com.contractEmployee.contractEmployee.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class DistrictDto {
    private Integer id;
    private String districtName;
    private String code;

    private ProvinceDto province;
    private List<BranchDto> branches;

}