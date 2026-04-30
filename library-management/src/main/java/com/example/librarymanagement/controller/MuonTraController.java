package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LapPhieuMuonRequest;
import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.TheThuVien;
import com.example.librarymanagement.service.BanSaoService;
import com.example.librarymanagement.service.MuonTraService;
import com.example.librarymanagement.service.TheThuVienService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/muon-tra")
public class MuonTraController {
    private final MuonTraService muonTraService;
    private final TheThuVienService theThuVienService;
    private final BanSaoService banSaoService;

    public MuonTraController(
            MuonTraService muonTraService,
            TheThuVienService theThuVienService,
            BanSaoService banSaoService
    ) {
        this.muonTraService = muonTraService;
        this.theThuVienService = theThuVienService;
        this.banSaoService = banSaoService;
    }

    @GetMapping("/lap-phieu-muon")
    public String hienThiFormLapPhieuMuon(Model model) {
        model.addAttribute("lapPhieuMuonRequest", new LapPhieuMuonRequest());
        model.addAttribute("dsBanSao", banSaoService.getAll());
        return "muontra/FormLapPhieuMuon";
    }

    @GetMapping("/thong-tin-ban-doc")
    public String hienThiThongTinBanDoc(@RequestParam Integer theId, Model model) {
        try {
            TheThuVien theThuVien = theThuVienService.getTheById(theId);
            long soSachChuaTra = theThuVienService.getBanSaoChuaTraByTheId(theId);
            List<BanSao> dsBanSao = banSaoService.getAll();

            LapPhieuMuonRequest request = new LapPhieuMuonRequest();
            request.setTheId(theId);
            request.setMaNhanVien(1);

            model.addAttribute("theThuVien", theThuVien);
            model.addAttribute("soSachChuaTra", soSachChuaTra);
            model.addAttribute("dsBanSao", dsBanSao);
            model.addAttribute("lapPhieuMuonRequest", request);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("lapPhieuMuonRequest", new LapPhieuMuonRequest());
            model.addAttribute("dsBanSao", banSaoService.getAll());
        }
        return "muontra/FormLapPhieuMuon";
    }

    @PostMapping("/lap-phieu-muon")
    public String xuLyLapPhieuMuon(@ModelAttribute LapPhieuMuonRequest request, Model model) {
        try {
            request.setMaNhanVien(1);
            muonTraService.xuLyLapPhieuMuon(request);
            model.addAttribute("successMessage", "Lập phiếu mượn thành công!");
            model.addAttribute("lapPhieuMuonRequest", new LapPhieuMuonRequest());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("lapPhieuMuonRequest", request);
        }

        model.addAttribute("dsBanSao", banSaoService.getAll());
        if (request.getTheId() != null) {
            try {
                model.addAttribute("theThuVien", theThuVienService.getTheById(request.getTheId()));
                model.addAttribute("soSachChuaTra", theThuVienService.getBanSaoChuaTraByTheId(request.getTheId()));
            } catch (Exception ignored) {
                // Không chặn render form nếu thẻ không hợp lệ.
            }
        }
        return "muontra/FormLapPhieuMuon";
    }
}
