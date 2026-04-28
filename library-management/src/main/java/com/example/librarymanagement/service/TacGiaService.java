package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.TacGia;
import com.example.librarymanagement.repository.TacGiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacGiaService {

    private final TacGiaRepository repository;

    public TacGiaService(TacGiaRepository repository) {
        this.repository = repository;
    }

    public List<TacGia> findAll() {
        return repository.findAll();
    }

    public TacGia save(TacGia tacGia) {
        return repository.save(tacGia);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}