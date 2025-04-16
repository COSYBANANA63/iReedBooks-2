package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class RememberMeFilter implements Filter {
    
    private static final String REMEMBER_ME_COOKIE = "bookstore_remember_me";
    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 30; // 30 days in seconds
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // If user is not already logged in, try to auto-login via remember-me cookie
        if (session == null || session.getAttribute("user") == null) {
            Cookie[] cookies = httpRequest.getCookies();
            String tokenValue = null;
            
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (REMEMBER_ME_COOKIE.equals(cookie.getName())) {
                        tokenValue = cookie.getValue();
                        break;
                    }
                }
            }
            
            if (tokenValue != null) {
                try {
                    autoLogin(tokenValue, httpRequest, httpResponse);
                } catch (SQLException e) {
                    // Log error but continue
                    e.printStackTrace();
                }
            }
        }
        
        // Continue processing the request
        chain.doFilter(request, response);
    }
    
    private void autoLogin(String tokenValue, HttpServletRequest request, HttpServletResponse response) 
            throws SQLException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT u.username, u.firstname, u.lastname, u.isStaff " +
                        "FROM RememberMeTokens t " +
                        "JOIN Users u ON t.username = u.username " +
                        "WHERE t.token = ? AND t.expiry_date > GETDATE()";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, tokenValue);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    // Create user session
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", rs.getString("username"));
                    user.put("firstname", rs.getString("firstname"));
                    user.put("lastname", rs.getString("lastname"));
                    user.put("isStaff", rs.getBoolean("isStaff"));
                    
                    // Get or create session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    
                    // Restore cart from database if available
                    restoreCart(rs.getString("username"), session);
                    
                    // Update token expiry
                    updateTokenExpiry(tokenValue, conn);
                }
            }
        }
    }
    
    private void updateTokenExpiry(String tokenValue, Connection conn) throws SQLException {
        String sql = "UPDATE RememberMeTokens SET expiry_date = DATEADD(day, 30, GETDATE()) " +
                    "WHERE token = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tokenValue);
            stmt.executeUpdate();
        }
    }
    
    private void restoreCart(String username, HttpSession session) throws SQLException {
    Map<String, Integer> cart = new HashMap<>();
    
    // First, get any existing items in the current session cart
    @SuppressWarnings("unchecked")
    Map<String, Integer> sessionCart = (Map<String, Integer>) session.getAttribute("cart");
    if (sessionCart != null) {
        cart.putAll(sessionCart);
    }
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT isbn, quantity FROM SavedCart WHERE username = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                int quantity = rs.getInt("quantity");
                
                // If item exists in both, keep the higher quantity
                if (cart.containsKey(isbn)) {
                    cart.put(isbn, Math.max(cart.get(isbn), quantity));
                } else {
                    cart.put(isbn, quantity);
                }
            }
        }
    }
    
    session.setAttribute("cart", cart);
}
    
    public static void createRememberMeToken(String username, HttpServletResponse response) 
            throws SQLException {
        
        String tokenValue = UUID.randomUUID().toString();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Delete any existing tokens for this user
            String deleteSql = "DELETE FROM RememberMeTokens WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
            
            // Create new token
            String insertSql = "INSERT INTO RememberMeTokens (username, token, expiry_date) " +
                              "VALUES (?, ?, DATEADD(day, 30, GETDATE()))";
            
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, username);
                stmt.setString(2, tokenValue);
                stmt.executeUpdate();
            }
            
            // Set cookie
            Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, tokenValue);
            cookie.setMaxAge(COOKIE_MAX_AGE);
            cookie.setPath("/");
            cookie.setHttpOnly(true); // For security
            response.addCookie(cookie);
        }
    }
    
    public static void clearRememberMeToken(String username, HttpServletRequest request, 
            HttpServletResponse response) throws SQLException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String deleteSql = "DELETE FROM RememberMeTokens WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
        }
        
        // Clear the cookie
        Cookie cookie = new Cookie(REMEMBER_ME_COOKIE, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    @Override
    public void destroy() {
        // Cleanup if needed
    }
}