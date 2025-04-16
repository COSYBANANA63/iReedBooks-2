package filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class SessionCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        // Skip filter for static resources and login/logout pages
        if (path.startsWith("/assets/") || path.startsWith("/css/") || 
            path.startsWith("/js/") || path.startsWith("/images/") ||
            path.startsWith("/auth/login") || path.startsWith("/auth/logout") ||
            path.startsWith("/auth/register")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Process session check for all other requests
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        
        // For protected pages, redirect to login if not logged in
        if (path.startsWith("/profile") || path.startsWith("/orders") || 
            path.startsWith("/checkout") || path.startsWith("/account")) {
            if (!isLoggedIn) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login");
                return;
            }
        }
        
        // Set request attribute for use in JSPs to check login status
        request.setAttribute("isLoggedIn", isLoggedIn);
        
        // Apply cache control for authenticated pages
        if (isLoggedIn) {
            httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResponse.setHeader("Pragma", "no-cache");
            httpResponse.setDateHeader("Expires", 0);
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}