package com.example.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tacgia")
@Data
public class TacGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_tac_gia")
    private Integer maTacGia;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "tieu_su", columnDefinition = "TEXT")
    private String tieuSu;

    @OneToMany(mappedBy = "tacGia")
    private List<TacGiaTaiLieu> tacGiaTaiLieus;
}