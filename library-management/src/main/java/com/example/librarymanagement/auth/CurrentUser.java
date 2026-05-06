package com.example.librarymanagement.auth;

import java.io.Serializable;

public class CurrentUser implements Serializable {

    private final Integer id;
    private final String ten;
    private final String lienHe;
    private final AuthRole role;

    public CurrentUser(Integer id, String ten, String lienHe, AuthRole role) {
        this.id = id;
        this.ten = ten;
        this.lienHe = lienHe;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getLienHe() {
        return lienHe;
    }

    public AuthRole getRole() {
        return role;
    }

    public boolean isQuanLy() {
        return role == AuthRole.QUAN_LY;
    }

    public boolean isNhanVien() {
        return role == AuthRole.NHAN_VIEN;
    }

    public boolean isBanDoc() {
        return role == AuthRole.BAN_DOC;
    }
}
