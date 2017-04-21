<%@ include file="head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/myTagLib.tld" prefix="myLib"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<section id="main">
	<c:out value="${greetings}"></c:out>
</section>
</body>
</html>