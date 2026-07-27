<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create your account, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="auth-page">
    <div class="auth-card card">
        <h1>Start your story</h1>
        <p class="auth-subtitle">Create an account to build your first portfolio.</p>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error"><span>${errorMessage}</span></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register" id="register-form">
            <div class="form-group">
                <label for="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" value="${submittedFullName}" required autofocus>
            </div>
            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" id="email" name="email" value="${submittedEmail}" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" minlength="8" required>
                <p class="form-hint">At least 8 characters, including a letter and a number.</p>
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" minlength="8" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Create account</button>
        </form>

        <p class="auth-footer">Already have an account? <a href="${pageContext.request.contextPath}/login.jsp">Log in</a></p>
    </div>
</div>

<script>
    document.getElementById('register-form').addEventListener('submit', function (event) {
        var password = document.getElementById('password').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        if (password !== confirmPassword) {
            event.preventDefault();
            window.alert('Password and confirmation password do not match.');
        }
    });
</script>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>