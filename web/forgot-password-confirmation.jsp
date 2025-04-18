<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Password Reset Confirmation - BookStore</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
</head>
<body>
    <jsp:include page="/header.jsp" />
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header">
                        <h2 class="text-center">Password Reset Sent</h2>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-success" role="alert">
                            ${message}
                        </div>
                        
                        <!-- This is just for demonstration purposes - in a real app, you'd send an email -->
                        <c:if test="${not empty resetLink}">
                            <div class="alert alert-info mt-3" role="alert">
                                <p><strong>Demo only:</strong>Please, use the link below to reset your accounts password.</p>
                                <p>Reset link: <a href="${resetLink}">${resetLink}</a></p>
                            </div>
                        </c:if>
                        
                        <div class="mt-3 text-center">
                            <p><a href="${pageContext.request.contextPath}/auth/login">Return to login</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>