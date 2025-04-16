package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DatabaseConnection;

@WebServlet("/review/add")
public class AddReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Get form data
        String isbn = request.getParameter("isbn");
        Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("user");
        String username = (String) userMap.get("username");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String reviewText = request.getParameter("reviewText");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // First check if user has already reviewed this book
            String checkSql = "SELECT COUNT(*) FROM Rating WHERE username = ? AND isbn = ?";
            
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                checkStmt.setString(2, isbn);
                
                int count = 0;
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                rs.close();
                
                String sql;
                if (count > 0) {
                    // Update existing review
                    sql = "UPDATE Rating SET rating = ?, review_text = ? WHERE username = ? AND isbn = ?";
                } else {
                    // Insert new review
                    sql = "INSERT INTO Rating (username, isbn, rating, review_text) VALUES (?, ?, ?, ?)";
                }
                
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    if (count > 0) {
                        // Update
                        stmt.setInt(1, rating);
                        stmt.setString(2, reviewText);
                        stmt.setString(3, username);
                        stmt.setString(4, isbn);
                    } else {
                        // Insert
                        stmt.setString(1, username);
                        stmt.setString(2, isbn);
                        stmt.setInt(3, rating);
                        stmt.setString(4, reviewText);
                    }
                    
                    stmt.executeUpdate();
                }
            }
            
            // Redirect back to the book detail page
            response.sendRedirect(request.getContextPath() + "/book/" + isbn);
            
        } catch (SQLException e) {
            throw new ServletException("Database error when adding review", e);
        }
    }
}