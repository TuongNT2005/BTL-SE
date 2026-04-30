package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.TheThuVien;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TheThuVienRepository extends JpaRepository<TheThuVien, Integer> {
    boolean existsByBanDoc_Email(String email);
    Optional<TheThuVien> findByMaThe(Integer maThe);
}