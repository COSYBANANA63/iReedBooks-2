<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${book.title} - BookStore</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/books">Books</a></li>
                <li class="breadcrumb-item active" aria-current="page">${book.title}</li>
            </ol>
        </nav>
        
        <div class="row mt-4">
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${not empty book.coverImg}">
                        <img src="${book.coverImg}" class="img-fluid rounded" alt="${book.title}">
                    </c:when>
                    <c:otherwise>
                        <div class="bg-secondary text-white d-flex justify-content-center align-items-center rounded" style="height: 400px;">
                            <i class="bi bi-book" style="font-size: 8rem;"></i>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div class="col-md-8">
                <h1>${book.title}</h1>
                <p class="text-muted">By ${book.authors}</p>
                
                <div class="mb-3">
                    <c:forEach items="${genres}" var="genre">
                        <span class="badge bg-secondary me-1">${genre}</span>
                    </c:forEach>
                </div>
                
                <p>Publisher: ${book.publisher}</p>
                <p>Publication Date: <fmt:formatDate value="${book.publicationDate}" pattern="MMMM dd, yyyy" /></p>
                <p>ISBN: ${book.isbn}</p>
                
                <h3 class="text-primary mb-3">$<fmt:formatNumber value="${book.price}" pattern="#,##0.00" /></h3>
                
                <c:choose>
                    <c:when test="${book.inventory > 0}">
                        <p class="text-success"><i class="bi bi-check-circle"></i> In Stock (${book.inventory} available)</p>
                        
                        <form action="${pageContext.request.contextPath}/cart/add" method="post" class="d-flex align-items-center">
                            <input type="hidden" name="isbn" value="${book.isbn}">
                            <select name="quantity" class="form-select me-3" style="width: 80px;">
                                <c:forEach begin="1" end="${maxOrderQuantity}" var="i">
                                    <option value="${i}">${i}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-cart-plus"></i> Add to Cart
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p class="text-danger"><i class="bi bi-x-circle"></i> Out of Stock</p>
                        <button class="btn btn-secondary" disabled>
                            <i class="bi bi-cart-plus"></i> Add to Cart
                        </button>
                    </c:otherwise>
                </c:choose>
                
                <hr>
                
                <h4>Description</h4>
                <p>${book.description}</p>
            </div>
        </div>
        
        <div class="row mt-5">
            <div class="col-12">
                <h3>Customer Reviews</h3>
                
                <c:choose>
                    <c:when test="${empty reviews}">
                        <div class="alert alert-info">No reviews yet. Be the first to review this book!</div>
                    </c:when>
                    <c:otherwise>
                        <div class="list-group mt-3">
                            <c:forEach items="${reviews}" var="review">
                                <div class="list-group-item">
                                    <div class="d-flex justify-content-between">
                                        <h5>${review.firstName} ${review.lastName}</h5>
                                        <div>
                                            <c:forEach begin="1" end="5" var="star">
                                                <i class="bi ${star <= review.rating ? 'bi-star-fill text-warning' : 'bi-star'}"></i>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <p class="mb-0">${review.reviewText}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
                
                <c:if test="${not empty sessionScope.user}">
                    <div class="card mt-4">
                        <div class="card-header">
                            <h5>Write a Review</h5>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/review/add" method="post">
                                <input type="hidden" name="isbn" value="${book.isbn}">
                                
                                <div class="mb-3">
                                    <label for="rating" class="form-label">Rating</label>
                                    <select class="form-select" id="rating" name="rating" required>
                                        <option value="">Select rating</option>
                                        <option value="5">5 stars - Excellent</option>
                                        <option value="4">4 stars - Good</option>
                                        <option value="3">3 stars - Average</option>
                                        <option value="2">2 stars - Fair</option>
                                        <option value="1">1 star - Poor</option>
                                    </select>
                                </div>
                                
                                <div class="mb-3">
                                    <label for="reviewText" class="form-label">Review</label>
                                    <textarea class="form-control" id="reviewText" name="reviewText" rows="3" required></textarea>
                                </div>
                                
                                <button type="submit" class="btn btn-primary">Submit Review</button>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>