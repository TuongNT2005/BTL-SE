package com.example.librarymanagement.service;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.dto.BaoCaoTongQuan;
import com.example.librarymanagement.entity.TinhTrangVatLy;
import com.example.librarymanagement.entity.TrangThaiHoaDon;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
import com.example.librarymanagement.entity.TrangThaiPhieuMuon;
import com.example.librarymanagement.repository.BanDocRepository;
import com.example.librarymanagement.repository.BanSaoRepository;
import com.example.librarymanagement.repository.HoaDonRepository;
import com.example.librarymanagement.repository.NhanVienRepository;
import com.example.librarymanagement.repository.PhieuMuonRepository;
import com.example.librarymanagement.repository.PhieuNhapKhoRepository;
import com.example.librarymanagement.repository.PhieuPhatRepository;
import com.example.librarymanagement.repository.TaiLieuRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaoCaoService {

    private final BanDocRepository banDocRepository;
    private final NhanVienRepository nhanVienRepository;
    private final TaiLieuRepository taiLieuRepository;
    private final BanSaoRepository banSaoRepository;
    private final PhieuMuonRepository phieuMuonRepository;
    private final PhieuPhatRepository phieuPhatRepository;
    private final HoaDonRepository hoaDonRepository;
    private final PhieuNhapKhoRepository phieuNhapKhoRepository;

    public BaoCaoTongQuan getTongQuan() {
        return new BaoCaoTongQuan(
                banDocRepository.count(),
                nhanVienRepository.count(),
                nhanVienRepository.countByTrangThaiLamViec(true),
                taiLieuRepository.count(),
                banSaoRepository.count(),
                banSaoRepository.countByTrangThaiLuuThong(TrangThaiLuuThong.SAN_SANG),
                banSaoRepository.countByTrangThaiLuuThong(TrangThaiLuuThong.DANG_MUON),
                banSaoRepository.countByTinhTrangVatLy(TinhTrangVatLy.HU_HONG),
                banSaoRepository.countByTinhTrangVatLy(TinhTrangVatLy.MAT),
                phieuMuonRepository.count(),
                phieuMuonRepository.countByTrangThai(TrangThaiPhieuMuon.DANG_MUON),
                phieuMuonRepository.countByTrangThai(TrangThaiPhieuMuon.DA_TRA),
                phieuMuonRepository.countByTrangThai(TrangThaiPhieuMuon.QUA_HAN),
                phieuPhatRepository.count(),
                hoaDonRepository.countByTrangThai(TrangThaiHoaDon.CHUA_THANH_TOAN),
                safe(phieuPhatRepository.sumTienPhat()),
                hoaDonRepository.count(),
                safe(hoaDonRepository.sumTongTienPhat()),
                phieuNhapKhoRepository.count(),
                safe(phieuNhapKhoRepository.sumTongTien())
        );
    }

    private long safe(Long value) {
        return value == null ? 0L : value;
    }
}
