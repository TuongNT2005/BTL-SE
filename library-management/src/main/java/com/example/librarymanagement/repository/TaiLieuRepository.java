package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.TaiLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiLieuRepository extends JpaRepository<TaiLieu, Integer> {
}