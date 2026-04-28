package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.TacGiaTaiLieu;
import com.example.librarymanagement.entity.TacGiaTaiLieuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacGiaTaiLieuRepository extends JpaRepository<TacGiaTaiLieu, TacGiaTaiLieuId> {
}
