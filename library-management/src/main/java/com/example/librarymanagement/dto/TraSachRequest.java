package com.example.librarymanagement.dto;

import lombok.Data;
import java.util.List;

@Data
public class TraSachRequest {
    private Integer idPhieuMuon;
    private List<ChiTietTraDTO> danhSachChiTiet;
}
