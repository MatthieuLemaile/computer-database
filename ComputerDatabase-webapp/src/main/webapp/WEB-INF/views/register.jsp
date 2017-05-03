<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section id="main">
	<c:if test="${not empty exception}">
		<%--Display with a List of ObjectError --%>
		<c:forEach var="error" items="${requestScope.exception}">
			<div class="container">
				<div class="alert alert-danger">
					<c:out value="${error.getCode()}" />
				</div>
			</div>

		</c:forEach>
	</c:if>

	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>
					Register
					<%-- <spring:message code="login.loginPage" /> --%>
				</h1>

				<form:form action="<c:url value='register'/>" method='POST' modelAttribute="userDto">
					<fieldset>
						<div class="form-group">
							<form:label path="username"><spring:message code="login.user" />
								: </form:label>
							<form:input path="username" id='username' type='text'
								class="form-control" />
						</div>
						<div class="form-group">
							<form:label path="password"><spring:message
									code="login.password" /> : </form:label>
							<form:input path="password" id='password' type='password'
								class="form-control" />
						</div>
						<div class="form-group">
							<form:label path="password2"><spring:message
									code="login.password" /> : </form:label>
							<form:input path="password2" id='password2' type='password'
								class="form-control" />
						</div>
						<div class="form-group">
							<input id="submit" name="submit" type="submit"
								value="<spring:message code='login.login'/>" />
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</section>
</body>
</html>