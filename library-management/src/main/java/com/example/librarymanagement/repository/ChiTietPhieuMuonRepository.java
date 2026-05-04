package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.ChiTietPhieuMuon;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuMuonRepository extends JpaRepository<ChiTietPhieuMuon, Integer> {

    ChiTietPhieuMuon findByBanSaoAndNgayTraIsNull(BanSao banSao);

    Optional<ChiTietPhieuMuon> findByPhieuMuon_MaPhieuMuonAndBanSao_MaBanSaoAndNgayTraIsNull(
            Integer maPhieuMuon,
            Integer maBanSao
    );

    long countByPhieuMuon_MaPhieuMuonAndNgayTraIsNull(Integer maPhieuMuon);
}