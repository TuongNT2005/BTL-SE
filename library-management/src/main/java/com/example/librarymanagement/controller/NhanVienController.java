package com.example.librarymanagement.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.librarymanagement.entity.NhanVien;
import com.example.librarymanagement.service.NhanVienService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/nhanvien/quanly")
public class NhanVienController {

    private final NhanVienService service;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping
    public String list(Model model) {
        List<NhanVien> list = service.findAll();
        model.addAttribute("nhanViens", list);
        return "nhanvien/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTrangThaiLamViec(true);
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("isEdit", false);
        return "nhanvien/form";
    }

    @PostMapping
    public String save(@ModelAttribute NhanVien nhanVien) {
        service.save(nhanVien);
        return "redirect:/nhanvien/quanly";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("nhanVien", service.findById(id));
        model.addAttribute("isEdit", true);
        return "nhanvien/form";
    }

    @PostMapping("/update/{id}")
    public String capNhat(@PathVariable Integer id, @ModelAttribute NhanVien nhanVien) {
        service.capNhat(id, nhanVien);
        return "redirect:/nhanvien/quanly";
    }
}
