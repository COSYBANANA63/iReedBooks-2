<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*"%>
<%@ page import="util.DatabaseConnection"%>
<%
    // Check if user is logged in
    if(session.getAttribute("adminUser") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
    
    // Get genres list
    List<String> genres = new ArrayList<>();
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        String genreSql = "SELECT genre_name FROM Genre ORDER BY genre_name";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(genreSql);
        
        while (rs.next()) {
            genres.add(rs.getString("genre_name"));
        }
    } catch (SQLException e) {
        request.setAttribute("errorMessage", "Database error: " + e.getMessage());
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Genre Management - BookStore Admin</title>
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"] {
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
            color: green;
            padding: 10px;
            background-color: #e8f5e9;
            border-left: 5px solid green;
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
        tr:hover {
            background-color: #f5f5f5;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
        .action-btn {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            color: white;
        }
        .edit-btn {
            background-color: #2196F3;
        }
        .delete-btn {
            background-color: #f44336;
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
        
        <div class="content">
            <h2>Genre Management</h2>
            
            <div class="form-container">
                <form action="AdminServlet" method="post">
                    <input type="hidden" name="action" value="addGenre">
                    
                    <div class="form-group">
                        <label for="genre_name">New Genre:</label>
                        <input type="text" id="genre_name" name="genre_name" required>
                    </div>
                    
                    <button type="submit" class="button">Add Genre</button>
                    <a href="admin-dashboard.jsp" class="button button-red">Back to Dashboard</a>
                </form>
            </div>
            
            <h3>Current Genres</h3>
            <table>
                <thead>
                    <tr>
                        <th>Genre Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(String genre : genres) { %>
                        <tr>
                            <td><%= genre %></td>
                            <td class="actions">
                                <button class="action-btn edit-btn" onclick="showEditForm('<%= genre %>')">Edit</button>
                                <form action="AdminServlet" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="deleteGenre">
                                    <input type="hidden" name="genre_name" value="<%= genre %>">
                                    <button type="submit" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this genre? This will affect all books with this genre!')">Delete</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            
            <!-- Edit Genre Form (hidden by default) -->
            <div id="editGenreForm" style="display: none; margin-top: 30px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                <h3>Edit Genre</h3>
                <form action="AdminServlet" method="post">
                    <input type="hidden" name="action" value="updateGenre">
                    <input type="hidden" id="originalGenre" name="originalGenre">
                    
                    <div class="form-group">
                        <label for="newGenreName">New Genre Name:</label>
                        <input type="text" id="newGenreName" name="newGenreName" required>
                    </div>
                    
                    <button type="submit" class="button">Update Genre</button>
                    <button type="button" class="button button-red" onclick="hideEditForm()">Cancel</button>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        function showEditForm(genreName) {
            document.getElementById('editGenreForm').style.display = 'block';
            document.getElementById('originalGenre').value = genreName;
            document.getElementById('newGenreName').value = genreName;
        }
        
        function hideEditForm() {
            document.getElementById('editGenreForm').style.display = 'none';
        }
    </script>
</body>
</html>