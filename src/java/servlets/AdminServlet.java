package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DatabaseConnection;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public AdminServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }
        
        HttpSession session = request.getSession();
        
        switch (action) {
            case "logout":
                session.invalidate();
                response.sendRedirect("admin-login.jsp");
                break;
            default:
                response.sendRedirect("admin-login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect("admin-login.jsp");
            return;
        }
        
        HttpSession session = request.getSession();
        
        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "logout":
                session.invalidate();
                response.sendRedirect("admin-login.jsp");
                break;
            case "addBook":
                if (isAdmin(session)) {
                    handleAddBook(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "editBookForm":
                if (isAdmin(session)) {
                    handleEditBookForm(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "updateBook":
                if (isAdmin(session)) {
                    handleUpdateBook(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "deleteBook":
                if (isAdmin(session)) {
                    handleDeleteBook(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "addUser":
                if (isAdmin(session)) {
                    handleAddUser(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "makeAdmin":
                if (isAdmin(session)) {
                    handleChangeAdminStatus(request, response, true);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "removeAdmin":
                if (isAdmin(session)) {
                    handleChangeAdminStatus(request, response, false);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "resetPassword":
                if (isAdmin(session)) {
                    handleResetPassword(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "changePassword":
                if (isAdmin(session)) {
                    handleChangePassword(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "addGenre":
                if (isAdmin(session)) {
                    handleAddGenre(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "updateGenre":
                if (isAdmin(session)) {
                    handleUpdateGenre(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            case "deleteGenre":
                if (isAdmin(session)) {
                    handleDeleteGenre(request, response);
                } else {
                    response.sendRedirect("admin-login.jsp");
                }
                break;
            default:
                response.sendRedirect("admin-login.jsp");
        }
    }
    
    private boolean isAdmin(HttpSession session) {
        return session.getAttribute("adminUser") != null;
    }
    
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ? AND isStaff = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUser", username);
                response.sendRedirect("admin-dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password or not an admin user");
                request.getRequestDispatcher("admin-login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("admin-login.jsp").forward(request, response);
        }
    }
    
    private void handleAddBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String publisher = request.getParameter("publisher");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String pubDateStr = request.getParameter("publication_date");
        String coverImg = request.getParameter("cover_img");
        String inventoryStr = request.getParameter("inventory");
        String author = request.getParameter("author");
        String genreStr = request.getParameter("genre");
        
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Insert book
            String bookSql = "INSERT INTO Book (isbn, title, publisher, price, description, publication_date, cover_img, inventory) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement bookStmt = conn.prepareStatement(bookSql);
            bookStmt.setString(1, isbn);
            bookStmt.setString(2, title);
            bookStmt.setString(3, publisher);
            bookStmt.setBigDecimal(4, new java.math.BigDecimal(priceStr));
            bookStmt.setString(5, description);
            bookStmt.setDate(6, java.sql.Date.valueOf(pubDateStr));
            bookStmt.setString(7, coverImg);
            bookStmt.setInt(8, Integer.parseInt(inventoryStr));
            
            bookStmt.executeUpdate();
            
            // Add author if provided
            if (author != null && !author.trim().isEmpty()) {
                // Check if author exists
                int authorId = -1;
                String checkAuthorSql = "SELECT author_id FROM Author WHERE author_name = ?";
                PreparedStatement checkAuthorStmt = conn.prepareStatement(checkAuthorSql);
                checkAuthorStmt.setString(1, author);
                ResultSet authorRs = checkAuthorStmt.executeQuery();
                
                if (authorRs.next()) {
                    authorId = authorRs.getInt("author_id");
                } else {
                    // Insert new author
                    String addAuthorSql = "INSERT INTO Author (author_name) VALUES (?)";
                    PreparedStatement addAuthorStmt = conn.prepareStatement(addAuthorSql, Statement.RETURN_GENERATED_KEYS);
                    addAuthorStmt.setString(1, author);
                    addAuthorStmt.executeUpdate();
                    
                    ResultSet generatedKeys = addAuthorStmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        authorId = generatedKeys.getInt(1);
                    }
                }
                
                // Link book with author
                if (authorId > 0) {
                    String linkAuthorSql = "INSERT INTO BookAuthor (isbn, author_id) VALUES (?, ?)";
                    PreparedStatement linkAuthorStmt = conn.prepareStatement(linkAuthorSql);
                    linkAuthorStmt.setString(1, isbn);
                    linkAuthorStmt.setInt(2, authorId);
                    linkAuthorStmt.executeUpdate();
                }
            }
            
            // Add genres if provided
            if (genreStr != null && !genreStr.trim().isEmpty()) {
                String[] genres = genreStr.split(",");
                
                for (String genre : genres) {
                    genre = genre.trim();
                    
                    // Check if genre exists
                    String checkGenreSql = "SELECT COUNT(*) FROM Genre WHERE genre_name = ?";
                    PreparedStatement checkGenreStmt = conn.prepareStatement(checkGenreSql);
                    checkGenreStmt.setString(1, genre);
                    ResultSet genreRs = checkGenreStmt.executeQuery();
                    genreRs.next();
                    int genreCount = genreRs.getInt(1);
                    
                    // Add genre if it doesn't exist
                    if (genreCount == 0) {
                        String addGenreSql = "INSERT INTO Genre (genre_name) VALUES (?)";
                        PreparedStatement addGenreStmt = conn.prepareStatement(addGenreSql);
                        addGenreStmt.setString(1, genre);
                        addGenreStmt.executeUpdate();
                    }
                    
                    // Link book with genre
                    String linkGenreSql = "INSERT INTO BookGenre (isbn, genre_name) VALUES (?, ?)";
                    PreparedStatement linkGenreStmt = conn.prepareStatement(linkGenreSql);
                    linkGenreStmt.setString(1, isbn);
                    linkGenreStmt.setString(2, genre);
                    linkGenreStmt.executeUpdate();
                }
            }
            
            conn.commit();
            request.setAttribute("successMessage", "Book added successfully!");
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("errorMessage", "Error adding book: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
    
    private void handleEditBookForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Get book details
            String bookSql = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement bookStmt = conn.prepareStatement(bookSql);
            bookStmt.setString(1, isbn);
            ResultSet bookRs = bookStmt.executeQuery();
            
            if (bookRs.next()) {
                request.setAttribute("book", bookRs);
                
                // Get authors
                String authorSql = "SELECT a.author_name FROM Author a JOIN BookAuthor ba ON a.author_id = ba.author_id WHERE ba.isbn = ?";
                PreparedStatement authorStmt = conn.prepareStatement(authorSql);
                authorStmt.setString(1, isbn);
                ResultSet authorRs = authorStmt.executeQuery();
                
                List<String> authors = new ArrayList<>();
                while (authorRs.next()) {
                    authors.add(authorRs.getString("author_name"));
                }
                request.setAttribute("authors", String.join(", ", authors));
                
                // Get genres
                String genreSql = "SELECT genre_name FROM BookGenre WHERE isbn = ?";
                PreparedStatement genreStmt = conn.prepareStatement(genreSql);
                genreStmt.setString(1, isbn);
                ResultSet genreRs = genreStmt.executeQuery();
                
                List<String> genres = new ArrayList<>();
                while (genreRs.next()) {
                    genres.add(genreRs.getString("genre_name"));
                }
                request.setAttribute("genres", String.join(", ", genres));
                
                request.getRequestDispatcher("edit-book.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Book not found");
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        }
    }
    
    private void handleUpdateBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String isbn = request.getParameter("isbn");
    String title = request.getParameter("title");
    String publisher = request.getParameter("publisher");
    String priceStr = request.getParameter("price");
    String description = request.getParameter("description");
    String pubDateStr = request.getParameter("publication_date");
    String coverImg = request.getParameter("cover_img");
    String inventoryStr = request.getParameter("inventory");
    String authorsStr = request.getParameter("authors");
    String genresStr = request.getParameter("genres");
    
    Connection conn = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        
        // Update book basic information
        String updateSql = "UPDATE Book SET title = ?, publisher = ?, price = ?, description = ?, publication_date = ?, cover_img = ?, inventory = ? WHERE isbn = ?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
        updateStmt.setString(1, title);
        updateStmt.setString(2, publisher);
        updateStmt.setBigDecimal(3, new java.math.BigDecimal(priceStr));
        updateStmt.setString(4, description);
        updateStmt.setDate(5, java.sql.Date.valueOf(pubDateStr));
        updateStmt.setString(6, coverImg);
        updateStmt.setInt(7, Integer.parseInt(inventoryStr));
        updateStmt.setString(8, isbn);
        
        updateStmt.executeUpdate();
        
        // Handle author updates if provided
        if (authorsStr != null && !authorsStr.trim().isEmpty()) {
            // First, remove existing author associations
            String deleteAuthorsSql = "DELETE FROM BookAuthor WHERE isbn = ?";
            PreparedStatement deleteAuthorsStmt = conn.prepareStatement(deleteAuthorsSql);
            deleteAuthorsStmt.setString(1, isbn);
            deleteAuthorsStmt.executeUpdate();
            
            // Add new author associations
            String[] authors = authorsStr.split(",");
            for (String authorName : authors) {
                authorName = authorName.trim();
                if (!authorName.isEmpty()) {
                    // Check if author exists
                    int authorId = -1;
                    String checkAuthorSql = "SELECT author_id FROM Author WHERE author_name = ?";
                    PreparedStatement checkAuthorStmt = conn.prepareStatement(checkAuthorSql);
                    checkAuthorStmt.setString(1, authorName);
                    ResultSet authorRs = checkAuthorStmt.executeQuery();
                    
                    if (authorRs.next()) {
                        authorId = authorRs.getInt("author_id");
                    } else {
                        // Insert new author
                        String addAuthorSql = "INSERT INTO Author (author_name) VALUES (?)";
                        PreparedStatement addAuthorStmt = conn.prepareStatement(addAuthorSql, Statement.RETURN_GENERATED_KEYS);
                        addAuthorStmt.setString(1, authorName);
                        addAuthorStmt.executeUpdate();
                        
                        ResultSet generatedKeys = addAuthorStmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            authorId = generatedKeys.getInt(1);
                        }
                    }
                    
                    // Link book with author
                    if (authorId > 0) {
                        String linkAuthorSql = "INSERT INTO BookAuthor (isbn, author_id) VALUES (?, ?)";
                        PreparedStatement linkAuthorStmt = conn.prepareStatement(linkAuthorSql);
                        linkAuthorStmt.setString(1, isbn);
                        linkAuthorStmt.setInt(2, authorId);
                        linkAuthorStmt.executeUpdate();
                    }
                }
            }
        }
        
        // Handle genre updates if provided
        if (genresStr != null && !genresStr.trim().isEmpty()) {
            // First, remove existing genre associations
            String deleteGenresSql = "DELETE FROM BookGenre WHERE isbn = ?";
            PreparedStatement deleteGenresStmt = conn.prepareStatement(deleteGenresSql);
            deleteGenresStmt.setString(1, isbn);
            deleteGenresStmt.executeUpdate();
            
            // Add new genre associations
            String[] genres = genresStr.split(",");
            for (String genreName : genres) {
                genreName = genreName.trim();
                if (!genreName.isEmpty()) {
                    // Check if genre exists
                    String checkGenreSql = "SELECT COUNT(*) FROM Genre WHERE genre_name = ?";
                    PreparedStatement checkGenreStmt = conn.prepareStatement(checkGenreSql);
                    checkGenreStmt.setString(1, genreName);
                    ResultSet genreRs = checkGenreStmt.executeQuery();
                    genreRs.next();
                    int genreCount = genreRs.getInt(1);
                    
                    // Add genre if it doesn't exist
                    if (genreCount == 0) {
                        String addGenreSql = "INSERT INTO Genre (genre_name) VALUES (?)";
                        PreparedStatement addGenreStmt = conn.prepareStatement(addGenreSql);
                        addGenreStmt.setString(1, genreName);
                        addGenreStmt.executeUpdate();
                    }
                    
                    // Link book with genre
                    String linkGenreSql = "INSERT INTO BookGenre (isbn, genre_name) VALUES (?, ?)";
                    PreparedStatement linkGenreStmt = conn.prepareStatement(linkGenreSql);
                    linkGenreStmt.setString(1, isbn);
                    linkGenreStmt.setString(2, genreName);
                    linkGenreStmt.executeUpdate();
                }
            }
        }
        
        conn.commit();
        request.setAttribute("successMessage", "Book updated successfully!");
        
    } catch (Exception e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        request.setAttribute("errorMessage", "Error updating book: " + e.getMessage());
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
}
    
    private void handleDeleteBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Delete related records first
            String deleteBookGenreSql = "DELETE FROM BookGenre WHERE isbn = ?";
            PreparedStatement deleteBookGenreStmt = conn.prepareStatement(deleteBookGenreSql);
            deleteBookGenreStmt.setString(1, isbn);
            deleteBookGenreStmt.executeUpdate();
            
            String deleteBookAuthorSql = "DELETE FROM BookAuthor WHERE isbn = ?";
            PreparedStatement deleteBookAuthorStmt = conn.prepareStatement(deleteBookAuthorSql);
            deleteBookAuthorStmt.setString(1, isbn);
            deleteBookAuthorStmt.executeUpdate();
            
            String deleteBookViewSql = "DELETE FROM BookView WHERE isbn = ?";
            PreparedStatement deleteBookViewStmt = conn.prepareStatement(deleteBookViewSql);
            deleteBookViewStmt.setString(1, isbn);
            deleteBookViewStmt.executeUpdate();
            
            String deleteRatingSql = "DELETE FROM Rating WHERE isbn = ?";
            PreparedStatement deleteRatingStmt = conn.prepareStatement(deleteRatingSql);
            deleteRatingStmt.setString(1, isbn);
            deleteRatingStmt.executeUpdate();
            
            // Check for transaction items with this book
            String checkTransactionSql = "SELECT COUNT(*) FROM TransactionItem WHERE isbn = ?";
            PreparedStatement checkTransactionStmt = conn.prepareStatement(checkTransactionSql);
            checkTransactionStmt.setString(1, isbn);
            ResultSet rs = checkTransactionStmt.executeQuery();
            rs.next();
            int transactionCount = rs.getInt(1);
            
            if (transactionCount > 0) {
                conn.rollback();
                request.setAttribute("errorMessage", "Cannot delete book. It is referenced in transaction records.");
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                return;
            }
            
            // Now delete the book
            String deleteBookSql = "DELETE FROM Book WHERE isbn = ?";
            PreparedStatement deleteBookStmt = conn.prepareStatement(deleteBookSql);
            deleteBookStmt.setString(1, isbn);
            deleteBookStmt.executeUpdate();
            
            conn.commit();
            request.setAttribute("successMessage", "Book deleted successfully!");
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            request.setAttribute("errorMessage", "Error deleting book: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
    
    // Add these methods to your AdminServlet class

private void handleAddGenre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String genreName = request.getParameter("genre_name");
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        // Check if genre already exists
        String checkSql = "SELECT COUNT(*) FROM Genre WHERE genre_name = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, genreName);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int genreCount = rs.getInt(1);
        
        if (genreCount > 0) {
            request.setAttribute("errorMessage", "Genre already exists");
            request.getRequestDispatcher("genre-management.jsp").forward(request, response);
            return;
        }
        
        // Insert new genre
        String insertSql = "INSERT INTO Genre (genre_name) VALUES (?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
        insertStmt.setString(1, genreName);
        
        insertStmt.executeUpdate();
        
        request.setAttribute("successMessage", "Genre added successfully!");
        
    } catch (SQLException e) {
        request.setAttribute("errorMessage", "Database error: " + e.getMessage());
    }
    
    request.getRequestDispatcher("genre-management.jsp").forward(request, response);
}

private void handleUpdateGenre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String originalGenre = request.getParameter("originalGenre");
    String newGenreName = request.getParameter("newGenreName");
    
    if (originalGenre.equals(newGenreName)) {
        // No change, just return
        request.getRequestDispatcher("genre-management.jsp").forward(request, response);
        return;
    }
    
    Connection conn = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        
        // Check if new genre name already exists
        String checkSql = "SELECT COUNT(*) FROM Genre WHERE genre_name = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, newGenreName);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int genreCount = rs.getInt(1);
        
        if (genreCount > 0) {
            conn.rollback();
            request.setAttribute("errorMessage", "Genre name already exists");
            request.getRequestDispatcher("genre-management.jsp").forward(request, response);
            return;
        }
        
        // Update genre name in Genre table
        String updateGenreSql = "UPDATE Genre SET genre_name = ? WHERE genre_name = ?";
        PreparedStatement updateGenreStmt = conn.prepareStatement(updateGenreSql);
        updateGenreStmt.setString(1, newGenreName);
        updateGenreStmt.setString(2, originalGenre);
        updateGenreStmt.executeUpdate();
        
        // Update genre name in BookGenre table
        String updateBookGenreSql = "UPDATE BookGenre SET genre_name = ? WHERE genre_name = ?";
        PreparedStatement updateBookGenreStmt = conn.prepareStatement(updateBookGenreSql);
        updateBookGenreStmt.setString(1, newGenreName);
        updateBookGenreStmt.setString(2, originalGenre);
        updateBookGenreStmt.executeUpdate();
        
        conn.commit();
        request.setAttribute("successMessage", "Genre updated successfully!");
        
    } catch (Exception e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        request.setAttribute("errorMessage", "Error updating genre: " + e.getMessage());
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    request.getRequestDispatcher("genre-management.jsp").forward(request, response);
}

private void handleDeleteGenre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String genreName = request.getParameter("genre_name");
    
    Connection conn = null;
    
    try {
        conn = DatabaseConnection.getConnection();
        conn.setAutoCommit(false);
        
        // Check if genre is associated with any books
        String checkSql = "SELECT COUNT(*) FROM BookGenre WHERE genre_name = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, genreName);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int bookCount = rs.getInt(1);
        
        if (bookCount > 0) {
            // Delete associations in BookGenre table
            String deleteBookGenreSql = "DELETE FROM BookGenre WHERE genre_name = ?";
            PreparedStatement deleteBookGenreStmt = conn.prepareStatement(deleteBookGenreSql);
            deleteBookGenreStmt.setString(1, genreName);
            deleteBookGenreStmt.executeUpdate();
        }
        
        // Delete genre from Genre table
        String deleteGenreSql = "DELETE FROM Genre WHERE genre_name = ?";
        PreparedStatement deleteGenreStmt = conn.prepareStatement(deleteGenreSql);
        deleteGenreStmt.setString(1, genreName);
        deleteGenreStmt.executeUpdate();
        
        conn.commit();
        request.setAttribute("successMessage", "Genre deleted successfully!");
        
    } catch (Exception e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        request.setAttribute("errorMessage", "Error deleting genre: " + e.getMessage());
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    request.getRequestDispatcher("genre-management.jsp").forward(request, response);
}
    
    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        boolean isStaff = Boolean.parseBoolean(request.getParameter("isStaff"));
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if username already exists
            String checkSql = "SELECT COUNT(*) FROM Users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int userCount = rs.getInt(1);
            
            if (userCount > 0) {
                request.setAttribute("errorMessage", "Username already exists");
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                return;
            }
            
            // Insert new user
            String insertSql = "INSERT INTO Users (username, password, firstname, lastname, phone, email, isStaff, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, firstname);
            insertStmt.setString(4, lastname);
            insertStmt.setString(5, phone);
            insertStmt.setString(6, email);
            insertStmt.setBoolean(7, isStaff);
            insertStmt.setString(8, address);
            
            insertStmt.executeUpdate();
            
            request.setAttribute("successMessage", "User added successfully!");
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }
        
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
    
    private void handleChangeAdminStatus(HttpServletRequest request, HttpServletResponse response, boolean makeAdmin) throws ServletException, IOException {
        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        String currentAdmin = (String) session.getAttribute("adminUser");
        
        // Prevent admin from removing their own admin status
        if (!makeAdmin && username.equals(currentAdmin)) {
            request.setAttribute("errorMessage", "You cannot remove your own admin status");
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String updateSql = "UPDATE Users SET isStaff = ? WHERE username = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setBoolean(1, makeAdmin);
            updateStmt.setString(2, username);
            
            updateStmt.executeUpdate();
            
            request.setAttribute("successMessage", makeAdmin ? 
                "User " + username + " is now an admin" : 
                "Admin status removed from user " + username);
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }
        
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
    
    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String defaultPassword = "password123"; // Default password after reset
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String updateSql = "UPDATE Users SET password = ? WHERE username = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, defaultPassword);
            updateStmt.setString(2, username);
            
            updateStmt.executeUpdate();
            
            request.setAttribute("successMessage", "Password for user " + username + " has been reset to: " + defaultPassword);
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }
        
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
    
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("adminUser");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New passwords do not match");
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Verify current password
            String checkSql = "SELECT COUNT(*) FROM Users WHERE username = ? AND password = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            checkStmt.setString(2, currentPassword);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int validPassword = rs.getInt(1);
            
            if (validPassword == 0) {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                return;
            }
            
            // Update password
            String updateSql = "UPDATE Users SET password = ? WHERE username = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, username);
            
            updateStmt.executeUpdate();
            
            request.setAttribute("successMessage", "Password changed successfully");
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }
        
        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
}