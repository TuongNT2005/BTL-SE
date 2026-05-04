package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "thethuvien")
@Data
public class TheThuVien {

    @Id
    @Column(name = "ma_the", length = 50)
    private String maThe;

    @Column(name = "ngay_phat_hanh")
    private LocalDateTime ngayPhatHanh = LocalDateTime.now();

    @Column(name = "ngay_het_han", nullable = false)
    private LocalDateTime ngayHetHan;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiThe trangThai;

    @ManyToOne
    @JoinColumn(name = "ma_goi_the", referencedColumnName = "ma_goi_the")
    private GoiThe goiThe;

    @ManyToOne
    @JoinColumn(name = "ma_doc_gia", referencedColumnName = "ma_doc_gia")
    private BanDoc banDoc;

    @OneToMany(mappedBy = "theThuVien")
    private List<PhieuMuon> phieuMuons;

    @OneToMany(mappedBy = "theThuVien")
    private List<HoaDon> hoaDons;
}
