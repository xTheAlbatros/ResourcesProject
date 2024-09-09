package org.example.resourcesproject.casbin;

import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CasbinFilter implements Filter {

    private final Enforcer enforcer;

    private final CasdoorAuthService casdoorAuthService;

    public CasbinFilter(Enforcer enforcer, CasdoorAuthService casdoorAuthService) {
        this.enforcer = enforcer;
        this.casdoorAuthService = casdoorAuthService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        final String header = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);

        final String token = header.split(" ")[1].trim();

        CasdoorUser user = null;
        try {
            user = casdoorAuthService.parseJwtToken(token);
        } catch (Exception e) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED, "User Not Authenticated");
            response.getWriter().write("Invalid token");
            return;
        }

        String path = httpServletRequest.getRequestURI();

        //TODO: Delete, not needed (was for old logging)
//        if (path.equals("/login") || path.equals("/toLogin") || path.equals("/callback") || path.equals("/")) {
//            chain.doFilter(request, response);
//            return;
//        }

        if (user != null) {
            String username = user.getName();
            String method = httpServletRequest.getMethod();

            if (enforcer.enforce(username, path, method)) {
                System.out.println("Casbin zezwala na dostep: " + username + " -> " + path + " (" + method + ")");
                chain.doFilter(request, response);
            } else {
                System.out.println("Casbin odrzuca dostep: " + username + " -> " + path + " (" + method + ")");
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            }
        } else {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED, "User Not Authenticated");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}


