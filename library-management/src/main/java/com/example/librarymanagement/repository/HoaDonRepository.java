package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.HoaDon;
import com.example.librarymanagement.entity.TrangThaiHoaDon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @EntityGraph(attributePaths = {
            "theThuVien",
            "theThuVien.banDoc",
            "phieuPhats",
            "phieuPhats.chiTietPhieuMuon",
            "phieuPhats.chiTietPhieuMuon.banSao",
            "phieuPhats.chiTietPhieuMuon.banSao.taiLieu"
    })
    List<HoaDon> findByTheThuVien_MaTheAndTrangThai(String maThe, TrangThaiHoaDon trangThai);
}