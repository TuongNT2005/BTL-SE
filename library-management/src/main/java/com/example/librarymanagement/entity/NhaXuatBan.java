package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "nhaxuatban")
@Data
public class NhaXuatBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nxb")
    private Integer maNxb;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "dia_chi", columnDefinition = "TEXT")
    private String diaChi;

    @Column(name = "sdt", length = 20)
    private String sdt;

    @OneToMany(mappedBy = "nhaXuatBan")
    private List<TaiLieu> taiLieus;
}