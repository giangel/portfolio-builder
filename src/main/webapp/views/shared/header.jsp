<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<header class="site-header">
    <div class="container">
        <a class="brand" href="${pageContext.request.contextPath}/index.jsp">
            <span class="brand-mark">&#9670;</span>Storyline
        </a>
        <nav class="site-nav">
            <c:choose>
                <c:when test="${not empty sessionScope.userId}">
                    <c:choose>
                        <c:when test="${sessionScope.userRole == 'ADMINISTRATOR'}">
                            <a href="${pageContext.request.contextPath}/admin/dashboard">Admin dashboard</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                        </c:otherwise>
                    </c:choose>
                    <span class="text-muted">${sessionScope.userFullName}</span>
                    <a href="${pageContext.request.contextPath}/logout">Log out</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp">Log in</a>
                    <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/register.jsp">Get started</a>
                </c:otherwise>
            </c:choose>
        </nav>
        <button class="nav-toggle" type="button" aria-label="Toggle menu">&#9776;</button>
    </div>
</header>
<div class="container">
    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success">
            <span>${sessionScope.flashSuccess}</span>
            <button type="button" class="alert-dismiss">&times;</button>
        </div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-error">
            <span>${sessionScope.flashError}</span>
            <button type="button" class="alert-dismiss">&times;</button>
        </div>
        <c:remove var="flashError" scope="session" />
    </c:if>
</div>