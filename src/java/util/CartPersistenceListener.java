package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class CartPersistenceListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Nothing to do when session is created
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        
        // Only save cart if user is logged in
        if (user != null) {
            String username = (String) user.get("username");
            
            @SuppressWarnings("unchecked")
            Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
            
            if (cart != null && !cart.isEmpty()) {
                try {
                    saveCart(username, cart);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void saveCart(String username, Map<String, Integer> cart) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Delete existing saved cart items
            String deleteSql = "DELETE FROM SavedCart WHERE username = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }
            
            // Insert new cart items
            if (cart != null && !cart.isEmpty()) {
                String insertSql = "INSERT INTO SavedCart (username, isbn, quantity) VALUES (?, ?, ?)";
                
                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        stmt.setString(1, username);
                        stmt.setString(2, entry.getKey());
                        stmt.setInt(3, entry.getValue());
                        stmt.executeUpdate();
                    }
                }
            }
        }
    }
}