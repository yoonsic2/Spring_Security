package com.security.spring_security.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String ajaxHeader = request.getHeader("X-Requested-With");
        // AJAX 요청인지 확인
        if ("XMLHttpRequest".equals(ajaxHeader)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 응답
            response.getWriter().write("Access Denied, 권한(인가) 없음"); // 필요에 따라 메시지 작성
            response.getWriter().flush();
        } else {
            // 루트 경로로 리다이렉트
            HttpSession session = request.getSession();
            session.setAttribute("msg", "잘못된 요청입니다.");
            response.sendRedirect("/");
        }
    }
}
