package com.contractEmployee.contractEmployee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")   // 👈 ต้องใช้ "id" ไม่ใช่ "branch_id"
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_main_branch")
    private boolean isMainBranch;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
