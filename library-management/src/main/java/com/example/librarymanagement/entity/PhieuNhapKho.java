package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "phieunhapkho")
@Data
public class PhieuNhapKho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phieu_nhap")
    private Integer maPhieuNhap;

    @Column(name = "ngay_nhap")
    private java.time.LocalDateTime ngayNhap;

    @Column(name = "tong_tien")
    private Long tongTien;

    @ManyToOne
    @JoinColumn(name = "ma_ncc", referencedColumnName = "ma_ncc")
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien", referencedColumnName = "ma_nhan_vien")
    private NhanVien nguoiLapPhieu;

    @OneToMany(mappedBy = "phieuNhapKho")
    private List<ChiTietPhieuNhap> chiTietPhieuNhaps;
}