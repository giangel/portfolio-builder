<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter four</span>
    <h2>Education</h2>
    <p class="text-muted">Degrees, certificates, and programs, drag to reorder.</p>
</div>

<ul class="entry-list" data-reorder-url="/portfolio/education" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="edu" items="${educations}">
        <li class="entry-item" data-entry-id="${edu.educationId}">
            <span class="entry-drag-handle">&#8942;&#8942;</span>
            <div class="entry-body">
                <div class="entry-title">${edu.degree}, ${edu.institutionName}</div>
                <div class="entry-subtitle">${edu.fieldOfStudy}, ${edu.startDate} to ${edu.endDate}</div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/education" data-confirm="Remove this entry?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="educationId" value="${edu.educationId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add education</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/education">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-row">
            <div class="form-group">
                <label for="institutionName">Institution</label>
                <input type="text" id="institutionName" name="institutionName" maxlength="150" required>
            </div>
            <div class="form-group">
                <label for="degree">Degree</label>
                <input type="text" id="degree" name="degree" maxlength="150" required>
            </div>
        </div>
        <div class="form-group">
            <label for="fieldOfStudy">Field of study</label>
            <input type="text" id="fieldOfStudy" name="fieldOfStudy" maxlength="150">
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
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Add education</button>
    </form>
</div>