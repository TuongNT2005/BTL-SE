package com.example.librarymanagement.repository;

import java.util.List;

import com.example.librarymanagement.entity.HoaDon;
import com.example.librarymanagement.entity.TrangThaiHoaDon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    long countByTrangThai(TrangThaiHoaDon trangThai);

    @Query("select coalesce(sum(h.tongTienPhat), 0) from HoaDon h")
    Long sumTongTienPhat();
}
