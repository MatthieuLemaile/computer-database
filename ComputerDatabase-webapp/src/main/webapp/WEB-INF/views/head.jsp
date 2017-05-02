<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
			<%-- <sec:authentication property="principal.username" /> --%>
			<sec:authorize access="isAnonymous()">
				<form name='loginForm' action="<c:url value='login' />"
					method='POST'>
					<label class="navbar-brand" for="username">User : </label> <input class="navbar-brand"
						type='text' name='username' value=''>
					<label for="password" class="navbar-brand">  Password : </label> <input class="navbar-brand"
						type='password' name='password' /> <input class="navbar-brand"
						name="submit" type="submit" value="submit" />
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<a class="navbar-brand" href="<c:url value="/logout" />">
					Logout</a>
			</sec:authorize>

		</div>
	</header>