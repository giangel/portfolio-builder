<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter five</span>
    <h2>Projects</h2>
    <p class="text-muted">The work you are proudest of, drag to reorder.</p>
</div>

<ul class="entry-list" data-reorder-url="/portfolio/projects" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="project" items="${projects}">
        <li class="entry-item" data-entry-id="${project.projectId}">
            <span class="entry-drag-handle">&#8942;&#8942;</span>
            <div class="entry-body">
                <div class="entry-title">${project.title}</div>
                <div class="entry-subtitle">${project.description}</div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/projects" data-confirm="Remove this project?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="projectId" value="${project.projectId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add a project</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/projects">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-group">
            <label for="title">Project title</label>
            <input type="text" id="title" name="title" maxlength="150" required>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description"></textarea>
        </div>
        <div class="form-group">
            <label for="imageUrl">Image URL</label>
            <input type="url" id="imageUrl" name="imageUrl">
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="projectUrl">Live project URL</label>
                <input type="url" id="projectUrl" name="projectUrl">
            </div>
            <div class="form-group">
                <label for="repositoryUrl">Repository URL</label>
                <input type="url" id="repositoryUrl" name="repositoryUrl">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="startDate">Start date</label>
                <input type="date" id="startDate" name="startDate">
            </div>
            <div class="form-group">
                <label for="endDate">End date</label>
                <input type="date" id="endDate" name="endDate">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Add project</button>
    </form>
</div>