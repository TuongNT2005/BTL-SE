package com.example.librarymanagement.service;

import com.example.librarymanagement.auth.CurrentUser;
import com.example.librarymanagement.dto.ChiTietTraDTO;
import com.example.librarymanagement.dto.LapPhieuMuonRequest;
import com.example.librarymanagement.dto.TraSachChiTietRow;
import com.example.librarymanagement.dto.TraSachRequest;
import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.ChiTietPhieuMuon;
import com.example.librarymanagement.entity.HoaDon;
import com.example.librarymanagement.entity.NhanVien;
import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.PhieuPhat;
import com.example.librarymanagement.entity.TheThuVien;
import com.example.librarymanagement.entity.TinhTrangVatLy;
import com.example.librarymanagement.entity.TrangThaiHoaDon;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
import com.example.librarymanagement.entity.TrangThaiPhieuMuon;
import com.example.librarymanagement.repository.BanSaoRepository;
import com.example.librarymanagement.repository.ChiTietPhieuMuonRepository;
import com.example.librarymanagement.repository.ChiTietPhieuNhapRepository;
import com.example.librarymanagement.repository.HoaDonRepository;
import com.example.librarymanagement.repository.NhanVienRepository;
import com.example.librarymanagement.repository.PhieuMuonRepository;
import com.example.librarymanagement.repository.PhieuPhatRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class MuonTraService {

    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;
    private final TheThuVienService theThuVienService;
    private final BanSaoService banSaoService;
    private final NhanVienRepository nhanVienRepository;
    private final PhieuMuonRepository phieuMuonRepository;
    private final BanSaoRepository banSaoRepository;
    private final ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;
    private final PhieuPhatRepository phieuPhatRepository;
    private final HoaDonRepository hoaDonRepository;

    public MuonTraService(
            TheThuVienService theThuVienService,
            BanSaoService banSaoService,
            NhanVienRepository nhanVienRepository,
            PhieuMuonRepository phieuMuonRepository,
            BanSaoRepository banSaoRepository,
            ChiTietPhieuMuonRepository chiTietPhieuMuonRepository,
            PhieuPhatRepository phieuPhatRepository,
            HoaDonRepository hoaDonRepository, ChiTietPhieuNhapRepository chiTietPhieuNhapRepository) {
        this.theThuVienService = theThuVienService;
        this.banSaoService = banSaoService;
        this.nhanVienRepository = nhanVienRepository;
        this.phieuMuonRepository = phieuMuonRepository;
        this.banSaoRepository = banSaoRepository;
        this.chiTietPhieuMuonRepository = chiTietPhieuMuonRepository;
        this.phieuPhatRepository = phieuPhatRepository;
        this.hoaDonRepository = hoaDonRepository;
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
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

        long soSachChuaTra = theThuVienService.getBanSaoChuaTraByTheId(request.getTheId()).size();
        int soSachMuonThem = request.getDsBanSaoId().size();
        if (soSachChuaTra + soSachMuonThem > 5) {
            throw new RuntimeException("Số sách chưa trả + số sách muốn mượn không được vượt quá 5!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean xuLyLapPhieuMuon(LapPhieuMuonRequest request) {
        checkLapPhieuMuonConditions(request);

        TheThuVien theThuVien = theThuVienService.getTheById(request.getTheId());
        NhanVien nhanVien = nhanVienRepository.findById(request.getMaNhanVien())
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


    @Transactional(rollbackFor = Exception.class)
    public void xulyTraSach(TraSachRequest request, HttpSession session) {

        if (request == null || request.getIdPhieuMuon() == null) {
            throw new RuntimeException("Thông tin trả sách không được để trống!");
        }
        if (request.getDanhSachChiTiet() == null || request.getDanhSachChiTiet().isEmpty()) {
            throw new RuntimeException("Phải chọn ít nhất 1 bản sao để trả!");
        }
        HoaDon hoaDon = new HoaDon();
        Integer maNhanVien = ((CurrentUser) session.getAttribute("currentUser")).getId();
        hoaDon.setNguoiThu(nhanVienRepository.findById(maNhanVien).orElseThrow(() -> new RuntimeException("Không tìm thất nhân viên!")));
        hoaDon.setTrangThai(TrangThaiHoaDon.CHUA_THANH_TOAN);
        hoaDonRepository.save(hoaDon);


        PhieuMuon phieuMuon = phieuMuonRepository.findById(request.getIdPhieuMuon())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mượn"));
        if (phieuMuon.getTrangThai() != TrangThaiPhieuMuon.DANG_MUON) {
            throw new RuntimeException("Phiếu không ở trạng thái đang mượn!");
        }

        // long soBanSaoChuaTra = phieuMuonRepository.findByTheThuVien_MaTheAndTrangThai(phieuMuon.getTheThuVien().getMaThe(), TrangThaiPhieuMuon.DANG_MUON).size();
        long soBanSaoChuaTra = phieuMuon.getChiTietPhieuMuons().size();
        if (request.getDanhSachChiTiet().size() != soBanSaoChuaTra) {
            throw new RuntimeException("Phải trả đủ các bản sao chưa hoàn trả trên phiếu!");
        }

        Map<Integer, TinhTrangVatLy> danhSachTrangThai = new HashMap<>();
        for(ChiTietTraDTO ct : request.getDanhSachChiTiet()) {
            danhSachTrangThai.put(ct.getMaBanSao(), ct.getTrangThai());
        }

        Long tongTienPhat = 0L;
        for(ChiTietPhieuMuon chiTietPhieuMuon : phieuMuon.getChiTietPhieuMuons()) {

            BanSao banSao = chiTietPhieuMuon.getBanSao();
            TinhTrangVatLy ttvl = danhSachTrangThai.get(banSao.getMaBanSao());

            chiTietPhieuMuon.setTinhTrangVatLy(ttvl);
            chiTietPhieuMuon.setNgayTra(LocalDateTime.now());
            chiTietPhieuMuonRepository.save(chiTietPhieuMuon);
            
            banSao.setTinhTrangVatLy(ttvl);
            banSao.setTrangThaiLuuThong(TrangThaiLuuThong.SAN_SANG);
            banSaoRepository.save(banSao);

            Long tienPhat = 0l;
            if(ttvl == TinhTrangVatLy.HU_HONG) {
                tienPhat += 50000l;
            } 
            else if(ttvl == TinhTrangVatLy.MAT) {
                tienPhat += 100000l;
            }
            if (LocalDateTime.now().isAfter(phieuMuon.getNgayHetHan())) {
                long soNgayMuon = java.time.temporal.ChronoUnit.DAYS.between(
                        phieuMuon.getNgayHetHan(),
                        LocalDateTime.now());
                if (soNgayMuon > 0) {
                    tienPhat += soNgayMuon * 5000L;
                }
            }
            if(tienPhat != 0) {
                PhieuPhat phieuPhat = new PhieuPhat();
                phieuPhat.setChiTietPhieuMuon(chiTietPhieuMuon);
                phieuPhat.setHoaDon(hoaDon);
                phieuPhat.setNgayTao(LocalDateTime.now());
                phieuPhat.setTheThuVien(phieuMuon.getTheThuVien());
                phieuPhat.setTienPhat(tienPhat);
                phieuPhatRepository.save(phieuPhat);

                tongTienPhat += tienPhat;
            }
            
        }

        if (tongTienPhat > 0) {
            hoaDon.setNgayTao(LocalDateTime.now());
            hoaDon.setTongTienPhat(tongTienPhat);
            hoaDonRepository.save(hoaDon);
        }
        else {
            hoaDonRepository.delete(hoaDon);
        }

        phieuMuon.setTrangThai(TrangThaiPhieuMuon.DA_TRA);
        phieuMuonRepository.save(phieuMuon);
    }
}
