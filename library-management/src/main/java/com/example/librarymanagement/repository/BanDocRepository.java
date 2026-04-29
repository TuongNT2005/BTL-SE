package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.BanDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BanDocRepository extends JpaRepository<BanDoc, Integer> {
    Optional<BanDoc> findByEmailAndSdt(String email, String sdt);
}
