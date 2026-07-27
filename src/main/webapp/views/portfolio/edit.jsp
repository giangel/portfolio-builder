<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit portfolio details, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container" style="max-width: 640px; margin: var(--space-lg) auto;">
    <h1>Edit portfolio details</h1>
    <p class="text-muted mb-md">Title, description, and theme. Content like skills and projects live in the builder.</p>

    <div class="card">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error"><span>${errorMessage}</span></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/portfolio/edit">
            <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
            <div class="form-group">
                <label for="title">Portfolio title</label>
                <input type="text" id="title" name="title" value="${portfolio.title}" maxlength="150" required autofocus>
            </div>
            <div class="form-group">
                <label for="description">Short description</label>
                <textarea id="description" name="description" maxlength="500">${portfolio.description}</textarea>
            </div>
            <div class="form-group">
                <label for="themeId">Theme</label>
                <select id="themeId" name="themeId" required>
                    <c:forEach var="theme" items="${themes}">
                        <option value="${theme.themeId}" ${theme.themeId == portfolio.themeId ? 'selected' : ''}>
                            ${theme.themeName}, ${theme.description}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="flex gap-sm">
                <button type="submit" class="btn btn-primary">Save changes</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/dashboard">Cancel</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>