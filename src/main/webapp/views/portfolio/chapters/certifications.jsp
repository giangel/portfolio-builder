<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter six</span>
    <h2>Certifications</h2>
    <p class="text-muted">Credentials that back up your skills, drag to reorder.</p>
</div>

<ul class="entry-list" data-reorder-url="/portfolio/certifications" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="cert" items="${certifications}">
        <li class="entry-item" data-entry-id="${cert.certificationId}">
            <span class="entry-drag-handle">&#8942;&#8942;</span>
            <div class="entry-body">
                <div class="entry-title">${cert.certificationName}</div>
                <div class="entry-subtitle">${cert.issuingOrganization}, issued ${cert.issueDate}</div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/certifications" data-confirm="Remove this certification?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="certificationId" value="${cert.certificationId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add a certification</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/certifications">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-row">
            <div class="form-group">
                <label for="certificationName">Certification name</label>
                <input type="text" id="certificationName" name="certificationName" maxlength="150" required>
            </div>
            <div class="form-group">
                <label for="issuingOrganization">Issuing organization</label>
                <input type="text" id="issuingOrganization" name="issuingOrganization" maxlength="150" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group">
                <label for="issueDate">Issue date</label>
                <input type="date" id="issueDate" name="issueDate" required>
            </div>
            <div class="form-group">
                <label for="expirationDate">Expiration date</label>
                <input type="date" id="expirationDate" name="expirationDate">
            </div>
        </div>
        <div class="form-group">
            <label for="credentialUrl">Credential URL</label>
            <input type="url" id="credentialUrl" name="credentialUrl">
        </div>
        <button type="submit" class="btn btn-primary">Add certification</button>
    </form>
</div>