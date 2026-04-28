package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "bansao")
@Data
public class BanSao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_ban_sao")
    private Integer maBanSao;

    @ManyToOne
    @JoinColumn(name = "ma_tai_lieu", referencedColumnName = "ma_tai_lieu")
    private TaiLieu taiLieu;

    @Enumerated(EnumType.STRING)
    @Column(name = "tinh_trang_vat_ly")
    private TinhTrangVatLy tinhTrangVatLy;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai_luu_thong")
    private TrangThaiLuuThong trangThaiLuuThong;

    @OneToMany(mappedBy = "banSao")
    private List<ChiTietPhieuMuon> chiTietPhieuMuons;
}