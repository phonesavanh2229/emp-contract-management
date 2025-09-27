package com.contractEmployee.contractEmployee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")   // 👈 ต้องเป็น id ไม่ใช่ department_id
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    private String location;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentBranch> departmentBranches;
}
