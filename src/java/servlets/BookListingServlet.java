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

@WebServlet("/books")
public class BookListingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String genreFilter = request.getParameter("genre");
        String searchQuery = request.getParameter("search");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sql = new StringBuilder(
                "SELECT b.isbn, b.title, b.price, b.cover_img, b.description, b.publisher, " +
                "STRING_AGG(a.author_name, ', ') AS authors " +
                "FROM Book b " +
                "LEFT JOIN BookAuthor ba ON b.isbn = ba.isbn " +
                "LEFT JOIN Author a ON ba.author_id = a.author_id ");
            
            List<Object> params = new ArrayList<>();
            
            if (genreFilter != null && !genreFilter.isEmpty()) {
                sql.append("JOIN BookGenre bg ON b.isbn = bg.isbn WHERE bg.genre_name = ? ");
                params.add(genreFilter);
                
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    sql.append("AND (b.title LIKE ? OR b.description LIKE ?) ");
                    params.add("%" + searchQuery + "%");
                    params.add("%" + searchQuery + "%");
                }
            } else if (searchQuery != null && !searchQuery.isEmpty()) {
                sql.append("WHERE b.title LIKE ? OR b.description LIKE ? ");
                params.add("%" + searchQuery + "%");
                params.add("%" + searchQuery + "%");
            }
            
            sql.append("GROUP BY b.isbn, b.title, b.price, b.cover_img, b.description, b.publisher");
            
            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }
                
                ResultSet rs = stmt.executeQuery();
                List<Map<String, Object>> books = new ArrayList<>();
                
                while (rs.next()) {
                    Map<String, Object> book = new HashMap<>();
                    book.put("isbn", rs.getString("isbn"));
                    book.put("title", rs.getString("title"));
                    book.put("price", rs.getBigDecimal("price"));
                    book.put("coverImg", rs.getString("cover_img"));
                    book.put("description", rs.getString("description"));
                    book.put("publisher", rs.getString("publisher"));
                    book.put("authors", rs.getString("authors"));
                    books.add(book);
                }
                
                request.setAttribute("books", books);
            }
            
            // Get genres for filter dropdown
            try (PreparedStatement genreStmt = conn.prepareStatement("SELECT genre_name FROM Genre")) {
                ResultSet rs = genreStmt.executeQuery();
                List<String> genres = new ArrayList<>();
                
                while (rs.next()) {
                    genres.add(rs.getString("genre_name"));
                }
                
                request.setAttribute("genres", genres);
            }
            
            request.getRequestDispatcher("/bookList.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}