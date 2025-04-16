<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Track Order - iReedBooks</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/orders">My Orders</a></li>
                <li class="breadcrumb-item active" aria-current="page">Track Order #${order.transactionId}</li>
            </ol>
        </nav>
        
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="card-title mb-0">Order #${order.transactionId} Details</h5>
            </div>
            <div class="card-body">
                <div class="row mb-4">
                    <div class="col-md-6">
                        <h6>Order Information</h6>
                        <p><strong>Order Date:</strong> <fmt:formatDate value="${order.purchaseDate}" pattern="MMMM dd, yyyy" /></p>
                        <p><strong>Order Total:</strong> $<fmt:formatNumber value="${order.totalPrice}" pattern="#,##0.00" /></p>
                        <p><strong>Status:</strong> <span class="badge bg-success">Completed</span></p>
                    </div>
                </div>
                
                <h6>Order Progress</h6>
                <div class="progress-track mb-5">
                    <ul class="progressbar">
                        <li class="active text-center">Order Placed<br><small><fmt:formatDate value="${order.purchaseDate}" pattern="MMM dd" /></small></li>
                        <li class="active text-center">Processing<br><small><fmt:formatDate value="${order.purchaseDate}" pattern="MMM dd" /></small></li>
                        <li class="text-center">Shipped<br><small>Estimated<br><fmt:formatDate value="${shipDate}" pattern="MMM dd" /></small></li>
                        <li class="text-center">Delivered<br><small>Estimated<br><fmt:formatDate value="${deliveryDate}" pattern="MMM dd" /></small></li>
                    </ul>
                </div>
            </div>
        </div>
        
        <!-- Moved order items to a separate card for better spacing -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="card-title mb-0">Order Items</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th style="width: 100px;"></th>
                                <th>Book</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${order.items}" var="item">
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty item.coverImg}">
                                                <img src="${item.coverImg}" class="img-thumbnail" alt="${item.title}" style="max-width: 80px;">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="bg-secondary text-white d-flex justify-content-center align-items-center rounded" style="width: 80px; height: 100px;">
                                                    <i class="bi bi-book" style="font-size: 2rem;"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><a href="${pageContext.request.contextPath}/book/${item.isbn}">${item.title}</a></td>
                                    <td>${item.quantity}</td>
                                    <td>$<fmt:formatNumber value="${item.price}" pattern="#,##0.00" /></td>
                                    <td>$<fmt:formatNumber value="${item.price * item.quantity}" pattern="#,##0.00" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="4" class="text-end fw-bold">Total:</td>
                                <td class="fw-bold">$<fmt:formatNumber value="${order.totalPrice}" pattern="#,##0.00" /></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                
                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary">
                        <i class="bi bi-arrow-left me-2"></i> Back to My Orders
                    </a>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <style>
        .progress-track {
            margin: 0 10%;
        }
        .progressbar {
            counter-reset: step;
            padding: 0;
            display: flex;
            justify-content: space-between;
        }
        .progressbar li {
            list-style-type: none;
            width: 25%;
            float: left;
            font-size: 14px;
            position: relative;
            text-align: center;
            color: #7d7d7d;
        }
        .progressbar li:before {
            width: 40px;
            height: 40px;
            content: counter(step);
            counter-increment: step;
            line-height: 40px;
            border: 2px solid #7d7d7d;
            display: block;
            text-align: center;
            margin: 0 auto 10px auto;
            border-radius: 50%;
            background-color: white;
        }
        .progressbar li:after {
            width: 100%;
            height: 2px;
            content: '';
            position: absolute;
            background-color: #7d7d7d;
            top: 20px;
            left: -50%;
            z-index: -1;
        }
        .progressbar li:first-child:after {
            content: none;
        }
        .progressbar li.active {
            color: green;
        }
        .progressbar li.active:before {
            border-color: green;
            background-color: green;
            color: white;
        }
        .progressbar li.active + li:after {
            background-color: green;
        }
    </style>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>