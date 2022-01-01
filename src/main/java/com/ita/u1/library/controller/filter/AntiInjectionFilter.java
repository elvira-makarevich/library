package com.ita.u1.library.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AntiInjectionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();
        for (String[] v : params.values()) {
            sb.append(v[0]);
        }
        if (sb.toString().trim().matches("^((?!<|>|script).)*$")) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void destroy() {

    }
}
