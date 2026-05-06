package com.example.librarymanagement.repository;

import java.util.List;

import com.example.librarymanagement.entity.TaiLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiLieuRepository extends JpaRepository<TaiLieu, Integer> {
    @Query("""
            SELECT t FROM TaiLieu t
            LEFT JOIN t.nhaXuatBan n
            WHERE t.tenTl LIKE CONCAT('%', :keyword, '%')
               OR t.isbn LIKE CONCAT('%', :keyword, '%')
               OR n.ten LIKE CONCAT('%', :keyword, '%')
            ORDER BY t.tenTl ASC
            """)
    List<TaiLieu> searchBasic(@Param("keyword") String keyword);
}
