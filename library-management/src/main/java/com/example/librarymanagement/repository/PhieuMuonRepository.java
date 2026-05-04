package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.TrangThaiPhieuMuon;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Integer> {

    @EntityGraph(attributePaths = {
            "chiTietPhieuMuons",
            "chiTietPhieuMuons.banSao",
            "chiTietPhieuMuons.banSao.taiLieu"
    })
    List<PhieuMuon> findByTheThuVien_MaTheAndTrangThaiNot(String maThe, TrangThaiPhieuMuon trangThai);


}
