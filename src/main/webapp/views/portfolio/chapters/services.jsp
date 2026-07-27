<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter seven</span>
    <h2>Services</h2>
    <p class="text-muted">What you offer to clients or collaborators, drag to reorder.</p>
</div>

<ul class="entry-list" data-reorder-url="/portfolio/services" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="service" items="${services}">
        <li class="entry-item" data-entry-id="${service.serviceId}">
            <span class="entry-drag-handle">&#8942;&#8942;</span>
            <div class="entry-body">
                <div class="entry-title">${service.title}</div>
                <div class="entry-subtitle">${service.description}</div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/services" data-confirm="Remove this service?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="serviceId" value="${service.serviceId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add a service</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/services">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-group">
            <label for="title">Service title</label>
            <input type="text" id="title" name="title" maxlength="150" required>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" name="description"></textarea>
        </div>
        <div class="form-group">
            <label for="iconKey">Icon key</label>
            <input type="text" id="iconKey" name="iconKey" maxlength="50" placeholder="e.g. design, code, strategy">
        </div>
        <button type="submit" class="btn btn-primary">Add service</button>
    </form>
</div>