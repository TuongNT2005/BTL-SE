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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.librarymanagement.entity.NhaXuatBan;
import com.example.librarymanagement.entity.TaiLieu;
import com.example.librarymanagement.entity.TheLoai;
import com.example.librarymanagement.entity.TinhTrangVatLy;
import com.example.librarymanagement.entity.TrangThaiLuuThong;
import com.example.librarymanagement.service.BanSaoService;
import com.example.librarymanagement.service.NXBService;
import com.example.librarymanagement.service.TaiLieuService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/tailieu")
public class TaiLieuController {

    private final TaiLieuService service;
    private final NXBService nxbService;
    private final BanSaoService banSaoService;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping
    public String list(Model model) {
        List<TaiLieu> list = service.findAll();
        model.addAttribute("taiLieus", list);
        return "tailieu/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        TaiLieu taiLieu = new TaiLieu();
        taiLieu.setNhaXuatBan(new NhaXuatBan());
        model.addAttribute("taiLieu", taiLieu);
        addFormData(model, false);
        return "tailieu/form";
    }

    @PostMapping
    public String save(@ModelAttribute TaiLieu taiLieu) {
        service.save(taiLieu);
        return "redirect:/tailieu";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("taiLieu", service.findById(id));
        model.addAttribute("banSaos", banSaoService.findByTaiLieuId(id));
        addFormData(model, true);
        return "tailieu/form";
    }

    @PostMapping("/update/{id}")
    public String capNhat(@PathVariable Integer id, @ModelAttribute TaiLieu taiLieu) {
        service.capNhat(id, taiLieu);
        return "redirect:/tailieu";
    }

    @PostMapping("/update/{taiLieuId}/bansao/{banSaoId}")
    public String capNhatTrangThaiBanSao(
            @PathVariable Integer taiLieuId,
            @PathVariable Integer banSaoId,
            @RequestParam TinhTrangVatLy tinhTrangVatLy,
            @RequestParam TrangThaiLuuThong trangThaiLuuThong
    ) {
        banSaoService.capNhatTrangThaiCuaTaiLieu(taiLieuId, banSaoId, tinhTrangVatLy, trangThaiLuuThong);
        return "redirect:/tailieu/update/" + taiLieuId;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/tailieu";
    }

    private void addFormData(Model model, boolean isEdit) {
        model.addAttribute("nhaXuatBans", nxbService.geListNXB());
        model.addAttribute("theLoais", TheLoai.values());
        model.addAttribute("tinhTrangVatLys", TinhTrangVatLy.values());
        model.addAttribute("trangThaiLuuThongs", TrangThaiLuuThong.values());
        model.addAttribute("isEdit", isEdit);
    }
}
