package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.NhapKhoRequest;
import com.example.librarymanagement.dto.NhapKhoRequest.ChiTietNhapKhoRequest;
import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.ChiTietPhieuNhap;
import com.example.librarymanagement.entity.ChiTietPhieuNhapId;
import com.example.librarymanagement.entity.NhaCungCap;
import com.example.librarymanagement.entity.NhanVien;
import com.example.librarymanagement.entity.PhieuNhapKho;
import com.example.librarymanagement.entity.TaiLieu;
import com.example.librarymanagement.entity.TinhTrangVatLy;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
import com.example.librarymanagement.repository.BanSaoRepository;
import com.example.librarymanagement.repository.ChiTietPhieuNhapRepository;
import com.example.librarymanagement.repository.NhanVienRepository;
import com.example.librarymanagement.repository.PhieuNhapKhoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KhoService {
    private final NhaCungCapService nhaCungCapService;
    private final TaiLieuService taiLieuService;
    private final NhanVienRepository nhanVienRepository;
    private final PhieuNhapKhoRepository phieuNhapKhoRepository;
    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;
    private final BanSaoRepository banSaoRepository;

    public KhoService(
            NhaCungCapService nhaCungCapService,
            TaiLieuService taiLieuService,
            NhanVienRepository nhanVienRepository,
            PhieuNhapKhoRepository phieuNhapKhoRepository,
            ChiTietPhieuNhapRepository chiTietPhieuNhapRepository,
            BanSaoRepository banSaoRepository
    ) {
        this.nhaCungCapService = nhaCungCapService;
        this.taiLieuService = taiLieuService;
        this.nhanVienRepository = nhanVienRepository;
        this.phieuNhapKhoRepository = phieuNhapKhoRepository;
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
        this.banSaoRepository = banSaoRepository;
    }

    public boolean checkNhapKhoConditions(NhapKhoRequest request) {
        if (request == null) {
            throw new RuntimeException("Thông tin phiếu nhập không được để trống!");
        }

        if (request.getMaNcc() == null) {
            throw new RuntimeException("Mã NCC không được để trống!");
        }

        if (request.getMaNhanVien() == null) {
            throw new RuntimeException("Mã nhân viên không được để trống!");
        }

        if (request.getChiTietPhieuNhaps() == null || request.getChiTietPhieuNhaps().isEmpty()) {
            throw new RuntimeException("Phải có ít nhất 1 chi tiết nhập kho!");
        }

        nhanVienRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        // Tránh trùng mã tài liệu trong 1 phiếu muọn
        Set<Integer> seenTaiLieu = new HashSet<>();
        for (ChiTietNhapKhoRequest item : request.getChiTietPhieuNhaps()) {
            if(item == null || item.getMaTaiLieu() == null) continue;
            if (!seenTaiLieu.add(item.getMaTaiLieu())) {
                throw new RuntimeException("Bị trùng tài liệu: " + item.getMaTaiLieu());
            }
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void xuLyNhapKho(NhapKhoRequest request) {
        checkNhapKhoConditions(request); 

        NhaCungCap nhaCungCap = nhaCungCapService.findNCCById(request.getMaNcc());
        NhanVien nhanVien = nhanVienRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        long tongTien = 0L;
        List<ChiTietNhapKhoRequest> chiTietPhieuNhaps = request.getChiTietPhieuNhaps();
        for (ChiTietNhapKhoRequest item : chiTietPhieuNhaps) {
            tongTien += item.getSoLuong() * item.getDonGia();
        }

        PhieuNhapKho phieuNhapKho = new PhieuNhapKho();
        phieuNhapKho.setNgayNhap(LocalDateTime.now());
        phieuNhapKho.setTongTien(tongTien);
        phieuNhapKho.setNhaCungCap(nhaCungCap);
        phieuNhapKho.setNguoiLapPhieu(nhanVien);

        phieuNhapKhoRepository.save(phieuNhapKho); 

        for (ChiTietNhapKhoRequest item : chiTietPhieuNhaps) {
            TaiLieu taiLieu = taiLieuService.findTaiLieuById(item.getMaTaiLieu());

            ChiTietPhieuNhap chiTietPhieuNhap = new ChiTietPhieuNhap();
            chiTietPhieuNhap.setId(new ChiTietPhieuNhapId());
            chiTietPhieuNhap.getId().setMaPhieuNhap(phieuNhapKho.getMaPhieuNhap());
            chiTietPhieuNhap.getId().setMaTaiLieu(taiLieu.getMaTaiLieu());
            chiTietPhieuNhap.setPhieuNhapKho(phieuNhapKho);
            chiTietPhieuNhap.setTaiLieu(taiLieu);
            chiTietPhieuNhap.setSoLuong(item.getSoLuong());
            chiTietPhieuNhap.setDonGia(item.getDonGia());

            chiTietPhieuNhapRepository.save(chiTietPhieuNhap);

            for(int i = 0; i < item.getSoLuong(); i++) {
                BanSao banSao = new BanSao();
                banSao.setTaiLieu(taiLieu);
                banSao.setTinhTrangVatLy(TinhTrangVatLy.TOT);
                banSao.setTrangThaiLuuThong(TrangThaiLuuThong.SAN_SANG);

                banSaoRepository.save(banSao);
            }
        }
    }
}

