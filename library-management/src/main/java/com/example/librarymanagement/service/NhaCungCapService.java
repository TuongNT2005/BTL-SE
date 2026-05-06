package com.example.librarymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.NhaCungCap;
import com.example.librarymanagement.repository.NhaCungCapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NhaCungCapService {

    private final NhaCungCapRepository repository;

    public List<NhaCungCap> findAll() {
        return repository.findAll();
    }
    public NhaCungCap findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp"));
    }
    public NhaCungCap save(NhaCungCap nhaCungCap) {
        return repository.save(nhaCungCap);
    }

    public NhaCungCap capNhat(Integer id, NhaCungCap nhaCungCap) {
        NhaCungCap nhaCungCapRepo = findById(id);
        nhaCungCapRepo.setTen(nhaCungCap.getTen());
        nhaCungCapRepo.setDiaChi(nhaCungCap.getDiaChi());
        nhaCungCapRepo.setSdt(nhaCungCap.getSdt());
        nhaCungCapRepo.setEmail(nhaCungCap.getEmail());
        return repository.save(nhaCungCapRepo);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
