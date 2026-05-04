package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.PhieuPhat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuPhatRepository extends JpaRepository<PhieuPhat, Integer> {
    @Query("select coalesce(sum(p.tienPhat), 0) from PhieuPhat p")
    Long sumTienPhat();
}
