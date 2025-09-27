package com.contractEmployee.contractEmployee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "districts")
@Data
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String districtName;
    private  String code;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
}
