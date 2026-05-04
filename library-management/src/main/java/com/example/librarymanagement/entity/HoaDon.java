package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "hoadon")
@Data
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "tong_tien_phat")
    private Long tongTienPhat = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai")
    private TrangThaiHoaDon trangThai; 

@ManyToOne
    @JoinColumn(name = "ma_nhan_vien", referencedColumnName = "ma_nhan_vien")
    private NhanVien nguoiThu;

    @ManyToOne
    @JoinColumn(name = "ma_the", referencedColumnName = "ma_the")
    private TheThuVien theThuVien;

    @OneToMany(mappedBy = "hoaDon")
    private List<PhieuPhat> phieuPhats;
}
