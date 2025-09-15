package com.contractEmployee.contractEmployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter; import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate; import java.time.LocalDateTime;

@Entity
@Table(name = "rental_certificate")
@Getter
@Setter
@NoArgsConstructor
public class RentalCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "visa_id", nullable = false)
    private Visa visa;


    @Column(name = "certificate_number")
    private String certificateNumber;

    private String address;

    @Column(name = "landlord_name")
    private String landlordName;

    @Column(name = "landlord_contact")
    private String landlordContact;

    @Column(name = "rental_type")
    private String rentalType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
