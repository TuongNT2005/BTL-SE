package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.PhieuPhat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuPhatRepository extends JpaRepository<PhieuPhat, Integer> {
}