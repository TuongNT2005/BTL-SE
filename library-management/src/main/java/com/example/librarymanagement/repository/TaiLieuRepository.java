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
            select t
            from TaiLieu t
            left join t.nhaXuatBan n
            where lower(t.tenTl) like lower(concat('%', :keyword, '%'))
               or lower(coalesce(t.isbn, '')) like lower(concat('%', :keyword, '%'))
               or lower(coalesce(n.ten, '')) like lower(concat('%', :keyword, '%'))
            order by t.tenTl asc
            """)
    List<TaiLieu> searchBasic(@Param("keyword") String keyword);
}
