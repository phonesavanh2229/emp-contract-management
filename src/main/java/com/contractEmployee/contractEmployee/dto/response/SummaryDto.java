package com.contractEmployee.contractEmployee.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryDto {
    private String type;
    private long totalActive;
    private long totalExpiring;
    private long totalExpired;
}

