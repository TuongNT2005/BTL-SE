package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.ChiTietPhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuMuonRepository extends JpaRepository<ChiTietPhieuMuon, Integer> {
    @Query("SELECT COUNT(ct) FROM ChiTietPhieuMuon ct WHERE ct.phieuMuon.theThuVien.maThe = :maThe AND ct.ngayTra IS NULL")
    long countChuaTraByMaThe(@Param("maThe") Integer maThe);
}