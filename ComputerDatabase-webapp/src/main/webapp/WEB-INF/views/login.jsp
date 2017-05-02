<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section id="main">
	<div class="container">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Login Page</h1>

					<c:if test="${not empty error}">
						<div class="error">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
					</c:if>

					<form name='loginForm'
						action="<c:url value='login' />" method='POST'>
						<fieldset>
							<div class="form-group">
								<label for="username">User : </label><input id='username'
									type='text' name='username' class="form-control" value=''>
							</div>
							<div class="form-group">
								<label for="password">Password : </label><input id='password'
									type='password' name='password' class="form-control" />
							</div>
							<div class="form-group">
								<input name="submit" type="submit" value="submit" />
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />

						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>