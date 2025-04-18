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
    <style>
        .card-icon {
            font-size: 1.5rem;
            margin-left: 10px;
            vertical-align: middle;
        }
        .invalid-feedback {
            display: none;
        }
        input.is-invalid + .invalid-feedback {
            display: block;
        }
        .card-brand {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            margin-left: 8px;
            font-size: 0.8rem;
            font-weight: bold;
        }
        .card-brand.visa {
            background-color: #1A1F71;
            color: white;
        }
        .card-brand.mastercard {
            background-color: #EB001B;
            color: white;
        }
        .card-brand.amex {
            background-color: #006FCF;
            color: white;
        }
        .card-brand.discover {
            background-color: #FF6600;
            color: white;
        }
        .card-brand.diners {
            background-color: #0079BE;
            color: white;
        }
        .card-brand.jcb {
            background-color: #00873C;
            color: white;
        }
        .card-brand.verve {
            background-color: #3399cc;
            color: white;
        }
    </style>
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
                        <form id="paymentForm" action="${pageContext.request.contextPath}/cart/process-payment" method="post" novalidate>
                            <!-- Billing Information -->
                            <h6 class="mb-3">Billing Information</h6>
                            <div class="row g-3 mb-4">
                                <div class="col-md-6">
                                    <label for="firstName" class="form-label">First name</label>
                                    <input type="text" class="form-control" id="firstName" name="firstName" required
                                           pattern="[A-Za-z -']+" minlength="2" maxlength="50">
                                    <div class="invalid-feedback">
                                        Please enter a valid first name.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="lastName" class="form-label">Last name</label>
                                    <input type="text" class="form-control" id="lastName" name="lastName" required
                                           pattern="[A-Za-z -']+" minlength="2" maxlength="50">
                                    <div class="invalid-feedback">
                                        Please enter a valid last name.
                                    </div>
                                </div>
                                <div class="col-12">
                                    <label for="address" class="form-label">Address</label>
                                    <input type="text" class="form-control" id="address" name="address" required
                                           minlength="5" maxlength="100">
                                    <div class="invalid-feedback">
                                        Please enter your shipping address.
                                    </div>
                                </div>
                                <div class="col-md-5">
                                    <label for="city" class="form-label">City</label>
                                    <input type="text" class="form-control" id="city" name="city" required
                                           pattern="[A-Za-z -']+" minlength="2" maxlength="50">
                                    <div class="invalid-feedback">
                                        Please enter a valid city.
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <label for="state" class="form-label">State</label>
                                    <input type="text" class="form-control" id="state" name="state" required
                                           pattern="[A-Za-z -]+" minlength="2" maxlength="50">
                                    <div class="invalid-feedback">
                                        Please enter a valid state.
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label for="zipCode" class="form-label">Zip</label>
                                    <input type="text" class="form-control" id="zipCode" name="zipCode" required
                                           pattern="^\d{5}(-\d{4})?$">
                                    <div class="invalid-feedback">
                                        Please enter a valid zip code (e.g., 12345 or 12345-6789).
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Card Information -->
                            <h6 class="mb-3">Card Information</h6>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="cardName" class="form-label">Name on card</label>
                                    <input type="text" class="form-control" id="cardName" name="cardName" required
                                           pattern="[A-Za-z -']+" minlength="2" maxlength="100">
                                    <div class="invalid-feedback">
                                        Please enter the name as it appears on your card.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="cardNumber" class="form-label">Card number</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="cardNumber" name="cardNumber" 
                                               placeholder="XXXX XXXX XXXX XXXX" required maxlength="19">
                                        <span class="input-group-text" id="cardBrandDisplay"></span>
                                    </div>
                                    <div class="invalid-feedback">
                                        Please enter a valid credit card number.
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <label for="expiryMonth" class="form-label">Expiry month</label>
                                    <select class="form-select" id="expiryMonth" name="expiryMonth" required>
                                        <option value="">Choose...</option>
                                        <c:forEach begin="1" end="12" var="month">
                                            <option value="${month < 10 ? '0' : ''}${month}">${month < 10 ? '0' : ''}${month}</option>
                                        </c:forEach>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please select an expiry month.
                                    </div>
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
                                    <div class="invalid-feedback">
                                        Please select an expiry year.
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <label for="cvv" class="form-label">CVV</label>
                                    <input type="text" class="form-control" id="cvv" name="cvv" required
                                           placeholder="XXX" maxlength="4" pattern="^\d{3,4}$">
                                    <div class="invalid-feedback">
                                        Please enter a valid CVV (3-4 digits).
                                    </div>
                                </div>
                            </div>
                            
                            <input type="hidden" id="paymentSuccess" name="paymentSuccess" value="true">
                            
                            <hr class="my-4">
                            
                            <div class="d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Back to Cart
                                </a>
                                <button class="btn btn-primary" type="submit" id="submitBtn">
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
            const form = document.getElementById('paymentForm');
            const submitBtn = document.getElementById('submitBtn');
            const cardNumberInput = document.getElementById('cardNumber');
            const cardBrandDisplay = document.getElementById('cardBrandDisplay');
            const cvvInput = document.getElementById('cvv');
            const expiryMonth = document.getElementById('expiryMonth');
            const expiryYear = document.getElementById('expiryYear');
            
            // Card type regex patterns
            const cardPatterns = {
                visa: /^4[0-9]{12}(?:[0-9]{3})?$/,
                mastercard: /^5[1-5][0-9]{14}$|^2(?:2(?:2[1-9]|[3-9][0-9])|[3-6][0-9][0-9]|7(?:[01][0-9]|20))[0-9]{12}$/,
                amex: /^3[47][0-9]{13}$/,
                discover: /^6(?:011|5[0-9]{2})[0-9]{12}$/,
                diners: /^3(?:0[0-5]|[68][0-9])[0-9]{11}$/,
                jcb: /^(?:2131|1800|35[0-9]{3})[0-9]{11}$/,
                verve: /^506(?:099|1[0-9][0-9]|2[0-4][0-9]|25[0-5])[0-9]{10}$|^650[0-9]{13}$/
            };
            
            // Format card number with spaces
            cardNumberInput.addEventListener('input', function(e) {
                // Remove non-digits
                let value = this.value.replace(/\D/g, '');
                
                // Add spaces after every 4 digits
                if (value.length > 0) {
                    value = value.match(new RegExp('.{1,4}', 'g')).join(' ');
                }
                
                // Update input value
                this.value = value;
                
                // Detect and display card type
                detectCardType(this.value.replace(/\s/g, ''));
                
                // Set CVV max length based on card type (AMEX = 4, others = 3)
                const isAmex = this.value.replace(/\s/g, '').match(cardPatterns.amex);
                cvvInput.setAttribute('maxlength', isAmex ? '4' : '3');
                cvvInput.setAttribute('pattern', isAmex ? '^\\d{4}$' : '^\\d{3}$');
                
                // Validate the field
                validateField(this);
            });
            
            // Detect card type and display brand
            function detectCardType(cardNumber) {
                // Remove any existing brand classes
                cardBrandDisplay.className = 'input-group-text';
                cardBrandDisplay.textContent = '';
                
                if (!cardNumber) return;
                
                for (const [brand, pattern] of Object.entries(cardPatterns)) {
                    if (pattern.test(cardNumber)) {
                        const brandSpan = document.createElement('span');
                        brandSpan.className = `card-brand ${brand}`;
                        brandSpan.textContent = brand.charAt(0).toUpperCase() + brand.slice(1);
                        cardBrandDisplay.appendChild(brandSpan);
                        return;
                    }
                }
            }
            
            // Validate card number using Luhn algorithm
            function validateCardNumber(cardNumber) {
                // Remove spaces and non-digits
                cardNumber = cardNumber.replace(/\D/g, '');
                
                if (cardNumber.length < 13 || cardNumber.length > 19) return false;
                
                let sum = 0;
                let shouldDouble = false;
                
                // Loop through values starting from the right
                for (let i = cardNumber.length - 1; i >= 0; i--) {
                    let digit = parseInt(cardNumber.charAt(i));
                    
                    if (shouldDouble) {
                        digit *= 2;
                        if (digit > 9) digit -= 9;
                    }
                    
                    sum += digit;
                    shouldDouble = !shouldDouble;
                }
                
                return sum % 10 === 0;
            }
            
            // Validate credit card expiry date
            function validateExpiry() {
                const month = parseInt(expiryMonth.value);
                const year = parseInt(expiryYear.value);
                
                if (!month || !year) return false;
                
                const expiryDate = new Date(year, month);
                const currentDate = new Date();
                currentDate.setDate(1); // First day of current month
                
                return expiryDate > currentDate;
            }
            
            // Field validation function
            function validateField(field) {
                const isValid = field.checkValidity();
                
                // Special validation for card number
                if (field.id === 'cardNumber') {
                    const cardNumber = field.value.replace(/\s/g, '');
                    const matchesPattern = Object.values(cardPatterns).some(pattern => pattern.test(cardNumber));
                    const passesLuhn = validateCardNumber(cardNumber);
                    
                    if (!matchesPattern || !passesLuhn) {
                        field.setCustomValidity('Please enter a valid credit card number');
                        field.classList.add('is-invalid');
                        return false;
                    } else {
                        field.setCustomValidity('');
                    }
                }
                
                // Special validation for CVV
                if (field.id === 'cvv') {
                    const cardNumber = cardNumberInput.value.replace(/\s/g, '');
                    const isAmex = cardPatterns.amex.test(cardNumber);
                    const cvvLength = field.value.length;
                    
                    if ((isAmex && cvvLength !== 4) || (!isAmex && cvvLength !== 3)) {
                        field.setCustomValidity('Please enter a valid CVV code');
                        field.classList.add('is-invalid');
                        return false;
                    } else {
                        field.setCustomValidity('');
                    }
                }
                
                // Update field styling based on validation
                if (isValid) {
                    field.classList.remove('is-invalid');
                    field.classList.add('is-valid');
                } else {
                    field.classList.remove('is-valid');
                    field.classList.add('is-invalid');
                }
                
                return isValid;
            }
            
            // Add input validation to all required fields
            form.querySelectorAll('input[required], select[required]').forEach(field => {
                field.addEventListener('blur', () => validateField(field));
                field.addEventListener('input', () => validateField(field));
                field.addEventListener('change', () => validateField(field));
            });
            
            // Payment success/failure toggle
            toggleSwitch.addEventListener('change', function() {
                hiddenInput.value = this.checked ? 'true' : 'false';
            });
            
            // Form submission
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                
                let isFormValid = true;
                
                // Validate all fields
                form.querySelectorAll('input[required], select[required]').forEach(field => {
                    if (!validateField(field)) {
                        isFormValid = false;
                    }
                });
                
                // Validate expiry date
                if (!validateExpiry()) {
                    expiryMonth.classList.add('is-invalid');
                    expiryYear.classList.add('is-invalid');
                    isFormValid = false;
                }
                
                if (isFormValid) {
                    form.submit();
                } else {
                    // Scroll to the first invalid field
                    const firstInvalid = form.querySelector('.is-invalid');
                    if (firstInvalid) {
                        firstInvalid.focus();
                        firstInvalid.scrollIntoView({ behavior: 'smooth', block: 'center' });
                    }
                }
            });
            
            // Format CVV to numbers only
            cvvInput.addEventListener('input', function() {
                this.value = this.value.replace(/\D/g, '');
                validateField(this);
            });
        });
    </script>
</body>
</html>