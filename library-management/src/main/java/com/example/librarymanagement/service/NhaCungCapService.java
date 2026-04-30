package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.NhaCungCap;
import com.example.librarymanagement.repository.NhaCungCapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhaCungCapService {
    private final NhaCungCapRepository nhaCungCapRepository;

    public NhaCungCapService(NhaCungCapRepository nhaCungCapRepository) {
        this.nhaCungCapRepository = nhaCungCapRepository;
    }

    public List<NhaCungCap> getAll() {
        return nhaCungCapRepository.findAll();
    }

    public NhaCungCap findNCCById(Integer maNcc) {
        if (maNcc == null) {
            throw new RuntimeException("Mã NCC không được để trống!");
        }
        return nhaCungCapRepository.findById(maNcc)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp!"));
    }
}

