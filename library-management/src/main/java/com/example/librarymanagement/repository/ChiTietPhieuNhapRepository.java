package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.ChiTietPhieuNhap;
import com.example.librarymanagement.entity.ChiTietPhieuNhapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuNhapRepository extends JpaRepository<ChiTietPhieuNhap, ChiTietPhieuNhapId> {
}