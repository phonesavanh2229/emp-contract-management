package com.contractEmployee.contractEmployee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "provinces")
@Getter
@Setter
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_name", nullable = false, length = 255)
    private String provinceName;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    // One province → many districts
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<District> districts;
}