package com.example.librarymanagement.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LapPhieuMuonRequest {
    private String theId;
    private List<Integer> dsBanSaoId = new ArrayList<>();
    private Integer maNhanVien = 1;
}
