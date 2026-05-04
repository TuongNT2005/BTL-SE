package com.example.librarymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.TrangThaiPhieuMuon;
import com.example.librarymanagement.repository.PhieuMuonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhieuMuonService {
    private final PhieuMuonRepository phieuMuonRepository;

    public List<PhieuMuon> getAllPhieuMuonChuaTraByMaThe(String maThe) {
        // return phieuMuonRepository.findByTheThuVien_MaTheAndTrangThaiNot(maThe,
        // TrangThaiPhieuMuon.DANG_MUON);
        return phieuMuonRepository.findByTheThuVien_MaTheAndTrangThaiNot(maThe, TrangThaiPhieuMuon.DA_TRA);
    }

}
