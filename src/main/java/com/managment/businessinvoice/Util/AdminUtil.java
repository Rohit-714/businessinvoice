package com.managment.businessinvoice.Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class AdminUtil {
    public Long getAdminId(HttpServletRequest request)
    {
        Long adminId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("adminId".equals(cookie.getName())) {
                    adminId = Long.parseLong(cookie.getValue());
                    break;
                }
            }
        }
        return adminId;
    }
}
