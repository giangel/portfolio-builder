<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account settings, Storyline</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body data-context-path="${pageContext.request.contextPath}">
<jsp:include page="/views/shared/header.jsp" />

<div class="container" style="max-width: 640px; margin: var(--space-lg) auto;">
    <h1>Account settings</h1>
    <p class="text-muted mb-md">Your identity here applies across every portfolio you create.</p>

    <div class="card">
        <form method="post" action="${pageContext.request.contextPath}/profile/update">
            <div class="form-group">
                <label for="fullName">Full name</label>
                <input type="text" id="fullName" name="fullName" value="${profile.fullName}" maxlength="150" required>
            </div>
            <div class="form-group">
                <label for="headline">Professional headline</label>
                <input type="text" id="headline" name="headline" value="${profile.headline}" maxlength="200">
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
            <button type="submit" class="btn btn-primary">Save changes</button>
        </form>
    </div>
</div>

<jsp:include page="/views/shared/footer.jsp" />
</body>
</html>