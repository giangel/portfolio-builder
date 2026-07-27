<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<c:set var="themeClass" value="theme-${fn:toLowerCase(fn:replace(portfolio.themeName, ' ', '-'))}" />

<div class="public-portfolio ${themeClass} typography-${settings.typographyChoice} background-${settings.backgroundStyle} buttons-${settings.buttonStyle} layout-${settings.layoutVariant}" style="--accent: ${settings.accentColor};">

    <c:if test="${isPreview}">
        <div class="preview-banner">
            <span>You are viewing an owner-only preview.</span>
            <a href="${pageContext.request.contextPath}/portfolio/builder?portfolioId=${portfolio.portfolioId}&chapter=publish">Back to builder</a>
        </div>
    </c:if>

    <c:forEach var="section" items="${sections}">
        <c:if test="${section.enabled}">

            <c:if test="${section.sectionType == 'HERO'}">
                <section class="pp-section pp-hero">
                    <c:if test="${not empty profile.profileImageUrl}">
                        <img class="pp-hero-image" src="${profile.profileImageUrl}" alt="${profile.fullName}">
                    </c:if>
                    <h1>${profile.fullName}</h1>
                    <p class="pp-headline">${profile.headline}</p>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'ABOUT' and not empty profile.aboutText}">
                <section class="pp-section pp-about">
                    <h2>About</h2>
                    <p>${profile.aboutText}</p>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'SKILLS' and not empty skills}">
                <section class="pp-section pp-skills">
                    <h2>Skills</h2>
                    <div class="pp-skill-grid">
                        <c:forEach var="skill" items="${skills}">
                            <span class="pp-skill-chip">${skill.skillName}</span>
                        </c:forEach>
                    </div>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'EXPERIENCE' and not empty experiences}">
                <section class="pp-section pp-experience">
                    <h2>Experience</h2>
                    <c:forEach var="exp" items="${experiences}">
                        <div class="pp-timeline-item">
                            <h3>${exp.jobTitle}</h3>
                            <p class="pp-timeline-meta">${exp.companyName}, ${exp.location}</p>
                            <p class="pp-timeline-dates">${exp.startDate} to <c:choose><c:when test="${exp.current}">present</c:when><c:otherwise>${exp.endDate}</c:otherwise></c:choose></p>
                            <p>${exp.description}</p>
                        </div>
                    </c:forEach>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'EDUCATION' and not empty educations}">
                <section class="pp-section pp-education">
                    <h2>Education</h2>
                    <c:forEach var="edu" items="${educations}">
                        <div class="pp-timeline-item">
                            <h3>${edu.degree}, ${edu.fieldOfStudy}</h3>
                            <p class="pp-timeline-meta">${edu.institutionName}</p>
                            <p class="pp-timeline-dates">${edu.startDate} to ${edu.endDate}</p>
                        </div>
                    </c:forEach>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'PROJECTS' and not empty projects}">
                <section class="pp-section pp-projects">
                    <h2>Projects</h2>
                    <div class="pp-project-grid">
                        <c:forEach var="project" items="${projects}">
                            <div class="pp-project-card">
                                <c:if test="${not empty project.imageUrl}">
                                    <img src="${project.imageUrl}" alt="${project.title}">
                                </c:if>
                                <h3>${project.title}</h3>
                                <p>${project.description}</p>
                                <div class="pp-project-links">
                                    <c:if test="${not empty project.projectUrl}"><a href="${project.projectUrl}" target="_blank" rel="noopener">View project</a></c:if>
                                    <c:if test="${not empty project.repositoryUrl}"><a href="${project.repositoryUrl}" target="_blank" rel="noopener">Repository</a></c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'CERTIFICATIONS' and not empty certifications}">
                <section class="pp-section pp-certifications">
                    <h2>Certifications</h2>
                    <c:forEach var="cert" items="${certifications}">
                        <div class="pp-cert-item">
                            <strong>${cert.certificationName}</strong>, ${cert.issuingOrganization}, ${cert.issueDate}
                            <c:if test="${not empty cert.credentialUrl}"> &middot; <a href="${cert.credentialUrl}" target="_blank" rel="noopener">Verify</a></c:if>
                        </div>
                    </c:forEach>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'SERVICES' and not empty services}">
                <section class="pp-section pp-services">
                    <h2>Services</h2>
                    <div class="pp-service-grid">
                        <c:forEach var="service" items="${services}">
                            <div class="pp-service-card">
                                <h3>${service.title}</h3>
                                <p>${service.description}</p>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'CONTACT' and (not empty profile.phone or not empty profile.location)}">
                <section class="pp-section pp-contact">
                    <h2>Contact</h2>
                    <c:if test="${not empty profile.phone}"><p>${profile.phone}</p></c:if>
                    <c:if test="${not empty profile.location}"><p>${profile.location}</p></c:if>
                </section>
            </c:if>

            <c:if test="${section.sectionType == 'SOCIAL_LINKS' and not empty socialLinks}">
                <section class="pp-section pp-social">
                    <div class="pp-social-row">
                        <c:forEach var="link" items="${socialLinks}">
                            <a href="${link.url}" target="_blank" rel="noopener">${link.platform}</a>
                        </c:forEach>
                    </div>
                </section>
            </c:if>

        </c:if>
    </c:forEach>
</div>