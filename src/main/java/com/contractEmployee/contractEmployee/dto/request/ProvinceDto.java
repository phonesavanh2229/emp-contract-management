package com.contractEmployee.contractEmployee.dto.request;


import lombok.Data;
import java.util.List;

@Data
public class ProvinceDto {
    private Integer id;
    private String provinceName;
    private String code;

    private List<DistrictDto> districts; // optional ถ้าจะโหลดแบบ nested
}
