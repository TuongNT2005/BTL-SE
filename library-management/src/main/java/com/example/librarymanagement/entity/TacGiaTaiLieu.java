package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tacgia_tailieu")
@Data
public class TacGiaTaiLieu {

    @EmbeddedId
    private TacGiaTaiLieuId id;

    @ManyToOne
    @MapsId("maTacGia")
    @JoinColumn(name = "ma_tac_gia")
    private TacGia tacGia;

    @ManyToOne
    @MapsId("maTaiLieu")
    @JoinColumn(name = "ma_tai_lieu")
    private TaiLieu taiLieu;
}
