package com.example.librarymanagement.service;

import java.util.Comparator;
import java.util.List;

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
        String key = normalize(keyword);
        if (key.isBlank()) {
            List<TaiLieu> taiLieus = taiLieuRepository.findAll();
            // taiLieus.sort(new Comparator<TaiLieu>() {
            // @Override
            // public int compare(TaiLieu tl1, TaiLieu tl2){
            // return tl1.getTenTl().compareTo(tl2.getTenTl());
            // }
            // });
            return taiLieus;
        }
        return taiLieuRepository.searchBasic(key);
    }

    private String normalize(String value) {
        if (value == null)
            return "";
        return value.trim();
    }
}
