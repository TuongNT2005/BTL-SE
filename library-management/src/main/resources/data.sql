drop database quanlythuvien

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
    thoi_han INT NOT NULL,
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
    trang_thai ENUM('HOAT_DONG','HET_HAN', 'KHOA') DEFAULT 'HOAT_DONG',
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
    ma_the VARCHAR(50) NOT NULL,
    trang_thai ENUM('CHUA_THANH_TOAN', 'DA_THANH_TOAN') DEFAULT 'CHUA_THANH_TOAN',
    FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien) ON DELETE RESTRICT,
    FOREIGN KEY (ma_the) REFERENCES TheThuVien(ma_the) ON DELETE RESTRICT
);

CREATE TABLE PhieuPhat (
    ma_phieu_phat INT AUTO_INCREMENT PRIMARY KEY,
    tien_phat BIGINT NOT NULL,
    ngay_tao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ma_chi_tiet_phieu_muon INT NOT NULL,
    ma_hoa_don INT NOT NULL,
    FOREIGN KEY (ma_chi_tiet_phieu_muon) REFERENCES ChiTietPhieuMuon(ma_chi_tiet_phieu_muon) ON DELETE CASCADE,
    FOREIGN KEY (ma_hoa_don) REFERENCES HoaDon(ma_hoa_don) ON DELETE CASCADE
);


INSERT INTO TacGia (ten, tieu_su) VALUES
('Nguyễn Nhật Ánh', 'Nhà văn nổi tiếng Việt Nam'),
('Dan Brown', 'Tác giả tiểu thuyết trinh thám'),
('Stephen Hawking', 'Nhà vật lý học');

INSERT INTO NhaXuatBan (ten, dia_chi, sdt) VALUES
('NXB Trẻ', 'TP.HCM', '0123456789'),
('NXB Kim Đồng', 'Hà Nội', '0987654321');

INSERT INTO NhaCungCap (ten, dia_chi, sdt, email) VALUES
('Công ty Sách A', 'Hà Nội', '0911111111', 'a@gmail.com'),
('Công ty Sách B', 'TP.HCM', '0922222222', 'b@gmail.com');

INSERT INTO GoiThe (ten, gia, thoi_han) VALUES
('Gói 6 tháng', 100000, 6),
('Gói 12 tháng', 180000, 12);

INSERT INTO NhanVien (ten, sdt, mat_khau) VALUES
('Nguyễn Văn A', '0900000001', '123'),
('Trần Văn B', '0900000002', '123');

INSERT INTO BanDoc (ho_ten, dia_chi, email, sdt) VALUES
('Lê Văn C', 'Hà Nội', 'c@gmail.com', '0933333333'),
('Phạm Thị D', 'TP.HCM', 'd@gmail.com', '0944444444');

INSERT INTO TaiLieu (ten_tl, isbn, mo_ta, the_loai, ma_nxb) VALUES
('Mắt Biếc', 'ISBN001', 'Tiểu thuyết', 'TRINH_THAM', 1),
('Mật mã Da Vinci', 'ISBN002', 'Trinh thám', 'TRINH_THAM', 1),
('Lược sử thời gian', 'ISBN003', 'Khoa học', 'KHOA_HOC', 2);

INSERT INTO PhieuNhapKho (ma_ncc, ma_nhan_vien) VALUES
(1, 1),
(2, 2);

INSERT INTO ChiTietPhieuNhap (ma_phieu_nhap, ma_tai_lieu, so_luong, don_gia) VALUES
(1, 1, 10, 50000),
(1, 2, 5, 70000),
(2, 3, 8, 90000);

INSERT INTO TacGia_TaiLieu VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO BanSao (ma_tai_lieu) VALUES
(1), (1), (2), (3);

INSERT INTO TheThuVien (ma_the, ngay_het_han, ma_goi_the, ma_doc_gia) VALUES
('THE001', '2026-12-31', 1, 1),
('THE002', '2026-12-31', 2, 2);

INSERT INTO PhieuMuon (ngay_het_han, ma_nhan_vien, ma_the) VALUES
('2026-06-01', 1, 'THE001'),
('2026-06-10', 2, 'THE002');

INSERT INTO ChiTietPhieuMuon (ma_phieu_muon, ma_ban_sao) VALUES
(1, 1),
(1, 2),
(2, 3);

INSERT INTO HoaDon (ma_nhan_vien, ma_the) VALUES
(1, 'THE001'),
(2, 'THE002');

INSERT INTO PhieuPhat (tien_phat, ma_chi_tiet_phieu_muon, ma_hoa_don) VALUES
(50000, 1, 1),
(100000, 2, 1);

DELIMITER $$
CREATE EVENT e_khoa_the_do_no_cuoc
ON SCHEDULE EVERY 1 HOUR
STARTS CURRENT_TIMESTAMP
DO
BEGIN
    UPDATE TheThuVien
    SET trang_thai = 'HET_HAN'
    WHERE ma_the IN (
        SELECT DISTINCT ma_the
        FROM HoaDon
        WHERE trang_thai = 'CHUA_THANH_TOAN'
          AND ngay_tao <= NOW() - INTERVAL 7 DAY
    );
    
END$$
DELIMITER ;

DELIMITER $$

CREATE EVENT e_cap_nhat_the_het_han_hang_ngay
ON SCHEDULE EVERY 1 DAY
STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY + INTERVAL 1 MINUTE)
DO
BEGIN
    UPDATE TheThuVien
    SET trang_thai = 'HET_HAN'
    WHERE ngay_het_han < NOW() 
      AND trang_thai = 'HOAT_DONG';
END$$

DELIMITER ;