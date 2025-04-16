<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.sql.*"%>
<%
    // Check if user is logged in
    if(session.getAttribute("adminUser") == null) {
        response.sendRedirect("admin-login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BookStore Admin Dashboard</title>
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
            /*border: 1px solid white;*/
            color: white;
            padding: 5px 10px;
            cursor: pointer;
            border-radius: 4px;
            font-weight: bolder;
            transition: 0.3s;
        }
        .logout-btn:hover {
            background-color: #f93e9f;
            border: black;
            font-weight: bolder;
            transition: 0.3s;
        }
        nav {
            background-color: #f8f8f8;
            padding: 10px 20px;
            margin-bottom: 20px;
        }
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }
        nav ul li {
            margin-right: 20px;
        }
        nav ul li a {
            text-decoration: none;
            color: #666;
            font-weight: bold;
        }
        nav ul li a.active {
            color: #f93e9f;
        }
        .content {
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .form-container {
            max-width: 600px;
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
        .button {
            padding: 10px 15px;
            background-color: none;
            color: grey;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button:active {
            color: #f93e8f;
        }
        .button-red {
            background-color: #f44336;
            padding: 5px;
            border-radius: 4px;
            color: white;
        }
        .button-red:hover {
            background-color: #d32f2f;
        }
        .button-blue {
            background-color: #2196F3;
            padding: 5px;
            border-radius: 4px;
            color: white;
        }
        .button-blue:hover {
            background-color: #0b7dda;
        }
        .action-buttons {
            display: flex;
            gap: 5px;
        }
        .tab-content {
            display: none;
        }
        .tab-content.active {
            display: block;
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
    </style>
</head>
<body>
    <header>
        <h1><span style="color: #f93e9f; font-weight: bolder;">i</span>ReedBooks Admin</h1>
        <div class="user-info">
            Welcome, <span style="color: #f93e9f; font-weight: bolder;"> i</span><%= session.getAttribute("adminUser") %>
            <form action="AdminServlet" method="post" style="display: inline;">
                <input type="hidden" name="action" value="logout">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
        </div>
    </header>
    
    <nav>
        <ul>
            <li><a href="#" class="nav-link active" data-tab="books">Manage Books</a></li>
            <li><a href="#" class="nav-link" data-tab="users">Manage Users</a></li>
            <li><a href="genre-management.jsp" class="button">Manage Genres</a></li>
            <li><a href="author-management.jsp" class="button">Manage Authors</a></li>
            <li><a href="#" class="nav-link" data-tab="changePassword">Change Password</a></li>
        </ul>
    </nav>
    
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
        
        <!-- Books Management Tab -->
        <div id="books" class="content tab-content active">
            <h2>Book Management</h2>
            
            <button id="addBookBtn" class="button">Add New Book</button>
            
            <div id="addBookForm" style="display: none;" class="form-container">
                <h3>Add New Book</h3>
                <form action="AdminServlet" method="post">
                    <input type="hidden" name="action" value="addBook">
                    
                    <div class="form-group">
                        <label for="isbn">ISBN:</label>
                        <input type="text" id="isbn" name="isbn" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" id="title" name="title" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="publisher">Publisher:</label>
                        <input type="text" id="publisher" name="publisher" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="price">Price:</label>
                        <input type="number" id="price" name="price" step="0.01" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea id="description" name="description" rows="4"></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="publication_date">Publication Date:</label>
                        <input type="date" id="publication_date" name="publication_date" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="cover_img">Cover Image URL:</label>
                        <input type="text" id="cover_img" name="cover_img">
                    </div>
                    
                    <div class="form-group">
                        <label for="inventory">Inventory:</label>
                        <input type="number" id="inventory" name="inventory" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="author">Author:</label>
                        <input type="text" id="author" name="author" placeholder="Enter author name">
                    </div>
                    
                    <div class="form-group">
                        <label for="genre">Genre:</label>
                        <input type="text" id="genre" name="genre" placeholder="Enter genre (comma separated for multiple)">
                    </div>
                    
                    <button type="submit" class="button">Add Book</button>
                    <button type="button" class="button button-red" id="cancelAddBook">Cancel</button>
                </form>
            </div>
            
            <h3>Books in Stock</h3>
            <table>
                <thead>
                    <tr>
                        <th>ISBN</th>
                        <th>Title</th>
                        <th>Publisher</th>
                        <th>Price</th>
                        <th>Publication Date</th>
                        <th>Inventory</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    try {
                        Connection conn = util.DatabaseConnection.getConnection();
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
                        
                        while(rs.next()) {
                    %>
                    <tr>
                        <td><%= rs.getString("isbn") %></td>
                        <td><%= rs.getString("title") %></td>
                        <td><%= rs.getString("publisher") %></td>
                        <td>$<%= rs.getBigDecimal("price") %></td>
                        <td><%= rs.getDate("publication_date") %></td>
                        <td><%= rs.getInt("inventory") %></td>
                        <td class="action-buttons">
                            <form action="AdminServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="editBookForm">
                                <input type="hidden" name="isbn" value="<%= rs.getString("isbn") %>">
                                <button type="submit" class="button button-blue">Edit</button>
                            </form>
                            <form action="AdminServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="deleteBook">
                                <input type="hidden" name="isbn" value="<%= rs.getString("isbn") %>">
                                <button type="submit" class="button button-red" onclick="return confirm('Are you sure you want to delete this book?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% 
                        }
                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch(Exception e) {
                        out.println("<tr><td colspan='7'>Error: " + e.getMessage() + "</td></tr>");
                    }
                    %>
                </tbody>
            </table>
        </div>
        
        <!-- Users Management Tab -->
        <div id="users" class="content tab-content">
            <h2>User Management</h2>
            
            <button id="addUserBtn" class="button">Add New Admin User</button>
            
            <div id="addUserForm" style="display: none;" class="form-container">
                <h3>Add New Admin</h3>
                <form action="AdminServlet" method="post">
                    <input type="hidden" name="action" value="addUser">
                    
                    <div class="form-group">
                        <label for="newUsername">Username:</label>
                        <input type="text" id="newUsername" name="username" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newPassword">Password:</label>
                        <input type="password" id="newPassword" name="password" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="firstname">First Name:</label>
                        <input type="text" id="firstname" name="firstname" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="lastname">Last Name:</label>
                        <input type="text" id="lastname" name="lastname" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="text" id="email" name="email">
                    </div>
                    
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="text" id="phone" name="phone">
                    </div>
                    
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address">
                    </div>
                    
                    <input type="hidden" name="isStaff" value="true">
                    
                    <button type="submit" class="button">Add Admin</button>
                    <button type="button" class="button button-red" id="cancelAddUser">Cancel</button>
                </form>
            </div>
            
            <h3>User List</h3>
            <table>
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Admin Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    try {
                        Connection conn = util.DatabaseConnection.getConnection();
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
                        
                        while(rs.next()) {
                            boolean isAdmin = rs.getBoolean("isStaff");
                    %>
                    <tr>
                        <td><%= rs.getString("username") %></td>
                        <td><%= rs.getString("firstname") + " " + rs.getString("lastname") %></td>
                        <td><%= rs.getString("email") != null ? rs.getString("email") : "N/A" %></td>
                        <td><%= rs.getBigDecimal("phone") != null ? rs.getBigDecimal("phone") : "N/A" %></td>
                        <td><%= isAdmin ? "Admin" : "Customer" %></td>
                        <td class="action-buttons">
                            <form action="AdminServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="<%= isAdmin ? "removeAdmin" : "makeAdmin" %>">
                                <input type="hidden" name="username" value="<%= rs.getString("username") %>">
                                <button type="submit" class="button <%= isAdmin ? "button-red" : "button-blue" %>">
                                    <%= isAdmin ? "Remove Admin" : "Make Admin" %>
                                </button>
                            </form>
                            <form action="AdminServlet" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="resetPassword">
                                <input type="hidden" name="username" value="<%= rs.getString("username") %>">
                                <button type="submit" class="button" onclick="return confirm('Are you sure you want to reset password for this user?')">Reset Password</button>
                            </form>
                        </td>
                    </tr>
                    <% 
                        }
                        rs.close();
                        stmt.close();
                        conn.close();
                    } catch(Exception e) {
                        out.println("<tr><td colspan='6'>Error: " + e.getMessage() + "</td></tr>");
                    }
                    %>
                </tbody>
            </table>
        </div>
        
        <!-- Change Password Tab -->
        <div id="changePassword" class="content tab-content">
            <h2>Change Admin Password</h2>
            
            <div class="form-container">
                <form action="AdminServlet" method="post">
                    <input type="hidden" name="action" value="changePassword">
                    
                    <div class="form-group">
                        <label for="currentPassword">Current Password:</label>
                        <input type="password" id="currentPassword" name="currentPassword" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="newPassword">New Password:</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="confirmPassword">Confirm New Password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    
                    <button type="submit" class="button">Change Password</button>
                </form>
            </div>
        </div>
    </div>
    
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Tab switching
            const navLinks = document.querySelectorAll('.nav-link');
            navLinks.forEach(link => {
                link.addEventListener('click', function(e) {
                    e.preventDefault();
                    
                    // Remove active class from all links and tabs
                    navLinks.forEach(l => l.classList.remove('active'));
                    document.querySelectorAll('.tab-content').forEach(tab => tab.classList.remove('active'));
                    
                    // Add active class to clicked link and corresponding tab
                    this.classList.add('active');
                    const tabId = this.getAttribute('data-tab');
                    document.getElementById(tabId).classList.add('active');
                });
            });
            
            // Show/hide add book form
            const addBookBtn = document.getElementById('addBookBtn');
            const cancelAddBook = document.getElementById('cancelAddBook');
            if(addBookBtn) {
                addBookBtn.addEventListener('click', function() {
                    document.getElementById('addBookForm').style.display = 'block';
                    this.style.display = 'none';
                });
            }
            if(cancelAddBook) {
                cancelAddBook.addEventListener('click', function() {
                    document.getElementById('addBookForm').style.display = 'none';
                    document.getElementById('addBookBtn').style.display = 'block';
                });
            }
            
            // Show/hide add user form
            const addUserBtn = document.getElementById('addUserBtn');
            const cancelAddUser = document.getElementById('cancelAddUser');
            if(addUserBtn) {
                addUserBtn.addEventListener('click', function() {
                    document.getElementById('addUserForm').style.display = 'block';
                    this.style.display = 'none';
                });
            }
            if(cancelAddUser) {
                cancelAddUser.addEventListener('click', function() {
                    document.getElementById('addUserForm').style.display = 'none';
                    document.getElementById('addUserBtn').style.display = 'block';
                });
            }
        });
    </script>
</body>
</html>