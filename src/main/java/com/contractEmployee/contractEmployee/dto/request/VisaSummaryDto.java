package com.contractEmployee.contractEmployee.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisaSummaryDto {
    private long totalActive;
    private long totalExpiring;
    private long totalExpired;
}