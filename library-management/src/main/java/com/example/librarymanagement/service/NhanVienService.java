package com.example.librarymanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.entity.NhanVien;
import com.example.librarymanagement.repository.NhanVienRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NhanVienService {

    private final NhanVienRepository repository;

    public List<NhanVien> findAll() {
        return repository.findAll();
    }

    public NhanVien findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));
    }

    public NhanVien save(NhanVien nhanVien) {
        nhanVien.setTen(normalize(nhanVien.getTen()));
        nhanVien.setSdt(normalize(nhanVien.getSdt()));
        nhanVien.setMatKhau(normalize(nhanVien.getMatKhau()));
        if (nhanVien.getTrangThaiLamViec() == null) {
            nhanVien.setTrangThaiLamViec(true);
        }
        validateNhanVien(nhanVien);
        validateUniqueSdt(nhanVien.getSdt(), null);
        return repository.save(nhanVien);
    }

    public NhanVien capNhat(Integer id, NhanVien nhanVien) {
        NhanVien nhanVienRepo = findById(id);
        String sdt = normalize(nhanVien.getSdt());
        validateUniqueSdt(sdt, id);

        nhanVienRepo.setTen(normalize(nhanVien.getTen()));
        nhanVienRepo.setSdt(sdt);
        nhanVienRepo.setTrangThaiLamViec(Boolean.TRUE.equals(nhanVien.getTrangThaiLamViec()));

        String matKhauMoi = normalize(nhanVien.getMatKhau());
        if (!matKhauMoi.isBlank()) {
            nhanVienRepo.setMatKhau(matKhauMoi);
        }

        validateNhanVien(nhanVienRepo);
        return repository.save(nhanVienRepo);
    }

    private void validateNhanVien(NhanVien nhanVien) {
        if (nhanVien.getTen().isBlank()) {
            throw new RuntimeException("Ten nhan vien khong duoc de trong");
        }
        if (nhanVien.getSdt().isBlank()) {
            throw new RuntimeException("So dien thoai nhan vien khong duoc de trong");
        }
        if (nhanVien.getMatKhau().isBlank()) {
            throw new RuntimeException("Mat khau nhan vien khong duoc de trong");
        }
    }

    private void validateUniqueSdt(String sdt, Integer currentId) {
        repository.findBySdt(sdt)
                .filter(nhanVien -> currentId == null || !currentId.equals(nhanVien.getMaNhanVien()))
                .ifPresent(nhanVien -> {
                    throw new RuntimeException("So dien thoai nhan vien da ton tai");
                });
    }

    private String normalize(String value) {
        if (value == null)
            return "";
        return value.trim();
    }
}
