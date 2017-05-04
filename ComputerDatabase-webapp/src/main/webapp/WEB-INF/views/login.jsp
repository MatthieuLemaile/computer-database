<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>
					<spring:message code="login.loginPage" />
				</h1>

				<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>

				<form name='loginForm' action="<c:url value='login' />"
					method='POST'>
					<fieldset>
						<div class="form-group">
							<label for="username"><spring:message code="login.user" />
								: </label><input id='username' type='text' name='username'
								class="form-control" value=''>
						</div>
						<div class="form-group">
							<label for="password"><spring:message
									code="login.password" /> : </label><input id='password' type='password'
								name='password' class="form-control" />
						</div>
						<div class="form-group">
							<input id="submit" name="submit" type="submit"
								value="<spring:message code='login.login'/>" />
						</div>
						<input type="hidden" id="_csrf" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

					</fieldset>
				</form>
			</div>
		</div>
	</div>
</section>
</body>
</html>