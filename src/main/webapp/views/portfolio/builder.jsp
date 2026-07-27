<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${portfolio.title}, Builder, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/builder.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="preview-toggle-bar">
    <div class="container flex-between">
        <span class="text-muted">Editing <strong>${portfolio.title}</strong></span>
        <button type="button" id="toggle-preview-button" class="btn btn-sm btn-secondary">Show live preview</button>
    </div>
</div>
<div class="container">
    <div id="preview-frame-wrapper" class="preview-frame-wrapper">
        <iframe id="preview-frame" data-src="${pageContext.request.contextPath}/portfolio/preview?portfolioId=${portfolio.portfolioId}" title="Portfolio preview"></iframe>
    </div>
</div>

<div class="builder-layout">
    <aside class="chapters-rail">
        <div class="portfolio-title-block">
            <h3>${portfolio.title}</h3>
            <span class="portfolio-status ${portfolio.published ? 'status-published' : 'status-draft'}">
                <c:choose>
                    <c:when test="${portfolio.published}">Published</c:when>
                    <c:otherwise>Draft</c:otherwise>
                </c:choose>
            </span>
        </div>
        <ul class="chapter-list">
            <li><a class="chapter-link ${currentChapter == 'identity' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=identity">Identity</a></li>
            <li><a class="chapter-link ${currentChapter == 'skills' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=skills">Skills <span class="chapter-count">${chapterCounts.skills}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'experience' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=experience">Experience <span class="chapter-count">${chapterCounts.experience}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'education' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=education">Education <span class="chapter-count">${chapterCounts.education}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'projects' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=projects">Projects <span class="chapter-count">${chapterCounts.projects}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'certifications' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=certifications">Certifications <span class="chapter-count">${chapterCounts.certifications}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'services' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=services">Services <span class="chapter-count">${chapterCounts.services}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'social' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=social">Social links <span class="chapter-count">${chapterCounts.social}</span></a></li>
            <li><a class="chapter-link ${currentChapter == 'publish' ? 'active' : ''}" href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=publish">Publish</a></li>
        </ul>
        <div class="rail-footer">
            <a class="btn btn-secondary btn-block btn-sm" href="${pageContext.request.contextPath}/dashboard">Back to dashboard</a>
        </div>
    </aside>

    <main class="chapter-content">
        <jsp:include page="/views/portfolio/chapters/${currentChapter}.jsp" />
    </main>
</div>

<script src="${pageContext.request.contextPath}/js/builder.js"></script>
</body>
</html>