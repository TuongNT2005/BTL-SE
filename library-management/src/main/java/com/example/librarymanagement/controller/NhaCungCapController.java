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

import com.example.librarymanagement.entity.NhaCungCap;
import com.example.librarymanagement.service.NhaCungCapService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/nhacungcap")
public class NhaCungCapController {

    private final NhaCungCapService service;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping
    public String list(Model model) {
        List<NhaCungCap> list = service.findAll();
        model.addAttribute("nhaCungCaps", list);
        return "nhacungcap/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("nhaCungCap", new NhaCungCap());
        return "nhacungcap/form";
    }

    @PostMapping
    public String save(@ModelAttribute NhaCungCap nhaCungCap) {
        service.save(nhaCungCap);
        return "redirect:/nhacungcap";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("nhaCungCap", service.findById(id));
        model.addAttribute("isEdit", true);
        return "nhacungcap/form";
    }

    @PostMapping("/update/{id}")
    public String capNhat(@PathVariable Integer id, @ModelAttribute NhaCungCap nhaCungCap) {
        service.capNhat(id, nhaCungCap);
        return "redirect:/nhacungcap";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/nhacungcap";
    }
}
