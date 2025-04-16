package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DatabaseConnection;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        
        if (user == null) {
            // Redirect to login if not logged in
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        String username = (String) user.get("username");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get all transactions for this user
            String sql = "SELECT transaction_id, purchase_date, total_price FROM Transactions " +
                         "WHERE username = ? ORDER BY purchase_date DESC";
            
            List<Map<String, Object>> orders = new ArrayList<>();
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    Map<String, Object> order = new HashMap<>();
                    int transactionId = rs.getInt("transaction_id");
                    
                    order.put("transactionId", transactionId);
                    order.put("purchaseDate", rs.getTimestamp("purchase_date"));
                    order.put("totalPrice", rs.getBigDecimal("total_price"));
                    
                    // Get order items
                    List<Map<String, Object>> items = getOrderItems(conn, transactionId);
                    order.put("items", items);
                    
                    orders.add(order);
                }
            }
            
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/orders.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private List<Map<String, Object>> getOrderItems(Connection conn, int transactionId) throws SQLException {
        String sql = "SELECT ti.isbn, ti.quantity, b.title, b.price, b.cover_img " +
                     "FROM TransactionItem ti " +
                     "JOIN Book b ON ti.isbn = b.isbn " +
                     "WHERE ti.transaction_id = ?";
        
        List<Map<String, Object>> items = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("isbn", rs.getString("isbn"));
                item.put("quantity", rs.getInt("quantity"));
                item.put("title", rs.getString("title"));
                item.put("price", rs.getBigDecimal("price"));
                item.put("coverImg", rs.getString("cover_img"));
                
                items.add(item);
            }
        }
        
        return items;
    }
}