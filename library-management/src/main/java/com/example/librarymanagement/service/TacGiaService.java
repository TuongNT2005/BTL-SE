package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.TacGia;
import com.example.librarymanagement.repository.TacGiaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TacGiaService {

    private final TacGiaRepository repository;



    public List<TacGia> findAll() {
        return repository.findAll();
    }

    public TacGia findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả"));
    }

    public TacGia capNhat(Integer id, TacGia tacGia) {
        TacGia tacGiaRepo = findById(id);
        tacGiaRepo.setTen(tacGia.getTen());
        tacGiaRepo.setTieuSu(tacGia.getTieuSu());
        return repository.save(tacGiaRepo);
    }

    public TacGia save(TacGia tacGia) {
        return repository.save(tacGia);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
