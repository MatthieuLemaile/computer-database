<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/myTagLib.tld" prefix="myLib"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<section id="main">

	<c:if test="${not empty exception}">
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
				<h1><spring:message code="homepage.edit.computer"/></h1>

				<form:form action="editComputer" method="POST" modelAttribute="computerDto">
					<form:input type="hidden" path="id" id="id" />
					<fieldset>
						<div class="form-group">
							<form:label path="name"><spring:message code="computer.name"/></form:label>
							<form:input type="text" class="form-control" path="name"
								id="name" required="required"/>
						</div>
						<div class="form-group">
							<fmt:parseDate value="${requestScope.computerDto.introduced}"
								pattern="yyyy-MM-dd" var="parsedIntroDate" type="date" />
							<fmt:formatDate value="${parsedIntroDate}" var="intro"
								pattern="yyyy-MM-dd" />
							<form:label path="introduced"><spring:message code="computer.introduced"/></form:label> <form:input
								type="text" class="form-control" path="introduced"
								id="introduced" />

						</div>
						<div class="form-group">
							<fmt:parseDate value="${requestScope.computerDto.discontinued}"
								pattern="yyyy-MM-dd" var="parsedDiscoDate" type="date" />
							<fmt:formatDate value="${parsedDiscoDate}" var="disco"
								pattern="yyyy-MM-dd" />
							<form:label path="discontinued"><spring:message code="computer.discontinued"/></form:label> <form:input
								type="text" class="form-control" path="discontinued"
								id="discontinued" />
						</div>
						<div class="form-group">
							<form:label path="companyId"><spring:message code="computer.company"/></form:label> <form:select
								class="form-control" path="companyId" id="companyId">
								<form:option value="0">--</form:option>
								<c:forEach var="company" items="${requestScope.companies}">
									<form:option value="${company.id}" >
									<c:out value="${company.name}" /></form:option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" id="submit" value="<spring:message code="editChange.save"/>"
							class="btn btn-primary"> or <a
							href="${pageContext.request.contextPath}/homepage"
							class="btn btn-default"><spring:message code="addpage.cancel"/></a>
					</div>
					<input type="hidden" id="_csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
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