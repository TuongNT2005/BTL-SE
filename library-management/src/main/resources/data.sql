-- Database initialization script (generated from provided .txt)
CREATE DATABASE IF NOT EXISTS QuanLyThuVien
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE QuanLyThuVien;

CREATE TABLE TacGia (
    ma_tac_gia INT AUTO_INCREMENT PRIMARY KEY,
    ten VARCHAR(255) NOT NULL,
    tieu_su TEXT,
    UNIQUE KEY uq_tacgia_ten (ten)
);

CREATE TABLE NhaXuatBan (
    ma_nxb INT AUTO_INCREMENT PRIMARY KEY,
    ten VARCHAR(255) NOT NULL,
    dia_chi TEXT,
    sdt VARCHAR(20),
    UNIQUE KEY uq_nhaxuatban_ten (ten)
);

CREATE TABLE NhaCungCap (
    ma_ncc INT AUTO_INCREMENT PRIMARY KEY,
    ten VARCHAR(255) NOT NULL,
    dia_chi TEXT,
    sdt VARCHAR(20),
    email VARCHAR(100),
    UNIQUE KEY uq_nhacungcap_email (email)
);

CREATE TABLE GoiThe (
    ma_goi_the INT AUTO_INCREMENT PRIMARY KEY,
    ten VARCHAR(100) NOT NULL,
    gia BIGINT NOT NULL,
    thoi_han INT NOT NULL COMMENT 'Tháng',
    UNIQUE KEY uq_goithe_ten (ten)
);

CREATE TABLE NhanVien (
    ma_nhan_vien INT AUTO_INCREMENT PRIMARY KEY,
    ten VARCHAR(255) NOT NULL,
    sdt VARCHAR(20),
    mat_khau VARCHAR(100) NOT NULL,
    trang_thai_lam_viec BOOLEAN DEFAULT TRUE,
    UNIQUE KEY uq_nhanvien_sdt (sdt)
);

CREATE TABLE BanDoc (
    ma_doc_gia INT AUTO_INCREMENT PRIMARY KEY,
    ho_ten VARCHAR(255) NOT NULL,
    dia_chi TEXT,
    email VARCHAR(100),
    sdt VARCHAR(20),
    UNIQUE KEY uq_bandoc_email (email)
);

CREATE TABLE TaiLieu (
    ma_tai_lieu INT AUTO_INCREMENT PRIMARY KEY,
    ten_tl VARCHAR(255) NOT NULL,
    isbn VARCHAR(50),
    mo_ta TEXT,
    the_loai ENUM('TRINH_THAM','KHOA_HOC','PHIEU_LUU','LICH_SU','SACH_GIAO_KHOA','KY_NANG'),
    ma_nxb INT NOT NULL,
    FOREIGN KEY (ma_nxb) REFERENCES NhaXuatBan(ma_nxb) ON DELETE RESTRICT,
    UNIQUE KEY uq_tailieu_isbn (isbn)
);

CREATE TABLE PhieuNhapKho (
    ma_phieu_nhap INT AUTO_INCREMENT PRIMARY KEY,
    ngay_nhap DATETIME DEFAULT CURRENT_TIMESTAMP,
    tong_tien BIGINT DEFAULT 0,
    ma_ncc INT NOT NULL,
    ma_nhan_vien INT NOT NULL,
    FOREIGN KEY (ma_ncc) REFERENCES NhaCungCap(ma_ncc) ON DELETE RESTRICT,
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien) ON DELETE RESTRICT
);

CREATE TABLE ChiTietPhieuNhap (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_phieu_nhap INT NOT NULL,
    ma_tai_lieu INT NOT NULL,
    so_luong BIGINT NOT NULL,
    don_gia BIGINT NOT NULL,
    FOREIGN KEY (ma_phieu_nhap) REFERENCES PhieuNhapKho(ma_phieu_nhap) ON DELETE CASCADE,
    FOREIGN KEY (ma_tai_lieu) REFERENCES TaiLieu(ma_tai_lieu) ON DELETE RESTRICT
);

CREATE TABLE TacGia_TaiLieu (
    ma_tac_gia INT NOT NULL,
    ma_tai_lieu INT NOT NULL,
    PRIMARY KEY (ma_tac_gia, ma_tai_lieu),
    FOREIGN KEY (ma_tac_gia) REFERENCES TacGia(ma_tac_gia) ON DELETE CASCADE,
    FOREIGN KEY (ma_tai_lieu) REFERENCES TaiLieu(ma_tai_lieu) ON DELETE CASCADE
);

CREATE TABLE BanSao (
    ma_ban_sao INT AUTO_INCREMENT PRIMARY KEY,
    ma_tai_lieu INT NOT NULL,
    tinh_trang_vat_ly ENUM('TOT','HU_HONG','MAT') DEFAULT 'TOT',
    trang_thai_luu_thong ENUM('SAN_SANG','DANG_MUON') DEFAULT 'SAN_SANG',
    FOREIGN KEY (ma_tai_lieu) REFERENCES TaiLieu(ma_tai_lieu) ON DELETE CASCADE
);

CREATE TABLE TheThuVien (
    ma_the VARCHAR(50) PRIMARY KEY,
    ngay_phat_hanh DATETIME DEFAULT CURRENT_TIMESTAMP,
    ngay_het_han DATETIME NOT NULL,
    trang_thai ENUM('HOAT_DONG','HET_HAN') DEFAULT 'HOAT_DONG',
    ma_goi_the INT NOT NULL,
    ma_doc_gia INT NOT NULL,
    FOREIGN KEY (ma_goi_the) REFERENCES GoiThe(ma_goi_the) ON DELETE RESTRICT,
    FOREIGN KEY (ma_doc_gia) REFERENCES BanDoc(ma_doc_gia) ON DELETE CASCADE,
    UNIQUE KEY uq_thethuvien_docgia (ma_doc_gia)
);

CREATE TABLE PhieuMuon (
    ma_phieu_muon INT AUTO_INCREMENT PRIMARY KEY,
    ngay_muon DATETIME DEFAULT CURRENT_TIMESTAMP,
    ngay_het_han DATETIME NOT NULL,
    trang_thai ENUM('DANG_MUON','DA_TRA','QUA_HAN') DEFAULT 'DANG_MUON',
    ma_nhan_vien INT NOT NULL,
    ma_the VARCHAR(50) NOT NULL,
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien) ON DELETE RESTRICT,
    FOREIGN KEY (ma_the) REFERENCES TheThuVien(ma_the) ON DELETE RESTRICT
);

CREATE TABLE ChiTietPhieuMuon (
    ma_chi_tiet_phieu_muon INT AUTO_INCREMENT PRIMARY KEY,
    ma_phieu_muon INT NOT NULL,
    ma_ban_sao INT NOT NULL,
    ngay_tra DATETIME NULL,
    tinh_trang_vat_ly ENUM('TOT','HU_HONG','MAT') DEFAULT NULL,
    FOREIGN KEY (ma_phieu_muon) REFERENCES PhieuMuon(ma_phieu_muon) ON DELETE CASCADE,
    FOREIGN KEY (ma_ban_sao) REFERENCES BanSao(ma_ban_sao) ON DELETE RESTRICT,
    UNIQUE(ma_phieu_muon, ma_ban_sao)
);

CREATE TABLE HoaDon (
    ma_hoa_don INT AUTO_INCREMENT PRIMARY KEY,
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    tong_tien_phat BIGINT DEFAULT 0,
    ma_nhan_vien INT NOT NULL,
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien) ON DELETE RESTRICT
);

CREATE TABLE PhieuPhat (
    ma_phieu_phat INT AUTO_INCREMENT PRIMARY KEY,
    tien_phat BIGINT NOT NULL,
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    trang_thai VARCHAR(50) DEFAULT 'CHUA_THANH_TOAN',
    ma_the VARCHAR(50) NOT NULL,
    ma_chi_tiet_phieu_muon INT NOT NULL,
    ma_hoa_don INT NULL,
    FOREIGN KEY (ma_the) REFERENCES TheThuVien(ma_the) ON DELETE RESTRICT,
    FOREIGN KEY (ma_chi_tiet_phieu_muon) REFERENCES ChiTietPhieuMuon(ma_chi_tiet_phieu_muon) ON DELETE CASCADE,
    FOREIGN KEY (ma_hoa_don) REFERENCES HoaDon(ma_hoa_don) ON DELETE SET NULL
);
