package com.example.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TacGiaTaiLieuId implements Serializable {
    @Column(name = "ma_tac_gia")
    private Integer maTacGia;

    @Column(name = "ma_tai_lieu")
    private Integer maTaiLieu;

    public TacGiaTaiLieuId() {
    }

    public TacGiaTaiLieuId(Integer maTacGia, Integer maTaiLieu) {
        this.maTacGia = maTacGia;
        this.maTaiLieu = maTaiLieu;
    }

    public Integer getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(Integer maTacGia) {
        this.maTacGia = maTacGia;
    }

    public Integer getMaTaiLieu() {
        return maTaiLieu;
    }

    public void setMaTaiLieu(Integer maTaiLieu) {
        this.maTaiLieu = maTaiLieu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TacGiaTaiLieuId that = (TacGiaTaiLieuId) o;
        return Objects.equals(maTacGia, that.maTacGia) && Objects.equals(maTaiLieu, that.maTaiLieu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTacGia, maTaiLieu);
    }
}