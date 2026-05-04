package com.example.librarymanagement.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.librarymanagement.entity.NhaXuatBan;
import com.example.librarymanagement.service.NXBService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/NXB")
public class NXBController {

    private final NXBService nxbService;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping
    public String list(Model model) {
        List<NhaXuatBan> list = nxbService.geListNXB();
        model.addAttribute("nhaXuatBans", list);
        return "NXB/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("NXB", new NhaXuatBan());
        return "NXB/form";
    }

    @PostMapping
    public String save(@ModelAttribute NhaXuatBan nhaXuatBan) {
        nxbService.save(nhaXuatBan);
        return "redirect:/NXB";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("NXB", nxbService.findById(id));
        model.addAttribute("isEdit", true);
        return "NXB/form";
    }

    @PostMapping("/update/{id}")
    public String capNhat(@PathVariable Integer id, @ModelAttribute NhaXuatBan nhaXuatBan) {
        nxbService.capNhat(id, nhaXuatBan);
        return "redirect:/NXB";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        nxbService.deleteById(id);
        return "redirect:/NXB";
    }

}
