package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "chitietphieumuon")
@Data
public class ChiTietPhieuMuon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet_phieu_muon")
    private Integer maChiTietPhieuMuon;

    @ManyToOne
    @JoinColumn(name = "ma_phieu_muon", referencedColumnName = "ma_phieu_muon")
    private PhieuMuon phieuMuon;

    @ManyToOne
    @JoinColumn(name = "ma_ban_sao", referencedColumnName = "ma_ban_sao")
    private BanSao banSao;

    @Column(name = "ngay_tra")
    private LocalDateTime ngayTra;

    @Enumerated(EnumType.STRING)
    @Column(name = "tinh_trang_vat_ly")
    private TinhTrangVatLy tinhTrangVatLy;
}