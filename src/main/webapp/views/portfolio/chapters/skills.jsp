<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter two</span>
    <h2>Skills</h2>
    <p class="text-muted">Drag to reorder. This is the order visitors will see on your public portfolio.</p>
</div>

<ul class="entry-list" data-reorder-url="/portfolio/skills" data-portfolio-id="${portfolio.portfolioId}">
    <c:forEach var="skill" items="${skills}">
        <li class="entry-item" data-entry-id="${skill.skillId}">
            <span class="entry-drag-handle">&#8942;&#8942;</span>
            <div class="entry-body">
                <div class="entry-title">${skill.skillName}</div>
                <div class="entry-subtitle">${skill.proficiencyLevel}</div>
            </div>
            <div class="entry-actions">
                <form method="post" action="${pageContext.request.contextPath}/portfolio/skills" data-confirm="Remove this skill?">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
                    <input type="hidden" name="skillId" value="${skill.skillId}">
                    <button type="submit" class="btn btn-sm btn-danger">Remove</button>
                </form>
            </div>
        </li>
    </c:forEach>
</ul>

<div class="entry-add-form">
    <h4>Add a skill</h4>
    <form method="post" action="${pageContext.request.contextPath}/portfolio/skills">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
        <div class="form-row">
            <div class="form-group">
                <label for="skillName">Skill name</label>
                <input type="text" id="skillName" name="skillName" maxlength="100" required>
            </div>
            <div class="form-group">
                <label for="proficiencyLevel">Proficiency</label>
                <select id="proficiencyLevel" name="proficiencyLevel">
                    <option value="BEGINNER">Beginner</option>
                    <option value="INTERMEDIATE" selected>Intermediate</option>
                    <option value="ADVANCED">Advanced</option>
                    <option value="EXPERT">Expert</option>
                </select>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Add skill</button>
    </form>
</div>