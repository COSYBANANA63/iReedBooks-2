<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation - iReedBooks</title>
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
                <li class="breadcrumb-item active" aria-current="page">Order Confirmation</li>
            </ol>
        </nav>
        
        <div class="card mb-4">
            <div class="card-body text-center py-5">
                <i class="bi bi-check-circle-fill text-success" style="font-size: 4rem;"></i>
                <h1 class="mt-3">Thank You For Your Order!</h1>
                <p class="lead">Your order has been successfully placed.</p>
                <p class="mb-0">Order #<strong>${transactionId}</strong></p>
                <p>Total: <strong>$<fmt:formatNumber value="${total}" pattern="#,##0.00" /></strong></p>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-6 mx-auto text-center">
                <p>A confirmation email has been sent to your registered email address.</p>
                <p>You can track your order status in the <a href="${pageContext.request.contextPath}/orders">My Orders</a> section.</p>
                
                <div class="d-grid gap-2 mt-4">
                    <a href="${pageContext.request.contextPath}/orders" class="btn btn-outline-primary">
                        <i class="bi bi-box me-2"></i> View My Orders
                    </a>
                    <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">
                        <i class="bi bi-shop me-2"></i> Continue Shopping
                    </a>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>