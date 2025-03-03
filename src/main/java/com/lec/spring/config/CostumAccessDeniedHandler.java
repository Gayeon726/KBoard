package com.lec.spring.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CostumAccessDeniedHandler implements AccessDeniedHandler {

    // 권한이 없는 url 접근을 할 때
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println(" ### 접근권한 오류 CostumAccessDeniedHandler : " + request.getRequestURI() + " ###");

        response.sendRedirect("/user/rejectAuth");
    }
}
