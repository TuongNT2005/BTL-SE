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

    @Column(name = "tinh_trang_vat_ly")
    private String tinhTrangVatLy; // enum stored as string

    @Column(name = "trang_thai_luu_thong")
    private String trangThaiLuuThong; // enum stored as string

    @OneToMany(mappedBy = "banSao")
    private List<ChiTietPhieuMuon> chiTietPhieuMuons;
}