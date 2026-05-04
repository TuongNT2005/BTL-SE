package com.example.librarymanagement.controller;

import com.example.librarymanagement.auth.CurrentUser;
import com.example.librarymanagement.dto.LapPhieuMuonRequest;
import com.example.librarymanagement.dto.TraSachRequest;
import com.example.librarymanagement.entity.BanSao;
import com.example.librarymanagement.entity.HoaDon;
import com.example.librarymanagement.entity.PhieuMuon;
import com.example.librarymanagement.entity.TheThuVien;
import com.example.librarymanagement.service.BanSaoService;
import com.example.librarymanagement.service.MuonTraService;
import com.example.librarymanagement.service.PhieuMuonService;
import com.example.librarymanagement.service.TheThuVienService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/muon-tra")
public class MuonTraController {

    private final MuonTraService muonTraService;
    private final TheThuVienService theThuVienService;
    private final BanSaoService banSaoService;
    private final PhieuMuonService phieuMuonService;

    public MuonTraController(
            MuonTraService muonTraService,
            TheThuVienService theThuVienService,
            BanSaoService banSaoService,
            PhieuMuonService phieuMuonService) {
        this.muonTraService = muonTraService;
        this.theThuVienService = theThuVienService;
        this.banSaoService = banSaoService;
        this.phieuMuonService = phieuMuonService;
    }

    @GetMapping("/lap-phieu-muon")
    public String hienThiFormLapPhieuMuon(Model model) {
        model.addAttribute("lapPhieuMuonRequest", new LapPhieuMuonRequest());
        model.addAttribute("dsBanSao", banSaoService.getAll());
        return "muontra/FormLapPhieuMuon";
    }

    @GetMapping("/thong-tin-ban-doc")
    public String hienThiThongTinBanDoc(@RequestParam String theId, Model model, HttpSession session) {
        try {
            CurrentUser nhanVien = (CurrentUser) session.getAttribute("currentUser");
            if (nhanVien == null) {
                throw new RuntimeException("Chưa đăng nhập hệ thống!");
            }
            Integer maNhanVien = nhanVien.getId();

            TheThuVien theThuVien = theThuVienService.getTheById(theId);
            long soSachChuaTra = banSaoService.getAllBanSaoChuaTraByTheId(theId).size();
            List<BanSao> dsBanSao = banSaoService.getAll();

            LapPhieuMuonRequest request = new LapPhieuMuonRequest();
            request.setTheId(theId);
            request.setMaNhanVien(maNhanVien);

            model.addAttribute("theThuVien", theThuVien);
            model.addAttribute("soSachChuaTra", soSachChuaTra);
            model.addAttribute("dsBanSao", dsBanSao);
            model.addAttribute("lapPhieuMuonRequest", request);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("lapPhieuMuonRequest", new LapPhieuMuonRequest());
            model.addAttribute("dsBanSao", banSaoService.getAll());
        }
        return "muontra/FormLapPhieuMuon";
    }

    @PostMapping("/lap-phieu-muon")
    public String xuLyLapPhieuMuon(@ModelAttribute LapPhieuMuonRequest request, Model model, HttpSession session) {
        try {
            CurrentUser nhanVien = (CurrentUser) session.getAttribute("currentUser");
            if (nhanVien == null) {
                throw new RuntimeException("Chưa đăng nhập hệ thống!");
            }
            Integer maNhanVien = nhanVien.getId();

            request.setMaNhanVien(maNhanVien);
            muonTraService.xuLyLapPhieuMuon(request);
            model.addAttribute("successMessage", "Lập phiếu mượn thành công!");
            model.addAttribute("lapPhieuMuonRequest", new LapPhieuMuonRequest());
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("lapPhieuMuonRequest", request);
        }

        model.addAttribute("dsBanSao", banSaoService.getAll());
        if (request.getTheId() != null) {
            try {
                model.addAttribute("theThuVien", theThuVienService.getTheById(request.getTheId()));
                model.addAttribute("soSachChuaTra",
                        theThuVienService.getBanSaoChuaTraByTheId(request.getTheId()).size());
            } catch (Exception ignored) {
                System.out.println(ignored.getMessage());
            }
        }
        return "muontra/FormLapPhieuMuon";
    }

    @GetMapping("/tra-sach")
    public String hienThiFormTraSach(Model model, HttpSession session) {
        model.addAttribute("traSachRequest", new TraSachRequest());
        return "muontra/FormTraSach";
    }

    @PostMapping("/tra-sach/tim-phieu")
    public String hienThiThongTinTraSach(@RequestParam String maThe, Model model, HttpSession session) {
        try {
            TheThuVien theThuVien = theThuVienService.getTheById(maThe);
            List<PhieuMuon> phieuMuons = phieuMuonService.getAllPhieuMuonChuaTraByMaThe(maThe);
            model.addAttribute("theThuVien", theThuVien);
            model.addAttribute("danhSachPhieuTra", phieuMuons);

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("traSachRequest", new TraSachRequest());
        return "muontra/FormTraSach";
    }

    @PostMapping("/tra-sach/xac-nhan")
    public String xulyTraSach(
            @ModelAttribute TraSachRequest request,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            muonTraService.xulyTraSach(request, session);
            redirectAttributes.addFlashAttribute("successMessage", "Trả sách thành công.");
            return "redirect:/muon-tra/tra-sach";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("traSachRequest", request);
            return "muontra/FormTraSach";
        }
    }

    @GetMapping("/hoa-don")
    public String hienThiFormHoaDon(
            @RequestParam(required = false) String maThe,
            Model model) {
        model.addAttribute("maThe", maThe == null ? "" : maThe.trim());
        if (maThe != null && !maThe.isBlank()) {
            try {
                List<HoaDon> danhSachHoaDon = muonTraService.timHoaDonChuaThanhToanTheoMaThe(maThe);
                model.addAttribute("danhSachHoaDon", danhSachHoaDon);
            } catch (RuntimeException e) {
                model.addAttribute("errorMessage", e.getMessage());
            }
        }
        return "muontra/FormHoaDon";
    }

    @PostMapping("/hoa-don/tim")
    public String timHoaDonTheoMaThe(@RequestParam String maThe, RedirectAttributes redirectAttributes) {
        if (maThe == null || maThe.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mã thẻ không được để trống!");
            return "redirect:/muon-tra/hoa-don";
        }
        return "redirect:/muon-tra/hoa-don?maThe=" + maThe.trim();
    }

    @PostMapping("/hoa-don/xac-nhan-thanh-toan")
    public String xacNhanThanhToanHoaDon(
            @RequestParam Integer maHoaDon,
            @RequestParam(required = false) String maThe,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            muonTraService.xacNhanThanhToanHoaDon(maHoaDon, session);
            redirectAttributes.addFlashAttribute("successMessage", "Xác nhận thanh toán hóa đơn thành công.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        if (maThe != null && !maThe.isBlank()) {
            return "redirect:/muon-tra/hoa-don?maThe=" + maThe.trim();
        }
        return "redirect:/muon-tra/hoa-don";
    }
}
