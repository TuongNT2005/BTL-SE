package com.example.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ChiTietPhieuNhapId implements Serializable {

    @Column(name = "ma_phieu_nhap")
    private Integer maPhieuNhap;

    @Column(name = "ma_tai_lieu")
    private Integer maTaiLieu;
}

