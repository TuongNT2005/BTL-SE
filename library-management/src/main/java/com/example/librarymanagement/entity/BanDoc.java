package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bandoc")
@Data
public class BanDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_doc_gia")
    private Integer maDocGia;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "dia_chi", columnDefinition = "TEXT")
    private String diaChi;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "sdt", length = 20)
    private String sdt;

    @OneToOne(mappedBy = "banDoc")
    private TheThuVien theThuVien;
}