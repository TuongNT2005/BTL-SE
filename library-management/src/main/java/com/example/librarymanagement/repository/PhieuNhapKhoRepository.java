package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.PhieuNhapKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuNhapKhoRepository extends JpaRepository<PhieuNhapKho, Integer> {
}