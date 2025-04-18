package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import util.EmailService;

@WebServlet("/cart/*")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || "/".equals(pathInfo)) {
            // Show cart contents
            showCart(request, response, cart);
        } else if ("/checkout".equals(pathInfo)) {
            // Show checkout page
            if (session.getAttribute("user") == null) {
                // Redirect to login if not logged in
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            
            showCheckout(request, response, cart);
        } else if ("/payment".equals(pathInfo)) {
            // Show payment details page
            if (session.getAttribute("user") == null) {
                // Redirect to login if not logged in
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            
            showPaymentDetails(request, response, cart);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        
        String pathInfo = request.getPathInfo();
        
        if ("/add".equals(pathInfo)) {
            // Add book to cart
            String isbn = request.getParameter("isbn");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            if (cart.containsKey(isbn)) {
                cart.put(isbn, cart.get(isbn) + quantity);
            } else {
                cart.put(isbn, quantity);
            }
            
            response.sendRedirect(request.getContextPath() + "/book/" + isbn);
        } else if ("/update".equals(pathInfo)) {
            // Update cart quantities
            String[] isbns = request.getParameterValues("isbn");
            String[] quantities = request.getParameterValues("quantity");
            
            if (isbns != null && quantities != null && isbns.length == quantities.length) {
                for (int i = 0; i < isbns.length; i++) {
                    int qty = Integer.parseInt(quantities[i]);
                    
                    if (qty > 0) {
                        cart.put(isbns[i], qty);
                    } else {
                        cart.remove(isbns[i]);
                    }
                }
            }
            
            response.sendRedirect(request.getContextPath() + "/cart");
        } else if ("/remove".equals(pathInfo)) {
            // Remove item from cart
            String isbn = request.getParameter("isbn");
            cart.remove(isbn);
            
            response.sendRedirect(request.getContextPath() + "/cart");
        } else if ("/checkout".equals(pathInfo)) {
            // Redirect to payment page instead of processing checkout directly
            if (session.getAttribute("user") == null) {
                // Redirect to login if not logged in
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            
            response.sendRedirect(request.getContextPath() + "/cart/payment");
        } else if ("/process-payment".equals(pathInfo)) {
            // Process payment and checkout
            if (session.getAttribute("user") == null) {
                // Redirect to login if not logged in
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            
            // Check payment simulation toggle
            String paymentSuccess = request.getParameter("paymentSuccess");
            
            if (paymentSuccess != null && paymentSuccess.equals("false")) {
                // Simulate payment failure
                response.sendRedirect(request.getContextPath() + "/paymentFailure.jsp");
                return;
            }
            
            // Process successful payment
            processCheckout(request, response, cart);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
    
    private void showCart(HttpServletRequest request, HttpServletResponse response, Map<String, Integer> cart) 
            throws ServletException, IOException {
        
        List<Map<String, Object>> cartItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String isbn = entry.getKey();
                int quantity = entry.getValue();
                
                String sql = "SELECT isbn, title, price, cover_img FROM Book WHERE isbn = ?";
                
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, isbn);
                    ResultSet rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("isbn", rs.getString("isbn"));
                        item.put("title", rs.getString("title"));
                        item.put("price", rs.getBigDecimal("price"));
                        item.put("coverImg", rs.getString("cover_img"));
                        item.put("quantity", quantity);
                        
                        BigDecimal itemTotal = rs.getBigDecimal("price").multiply(new BigDecimal(quantity));
                        item.put("total", itemTotal);
                        
                        cartItems.add(item);
                        total = total.add(itemTotal);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
    
    private void showCheckout(HttpServletRequest request, HttpServletResponse response, Map<String, Integer> cart) 
            throws ServletException, IOException {
        
        // Just reuse the cart page but with a checkout form
        showCart(request, response, cart);
    }
    
    private void showPaymentDetails(HttpServletRequest request, HttpServletResponse response, Map<String, Integer> cart) 
            throws ServletException, IOException {
        
        // Calculate cart totals for the payment page
        List<Map<String, Object>> cartItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String isbn = entry.getKey();
                int quantity = entry.getValue();
                
                String sql = "SELECT isbn, title, price, cover_img FROM Book WHERE isbn = ?";
                
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, isbn);
                    ResultSet rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("isbn", rs.getString("isbn"));
                        item.put("title", rs.getString("title"));
                        item.put("price", rs.getBigDecimal("price"));
                        item.put("coverImg", rs.getString("cover_img"));
                        item.put("quantity", quantity);
                        
                        BigDecimal itemTotal = rs.getBigDecimal("price").multiply(new BigDecimal(quantity));
                        item.put("total", itemTotal);
                        
                        cartItems.add(item);
                        total = total.add(itemTotal);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
        
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/paymentDetails.jsp").forward(request, response);
    }
    
    private void processCheckout(HttpServletRequest request, HttpServletResponse response, Map<String, Integer> cart) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        String username = (String) user.get("username");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if there's enough inventory for each book
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String isbn = entry.getKey();
            int quantity = entry.getValue();
            
            // Check inventory
            String checkSql = "SELECT inventory, title FROM Book WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
                stmt.setString(1, isbn);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int inventory = rs.getInt("inventory");
                    String title = rs.getString("title");
                    if (inventory < quantity) {
                        // Not enough inventory
                        request.setAttribute("errorMessage", "Not enough inventory for book: " + title + 
                                             ". Only " + inventory + " copies available.");
                        showCart(request, response, cart);
                        return;
                    }
                }
            }
        }
            conn.setAutoCommit(false);
            
            try {
                // Calculate total price
                BigDecimal total = BigDecimal.ZERO;
                
                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    String isbn = entry.getKey();
                    int quantity = entry.getValue();
                    
                    String priceSql = "SELECT price FROM Book WHERE isbn = ?";
                    
                    try (PreparedStatement stmt = conn.prepareStatement(priceSql)) {
                        stmt.setString(1, isbn);
                        ResultSet rs = stmt.executeQuery();
                        
                        if (rs.next()) {
                            BigDecimal price = rs.getBigDecimal("price");
                            total = total.add(price.multiply(new BigDecimal(quantity)));
                        }
                    }
                }
                
                // Create transaction
                String transactionSql = "INSERT INTO Transactions (username, purchase_date, total_price) " +
                                       "VALUES (?, GETDATE(), ?)";
                
                int transactionId;
                
                try (PreparedStatement stmt = conn.prepareStatement(transactionSql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, username);
                    stmt.setBigDecimal(2, total);
                    stmt.executeUpdate();
                    
                    ResultSet rs = stmt.getGeneratedKeys();
                    
                    if (rs.next()) {
                        transactionId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating transaction failed, no ID obtained.");
                    }
                }
                
                // Create transaction items
                String itemSql = "INSERT INTO TransactionItem (isbn, transaction_id, quantity) VALUES (?, ?, ?)";
                
                try (PreparedStatement stmt = conn.prepareStatement(itemSql)) {
                    for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                        String isbn = entry.getKey();
                        int quantity = entry.getValue();
                        
                        stmt.setString(1, isbn);
                        stmt.setInt(2, transactionId);
                        stmt.setInt(3, quantity);
                        stmt.executeUpdate();
                        
                        // Update inventory
                        String updateSql = "UPDATE Book SET inventory = inventory - ? WHERE isbn = ?";

                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, quantity);
                            updateStmt.setString(2, isbn);
                            int rowsAffected = updateStmt.executeUpdate();

                            // Debug check
                            System.out.println("Updated inventory for ISBN " + isbn + ": " + rowsAffected + " rows affected");

                            if (rowsAffected == 0) {
                                // No rows were updated - this could happen if the ISBN doesn't exist
                                System.err.println("Warning: No inventory updated for ISBN " + isbn);
                            }
                        }
                    }
                }
                
                conn.commit();
                
                // Clear cart
                cart.clear();
                
                // Redirect to order confirmation
                request.setAttribute("transactionId", transactionId);
                request.setAttribute("total", total);
                // Send order confirmation email if email is available
                try {
                    String email = null;
                    String customerName = (String) user.get("firstname") + " " + (String) user.get("lastname");

                    // Get user's email
                    String emailSql = "SELECT email FROM Users WHERE username = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(emailSql)) {
                        stmt.setString(1, username);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            email = rs.getString("email");
                        }
                    }

                    if (email != null && !email.isEmpty()) {
                        EmailService.sendOrderConfirmation(email, transactionId, customerName);
                    }
                } catch (Exception e) {
                    // Log the error but don't disrupt the checkout process
                    e.printStackTrace();
                }
                request.getRequestDispatcher("/orderConfirmation.jsp").forward(request, response);
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}