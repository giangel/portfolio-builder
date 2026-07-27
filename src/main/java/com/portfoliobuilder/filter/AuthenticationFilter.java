package com.portfoliobuilder.filter;

import com.portfoliobuilder.util.RedirectUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // No initialization required.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute(SessionConstants.USER_ID) != null;

        if (isLoggedIn) {
            chain.doFilter(request, response);
            return;
        }

        String requestedUrl = httpRequest.getRequestURI();
        if (httpRequest.getQueryString() != null) {
            requestedUrl += "?" + httpRequest.getQueryString();
        }
        String encodedTarget = URLEncoder.encode(requestedUrl, StandardCharsets.UTF_8);
        httpResponse.sendRedirect(RedirectUtil.contextPath(httpRequest, "/login.jsp?redirectTo=" + encodedTarget));
    }

    @Override
    public void destroy() {
        // No cleanup required.
    }
}