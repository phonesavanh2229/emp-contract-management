package com.contractEmployee.contractEmployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate; import java.time.LocalDateTime; import java.util.ArrayList; import java.util.List;
@Entity
@Table(name = "visa")
@Getter
@Setter
@NoArgsConstructor
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visa_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "passport_id", nullable = false)
    private Passport passport;

    @OneToMany(mappedBy = "visa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalCertificate> rentals = new ArrayList<>();

    private String visaNumber;
    private String visaType;
    private String visaPurpose;
    private String countryCode;
    private String issuePlace;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Integer entries;
    private String visaStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
