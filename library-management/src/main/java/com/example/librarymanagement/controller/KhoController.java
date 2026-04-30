package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.NhapKhoRequest;
import com.example.librarymanagement.service.KhoService;
import com.example.librarymanagement.service.NhaCungCapService;
import com.example.librarymanagement.service.TaiLieuService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/kho")
public class KhoController {
    private final NhaCungCapService nhaCungCapService;
    private final TaiLieuService taiLieuService;
    private final KhoService khoService;

    public KhoController(NhaCungCapService nhaCungCapService, TaiLieuService taiLieuService, KhoService khoService) {
        this.nhaCungCapService = nhaCungCapService;
        this.taiLieuService = taiLieuService;
        this.khoService = khoService;
    }

    @GetMapping("/nhap-kho")
    public String hienThiFormNhapKho(Model model) {
        model.addAttribute("listNCC", nhaCungCapService.getAll());
        model.addAttribute("listTaiLieu", taiLieuService.getAll());

        NhapKhoRequest nhapKhoRequest = new NhapKhoRequest();
        // NhapKhoRequest.ChiTietNhapKhoRequest ct = new NhapKhoRequest.ChiTietNhapKhoRequest();
        // nhapKhoRequest.setChiTietPhieuNhaps(Collections.singletonList(cts));
        List<NhapKhoRequest.ChiTietNhapKhoRequest> cts = new ArrayList<>();
        nhapKhoRequest.setChiTietPhieuNhaps(cts);

        model.addAttribute("nhapKhoRequest", nhapKhoRequest);
        return "kho/FormNhapKho";
    }

    @PostMapping("/nhap-kho")
    public String xuLyNhapKho(
            @Valid @ModelAttribute("nhapKhoRequest") NhapKhoRequest request,
            Model model
    ) {
        try {
            khoService.xuLyNhapKho(request);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("listNCC", nhaCungCapService.getAll());
            model.addAttribute("listTaiLieu", taiLieuService.getAll());
            model.addAttribute("nhapKhoRequest", request);
            return "kho/FormNhapKho";
        }

        return "redirect:/kho/nhap-kho";
    }
}

