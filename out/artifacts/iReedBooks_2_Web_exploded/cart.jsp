<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart - iReedBooks</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">Shopping Cart</li>
            </ol>
        </nav>
        
        <h1 class="mb-4">Your Shopping Cart</h1>
        
        <c:choose>
            <c:when test="${empty cartItems}">
                <div class="alert alert-info">
                    <i class="bi bi-cart-x"></i> Your cart is empty.
                    <a href="${pageContext.request.contextPath}/books" class="alert-link">Continue shopping</a>
                </div>
            </c:when>
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/cart/update" method="post">
                    <div class="table-responsive">
                        <table class="table table-striped align-middle">
                            <thead>
                                <tr>
                                    <th scope="col" style="width: 60px;"></th>
                                    <th scope="col">Book</th>
                                    <th scope="col" style="width: 150px;">Price</th>
                                    <th scope="col" style="width: 150px;">Quantity</th>
                                    <th scope="col" style="width: 150px;">Total</th>
                                    <th scope="col" style="width: 80px;"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cartItems}" var="item">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty item.coverImg}">
                                                    <img src="${item.coverImg}" class="img-thumbnail" alt="${item.title}" style="max-width: 50px;">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="bg-secondary text-white d-flex justify-content-center align-items-center rounded" style="width: 50px; height: 50px;">
                                                        <i class="bi bi-book"></i>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/book/${item.isbn}" class="text-decoration-none">
                                                ${item.title}
                                            </a>
                                        </td>
                                        <td>$<fmt:formatNumber value="${item.price}" pattern="#,##0.00" /></td>
                                        <td>
                                            <input type="hidden" name="isbn" value="${item.isbn}">
                                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="10" class="form-control" style="max-width: 80px;">
                                        </td>
                                        <td>$<fmt:formatNumber value="${item.total}" pattern="#,##0.00" /></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/cart/remove?isbn=${item.isbn}" class="btn btn-sm btn-outline-danger">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="4" class="text-end fw-bold">Subtotal:</td>
                                    <td class="fw-bold">$<fmt:formatNumber value="${total}" pattern="#,##0.00" /></td>
                                    <td></td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    
                    <div class="d-flex justify-content-between mt-4">
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-outline-secondary">
                            <i class="bi bi-arrow-left"></i> Continue Shopping
                        </a>
                        <div>
                            <button type="submit" class="btn btn-outline-primary me-2">
                                <i class="bi bi-arrow-clockwise"></i> Update Cart
                            </button>
                            <a href="${pageContext.request.contextPath}/cart/payment" class="btn btn-success">
                                <i class="bi bi-credit-card"></i> Proceed to Checkout
                            </a>
                        </div>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
                
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>