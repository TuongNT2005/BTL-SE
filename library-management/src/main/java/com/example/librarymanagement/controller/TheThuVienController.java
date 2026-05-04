package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.GiaHanTheRequest;
import com.example.librarymanagement.dto.TaoTheRequest;
import com.example.librarymanagement.entity.GoiThe;
import com.example.librarymanagement.entity.TheThuVien;
import com.example.librarymanagement.service.GoiTheService;
import com.example.librarymanagement.service.TheThuVienService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/the-thu-vien")
public class TheThuVienController {
    private final GoiTheService goiTheService;
    private final TheThuVienService theThuVienService;

    public TheThuVienController(GoiTheService goiTheService, TheThuVienService theThuVienService) {
        this.goiTheService = goiTheService;
        this.theThuVienService = theThuVienService;
    }

    @GetMapping("/tao-the")
    public String hienThiFormTaoThe(Model model) {
        try {
            List<GoiThe> listGoiThe = goiTheService.getAll();
            model.addAttribute("listGoiThe", listGoiThe);
            model.addAttribute("taoTheRequest", new TaoTheRequest());
            return "thethuvien/FormTaoThe";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
        
    }

    @GetMapping("/quan-ly")
    public String quanLyThe(Model model) {
        List<TheThuVien> listThe = theThuVienService.getAll();
        model.addAttribute("listThe", listThe);
        model.addAttribute("dsSoThangGiaHan", Arrays.asList(6, 12, 18));
        model.addAttribute("giaHanTheRequest", new GiaHanTheRequest());
        return "thethuvien/QuanLyThe";
    }

    @PostMapping("/tao-the")
    public String taoTheTheThuVienMoi(@ModelAttribute TaoTheRequest request, Model model) {
        try {
            theThuVienService.taoTheThuVien(request);
            return "redirect:/the-thu-vien/quan-ly";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("listGoiThe", goiTheService.getAll());
            model.addAttribute("taoTheRequest", request);
            return "thethuvien/FormTaoThe";
        }
    }

    @PostMapping("/gia-han")
    public String giaHanThe(@ModelAttribute GiaHanTheRequest request, Model model) {
        boolean success = theThuVienService.giaHanThe(request);
        if (success) {
            model.addAttribute("successMessage", "Gia hạn thẻ thành công!");
        } else {
            model.addAttribute("errorMessage", "Gia hạn thẻ thất bại!");
        }
        return quanLyThe(model);
    }
}
