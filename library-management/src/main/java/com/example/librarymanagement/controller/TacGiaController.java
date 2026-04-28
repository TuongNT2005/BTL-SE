package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.TacGia;
import com.example.librarymanagement.service.TacGiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;

@Controller
@RequestMapping("/tacgia")
public class TacGiaController {

    private final TacGiaService service;

    public TacGiaController(TacGiaService service) {
        this.service = service;
    }

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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/tacgia";
    }
}