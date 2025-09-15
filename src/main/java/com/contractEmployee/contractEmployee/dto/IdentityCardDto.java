package com.contractEmployee.contractEmployee.dto;

import lombok.Data;

@Data
public class IdentityCardDto {
    private Long id;
    private String cardNumber;
    private String type;
    private String issuedDate;
    private String expiryDate;
    private String placeOfIssue;
    private IdentityCardDto identityCard;
}
