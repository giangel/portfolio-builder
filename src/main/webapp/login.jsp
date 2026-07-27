<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log in, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="auth-page">
    <div class="auth-card card">
        <h1>Welcome back</h1>
        <p class="auth-subtitle">Log in to keep building your story.</p>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error"><span>${errorMessage}</span></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <input type="hidden" name="redirectTo" value="${param.redirectTo}">
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" id="email" name="email" value="${submittedEmail}" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Log in</button>
        </form>

        <p class="auth-footer">New to Storyline? <a href="${pageContext.request.contextPath}/register.jsp">Create an account</a></p>
    </div>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>