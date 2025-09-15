package com.contractEmployee.contractEmployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate; import java.time.LocalDateTime; import java.util.ArrayList; import java.util.List;

@Entity @Table(name = "passport")
@Getter @Setter @NoArgsConstructor
public class Passport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore
    private Employee employee;

    @Column(name = "passport_no", nullable = false, unique = true)
    private String passportNo;

    @Column(name = "passport_type")
    private String passportType;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "issue_place")
    private String issuePlace;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_passport_id")
    @JsonIgnore
    private Passport previousPassport;

    @CreationTimestamp @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "passport", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Visa> visas = new ArrayList<>();
}
