<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Details - iReedBooks</title>
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
                <li class="breadcrumb-item active" aria-current="page">Payment Details</li>
            </ol>
        </nav>
        
        <div class="row">
            <!-- Order Summary -->
            <div class="col-md-4 order-md-2 mb-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Order Summary</h5>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <c:forEach items="${cartItems}" var="item">
                                <li class="list-group-item d-flex justify-content-between lh-sm">
                                    <div>
                                        <h6 class="my-0">${item.title}</h6>
                                        <small class="text-muted">Qty: ${item.quantity}</small>
                                    </div>
                                    <span class="text-muted">$<fmt:formatNumber value="${item.total}" pattern="#,##0.00" /></span>
                                </li>
                            </c:forEach>
                            <li class="list-group-item d-flex justify-content-between">
                                <span>Total</span>
                                <strong>$<fmt:formatNumber value="${total}" pattern="#,##0.00" /></strong>
                            </li>
                        </ul>
                    </div>
                </div>
                
                <!-- Payment success/failure toggle switch -->
                <div class="card mt-3">
                    <div class="card-body">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" id="paymentSuccessToggle" checked>
                            <label class="form-check-label" for="paymentSuccessToggle">Simulate Successful Payment</label>
                        </div>
                        <small class="text-muted">For demonstration purposes only</small>
                    </div>
                </div>
            </div>
            
            <!-- Payment Form -->
            <div class="col-md-8 order-md-1">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Payment Details</h5>
                    </div>
                    <div class="card-body">
                        <form id="paymentForm" action="${pageContext.request.contextPath}/cart/process-payment" method="post">
                            <!-- Billing Information -->
                            <h6 class="mb-3">Billing Information</h6>
                            <div class="row g-3 mb-4">
                                <div class="col-md-6">
                                    <label for="firstName" class="form-label">First name</label>
                                    <input type="text" class="form-control" id="firstName" name="firstName" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="lastName" class="form-label">Last name</label>
                                    <input type="text" class="form-control" id="lastName" name="lastName" required>
                                </div>
                                <div class="col-12">
                                    <label for="address" class="form-label">Address</label>
                                    <input type="text" class="form-control" id="address" name="address" required>
                                </div>
                                <div class="col-md-5">
                                    <label for="city" class="form-label">City</label>
                                    <input type="text" class="form-control" id="city" name="city" required>
                                </div>
                                <div class="col-md-4">
                                    <label for="state" class="form-label">State</label>
                                    <input type="text" class="form-control" id="state" name="state" required>
                                </div>
                                <div class="col-md-3">
                                    <label for="zipCode" class="form-label">Zip</label>
                                    <input type="text" class="form-control" id="zipCode" name="zipCode" required>
                                </div>
                            </div>
                            
                            <!-- Card Information -->
                            <h6 class="mb-3">Card Information</h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="cardName" class="form-label">Name on card</label>
                                    <input type="text" class="form-control" id="cardName" name="cardName" required>
                                </div>
                                <div class="col-md-6">
                                    <label for="cardNumber" class="form-label">Card number</label>
                                    <input type="text" class="form-control" id="cardNumber" name="cardNumber" 
                                           placeholder="XXXX XXXX XXXX XXXX" required>
                                </div>
                                <div class="col-md-4">
                                    <label for="expiryMonth" class="form-label">Expiry month</label>
                                    <select class="form-select" id="expiryMonth" name="expiryMonth" required>
                                        <option value="">Choose...</option>
                                        <c:forEach begin="1" end="12" var="month">
                                            <option value="${month}">${month}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label for="expiryYear" class="form-label">Expiry year</label>
                                    <select class="form-select" id="expiryYear" name="expiryYear" required>
                                        <option value="">Choose...</option>
                                        <c:set var="currentYear" value="<%= java.time.Year.now().getValue() %>" />
                                        <c:forEach begin="${currentYear}" end="${currentYear + 10}" var="year">
                                            <option value="${year}">${year}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label for="cvv" class="form-label">CVV</label>
                                    <input type="text" class="form-control" id="cvv" name="cvv" required
                                           placeholder="XXX">
                                </div>
                            </div>
                            
                            <input type="hidden" id="paymentSuccess" name="paymentSuccess" value="true">
                            
                            <hr class="my-4">
                            
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Back to Cart
                                </a>
                                <button class="btn btn-primary" type="submit">
                                    <i class="bi bi-credit-card me-2"></i> Complete Payment
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const toggleSwitch = document.getElementById('paymentSuccessToggle');
            const hiddenInput = document.getElementById('paymentSuccess');
            
            toggleSwitch.addEventListener('change', function() {
                hiddenInput.value = this.checked ? 'true' : 'false';
            });
        });
    </script>
</body>
</html>