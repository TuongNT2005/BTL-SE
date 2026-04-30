package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.GoiThe;
import com.example.librarymanagement.repository.GoiTheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoiTheService {
    private final GoiTheRepository goiTheRepository;

    public GoiTheService(GoiTheRepository goiTheRepository) {
        this.goiTheRepository = goiTheRepository;
    }

    public List<GoiThe> getAll() {
        return goiTheRepository.findAll();
    }
}
