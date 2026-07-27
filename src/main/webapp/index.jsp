<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Storyline, Portfolio Builder System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<section class="hero-section">
    <div class="container">
        <span class="eyebrow">A calmer way to build your portfolio</span>
        <h1>Tell your professional story, one chapter at a time</h1>
        <p class="hero-subtitle">Storyline turns your skills, projects, and experience into a polished portfolio website, no HTML or CSS required.</p>
        <div class="hero-actions">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/register.jsp">Start your story</a>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/login.jsp">Log in</a>
        </div>
    </div>
</section>

<section class="landing-section" id="features">
    <div class="container">
        <h2>Everything a professional portfolio needs</h2>
        <div class="feature-grid">
            <div class="feature-card">
                <div class="feature-icon">&#9998;</div>
                <h3>Guided chapters</h3>
                <p class="text-muted">Identity, skills, experience, education, projects, certifications, services, and social links, each as its own focused chapter.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">&#127912;</div>
                <h3>Real customization</h3>
                <p class="text-muted">Choose a theme, an accent color, typography, and layout, then reorder and toggle sections with working drag and drop.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">&#128279;</div>
                <h3>One shareable link</h3>
                <p class="text-muted">Publish to a clean, memorable URL you can share anywhere, and unpublish just as easily while you make changes.</p>
            </div>
            <div class="feature-card">
                <div class="feature-icon">&#128065;</div>
                <h3>Read your story</h3>
                <p class="text-muted">Preview your portfolio exactly as visitors will see it before you publish, in the theme you have chosen.</p>
            </div>
        </div>
    </div>
</section>

<section class="landing-section" id="themes" style="background-color: var(--color-paper-dark);">
    <div class="container">
        <h2>Five genuinely different themes</h2>
        <div class="theme-preview-strip">
            <div class="theme-preview-card">
                <div class="theme-swatch" style="background: linear-gradient(135deg,#EDEAE2,#B7B2A4);"></div>
                <div class="theme-label">Minimal Professional</div>
            </div>
            <div class="theme-preview-card">
                <div class="theme-swatch" style="background: linear-gradient(135deg,#2E2A24,#C97B5C);"></div>
                <div class="theme-label">Editorial</div>
            </div>
            <div class="theme-preview-card">
                <div class="theme-swatch" style="background: linear-gradient(135deg,#C97B5C,#8FA07E);"></div>
                <div class="theme-label">Creative Visual</div>
            </div>
            <div class="theme-preview-card">
                <div class="theme-swatch" style="background: linear-gradient(135deg,#1F2A24,#4A6FA5);"></div>
                <div class="theme-label">Developer</div>
            </div>
            <div class="theme-preview-card">
                <div class="theme-swatch" style="background: linear-gradient(135deg,#3A4A63,#8FA07E);"></div>
                <div class="theme-label">Corporate Professional</div>
            </div>
        </div>
    </div>
</section>

<section class="landing-section">
    <div class="container" style="text-align:center;">
        <h2>Ready to write your story?</h2>
        <p class="text-muted mb-md">Creating an account takes less than a minute.</p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/register.jsp">Get started for free</a>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>