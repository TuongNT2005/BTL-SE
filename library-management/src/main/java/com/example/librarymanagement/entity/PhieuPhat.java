package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "phieuphat")
@Data
public class PhieuPhat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phieu_phat")
    private Integer maPhieuPhat;

    @Column(name = "tien_phat", nullable = false)
    private Long tienPhat;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ma_the", referencedColumnName = "ma_the")
    private TheThuVien theThuVien;

    @ManyToOne
    @JoinColumn(name = "ma_chi_tiet_phieu_muon", referencedColumnName = "ma_chi_tiet_phieu_muon")
    private ChiTietPhieuMuon chiTietPhieuMuon;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", referencedColumnName = "ma_hoa_don")
    private HoaDon hoaDon;
}