package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tailieu")
@Data
public class TaiLieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tai_lieu")
    private Integer maTaiLieu;

    @Column(name = "ten_tl", nullable = false)
    private String tenTl;

    @Column(name = "isbn", length = 50)
    private String isbn;

    @Column(name = "mo_ta", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "the_loai")
    private String theLoai; // enum stored as string

    @ManyToOne
    @JoinColumn(name = "ma_nxb", referencedColumnName = "ma_nxb")
    private NhaXuatBan nhaXuatBan;

    @OneToMany(mappedBy = "taiLieu")
    private List<TacGiaTaiLieu> tacGiaTaiLieus;

    @OneToMany(mappedBy = "taiLieu")
    private List<BanSao> banSaoList;
}