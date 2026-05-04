package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.PhieuNhapKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuNhapKhoRepository extends JpaRepository<PhieuNhapKho, Integer> {
    @Query("select coalesce(sum(p.tongTien), 0) from PhieuNhapKho p")
    Long sumTongTien();
}
