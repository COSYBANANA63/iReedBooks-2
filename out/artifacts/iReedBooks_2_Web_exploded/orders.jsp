<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders - iReedBooks</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">My Orders</li>
            </ol>
        </nav>
        
        <div class="row">
            <!-- Left sidebar / menu -->
            <div class="col-md-3 mb-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Account</h5>
                    </div>
                    <div class="list-group list-group-flush">
                        <a href="${pageContext.request.contextPath}/profile" class="list-group-item list-group-item-action">
                            <i class="bi bi-person me-2"></i> My Profile
                        </a>
                        <a href="${pageContext.request.contextPath}/orders" class="list-group-item list-group-item-action active">
                            <i class="bi bi-box me-2"></i> My Orders
                        </a>
                        <a href="${pageContext.request.contextPath}/auth/change-password" class="list-group-item list-group-item-action">
                            <i class="bi bi-key me-2"></i> Change Password
                        </a>
                        <a href="${pageContext.request.contextPath}/auth/logout" class="list-group-item list-group-item-action text-danger">
                            <i class="bi bi-box-arrow-right me-2"></i> Logout
                        </a>
                    </div>
                </div>
            </div>
            
            <!-- Main content -->
            <div class="col-md-9">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">My Orders</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${empty orders}">
                                <div class="alert alert-info">
                                    <i class="bi bi-info-circle me-2"></i> You haven't placed any orders yet.
                                    <a href="${pageContext.request.contextPath}/books" class="alert-link">Start shopping</a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="accordion" id="orderAccordion">
                                    <c:forEach items="${orders}" var="order" varStatus="status">
                                        <div class="accordion-item mb-3">
                                            <h2 class="accordion-header" id="heading${order.transactionId}">
                                                <button class="accordion-button ${status.first ? '' : 'collapsed'}" 
                                                    type="button" 
                                                    data-bs-toggle="collapse" 
                                                    data-bs-target="#collapse${order.transactionId}" 
                                                    aria-expanded="${status.first ? 'true' : 'false'}" 
                                                    aria-controls="collapse${order.transactionId}">
                                                <div class="d-flex justify-content-between align-items-center w-100">
                                                    <span>
                                                        <strong>Order #${order.transactionId}</strong>
                                                    </span>
                                                    <span class="ms-3 text-secondary">
                                                        <fmt:formatDate value="${order.purchaseDate}" pattern="MMM dd, yyyy" />
                                                    </span>
                                                    <span class="ms-auto badge bg-success">
                                                        $<fmt:formatNumber value="${order.totalPrice}" pattern="#,##0.00" />
                                                    </span>
                                                </div>
                                            </button>
                                            </h2>
                                            <div id="collapse${order.transactionId}" 
                                                 class="accordion-collapse collapse ${status.first ? 'show' : ''}" 
                                                 aria-labelledby="heading${order.transactionId}" 
                                                 data-bs-parent="#orderAccordion">
                                                <div class="accordion-body">
                                                    <div class="table-responsive">
                                                        <table class="table">
                                                            <thead>
                                                                <tr>
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
                                                                            <a href="${pageContext.request.contextPath}/book/${item.isbn}" class="text-decoration-none">
                                                                                ${item.title}
                                                                            </a>
                                                                        </td>
                                                                        <td>${item.quantity}</td>
                                                                        <td>$<fmt:formatNumber value="${item.price}" pattern="#,##0.00" /></td>
                                                                        <td>$<fmt:formatNumber value="${item.price * item.quantity}" pattern="#,##0.00" /></td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                    <div class="text-end mt-3">
                                                        <a href="${pageContext.request.contextPath}/track-order/${order.transactionId}" class="btn btn-outline-primary btn-sm">
                                                            <i class="bi bi-truck me-1"></i> Track Order
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>