package com.contractEmployee.contractEmployee.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String staffCode;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender  gender;

    private LocalDate birthday;

    private String phone;
    private String phoneTwo;
    private String email;

    private LocalDate startWorking;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    @Column(name = "district")
    private String distircts;
    private String province;
    private String village;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District  district;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "department_branch_id")
    private DepartmentBranch departmentBranch;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference(value = "employee-passports")
    private Set<Passport> passports = new HashSet<>();

    private Boolean isCurrent = true;
}
