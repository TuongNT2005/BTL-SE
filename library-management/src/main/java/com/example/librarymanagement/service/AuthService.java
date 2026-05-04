package com.example.librarymanagement.service;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.auth.AuthRole;
import com.example.librarymanagement.auth.CurrentUser;
import com.example.librarymanagement.entity.BanDoc;
import com.example.librarymanagement.entity.NhanVien;
import com.example.librarymanagement.repository.BanDocRepository;
import com.example.librarymanagement.repository.NhanVienRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final int MANAGER_ID = 1;

    private final BanDocRepository banDocRepository;
    private final NhanVienRepository nhanVienRepository;

    public CurrentUser loginBanDoc(String email, String sdt) {
        BanDoc banDoc = banDocRepository.findByEmailAndSdt(normalize(email), normalize(sdt))
                .orElseThrow(() -> new RuntimeException("Email hoặc số điện thoại bạn đọc không đúng"));

        return new CurrentUser(banDoc.getMaDocGia(), banDoc.getHoTen(), banDoc.getEmail(), AuthRole.BAN_DOC);
    }
 
    public CurrentUser loginNhanVien(String sdt, String matKhau) {
        NhanVien nhanVien = nhanVienRepository.findBySdt(normalize(sdt))
                .orElseThrow(() -> new RuntimeException("Số điện thoại nhân viên không đúng"));

        if (Boolean.FALSE.equals(nhanVien.getTrangThaiLamViec())) {
            throw new RuntimeException("Tài khoản nhân viên đã bị khóa");
        }

        if (!normalize(matKhau).equals(nhanVien.getMatKhau())) {
            throw new RuntimeException("Mật khẩu nhân viên không đúng");
        }

        AuthRole role = MANAGER_ID == nhanVien.getMaNhanVien() ? AuthRole.QUAN_LY : AuthRole.NHAN_VIEN;
        return new CurrentUser(nhanVien.getMaNhanVien(), nhanVien.getTen(), nhanVien.getSdt(), role);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
