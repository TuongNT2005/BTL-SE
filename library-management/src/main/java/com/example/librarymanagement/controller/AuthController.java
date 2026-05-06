package com.example.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.librarymanagement.auth.CurrentUser;
import com.example.librarymanagement.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    public static final String CURRENT_USER_SESSION_KEY = "currentUser";

    private final AuthService authService;

    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/bandoc/tracuu";
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    @GetMapping( "/login" )
    public String loginPage(HttpSession session) {
        CurrentUser currentUser = (CurrentUser) session.getAttribute(CURRENT_USER_SESSION_KEY);
        if (currentUser == null) {
            return "login";
        }
        return redirectByRole(currentUser);
    }

    @PostMapping("/login/bandoc")
    public String loginBanDoc(
            @RequestParam String email,
            @RequestParam String sdt,
            HttpSession session) {
        CurrentUser currentUser = authService.loginBanDoc(email, sdt);
        session.setAttribute(CURRENT_USER_SESSION_KEY, currentUser);
        return redirectByRole(currentUser);
    }

    @PostMapping("/login/nhanvien")
    public String loginNhanVien(
            @RequestParam String sdt,
            @RequestParam String matKhau,
            HttpSession session) {
        CurrentUser currentUser = authService.loginNhanVien(sdt, matKhau);
        session.setAttribute(CURRENT_USER_SESSION_KEY, currentUser);
        return redirectByRole(currentUser);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/bandoc")
    public String banDocHome(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute(CURRENT_USER_SESSION_KEY));
        return "bandoc-home";
    }

    @GetMapping("/nhanvien")
    public String nhanVienHome(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute(CURRENT_USER_SESSION_KEY));
        return "nhanvien-home";
    }

    @GetMapping("/quanly")
    public String quanLyHome(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute(CURRENT_USER_SESSION_KEY));
        return "quanly-home";
    }

    private String redirectByRole(CurrentUser currentUser) {
        if (currentUser.isQuanLy()) {
            return "redirect:/quanly";
        }
        if (currentUser.isNhanVien()) {
            return "redirect:/nhanvien";
        }
        return "redirect:/bandoc";
    }
}
