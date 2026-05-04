package com.example.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.librarymanagement.auth.CurrentUser;
import com.example.librarymanagement.service.BanDocPortalService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bandoc")
public class BanDocController {

    private final BanDocPortalService service;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping("/lichsu")
    public String lichSuMuonTra(HttpSession session, Model model) {
        CurrentUser currentUser = getCurrentUser(session);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("phieuMuons", service.findLichSuMuonTra(currentUser.getId()));
        return "bandoc/lichsu";
    }

    @GetMapping("/tracuu")
    public String traCuuTaiLieu(
            @RequestParam(required = false) String keyword,
            Model model
    ) {
        model.addAttribute("keyword", keyword == null ? "" : keyword.trim());
        model.addAttribute("taiLieus", service.traCuuTaiLieu(keyword));
        return "bandoc/tracuu";
    }

    private CurrentUser getCurrentUser(HttpSession session) {
        CurrentUser currentUser = (CurrentUser) session.getAttribute(AuthController.CURRENT_USER_SESSION_KEY);
        if (currentUser == null) {
            throw new RuntimeException("Vui long dang nhap");
        }
        return currentUser;
    }
}
