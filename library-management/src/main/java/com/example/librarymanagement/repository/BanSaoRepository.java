package com.example.librarymanagement.repository;

import java.util.List;

import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.TinhTrangVatLy;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BanSaoRepository extends JpaRepository<BanSao, Integer> {

    @Query(value = "SELECT bs.* FROM chitietphieumuon ct LEFT JOIN bansao bs ON bs.ma_ban_sao = ct.ma_ban_sao LEFT JOIN phieumuon pm ON pm.ma_phieu_muon = ct.ma_phieu_muon LEFT JOIN thethuvien ttv ON ttv.ma_the = pm.ma_the WHERE ct.ngay_tra IS NULL AND ttv.ma_the = :maThe", nativeQuery = true)
    List<BanSao> findAllBanSaoChuaTraByMaThe(@Param("maThe") String maThe);

    List<BanSao> findByTaiLieu_MaTaiLieuOrderByMaBanSaoAsc(Integer maTaiLieu);

    long countByTrangThaiLuuThong(TrangThaiLuuThong trangThaiLuuThong);

    long countByTinhTrangVatLy(TinhTrangVatLy tinhTrangVatLy);
}
