package com.example.librarymanagement.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.librarymanagement.auth.CurrentUser;
import com.example.librarymanagement.controller.AuthController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/",
            "/login",
            "/login/bandoc",
            "/login/nhanvien",
            "/error"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String path = normalizePath(request);
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }

        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute(AuthController.CURRENT_USER_SESSION_KEY);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (isManagerPath(path) && !currentUser.isQuanLy()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền quản lý");
            return false;
        }

        if (path.startsWith("/nhanvien") && !currentUser.isNhanVien()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ nhân viên được truy cập");
            return false;
        }

        if (path.startsWith("/bandoc") && !currentUser.isBanDoc()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ bạn đọc được truy cập");
            return false;
        }

        return true;
    }

    private boolean isManagerPath(String path) {
        return path.startsWith("/quanly")
                || path.startsWith("/NXB")
                || path.startsWith("/nhanvien/quanly")
                || path.startsWith("/tailieu")
                || path.startsWith("/tacgia")
                || path.startsWith("/nhacungcap");
    }

    private String normalizePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String requestUri = request.getRequestURI();
        if (contextPath != null && !contextPath.isBlank() && requestUri.startsWith(contextPath)) {
            return requestUri.substring(contextPath.length());
        }
        return requestUri;
    }
}
