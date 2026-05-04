package com.example.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.librarymanagement.service.BaoCaoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/quanly/baocao")
public class BaoCaoController {

    private final BaoCaoService baoCaoService;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping
    public String tongQuan(Model model) {
        model.addAttribute("baoCao", baoCaoService.getTongQuan());
        return "baocao/tongquan";
    }
}
