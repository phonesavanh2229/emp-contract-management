package com.contractEmployee.contractEmployee.mapper;

import com.contractEmployee.contractEmployee.dto.IdentityCardDto;
import com.contractEmployee.contractEmployee.entity.Employee;
import com.contractEmployee.contractEmployee.entity.IdentityCard;

import java.time.LocalDate;

public class IdentityCardMapper {


    public static IdentityCard toEntity(IdentityCardDto req, Employee employee) {
        IdentityCard card = new IdentityCard();
        card.setEmployee(employee);
        card.setCardNumber(req.getCardNumber());
        card.setType(req.getType());
        card.setIssuedDate(LocalDate.parse(req.getIssuedDate()));
        card.setExpiryDate(LocalDate.parse(req.getExpiryDate()));
        card.setPlaceOfIssue(req.getPlaceOfIssue());
        return card;
    }

    public static IdentityCardDto toDto(IdentityCard card) {
        IdentityCardDto dto = new IdentityCardDto();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setType(card.getType());
        dto.setIssuedDate(String.valueOf(card.getIssuedDate()));
        dto.setExpiryDate(String.valueOf(card.getExpiryDate()));
        dto.setPlaceOfIssue(card.getPlaceOfIssue());
        return dto;
    }
}