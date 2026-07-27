<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="chapter-header">
    <span class="chapter-eyebrow">Chapter one</span>
    <h2>Identity</h2>
    <p class="text-muted">Your name, headline, and about section, plus how your portfolio looks.</p>
</div>

<form method="post" action="${pageContext.request.contextPath}/profile/update">
    <input type="hidden" name="returnPortfolioId" value="${portfolio.portfolioId}">
    <div class="form-row">
        <div class="form-group">
            <label for="fullName">Full name</label>
            <input type="text" id="fullName" name="fullName" value="${profile.fullName}" maxlength="150" required>
        </div>
        <div class="form-group">
            <label for="headline">Professional headline</label>
            <input type="text" id="headline" name="headline" value="${profile.headline}" maxlength="200">
        </div>
    </div>
    <div class="form-group">
        <label for="aboutText">About</label>
        <textarea id="aboutText" name="aboutText" rows="6">${profile.aboutText}</textarea>
    </div>
    <div class="form-row">
        <div class="form-group">
            <label for="phone">Phone</label>
            <input type="tel" id="phone" name="phone" value="${profile.phone}" maxlength="30">
        </div>
        <div class="form-group">
            <label for="location">Location</label>
            <input type="text" id="location" name="location" value="${profile.location}" maxlength="150">
        </div>
    </div>
    <div class="form-group">
        <label for="profileImageUrl">Profile image URL</label>
        <input type="url" id="profileImageUrl" name="profileImageUrl" value="${profile.profileImageUrl}">
    </div>
    <button type="submit" class="btn btn-primary">Save identity</button>
</form>

<h3 class="mb-sm" style="margin-top: var(--space-lg);">Theme</h3>
<form method="post" action="${pageContext.request.contextPath}/portfolio/theme">
    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
    <div class="form-group">
        <select name="themeId" onchange="this.form.submit()">
            <c:forEach var="theme" items="${themes}">
                <option value="${theme.themeId}" ${theme.themeId == portfolio.themeId ? 'selected' : ''}>${theme.themeName}</option>
            </c:forEach>
        </select>
    </div>
</form>

<h3 class="mb-sm">Customize appearance</h3>
<form method="post" action="${pageContext.request.contextPath}/portfolio/settings">
    <input type="hidden" name="portfolioId" value="${portfolio.portfolioId}">
    <div class="customization-grid mb-md">
        <div class="form-group">
            <label for="accentColorText">Accent color</label>
            <div class="flex gap-sm">
                <input type="color" data-sync="accentColorText" value="${settings.accentColor}">
                <input type="text" id="accentColorText" name="accentColor" value="${settings.accentColor}" pattern="^#[0-9A-Fa-f]{6}$" required>
            </div>
        </div>
        <div class="form-group">
            <label for="typographyChoice">Typography</label>
            <select id="typographyChoice" name="typographyChoice">
                <option value="storyline-serif" ${settings.typographyChoice == 'storyline-serif' ? 'selected' : ''}>Storyline serif</option>
                <option value="modern-sans" ${settings.typographyChoice == 'modern-sans' ? 'selected' : ''}>Modern sans</option>
                <option value="editorial-mix" ${settings.typographyChoice == 'editorial-mix' ? 'selected' : ''}>Editorial mix</option>
                <option value="developer-mono" ${settings.typographyChoice == 'developer-mono' ? 'selected' : ''}>Developer mono</option>
            </select>
        </div>
        <div class="form-group">
            <label for="backgroundStyle">Background</label>
            <select id="backgroundStyle" name="backgroundStyle">
                <option value="paper" ${settings.backgroundStyle == 'paper' ? 'selected' : ''}>Paper</option>
                <option value="clean-white" ${settings.backgroundStyle == 'clean-white' ? 'selected' : ''}>Clean white</option>
                <option value="soft-gray" ${settings.backgroundStyle == 'soft-gray' ? 'selected' : ''}>Soft gray</option>
                <option value="dark" ${settings.backgroundStyle == 'dark' ? 'selected' : ''}>Dark</option>
            </select>
        </div>
        <div class="form-group">
            <label for="buttonStyle">Buttons</label>
            <select id="buttonStyle" name="buttonStyle">
                <option value="rounded" ${settings.buttonStyle == 'rounded' ? 'selected' : ''}>Rounded</option>
                <option value="square" ${settings.buttonStyle == 'square' ? 'selected' : ''}>Square</option>
                <option value="pill" ${settings.buttonStyle == 'pill' ? 'selected' : ''}>Pill</option>
            </select>
        </div>
        <div class="form-group">
            <label for="layoutVariant">Layout density</label>
            <select id="layoutVariant" name="layoutVariant">
                <option value="standard" ${settings.layoutVariant == 'standard' ? 'selected' : ''}>Standard</option>
                <option value="compact" ${settings.layoutVariant == 'compact' ? 'selected' : ''}>Compact</option>
                <option value="spacious" ${settings.layoutVariant == 'spacious' ? 'selected' : ''}>Spacious</option>
            </select>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Save appearance</button>
</form>