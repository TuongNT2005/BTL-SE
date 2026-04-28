package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "nhanvien")
@Data
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nhan_vien")
    private Integer maNhanVien;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "sdt", length = 20)
    private String sdt;

    @Column(name = "trang_thai_lam_viec")
    private Boolean trangThaiLamViec = true;

    @OneToMany(mappedBy = "nguoiLapPhieu")
    private List<PhieuNhapKho> phieuNhapKhos;

    @OneToMany(mappedBy = "nhanVienTao")
    private List<PhieuMuon> phieuMuons;

    @OneToMany(mappedBy = "nguoiThu")
    private List<HoaDon> hoaDons;
}