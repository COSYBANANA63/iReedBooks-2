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

import util.DatabaseConnection;

@WebServlet("/book/*")
public class BookDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String isbn = pathInfo.substring(1); // Remove leading slash
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get book details
            String sql = "SELECT b.*, STRING_AGG(a.author_name, ', ') AS authors " +
                          "FROM Book b " +
                          "LEFT JOIN BookAuthor ba ON b.isbn = ba.isbn " +
                          "LEFT JOIN Author a ON ba.author_id = a.author_id " +
                          "WHERE b.isbn = ? " +
                          "GROUP BY b.isbn, b.title, b.publisher, b.price, b.description, " +
                          "b.publication_date, b.cover_img, b.inventory";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, isbn);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    Map<String, Object> book = new HashMap<>();
                    book.put("isbn", rs.getString("isbn"));
                    book.put("title", rs.getString("title"));
                    book.put("publisher", rs.getString("publisher"));
                    book.put("price", rs.getBigDecimal("price"));
                    book.put("description", rs.getString("description"));
                    book.put("publicationDate", rs.getDate("publication_date"));
                    book.put("coverImg", rs.getString("cover_img"));
                    book.put("inventory", rs.getInt("inventory"));
                    book.put("authors", rs.getString("authors"));
                    
                    request.setAttribute("book", book);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
                    return;
                }
            }
            
            // Get book genres
            String genreSql = "SELECT g.genre_name FROM Genre g " +
                              "JOIN BookGenre bg ON g.genre_name = bg.genre_name " +
                              "WHERE bg.isbn = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(genreSql)) {
                stmt.setString(1, isbn);
                ResultSet rs = stmt.executeQuery();
                
                List<String> genres = new ArrayList<>();
                while (rs.next()) {
                    genres.add(rs.getString("genre_name"));
                }
                
                request.setAttribute("genres", genres);
            }
            
            // Update book view count
            try (PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE BookView SET numView = numView + 1 WHERE isbn = ?; " +
                    "IF @@ROWCOUNT = 0 " +
                    "INSERT INTO BookView (isbn, numView) VALUES (?, 1)")) {
                updateStmt.setString(1, isbn);
                updateStmt.setString(2, isbn);
                updateStmt.executeUpdate();
            }
            
            // Get reviews
            String reviewSql = "SELECT r.*, u.firstname, u.lastname " +
                               "FROM Rating r " +
                               "JOIN Users u ON r.username = u.username " +
                               "WHERE r.isbn = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(reviewSql)) {
                stmt.setString(1, isbn);
                ResultSet rs = stmt.executeQuery();
                
                List<Map<String, Object>> reviews = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> review = new HashMap<>();
                    review.put("username", rs.getString("username"));
                    review.put("rating", rs.getInt("rating"));
                    review.put("reviewText", rs.getString("review_text"));
                    review.put("firstName", rs.getString("firstname"));
                    review.put("lastName", rs.getString("lastname"));
                    reviews.add(review);
                }
                
                request.setAttribute("reviews", reviews);
                // Calculate max order quantity
                // Add right before the request.getRequestDispatcher("/bookDetail.jsp").forward(...) line
                int inventory = (Integer) ((Map<String, Object>)request.getAttribute("book")).get("inventory");
                int maxOrderQuantity = Math.min(inventory, 10);
                request.setAttribute("maxOrderQuantity", maxOrderQuantity);
            }
            
            request.getRequestDispatcher("/bookDetail.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}