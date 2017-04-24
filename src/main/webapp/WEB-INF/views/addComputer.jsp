<%@ include file="head.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myLib" uri="/WEB-INF/myTagLib.tld"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section id="main">

	<c:if test="${not empty exception}">
		<%--Display with a List of ObjectError --%>
		<%-- 	<c:forEach var="error" items="${requestScope.exception}">
				<div class="container">
					<div class="alert alert-danger">
						<c:out value="${error.getDefaultMessage()}" /><br/>
						<c:out value="${error.getObjectName()}" /><br/>
						<c:out value="${error.getClass()}" /><br/>
						<c:out value="${error.getCode()}" /><br/>

						<c:forEach var="arg" items="${error.getArguments()}" >
						<c:out value="${arg}" /><br/>
						</c:forEach>
						<c:forEach var="code" items="${error.getCodes()}" >
						<c:out value="${code}" /><br/>
						</c:forEach>
					</div>
				</div>

			</c:forEach> --%>
		<%-- Display with hash map : --%>
		<c:forEach var="map" items="${requestScope.exception}">
			<c:forEach var="hash" items="${map.value}">
				<div class="container">
					<div class="alert alert-danger">
						<c:out value="${hash}" />
					</div>
				</div>

			</c:forEach>
		</c:forEach>
	</c:if>

	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>
					<spring:message code="homepage.add.computer" />
				</h1>

				<form:form method="POST" action="addComputer"
					modelAttribute="computerDto">
					<fieldset>
						<div class="form-group">
							<form:label path="name">
								<spring:message code="computer.name" />
							</form:label>
							<form:input path="name" type="text" class="form-control"
								id="name" required="required"/>
							<%-- placeholder="<spring:message code=computer.name />" --%>
						</div>
						<div class="form-group">
							<form:label path="introduced">
								<spring:message code="computer.introduced" />
							</form:label>
							<form:input type="date" class="form-control" path="introduced"
								id="introduced" />
						</div>
						<div class="form-group">
							<form:label path="discontinued">
								<spring:message code="computer.discontinued" />
							</form:label>
							<form:input type="date" class="form-control" path="discontinued"
								id="discontinued" />
						</div>
						<div class="form-group">
							<form:label path="companyId">
								<spring:message code="computer.company" />
							</form:label>
							<form:select class="form-control" path="companyId" id="companyId">
								<form:option value="0">--</form:option>
								<c:forEach var="company" items="${requestScope.companies}">
									<form:option value="${company.id}">
										<c:out value="${company.name}" />
									</form:option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="<spring:message code="addpage.add"/>"
							id="submit" class="btn btn-primary"> or <a
							href="${pageContext.request.contextPath}/homepage"
							class="btn btn-default"><spring:message code="addpage.cancel" /></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/js/validate.js"></script>
</body>
</html>