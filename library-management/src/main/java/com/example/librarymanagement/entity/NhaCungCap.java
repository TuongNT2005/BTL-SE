package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "nhacungcap")
@Data
public class NhaCungCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ncc")
    private Integer maNcc;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "dia_chi", columnDefinition = "TEXT")
    private String diaChi;

    @Column(name = "sdt", length = 20)
    private String sdt;

    @Column(name = "email", length = 100)
    private String email;

    @OneToMany(mappedBy = "nhaCungCap")
    private List<PhieuNhapKho> phieuNhapKhos;
}