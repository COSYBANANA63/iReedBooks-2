package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DatabaseConnection;

@WebServlet({"/profile", "/profile/update"})
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionUser = (Map<String, Object>) session.getAttribute("user");
        
        if (sessionUser == null) {
            // Redirect to login if not logged in
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        String username = (String) sessionUser.get("username");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    Map<String, Object> userProfile = new HashMap<>();
                    userProfile.put("username", rs.getString("username"));
                    userProfile.put("firstname", rs.getString("firstname"));
                    userProfile.put("lastname", rs.getString("lastname"));
                    userProfile.put("email", rs.getString("email"));
                    userProfile.put("phone", rs.getString("phone"));
                    userProfile.put("address", rs.getString("address"));
                    
                    request.setAttribute("userProfile", userProfile);
                    request.getRequestDispatcher("/profile.jsp").forward(request, response);
                } else {
                    // Shouldn't happen, but just in case
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/auth/login");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Object> sessionUser = (Map<String, Object>) session.getAttribute("user");
        
        if (sessionUser == null) {
            // Redirect to login if not logged in
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        String username = (String) sessionUser.get("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Users SET firstname = ?, lastname = ?, email = ?, phone = ?, address = ? WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, firstname);
                stmt.setString(2, lastname);
                stmt.setString(3, email);
                
                if (phone != null && !phone.isEmpty()) {
                    stmt.setString(4, phone);
                } else {
                    stmt.setNull(4, java.sql.Types.VARCHAR);
                }
                
                if (address != null && !address.isEmpty()) {
                    stmt.setString(5, address);
                } else {
                    stmt.setNull(5, java.sql.Types.VARCHAR);
                }
                
                stmt.setString(6, username);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Update session with new name
                    sessionUser.put("firstname", firstname);
                    sessionUser.put("lastname", lastname);
                    session.setAttribute("user", sessionUser);
                    
                    request.setAttribute("message", "Profile updated successfully!");
                } else {
                    request.setAttribute("error", "Failed to update profile. Please try again.");
                }
            }
            
            // Reload profile data and show the page
            doGet(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}