<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter eight</span>
    <h2>Social links</h2>
    <p class="text-muted">One link per platform.</p>
</div>

<ul class="entry-list" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="link" items="${socialLinks}">
        <li class="entry-item" data-entry-id="${link.socialLinkId}">
            <div class="entry-body">
                <div class="entry-title">${link.platform}</div>
                <div class="entry-subtitle">${link.url}</div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/social-links" data-confirm="Remove this link?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="socialLinkId" value="${link.socialLinkId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add a social link</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/social-links">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-row">
            <div class="form-group">
                <label for="platform">Platform</label>
                <select id="platform" name="platform">
                    <option value="LINKEDIN">LinkedIn</option>
                    <option value="GITHUB">GitHub</option>
                    <option value="TWITTER">Twitter</option>
                    <option value="INSTAGRAM">Instagram</option>
                    <option value="BEHANCE">Behance</option>
                    <option value="DRIBBBLE">Dribbble</option>
                    <option value="YOUTUBE">YouTube</option>
                    <option value="FACEBOOK">Facebook</option>
                    <option value="WEBSITE">Website</option>
                    <option value="OTHER">Other</option>
                </select>
            </div>
            <div class="form-group">
                <label for="url">URL</label>
                <input type="url" id="url" name="url" required>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Add link</button>
    </form>
</div>