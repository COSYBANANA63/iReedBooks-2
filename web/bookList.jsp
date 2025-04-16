<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BookStore - Browse Books</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-3">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Filters</h5>
                    </div>
                    <div class="card-body">
                        <h6>Genres</h6>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="${pageContext.request.contextPath}/books" class="text-decoration-none">All Genres</a>
                            </li>
                            <c:forEach items="${genres}" var="genre">
                                <li class="list-group-item">
                                    <a href="${pageContext.request.contextPath}/books?genre=${genre}" 
                                       class="text-decoration-none ${param.genre eq genre ? 'fw-bold' : ''}">
                                        ${genre}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            
            <div class="col-md-9">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2>Books</h2>
                    <div>
                        <c:if test="${not empty param.genre}">
                            <span class="badge bg-primary">${param.genre}</span>
                        </c:if>
                        <c:if test="${not empty param.search}">
                            <span class="badge bg-info">Search: ${param.search}</span>
                        </c:if>
                    </div>
                </div>
                
                <c:choose>
                    <c:when test="${empty books}">
                        <div class="alert alert-info">
                            No books found. Please try different search criteria.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="row row-cols-1 row-cols-md-3 g-4">
                            <c:forEach items="${books}" var="book">
                                <div class="col">
                                    <div class="card h-100">
                                        <c:choose>
                                            <c:when test="${not empty book.coverImg}">
                                                <img src="${book.coverImg}" class="card-img-top" alt="${book.title}" style="height: 250px; object-fit: cover;">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="bg-secondary text-white d-flex justify-content-center align-items-center" style="height: 250px;">
                                                    <i class="bi bi-book" style="font-size: 5rem;"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        
                                        <div class="card-body">
                                            <h5 class="card-title">${book.title}</h5>
                                            <p class="card-text text-muted">By ${book.authors}</p>
                                            <p class="card-text fw-bold">$<fmt:formatNumber value="${book.price}" pattern="#,##0.00" /></p>
                                            <div class="d-grid">
                                                <a href="${pageContext.request.contextPath}/book/${book.isbn}" class="btn btn-outline-primary">View Details</a>
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
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>