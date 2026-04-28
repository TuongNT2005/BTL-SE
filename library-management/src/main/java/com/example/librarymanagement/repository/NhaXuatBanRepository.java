package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.NhaXuatBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaXuatBanRepository extends JpaRepository<NhaXuatBan, Integer> {
}