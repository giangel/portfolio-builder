<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage themes, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container">
    <h1>Manage themes</h1>
    <p class="text-muted mb-md">Deactivating a theme hides it from the theme picker for new selections.</p>

    <ul class="entry-list">
        <c:forEach var="theme" items="${themes}">
            <li class="entry-item">
                <div class="entry-body">
                    <div class="entry-title">${theme.themeName}</div>
                    <div class="entry-subtitle">${theme.description}</div>
                </div>
                <div class="entry-actions">
                    <form method="post" action="${pageContext.request.contextPath}/admin/themes">
                        <input type="hidden" name="themeId" value="${theme.themeId}">
                        <c:choose>
                            <c:when test="${theme.active}">
                                <input type="hidden" name="active" value="false">
                                <button type="submit" class="btn btn-sm btn-danger">Deactivate</button>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="active" value="true">
                                <button type="submit" class="btn btn-sm btn-sage">Activate</button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </li>
        </c:forEach>
    </ul>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>