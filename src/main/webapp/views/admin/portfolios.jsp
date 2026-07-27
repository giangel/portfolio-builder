<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All portfolios, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container">
    <h1>All portfolios</h1>
    <p class="text-muted mb-md">Read-only oversight. Editing content stays with each portfolio's owner.</p>

    <ul class="entry-list">
        <c:forEach var="portfolio" items="${portfolios}">
            <li class="entry-item">
                <div class="entry-body">
                    <div class="entry-title">${portfolio.title}</div>
                    <div class="entry-subtitle">${portfolio.themeName} theme, ${portfolio.published ? 'published' : 'draft'}, /${portfolio.slug}</div>
                </div>
                <div class="entry-actions">
                    <c:if test="${portfolio.published}">
                        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/view/${portfolio.slug}" target="_blank" rel="noopener">View</a>
                    </c:if>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>