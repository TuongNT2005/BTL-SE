package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.GoiThe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoiTheRepository extends JpaRepository<GoiThe, Integer> {
}