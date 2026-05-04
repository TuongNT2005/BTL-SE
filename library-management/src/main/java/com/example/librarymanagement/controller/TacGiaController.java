package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.TacGia;
import com.example.librarymanagement.service.TacGiaService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tacgia")
public class TacGiaController {

    private final TacGiaService service;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping
    public String list(Model model) {
        List<TacGia> list = service.findAll();
        model.addAttribute("tacGias", list);
        return "tacgia/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("tacGia", new TacGia());
        return "tacgia/form";
    }

    @PostMapping
    public String save(@ModelAttribute TacGia tacGia) {
        service.save(tacGia);
        return "redirect:/tacgia";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("tacGia", service.findById(id));
        model.addAttribute("isEdit", true);
        return "tacgia/form";
    }

    @PostMapping("/update/{id}")
    public String capNhat(@PathVariable Integer id, @ModelAttribute TacGia tacGia) {
        service.capNhat(id, tacGia);
        return "redirect:/tacgia";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/tacgia";
    }
}
