<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*"%>
<%
    // Check if user is logged in
    if(session.getAttribute("adminUser") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
    
    // Check if book attribute is available
    ResultSet book = (ResultSet)request.getAttribute("book");
    if(book == null) {
        response.sendRedirect("admin-dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Book - BookStore Admin</title>
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
        input[type="text"], input[type="password"], input[type="number"], input[type="date"], textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
        }
        .button {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }
        .button:hover {
            background-color: #45a049;
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
        .help-text {
            font-size: 12px;
            color: #666;
            margin-top: 2px;
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
            <h2>Edit Book</h2>
            
            <div class="form-container">
                <form action="AdminServlet" method="post">
                    <input type="hidden" name="action" value="updateBook">
                    <input type="hidden" name="isbn" value="<%= book.getString("isbn") %>">
                    
                    <div class="form-group">
                        <label for="isbn">ISBN:</label>
                        <input type="text" id="isbn" value="<%= book.getString("isbn") %>" disabled>
                        <small class="help-text">ISBN cannot be changed</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" id="title" name="title" value="<%= book.getString("title") %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="publisher">Publisher:</label>
                        <input type="text" id="publisher" name="publisher" value="<%= book.getString("publisher") %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="price">Price:</label>
                        <input type="number" id="price" name="price" step="0.01" value="<%= book.getBigDecimal("price") %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" rows="4"><%= book.getString("description") != null ? book.getString("description") : "" %></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="publication_date">Publication Date:</label>
                        <input type="date" id="publication_date" name="publication_date" value="<%= book.getDate("publication_date").toLocalDate() %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="cover_img">Cover Image URL:</label>
                        <input type="text" id="cover_img" name="cover_img" value="<%= book.getString("cover_img") != null ? book.getString("cover_img") : "" %>">
                    </div>
                    
                    <div class="form-group">
                        <label for="inventory">Inventory:</label>
                        <input type="number" id="inventory" name="inventory" value="<%= book.getInt("inventory") %>" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="authors">Authors:</label>
                        <input type="text" id="authors" name="authors" value="<%= request.getAttribute("authors") %>">
                        <small class="help-text">Separate multiple authors with commas</small>
                    </div>
                    
                    <div class="form-group">
                        <label for="genres">Genres:</label>
                        <input type="text" id="genres" name="genres" value="<%= request.getAttribute("genres") %>">
                        <small class="help-text">Separate multiple genres with commas</small>
                    </div>
                    
                    <button type="submit" class="button">Update Book</button>
                    <a href="admin-dashboard.jsp" class="button button-red">Cancel</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>