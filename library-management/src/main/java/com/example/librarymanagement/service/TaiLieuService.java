package com.example.librarymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.NhaXuatBan;
import com.example.librarymanagement.entity.TaiLieu;
import com.example.librarymanagement.repository.NhaXuatBanRepository;
import com.example.librarymanagement.repository.TaiLieuRepository;

@Service
public class TaiLieuService {

    private final TaiLieuRepository taiLieuRepository;
    private final NhaXuatBanRepository nhaXuatBanRepository;

    public TaiLieuService(TaiLieuRepository taiLieuRepository, NhaXuatBanRepository nhaXuatBanRepository) {
        this.taiLieuRepository = taiLieuRepository;
        this.nhaXuatBanRepository = nhaXuatBanRepository;
    }

    public List<TaiLieu> getAll() {
        return taiLieuRepository.findAll();
    }

    public List<TaiLieu> findAll() {
        return taiLieuRepository.findAll();
    }

    public TaiLieu findTaiLieuById(Integer maTaiLieu) {
        if (maTaiLieu == null) {
            throw new RuntimeException("Ma tai lieu khong duoc de trong!");
        }
        return findById(maTaiLieu);
    }

    public TaiLieu findById(Integer id) {
        return taiLieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay tai lieu"));
    }

    public TaiLieu save(TaiLieu taiLieu) {
        taiLieu.setNhaXuatBan(resolveNhaXuatBan(taiLieu));
        return taiLieuRepository.save(taiLieu);
    }

    public TaiLieu capNhat(Integer id, TaiLieu taiLieu) {
        TaiLieu taiLieuRepo = findById(id);
        taiLieuRepo.setTenTl(taiLieu.getTenTl());
        taiLieuRepo.setIsbn(taiLieu.getIsbn());
        taiLieuRepo.setMoTa(taiLieu.getMoTa());
        taiLieuRepo.setTheLoai(taiLieu.getTheLoai());
        taiLieuRepo.setNhaXuatBan(resolveNhaXuatBan(taiLieu));
        return taiLieuRepository.save(taiLieuRepo);
    }

    public void deleteById(Integer id) {
        taiLieuRepository.deleteById(id);
    }

    private NhaXuatBan resolveNhaXuatBan(TaiLieu taiLieu) {
        Integer maNxb = taiLieu.getNhaXuatBan() == null ? null : taiLieu.getNhaXuatBan().getMaNxb();
        if (maNxb == null) {
            throw new RuntimeException("Vui long chon nha xuat ban");
        }
        return nhaXuatBanRepository.findById(maNxb)
                .orElseThrow(() -> new RuntimeException("Khong tim thay nha xuat ban"));
    }
}
