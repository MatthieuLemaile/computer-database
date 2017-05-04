<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<head>
<title>Computer Database</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/js/jquery-ui-1.12.1/jquery-ui.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="homepage"> Application - Computer
				Database </a>
			<sec:authorize access="isAnonymous()">
				<form class="form-inline" name='loginForm'
					action="<c:url value='login' />" method='POST'>
					<label for="username-nav" class="perso-navbar-text"><spring:message
							code="login.user" /> : </label> <input type='text' class="form-control"
						name='username' id="username-nav"> <label for="password-nav"
						class="perso-navbar-text"><spring:message
							code="login.password" /> : </label> <input type='password'
						class="form-control" name='password' id="password-nav"/> <input name="submit"
						type="submit" value='<spring:message code="login.login"/>' /> <input
						type="hidden" id="_csrf-nav" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<a class="navbar-brand" href="<c:url value="/logout" />"> <spring:message
						code="login.logout" /></a>
			</sec:authorize>

		</div>
	</header>