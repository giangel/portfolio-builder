<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access denied, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<div class="auth-page">
    <div class="auth-card card" style="text-align:center;">
        <h1>Access denied</h1>
        <p class="text-muted mb-md">You do not have permission to view this page.</p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/index.jsp">Go home</a>
    </div>
</div>
</body>
</html>