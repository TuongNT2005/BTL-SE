package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.GiaHanTheRequest;
import com.example.librarymanagement.dto.TaoTheRequest;
import com.example.librarymanagement.entity.BanDoc;
import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.GoiThe;
import com.example.librarymanagement.entity.TheThuVien;
import com.example.librarymanagement.entity.TrangThaiThe;
import com.example.librarymanagement.repository.BanSaoRepository;
import com.example.librarymanagement.repository.ChiTietPhieuMuonRepository;
import com.example.librarymanagement.repository.GoiTheRepository;
import com.example.librarymanagement.repository.TheThuVienRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class TheThuVienService {
    private final BanSaoRepository banSaoRepository;
    private final TheThuVienRepository theThuVienRepository;
    private final BanDocService banDocService;
    private final GoiTheRepository goiTheRepository;

    public TheThuVienService(
            TheThuVienRepository theThuVienRepository,
            BanDocService banDocService,
            GoiTheRepository goiTheRepository,
            ChiTietPhieuMuonRepository chiTietPhieuMuonRepository, BanSaoService banSaoService,
            BanSaoRepository banSaoRepository) {
        this.theThuVienRepository = theThuVienRepository;
        this.banDocService = banDocService;
        this.goiTheRepository = goiTheRepository;
        this.banSaoRepository = banSaoRepository;
    }

    private void checkTaoTheConditions(TaoTheRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new RuntimeException("Email không dược để trống!");
        }

        boolean daCoEmail = theThuVienRepository.existsByBanDoc_Email(request.getEmail());
        boolean daCoSoDienThoai = theThuVienRepository.existsByBanDoc_Sdt(request.getSdt());

        if (daCoEmail) {
            throw new RuntimeException("Eamil này đã đăng ký cho 1 thẻ thư viện!");
        }
        if (daCoSoDienThoai) {
            throw new RuntimeException("Số điện thoại này đã đăng ký cho 1 thẻ thư viện!");
        }
    }

    public boolean taoTheThuVien(TaoTheRequest request) {
        checkTaoTheConditions(request);

        BanDoc banDoc = banDocService.createBanDoc(request);
        GoiThe goiThe = goiTheRepository.findById(request.getMaGoiThe())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy gói thẻ!"));

        LocalDateTime ngayPhatHanh = LocalDateTime.now();
        LocalDateTime ngayHetHan = ngayPhatHanh.plusMonths(goiThe.getThoiHan());

        TheThuVien theThuVien = new TheThuVien();
        theThuVien.setMaThe("THE_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMđHHmmss")) + "_"
                + String.valueOf(new Random().nextInt(100)));
        theThuVien.setBanDoc(banDoc);
        theThuVien.setGoiThe(goiThe);
        theThuVien.setNgayPhatHanh(ngayPhatHanh);
        theThuVien.setNgayHetHan(ngayHetHan);
        theThuVien.setTrangThai(TrangThaiThe.HOAT_DONG);
        theThuVienRepository.save(theThuVien);

        return true;
    }

    public List<TheThuVien> getAll() {
        return theThuVienRepository.findAll();
    }

    public TheThuVien getTheById(String theId) {
        if (theId == null) {
            throw new RuntimeException("Mã thẻ không được để trống!");
        }
        return theThuVienRepository.findById(theId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thẻ thư viện!"));
    }

    public List<BanSao> getBanSaoChuaTraByTheId(String theId) {
        getTheById(theId);
        return banSaoRepository.findAllBanSaoChuaTraByMaThe(theId);
    }

    public void checkGiaHanTheConditions(GiaHanTheRequest request) {
        if (request == null || request.getMaThe() == null || request.getMaThe().isBlank()) {
            throw new RuntimeException("Thông tin trống!");
        }
        if (request.getSoThangGiaHan() == null) {
            throw new RuntimeException("Thông tin trống!");
        }

        theThuVienRepository.findByMaThe(request.getMaThe())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thẻ thư viện!"));
    }

    public boolean giaHanThe(GiaHanTheRequest request) {

        TheThuVien the = theThuVienRepository.findByMaThe(request.getMaThe()).get();
        if (the == null || the.getNgayHetHan() == null) {
            return false;
        }
        if (the.getTrangThai() == TrangThaiThe.KHOA)
            return false;
        LocalDateTime ngayHetHanMoi = the.getNgayHetHan().plusMonths(request.getSoThangGiaHan());
        the.setNgayHetHan(ngayHetHanMoi);
        if (the.getNgayHetHan().isBefore(LocalDateTime.now())) {
            the.setTrangThai(TrangThaiThe.HET_HAN);
        } else {
            the.setTrangThai(TrangThaiThe.HOAT_DONG);
        }
        theThuVienRepository.save(the);
        return true;
    }

}
