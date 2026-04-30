package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chitietphieunhap")
@Data
public class ChiTietPhieuNhap {

    @EmbeddedId
    private ChiTietPhieuNhapId id;

    @ManyToOne
    @MapsId("maPhieuNhap")
    @JoinColumn(name = "ma_phieu_nhap", referencedColumnName = "ma_phieu_nhap")
    private PhieuNhapKho phieuNhapKho;

    @ManyToOne
    @MapsId("maTaiLieu")
    @JoinColumn(name = "ma_tai_lieu", referencedColumnName = "ma_tai_lieu")
    private TaiLieu taiLieu;

    @Column(name = "so_luong", nullable = false)
    private Long soLuong;

    @Column(name = "don_gia", nullable = false)
    private Long donGia;
}