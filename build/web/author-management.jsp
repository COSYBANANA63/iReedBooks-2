<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*"%>
<%@ page import="util.DatabaseConnection"%>
<%
    // Check if user is logged in
    if(session.getAttribute("adminUser") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
    
    // Get authors list
    List<Map<String, Object>> authors = new ArrayList<>();
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        String authorSql = "SELECT author_id, author_name, author_dob FROM Author ORDER BY author_name";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(authorSql);
        
        while(rs.next()) {
            Map<String, Object> author = new HashMap<>();
            author.put("id", rs.getInt("author_id"));
            author.put("name", rs.getString("author_name"));
            author.put("dob", rs.getDate("author_dob"));
            
            // Get books by this author
            List<Map<String, String>> books = new ArrayList<>();
            String bookSql = "SELECT b.isbn, b.title FROM Book b " +
                           "JOIN BookAuthor ba ON b.isbn = ba.isbn " +
                           "WHERE ba.author_id = " + rs.getInt("author_id");
            Statement bookStmt = conn.createStatement();
            ResultSet bookRs = bookStmt.executeQuery(bookSql);
            
            while(bookRs.next()) {
                Map<String, String> book = new HashMap<>();
                book.put("isbn", bookRs.getString("isbn"));
                book.put("title", bookRs.getString("title"));
                books.add(book);
            }
            
            author.put("books", books);
            authors.add(author);
        }
    } catch (Exception e) {
        request.setAttribute("errorMessage", "Database error: " + e.getMessage());
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Author Management - BookStore Admin</title>
    <style>
        *{
            font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
        }
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .container {
            width: 95%;
            margin: 0 auto;
            padding: 20px;
        }
        header {
            background-color: #000000;
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .user-info {
            display: flex;
            align-items: center;
        }
        .logout-btn {
            margin-left: 15px;
            background: none;
            border: 1px solid white;
            color: white;
            padding: 5px 10px;
            cursor: pointer;
            border-radius: 4px;
        }
        .logout-btn:hover {
            background-color: rgba(255,255,255,0.2);
        }
        .content {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        .form-container {
            max-width: 800px;
            margin: 0 auto;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="date"], textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .button {
            padding: 10px 15px;
            background-color: #f93e9f;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }
        .button:hover {
            background-color: #f93e8f;
        }
        .button-red {
            background-color: #f44336;
        }
        .button-red:hover {
            background-color: #d32f2f;
        }
        .success-message {
            color: #f93e9f;
            padding: 10px;
            background-color: #e8f5e9;
            border-left: 5px solid pink;
            margin-bottom: 20px;
        }
        .error-message {
            color: red;
            padding: 10px;
            background-color: #ffebee;
            border-left: 5px solid red;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        .actions {
            display: flex;
        }
        .action-btn {
            padding: 5px 10px;
            margin-right: 5px;
            background-color: #2196F3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 12px;
        }
        .action-btn.delete {
            background-color: #f44336;
        }
        .action-btn:hover {
            opacity: 0.8;
        }
        .books-list {
            margin-top: 10px;
            font-size: 0.9em;
        }
        .book-item {
            padding: 3px 0;
        }
        .nav-links {
            margin-bottom: 20px;
        }
        .nav-links a {
            margin-right: 15px;
            color: grey;
            text-decoration: none;
            font-weight: bold;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 5px;
        }
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover, .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <header>
        <h1><span style="color: #f93e9f; font-weight: bolder;">i</span>ReedBooks Admin</h1>
        <div class="user-info">
            Welcome, <%= session.getAttribute("adminUser") %>
            <form action="AdminServlet" method="post" style="display: inline;">
                <input type="hidden" name="action" value="logout">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
        </div>
    </header>
    
    <div class="container">
        <% if(request.getAttribute("successMessage") != null) { %>
            <div class="success-message">
                <%= request.getAttribute("successMessage") %>
            </div>
        <% } %>
        
        <% if(request.getAttribute("errorMessage") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>
        
        <div class="nav-links">
            <a href="admin-dashboard.jsp">Dashboard</a>
            <a href="author-management.jsp" class="active">Author Management</a>
            <a href="genre-management.jsp">Genre Management</a>
        </div>
        
        <div class="content">
            <h2>Author Management</h2>
            
            <button id="addAuthorBtn" class="button">Add New Author</button>
            
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Date of Birth</th>
                        <th>Books</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(Map<String, Object> author : authors) { %>
                        <tr>
                            <td><%= author.get("id") %></td>
                            <td><%= author.get("name") %></td>
                            <td><%= author.get("dob") != null ? author.get("dob") : "Not specified" %></td>
                            <td>
                                <div class="books-list">
                                    <% 
                                    List<Map<String, String>> books = (List<Map<String, String>>) author.get("books");
                                    if(books.isEmpty()) {
                                        out.println("No books");
                                    } else {
                                        for(Map<String, String> book : books) {
                                    %>
                                        <div class="book-item">
                                            <a href="AdminServlet?action=editBookForm&isbn=<%= book.get("isbn") %>">
                                                <%= book.get("title") %>
                                            </a>
                                        </div>
                                    <%
                                        }
                                    }
                                    %>
                                </div>
                            </td>
                            <td class="actions">
                                <button class="action-btn edit-author" data-id="<%= author.get("id") %>" 
                                        data-name="<%= author.get("name") %>" 
                                        data-dob="<%= author.get("dob") != null ? author.get("dob") : "" %>">
                                    Edit
                                </button>
                                <form action="AdminServlet" method="post" style="display: inline;" 
                                      onsubmit="return confirm('Are you sure you want to delete this author? This will remove this author from all associated books.');">
                                    <input type="hidden" name="action" value="deleteAuthor">
                                    <input type="hidden" name="authorId" value="<%= author.get("id") %>">
                                    <button type="submit" class="action-btn delete">Delete</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    
    <!-- Add Author Modal -->
    <div id="addAuthorModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Add Author</h3>
            <form action="AdminServlet" method="post">
                <input type="hidden" name="action" value="addAuthor">
                
                <div class="form-group">
                    <label for="authorName">Author Name:</label>
                    <input type="text" id="authorName" name="authorName" required>
                </div>
                
                <div class="form-group">
                    <label for="authorDob">Date of Birth:</label>
                    <input type="date" id="authorDob" name="authorDob">
                </div>
                
                <button type="submit" class="button">Add Author</button>
            </form>
        </div>
    </div>
    
    <!-- Edit Author Modal -->
    <div id="editAuthorModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Edit Author</h3>
            <form action="AdminServlet" method="post">
                <input type="hidden" name="action" value="updateAuthor">
                <input type="hidden" id="editAuthorId" name="authorId">
                
                <div class="form-group">
                    <label for="editAuthorName">Author Name:</label>
                    <input type="text" id="editAuthorName" name="authorName" required>
                </div>
                
                <div class="form-group">
                    <label for="editAuthorDob">Date of Birth:</label>
                    <input type="date" id="editAuthorDob" name="authorDob">
                </div>
                
                <button type="submit" class="button">Update Author</button>
            </form>
        </div>
    </div>
    
    <script>
        // Modal functions
        const addAuthorModal = document.getElementById("addAuthorModal");
        const editAuthorModal = document.getElementById("editAuthorModal");
        const addAuthorBtn = document.getElementById("addAuthorBtn");
        const closeBtns = document.getElementsByClassName("close");
        
        // Open Add Author modal
        addAuthorBtn.onclick = function() {
            addAuthorModal.style.display = "block";
        }
        
        // Open Edit Author modal
        document.querySelectorAll('.edit-author').forEach(btn => {
            btn.onclick = function() {
                const authorId = this.getAttribute('data-id');
                const authorName = this.getAttribute('data-name');
                const authorDob = this.getAttribute('data-dob');
                
                document.getElementById('editAuthorId').value = authorId;
                document.getElementById('editAuthorName').value = authorName;
                document.getElementById('editAuthorDob').value = authorDob;
                
                editAuthorModal.style.display = "block";
            }
        });
        
        // Close modals
        for (let i = 0; i < closeBtns.length; i++) {
            closeBtns[i].onclick = function() {
                addAuthorModal.style.display = "none";
                editAuthorModal.style.display = "none";
            }
        }
        
        // Close when clicking outside the modal
        window.onclick = function(event) {
            if (event.target == addAuthorModal) {
                addAuthorModal.style.display = "none";
            }
            if (event.target == editAuthorModal) {
                editAuthorModal.style.display = "none";
            }
        }
    </script>
</body>
</html>