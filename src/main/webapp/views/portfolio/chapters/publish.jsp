<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Final chapter</span>
    <h2>Publish</h2>
    <p class="text-muted">Choose which sections appear and in what order, then share your story with the world.</p>
</div>

<div class="publish-status-panel">
    <h3 class="mb-0">
        <c:choose>
            <c:when test="${portfolio.published}">Your portfolio is live</c:when>
            <c:otherwise>Your portfolio is not published yet</c:otherwise>
        </c:choose>
    </h3>
    <c:if test="${portfolio.published}">
        <div class="publish-url-box">${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}/view/${portfolio.slug}</div>
    </c:if>
    <div class="flex gap-sm">
        <c:choose>
            <c:when test="${portfolio.published}">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/unpublish">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <button type="submit" class="btn btn-secondary">Unpublish</button>
                </form>
            </c:when>
            <c:otherwise>
                <form method="post" action="${pageContext.request.contextPath}/portfolio/publish">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <button type="submit" class="btn btn-sage">Publish now</button>
                </form>
            </c:otherwise>
        </c:choose>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/portfolio/preview?portfolioId=${portfolio.portfolioId}" target="_blank" rel="noopener">Read your story</a>
    </div>
</div>

<h3 class="mb-sm">Section order and visibility</h3>
<ul class="section-order-list" id="section-order-list" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="section" items="${sections}">
        <li class="section-order-item" data-section-id="${section.sectionId}">
            <span class="drag-handle">&#8942;&#8942;</span>
            <span class="section-name">${section.sectionType}</span>
            <form method="post" action="${pageContext.request.contextPath}/portfolio/section-order">
                <input type="hidden" name="action" value="toggle">
                <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                <input type="hidden" name="sectionId" value="${section.sectionId}">
                <input type="hidden" name="enabled" value="${section.enabled}">
                <label class="toggle-switch">
                    <input type="checkbox" class="section-visibility-toggle" ${section.enabled ? 'checked' : ''}>
                    <span class="toggle-slider"></span>
                </label>
            </form>
        </li>
    </c:forEach>
</ul>
<p class="form-hint" id="section-order-status"></p>