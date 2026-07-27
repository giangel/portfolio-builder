<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin dashboard, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container">
    <div class="dashboard-header">
        <div>
            <h1>Platform overview</h1>
            <p class="text-muted">A snapshot of everyone using Storyline.</p>
        </div>
    </div>

    <div class="dashboard-stats">
        <div class="stat-pill"><span class="stat-number">${statistics.totalUsers}</span><span class="stat-label">Total users</span></div>
        <div class="stat-pill"><span class="stat-number">${statistics.totalPortfolioUsers}</span><span class="stat-label">Portfolio owners</span></div>
        <div class="stat-pill"><span class="stat-number">${statistics.totalPortfolios}</span><span class="stat-label">Total portfolios</span></div>
        <div class="stat-pill"><span class="stat-number">${statistics.publishedPortfolios}</span><span class="stat-label">Published</span></div>
        <div class="stat-pill"><span class="stat-number">${statistics.unpublishedPortfolios}</span><span class="stat-label">Unpublished</span></div>
    </div>

    <div class="flex gap-sm mb-md">
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/users">Manage users</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/portfolios">View portfolios</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/themes">Manage themes</a>
    </div>

    <div class="card">
        <h3>Recent activity</h3>
        <c:choose>
            <c:when test="${empty recentActivity}">
                <p class="text-muted">No activity recorded yet.</p>
            </c:when>
            <c:otherwise>
                <ul class="entry-list">
                    <c:forEach var="log" items="${recentActivity}">
                        <li class="entry-item">
                            <div class="entry-body">
                                <div class="entry-title">${log.action}</div>
                                <div class="entry-subtitle">${log.details}, ${log.createdAt}</div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>