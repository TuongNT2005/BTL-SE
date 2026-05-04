package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.TaiLieu;
import com.example.librarymanagement.repository.TaiLieuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiLieuService {
    private final TaiLieuRepository taiLieuRepository;

    public TaiLieuService(TaiLieuRepository taiLieuRepository) {
        this.taiLieuRepository = taiLieuRepository;
    }

    public TaiLieu findTaiLieuById(Integer maTaiLieu) {
        if (maTaiLieu == null) {
            throw new RuntimeException("Mã tài liệu không được để trống!");
        }
        return taiLieuRepository.findById(maTaiLieu)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài liệu!"));
    }

    public List<TaiLieu> getAll() {
        return taiLieuRepository.findAll();
    }
}
