package com.contractEmployee.contractEmployee.dto;


import lombok.Data;

@Data
public class VisaSummaryDto {
    private long totalActive;
    private long totalExpiring;
    private long totalExpired;
}