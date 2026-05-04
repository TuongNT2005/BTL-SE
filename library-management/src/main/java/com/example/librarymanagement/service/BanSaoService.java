package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.TinhTrangVatLy;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
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

    public List<BanSao> findByTaiLieuId(Integer maTaiLieu) {
        return banSaoRepository.findByTaiLieu_MaTaiLieuOrderByMaBanSaoAsc(maTaiLieu);
    }

    public BanSao capNhatTrangThai(Integer maBanSao, TinhTrangVatLy tinhTrangVatLy,
            TrangThaiLuuThong trangThaiLuuThong) {
        BanSao banSao = getBanSaoById(maBanSao);
        return capNhatTrangThai(banSao, tinhTrangVatLy, trangThaiLuuThong);
    }

    public BanSao capNhatTrangThaiCuaTaiLieu(Integer maTaiLieu, Integer maBanSao, TinhTrangVatLy tinhTrangVatLy,
            TrangThaiLuuThong trangThaiLuuThong) {
        BanSao banSao = getBanSaoById(maBanSao);
        Integer taiLieuId = banSao.getTaiLieu() == null ? null : banSao.getTaiLieu().getMaTaiLieu();
        if (!maTaiLieu.equals(taiLieuId)) {
            throw new RuntimeException("Ban sao khong thuoc tai lieu dang cap nhat");
        }
        return capNhatTrangThai(banSao, tinhTrangVatLy, trangThaiLuuThong);
    }

    private BanSao capNhatTrangThai(BanSao banSao, TinhTrangVatLy tinhTrangVatLy,
            TrangThaiLuuThong trangThaiLuuThong) {
        banSao.setTinhTrangVatLy(tinhTrangVatLy);
        banSao.setTrangThaiLuuThong(trangThaiLuuThong);
        return banSaoRepository.save(banSao);
    }
}
