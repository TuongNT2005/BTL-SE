package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.LapPhieuMuonRequest;
import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.ChiTietPhieuMuon;
import com.example.librarymanagement.entity.NhanVien;
import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.TheThuVien;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
import com.example.librarymanagement.entity.TrangThaiPhieuMuon;
import com.example.librarymanagement.repository.BanSaoRepository;
import com.example.librarymanagement.repository.ChiTietPhieuMuonRepository;
import com.example.librarymanagement.repository.NhanVienRepository;
import com.example.librarymanagement.repository.PhieuMuonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class MuonTraService {

    private final TheThuVienService theThuVienService;
    private final BanSaoService banSaoService;
    private final NhanVienRepository nhanVienRepository;
    private final PhieuMuonRepository phieuMuonRepository;
    private final BanSaoRepository banSaoRepository;
    private final ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;

    public MuonTraService(
            TheThuVienService theThuVienService,
            BanSaoService banSaoService,
            NhanVienRepository nhanVienRepository,
            PhieuMuonRepository phieuMuonRepository,
            BanSaoRepository banSaoRepository,
            ChiTietPhieuMuonRepository chiTietPhieuMuonRepository) {
        this.theThuVienService = theThuVienService;
        this.banSaoService = banSaoService;
        this.nhanVienRepository = nhanVienRepository;
        this.phieuMuonRepository = phieuMuonRepository;
        this.banSaoRepository = banSaoRepository;
        this.chiTietPhieuMuonRepository = chiTietPhieuMuonRepository;
    }

    public void checkLapPhieuMuonConditions(LapPhieuMuonRequest request) {
        if (request == null) {
            throw new RuntimeException("Thông tin lập phiếu mượn không được để trống!");
        }
        if (request.getTheId() == null) {
            throw new RuntimeException("Mã thẻ không được để trống!");
        }
        if (request.getDsBanSaoId() == null || request.getDsBanSaoId().isEmpty()) {
            throw new RuntimeException("Phải chọn ít nhất 1 bản sao để mượn!");
        }

        TheThuVien theThuVien = theThuVienService.getTheById(request.getTheId());
        if (theThuVien == null) {
            throw new RuntimeException("Không tìm thấy thẻ thư viện!");
        }

        Set<Integer> seenBanSao = new HashSet<>();
        for (Integer banSaoId : request.getDsBanSaoId()) {
            if (banSaoId == null) {
                throw new RuntimeException("Danh sách bản sao không hợp lệ!");
            }
            if (!seenBanSao.add(banSaoId)) {
                throw new RuntimeException("Trùng mã bản sao: " + banSaoId);
            }

            BanSao banSao = banSaoService.getBanSaoById(banSaoId);
            if (banSao.getTrangThaiLuuThong() != TrangThaiLuuThong.SAN_SANG) {
                throw new RuntimeException("Bản sao " + banSaoId + " không ở trạng thái sẵn sàng!");
            }
        }

        long soSachChuaTra = theThuVienService.getBanSaoChuaTraByTheId(request.getTheId());
        int soSachMuonThem = request.getDsBanSaoId().size();
        if (soSachChuaTra + soSachMuonThem > 5) {
            throw new RuntimeException("Số sách chưa trả + số sách muốn mượn không được vượt quá 5!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean xuLyLapPhieuMuon(LapPhieuMuonRequest request) {
        checkLapPhieuMuonConditions(request);

        TheThuVien theThuVien = theThuVienService.getTheById(request.getTheId());
        NhanVien nhanVien = nhanVienRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên có mã 1!"));

        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setNgayMuon(LocalDateTime.now());
        phieuMuon.setNgayHetHan(LocalDateTime.now().plusDays(14));
        phieuMuon.setTrangThai(TrangThaiPhieuMuon.DANG_MUON);
        phieuMuon.setTheThuVien(theThuVien);
        phieuMuon.setNhanVienTao(nhanVien);
        phieuMuon = phieuMuonRepository.save(phieuMuon);
        for (Integer banSaoId : request.getDsBanSaoId()) {
            BanSao banSao = banSaoService.getBanSaoById(banSaoId);
            banSao.setTrangThaiLuuThong(TrangThaiLuuThong.DANG_MUON);
            banSaoRepository.save(banSao);

            ChiTietPhieuMuon chiTietPhieuMuon = new ChiTietPhieuMuon();
            chiTietPhieuMuon.setPhieuMuon(phieuMuon);
            chiTietPhieuMuon.setBanSao(banSao);
            chiTietPhieuMuonRepository.save(chiTietPhieuMuon);
        }
        return true;
    }
}
