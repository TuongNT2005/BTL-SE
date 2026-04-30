package com.example.librarymanagement.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.Data;

@Data
public class NhapKhoRequest {
    @NotNull(message = "Mã NCC không được để trống")
    private Integer maNcc;

    private Integer maNhanVien;

    private List<ChiTietNhapKhoRequest> chiTietPhieuNhaps;

    @Data
    public static class ChiTietNhapKhoRequest {
        private Integer maTaiLieu;

        private Long soLuong;

        private Long donGia;
    }

}
