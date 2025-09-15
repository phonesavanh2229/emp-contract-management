package com.contractEmployee.contractEmployee.dto;

import java.util.List;

public class PassportFullResponse {
    private PassportDto passport;
    private List<VisaDto> visas;
    private List<RentalCertificateDto> rentalCertificates;

    public PassportDto getPassport() {
        return passport;
    }
    public void setPassport(PassportDto passport) {
        this.passport = passport;
    }
    public List<VisaDto> getVisas() {
        return visas;
    }
    public void setVisas(List<VisaDto> visas) {
        this.visas = visas;
    }
    public List<RentalCertificateDto> getRentalCertificates() {
        return rentalCertificates;
    }
    public void setRentalCertificates(List<RentalCertificateDto> rentalCertificates) {
        this.rentalCertificates = rentalCertificates;
    }
}

