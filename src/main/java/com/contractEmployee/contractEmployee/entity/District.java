package com.contractEmployee.contractEmployee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "district")
@Data
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String districtName;
    private  String code;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Branch> branches;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
}
