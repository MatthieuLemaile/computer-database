<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/myTagLib.tld" prefix="myLib"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">

	<c:if test="${not empty exception}">
		<div class="container">
			<div class="alert alert-danger">
				<c:out value="${exception}" />
			</div>
		</div>
	</c:if>

	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1><spring:message code="homepage.edit.computer"/></h1>

				<form action="editComputer" method="POST">
					<input type="hidden" name="computerId"
						value='<c:out value="${requestScope.computer.id}"/>' id="id" />
					<fieldset>
						<div class="form-group">
							<label for="computerName"><spring:message code="computer.name"/></label>
							<input type="text" class="form-control" name="computerName"
								id="computerName" required
								value='<c:out value="${requestScope.computer.name}"/>'>
						</div>
						<div class="form-group">
							<fmt:parseDate value="${requestScope.computer.introduced}"
								pattern="yyyy-MM-dd" var="parsedIntroDate" type="date" />
							<fmt:formatDate value="${parsedIntroDate}" var="intro"
								pattern="yyyy-MM-dd" />
							<label for="introduced"><spring:message code="computer.introduced"/></label> <input
								type="date" class="form-control" name="introduced"
								id="introduced" value="<c:out value="${intro}"/>">

						</div>
						<div class="form-group">
							<fmt:parseDate value="${requestScope.computer.discontinued}"
								pattern="yyyy-MM-dd" var="parsedDiscoDate" type="date" />
							<fmt:formatDate value="${parsedDiscoDate}" var="disco"
								pattern="yyyy-MM-dd" />
							<label for="discontinued"><spring:message code="computer.discontinued"/></label> <input
								type="date" class="form-control" name="discontinued"
								id="discontinued" value="<c:out value="${disco}"/>">
						</div>
						<div class="form-group">
							<label for="companyId"><spring:message code="computer.company"/></label> <select
								class="form-control" name="companyId" id="companyId">
								<option value="0">--</option>
								<c:forEach var="company" items="${requestScope.companies}">

									<option value="<c:out value="${company.id}"/>"
										<c:if test="${requestScope.computer.companyId==company.id}">selected</c:if>><c:out
											value="${company.name}" /></option>
								</c:forEach>
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" id="submit" value="<spring:message code="editChange.save"/>"
							class="btn btn-primary"> or <a
							href="${pageContext.request.contextPath}/homepage"
							class="btn btn-default"><spring:message code="addpage.cancel"/></a>
					</div>
				</form>
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