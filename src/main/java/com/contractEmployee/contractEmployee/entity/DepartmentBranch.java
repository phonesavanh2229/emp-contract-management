package com.contractEmployee.contractEmployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "department_branch")
public class DepartmentBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")   //
    private Long id;
    // --- Many-to-One: Department ---
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    // --- Many-to-One: Branch ---
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    // --- One-to-Many: Employee ---
    @JsonIgnore
    @OneToMany(mappedBy = "departmentBranch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;
}
