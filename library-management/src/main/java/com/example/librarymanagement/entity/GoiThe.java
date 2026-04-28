package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "goithe")
@Data
public class GoiThe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_goi_the")
    private Integer maGoiThe;

    @Column(name = "ten", nullable = false, length = 100)
    private String ten;

    @Column(name = "gia", nullable = false)
    private Long gia;

    @Column(name = "thoi_han", nullable = false)
    private Integer thoiHan; // months

    @OneToMany(mappedBy = "goiThe")
    private List<TheThuVien> theThuViens;
}