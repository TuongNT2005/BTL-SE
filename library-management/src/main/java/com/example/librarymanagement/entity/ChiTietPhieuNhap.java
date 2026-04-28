package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chitietphieunhap")
@Data
public class ChiTietPhieuNhap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // surrogate key

    @ManyToOne
    @JoinColumn(name = "ma_phieu_nhap", referencedColumnName = "ma_phieu_nhap")
    private PhieuNhapKho phieuNhapKho;

    @ManyToOne
    @JoinColumn(name = "ma_tai_lieu", referencedColumnName = "ma_tai_lieu")
    private TaiLieu taiLieu;

    @Column(name = "so_luong", nullable = false)
    private Long soLuong;

    @Column(name = "don_gia", nullable = false)
    private Long donGia;
}