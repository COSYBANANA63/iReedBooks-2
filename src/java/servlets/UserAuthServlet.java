package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DatabaseConnection;

@WebServlet("/auth/*")
public class UserAuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    String pathInfo = request.getPathInfo();
    
    if ("/logout".equals(pathInfo)) {
        // Get the current session without creating a new one
        HttpSession session = request.getSession(false);
        
        // If session exists, clear it completely
        if (session != null) {
            // Clear all session attributes
            java.util.Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                session.removeAttribute(attributeNames.nextElement());
            }
            // Invalidate the session
            session.invalidate();
        }
        
        // Clear all cookies including JSESSIONID
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        
        // Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        
        // Redirect to a completely new URL with timestamp to prevent browser caching
        response.sendRedirect(request.getContextPath() + "/books?logout=true&t=" + System.currentTimeMillis());
        return;
    } else if ("/login".equals(pathInfo)) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
    } else if ("/register".equals(pathInfo)) {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
        return;
    }else if ("/change-password".equals(pathInfo)) {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
    
            // Forward to change-password.jsp
            request.getRequestDispatcher("/change-password.jsp").forward(request, response);
            return;
        }// Add to the doGet method in UserAuthServlet.java, inside the if-else chain
else if ("/forgot-password".equals(pathInfo)) {
    request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
    return;
} else if ("/reset-password".equals(pathInfo)) {
    String token = request.getParameter("token");
    String username = request.getParameter("username");
    
        try {
            // Validate token
            if (isValidResetToken(username, token)) {
                request.setAttribute("username", username);
                request.setAttribute("token", token);
                request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("error", "Invalid or expired password reset link");
                request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
                return;
            }   } catch (SQLException ex) {
            Logger.getLogger(UserAuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    // Default redirect to login
    response.sendRedirect(request.getContextPath() + "/auth/login");
}
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if ("/login".equals(pathInfo)) {
            handleLogin(request, response);
        } else if ("/register".equals(pathInfo)) {
            handleRegister(request, response);
        } else if ("/change-password".equals(pathInfo)) {
            handleChangePassword(request, response);
        } else if ("/forgot-password".equals(pathInfo)) {
            handleForgotPassword(request, response);
        } else if ("/reset-password".equals(pathInfo)) {
            try {
                handleResetPassword(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UserAuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else {
            response.sendRedirect(request.getContextPath() + "/auth/login");
        }
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", rs.getString("username"));
                    user.put("firstname", rs.getString("firstname"));
                    user.put("lastname", rs.getString("lastname"));
                    user.put("isStaff", rs.getBoolean("isStaff"));
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    
                    response.sendRedirect(request.getContextPath() + "/books");
                } else {
                    request.setAttribute("error", "Invalid username or password");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if username exists
            String checkSql = "SELECT COUNT(*) AS count FROM Users WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next() && rs.getInt("count") > 0) {
                    request.setAttribute("error", "Username already exists");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
            }
            
            // Insert new user
            String insertSql = "INSERT INTO Users (username, password, firstname, lastname, email, phone, isStaff) " +
                               "VALUES (?, ?, ?, ?, ?, ?, 0)";
            
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, firstname);
                stmt.setString(4, lastname);
                stmt.setString(5, email);
                
                if (phone != null && !phone.isEmpty()) {
                    stmt.setLong(6, Long.parseLong(phone));
                } else {
                    stmt.setNull(6, java.sql.Types.BIGINT);
                }
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Auto-login after registration
                    Map<String, Object> user = new HashMap<>();
                    user.put("username", username);
                    user.put("firstname", firstname);
                    user.put("lastname", lastname);
                    user.put("isStaff", false);
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    
                    response.sendRedirect(request.getContextPath() + "/books");
                } else {
                    request.setAttribute("error", "Registration failed");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        // Get current user from session
        @SuppressWarnings("unchecked")
        Map<String, Object> currentUser = (Map<String, Object>) session.getAttribute("user");
        String username = (String) currentUser.get("username");
        
        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate inputs
        if (currentPassword == null || newPassword == null || confirmPassword == null ||
            currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/change-password.jsp").forward(request, response);
            return;
        }
        
        if (newPassword.length() < 6) {
            request.setAttribute("error", "New password must be at least 6 characters long");
            request.getRequestDispatcher("/change-password.jsp").forward(request, response);
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New password and confirm password do not match");
            request.getRequestDispatcher("/change-password.jsp").forward(request, response);
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Verify current password
            String verifySql = "SELECT COUNT(*) AS count FROM Users WHERE username = ? AND password = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(verifySql)) {
                stmt.setString(1, username);
                stmt.setString(2, currentPassword);
                ResultSet rs = stmt.executeQuery();
                
                if (!rs.next() || rs.getInt("count") == 0) {
                    request.setAttribute("error", "Current password is incorrect");
                    request.getRequestDispatcher("/change-password.jsp").forward(request, response);
                    return;
                }
            }
            
            // Update password
            String updateSql = "UPDATE Users SET password = ? WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, newPassword);
                stmt.setString(2, username);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    request.setAttribute("message", "Password changed successfully");
                    request.getRequestDispatcher("/change-password.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Failed to change password");
                    request.getRequestDispatcher("/change-password.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    private void handleForgotPassword(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    String username = request.getParameter("username");
    String email = request.getParameter("email");
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        // Verify user exists and email matches
        String sql = "SELECT email FROM Users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next() && email.equals(rs.getString("email"))) {
            // Generate a reset token (a simple example - in production use a more secure method)
            String token = generateResetToken();
            
            // Store the token with expiration (e.g., 1 hour from now)
            long expiryTime = System.currentTimeMillis() + 3600000; // 1 hour
            storeResetToken(username, token, expiryTime);
            
            // In a real application, you would send an email with the reset link
            // For this example, we'll just display the link on the page
            String resetLink = request.getContextPath() + "/auth/reset-password?username=" + username + "&token=" + token;
            
            request.setAttribute("message", "Password reset instructions have been sent to your email address. Check your inbox.");
            request.setAttribute("resetLink", resetLink); // This is just for demonstration
            request.getRequestDispatcher("/forgot-password-confirmation.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Username and email do not match our records");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
        }
    } catch (SQLException e) {
        throw new ServletException("Database error", e);
    }
}

    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {

        String username = request.getParameter("username");
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate passwords match
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.setAttribute("username", username);
            request.setAttribute("token", token);
            request.getRequestDispatcher("/reset-password.jsp").forward(request, response);
            return;
        }

        // Verify token is valid
        if (isValidResetToken(username, token)) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Update password
                String updateSql = "UPDATE Users SET password = ? WHERE username = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();

                // Delete the used token
                deleteResetToken(username);

                request.setAttribute("message", "Your password has been successfully reset. You can now login with your new password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        } else {
            request.setAttribute("error", "Invalid or expired password reset link");
            request.getRequestDispatcher("/forgot-password.jsp").forward(request, response);
        }
    }

    // Helper methods for token management
    private String generateResetToken() {
        // Generate a random token (in production, use a more secure method)
        return UUID.randomUUID().toString();
    }

    // You'll need to create a table in your database to store reset tokens
    private void storeResetToken(String username, String token, long expiryTime) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // First delete any existing tokens for this user
            String deleteSql = "DELETE FROM PasswordResetTokens WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }

            // Insert the new token
            String insertSql = "INSERT INTO PasswordResetTokens (username, token, expiry_time) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, username);
                stmt.setString(2, token);
                stmt.setLong(3, expiryTime);
                stmt.executeUpdate();
            }
        }
    }

    private boolean isValidResetToken(String username, String token) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT expiry_time FROM PasswordResetTokens WHERE username = ? AND token = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, token);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    long expiryTime = rs.getLong("expiry_time");
                    return System.currentTimeMillis() < expiryTime;
                }
            }
        }
        return false;
    }

    private void deleteResetToken(String username) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM PasswordResetTokens WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
        }
    }
}