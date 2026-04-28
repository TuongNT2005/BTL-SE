package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.PhieuMuon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuMuonRepository extends JpaRepository<PhieuMuon, Integer> {
}