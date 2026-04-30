package com.example.librarymanagement.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LapPhieuMuonRequest {
    private Integer theId;
    private List<Integer> dsBanSaoId = new ArrayList<>();
    private Integer maNhanVien = 1;
}
