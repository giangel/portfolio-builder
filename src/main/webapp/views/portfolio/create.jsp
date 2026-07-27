<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Start a new portfolio, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container" style="max-width: 640px; margin: var(--space-lg) auto;">
    <h1>Start a new portfolio</h1>
    <p class="text-muted mb-md">Give it a title and pick a starting theme, you can change everything later.</p>

    <div class="card">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error"><span>${errorMessage}</span></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/portfolio/create">
            <div class="form-group">
                <label for="title">Portfolio title</label>
                <input type="text" id="title" name="title" value="${submittedTitle}" maxlength="150" required autofocus>
            </div>
            <div class="form-group">
                <label for="description">Short description</label>
                <textarea id="description" name="description" maxlength="500">${submittedDescription}</textarea>
                <p class="form-hint">A one or two sentence summary, optional, shown near the top of your portfolio.</p>
            </div>
            <div class="form-group">
                <label for="themeId">Starting theme</label>
                <select id="themeId" name="themeId" required>
                    <c:forEach var="theme" items="${themes}">
                        <option value="${theme.themeId}">${theme.themeName}, ${theme.description}</option>
                    </c:forEach>
                </select>
                <p class="form-hint">You can switch themes at any time from the builder.</p>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Create and open builder</button>
        </form>
    </div>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>