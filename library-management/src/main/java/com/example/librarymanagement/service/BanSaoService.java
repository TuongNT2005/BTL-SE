package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.repository.BanSaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanSaoService {
    private final BanSaoRepository banSaoRepository;

    public BanSaoService(BanSaoRepository banSaoRepository) {
        this.banSaoRepository = banSaoRepository;
    }

    public List<BanSao> getAll() {
        return banSaoRepository.findAll();
    }

    public BanSao getBanSaoById(Integer id) {
        if (id == null) {
            throw new RuntimeException("Mã bản sao không được để trống!");
        }
        return banSaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản sao có mã: " + id));
    }

    public List<BanSao> getAllBanSaoChuaTraByTheId(String theId) {
        return banSaoRepository.findAllBanSaoChuaTraByMaThe(theId);
    }
}
