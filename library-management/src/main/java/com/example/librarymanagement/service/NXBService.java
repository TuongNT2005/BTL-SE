package com.example.librarymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.NhaXuatBan;
import com.example.librarymanagement.repository.NhaXuatBanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NXBService {
    private final NhaXuatBanRepository NXBrepo;
    public List<NhaXuatBan> geListNXB(){
        return NXBrepo.findAll();
    }
    
    public NhaXuatBan findById(Integer id) {
        return NXBrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Nha Xuat Ban"));
    }

    public NhaXuatBan capNhat(Integer id, NhaXuatBan nhaXuatBan) {
        NhaXuatBan NXB = findById(id);
        NXB.setTen(nhaXuatBan.getTen());
        NXB.setDiaChi(nhaXuatBan.getDiaChi());
        NXB.setSdt(nhaXuatBan.getSdt());
        return NXBrepo.save(NXB);
    }

    public NhaXuatBan save(NhaXuatBan nhaXuatBan) {
        return NXBrepo.save(nhaXuatBan);
    }

    public void deleteById(Integer id) {
        NXBrepo.deleteById(id);
    }
}
