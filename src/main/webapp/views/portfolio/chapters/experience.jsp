<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter three</span>
    <h2>Experience</h2>
    <p class="text-muted">Your work history, most recent first, drag to reorder.</p>
</div>

<ul class="entry-list" data-reorder-url="/portfolio/experience" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="exp" items="${experiences}">
        <li class="entry-item" data-entry-id="${exp.experienceId}">
            <span class="entry-drag-handle">&#8942;&#8942;</span>
            <div class="entry-body">
                <div class="entry-title">${exp.jobTitle}, ${exp.companyName}</div>
                <div class="entry-subtitle">
                    ${exp.startDate} to <c:choose><c:when test="${exp.current}">present</c:when><c:otherwise>${exp.endDate}</c:otherwise></c:choose>
                </div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/experience" data-confirm="Remove this entry?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="experienceId" value="${exp.experienceId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add work experience</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/experience">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-row">
            <div class="form-group">
                <label for="jobTitle">Job title</label>
                <input type="text" id="jobTitle" name="jobTitle" maxlength="150" required>
            </div>
            <div class="form-group">
                <label for="companyName">Company</label>
                <input type="text" id="companyName" name="companyName" maxlength="150" required>
            </div>
        </div>
        <div class="form-group">
            <label for="location">Location</label>
            <input type="text" id="location" name="location" maxlength="150">
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="startDate">Start date</label>
                <input type="date" id="startDate" name="startDate" required>
            </div>
            <div class="form-group">
                <label for="endDate">End date</label>
                <input type="date" id="endDate" name="endDate">
            </div>
        </div>
        <div class="checkbox-row mb-md">
            <input type="checkbox" id="isCurrent" name="isCurrent">
            <label for="isCurrent" style="margin-bottom:0;">I currently work here</label>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Add experience</button>
    </form>
</div>