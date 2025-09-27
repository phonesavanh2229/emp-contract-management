package com.contractEmployee.contractEmployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate; import java.time.LocalDateTime; import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "visa")
@Getter
@Setter
@NoArgsConstructor
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visa_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passport_id", nullable = false)
//    @Fetch(FetchMode.SUBSELECT)
    private Passport passport;
    @OneToMany(mappedBy = "visa", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference(value = "visa-rentals")
    private Set<RentalCertificate> rentals = new HashSet<>();


    private String visaNumber;
    private String visaType;
    private String visaPurpose;
    private String countryCode;
    private String issuePlace;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Long entries;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_visa_id")
    @JsonIgnore
    private Visa previousVisa;

    @Column(name = "visa_status")
    private String status;
    private Boolean isCurrent = true;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
