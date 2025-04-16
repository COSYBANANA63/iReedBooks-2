<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>iReedBooks - Home</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="/header.jsp" />
    
    <div class="container mt-5">
        <div class="jumbotron">
            <h1 class="display-4">Welcome to <span style="color: #f93e9f; font-weight: bolder;">i</span>ReedBooks!</h1>
            <p class="lead">Discover your next favorite book from our extensive collection.</p>
            <hr class="my-4">
            <p>Browse our catalog, find recommendations, and get the best deals on books.</p>
            <a class="btn btn-primary btn-lg" href="books" role="button">Browse Books</a>
        </div>
    </div>
    
    <jsp:include page="/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>