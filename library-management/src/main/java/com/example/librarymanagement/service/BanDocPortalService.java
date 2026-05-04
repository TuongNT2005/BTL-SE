package com.example.librarymanagement.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.TaiLieu;
import com.example.librarymanagement.repository.PhieuMuonRepository;
import com.example.librarymanagement.repository.TaiLieuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BanDocPortalService {

    private final PhieuMuonRepository phieuMuonRepository;
    private final TaiLieuRepository taiLieuRepository;

    public List<PhieuMuon> findLichSuMuonTra(Integer maDocGia) {
        return phieuMuonRepository.findLichSuByMaDocGia(maDocGia);
    }

    public List<TaiLieu> traCuuTaiLieu(String keyword) {
        String normalizedKeyword = normalize(keyword);
        if (normalizedKeyword.isBlank()) {
            return taiLieuRepository.findAll(Sort.by(Sort.Direction.ASC, "tenTl"));
        }
        return taiLieuRepository.searchBasic(normalizedKeyword);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
