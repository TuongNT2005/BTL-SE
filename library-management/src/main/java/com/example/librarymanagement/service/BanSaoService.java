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

    public BanSao capNhatTrangThaiCuaTaiLieu(Integer maTaiLieu, Integer maBanSao, TinhTrangVatLy tinhTrangVatLy,
            TrangThaiLuuThong trangThaiLuuThong) {
        BanSao banSao = getBanSaoById(maBanSao);
        Integer taiLieuId;
        if (banSao.getTaiLieu() == null)
            taiLieuId = null;
        else
            taiLieuId = banSao.getTaiLieu().getMaTaiLieu();
        if (!maTaiLieu.equals(taiLieuId)) {
            throw new RuntimeException("Bản sao không thuộc tài liệu cập nhật");
        }
        banSao.setTinhTrangVatLy(tinhTrangVatLy);
        banSao.setTrangThaiLuuThong(trangThaiLuuThong);
        return banSaoRepository.save(banSao);
    }

    public int getSoLuongSanCo(Integer maTaiLieu) {
        return banSaoRepository.countByTaiLieu_MaTaiLieuAndTrangThaiLuuThong(maTaiLieu, TrangThaiLuuThong.SAN_SANG);
    }
}
