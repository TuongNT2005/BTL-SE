package com.example.librarymanagement.repository;

import java.util.List;

import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.TrangThaiPhieuMuon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Integer> {

    @EntityGraph(attributePaths = {
            "chiTietPhieuMuons",
            "chiTietPhieuMuons.banSao",
            "chiTietPhieuMuons.banSao.taiLieu"
    })
    List<PhieuMuon> findByTheThuVien_MaTheAndTrangThaiNot(String maThe, TrangThaiPhieuMuon trangThai);

    long countByTrangThai(TrangThaiPhieuMuon trangThai);

    @Query("""
            select distinct p
            from PhieuMuon p
            left join fetch p.theThuVien
            left join fetch p.chiTietPhieuMuons c
            left join fetch c.banSao b
            left join fetch b.taiLieu
            where p.theThuVien.banDoc.maDocGia = :maDocGia
            order by p.ngayMuon desc
            """)
    List<PhieuMuon> findLichSuByMaDocGia(@Param("maDocGia") Integer maDocGia);
}
