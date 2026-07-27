<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your dashboard, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container">
    <div class="dashboard-header">
        <div>
            <h1>Your portfolios</h1>
            <p class="text-muted">Every story you are telling, in one place.</p>
        </div>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/portfolio/create">Start a new portfolio</a>
    </div>

    <div class="dashboard-stats">
        <div class="stat-pill">
            <span class="stat-number">${fn:length(portfolios)}</span>
            <span class="stat-label">Total</span>
        </div>
        <div class="stat-pill">
            <span class="stat-number">${publishedCount}</span>
            <span class="stat-label">Published</span>
        </div>
        <div class="stat-pill">
            <span class="stat-number">${draftCount}</span>
            <span class="stat-label">Drafts</span>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty portfolios}">
            <div class="empty-state">
                <h3>Your first chapter starts here</h3>
                <p class="text-muted mb-md">You have not created a portfolio yet. It only takes a couple of minutes to get something worth sharing.</p>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/portfolio/create">Create your first portfolio</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="story-cover-grid">
                <c:forEach var="portfolio" items="${portfolios}">
                    <div class="story-cover">
                        <div class="story-cover-banner">
                            <span class="story-cover-status ${portfolio.published ? 'status-published' : 'status-draft'}">
                                <c:choose>
                                    <c:when test="${portfolio.published}">Published</c:when>
                                    <c:otherwise>Draft</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="story-cover-body">
                            <h3>${portfolio.title}</h3>
                            <p class="story-cover-meta">${portfolio.themeName} theme &middot; /${portfolio.slug}</p>
                            <div class="story-cover-actions">
                                <a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}">Open builder</a>
                                <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/portfolio/edit?portfolioId=${portfolio.portfolioId}">Edit details</a>
                                <c:if test="${portfolio.published}">
                                    <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/view/${portfolio.slug}" target="_blank" rel="noopener">View live</a>
                                    <form method="post" action="${pageContext.request.contextPath}/portfolio/unpublish" style="display:inline;">
                                        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                                        <button type="submit" class="btn btn-sm btn-secondary">Unpublish</button>
                                    </form>
                                </c:if>
                                <c:if test="${!portfolio.published}">
                                    <form method="post" action="${pageContext.request.contextPath}/portfolio/publish" style="display:inline;">
                                        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                                        <button type="submit" class="btn btn-sm btn-sage">Publish</button>
                                    </form>
                                </c:if>
                                <form method="post" action="${pageContext.request.contextPath}/portfolio/delete" data-confirm="Delete this portfolio permanently? This cannot be undone." style="display:inline;">
                                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>