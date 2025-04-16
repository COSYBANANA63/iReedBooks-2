<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Failed - iReedBooks</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/cart">Shopping Cart</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/cart/payment">Payment Details</a></li>
                <li class="breadcrumb-item active" aria-current="page">Payment Failed</li>
            </ol>
        </nav>
        
        <div class="card mb-4">
            <div class="card-body text-center py-5">
                <i class="bi bi-x-circle-fill text-danger" style="font-size: 4rem;"></i>
                <h1 class="mt-3">Payment Failed</h1>
                <p class="lead">We're sorry, but your payment could not be processed.</p>
                <p>Please check your payment details and try again.</p>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6 mx-auto text-center">
                <div class="d-grid gap-2 mt-4">
                    <a href="${pageContext.request.contextPath}/cart/payment" class="btn btn-primary">
                        <i class="bi bi-credit-card me-2"></i> Try Again
                    </a>
                    <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
                        <i class="bi bi-cart me-2"></i> Return to Cart
                    </a>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>