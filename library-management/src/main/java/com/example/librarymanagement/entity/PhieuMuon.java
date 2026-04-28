package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "phieumuon")
@Data
public class PhieuMuon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_phieu_muon")
    private Integer maPhieuMuon;

    @Column(name = "ngay_muon")
    private LocalDateTime ngayMuon = LocalDateTime.now();

    @Column(name = "ngay_het_han", nullable = false)
    private LocalDateTime ngayHetHan;

    @Column(name = "trang_thai")
    private String trangThai; // enum stored as string

    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien", referencedColumnName = "ma_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "ma_the", referencedColumnName = "ma_the")
    private TheThuVien theThuVien;

    @OneToMany(mappedBy = "phieuMuon")
    private List<ChiTietPhieuMuon> chiTietPhieuMuons;
}