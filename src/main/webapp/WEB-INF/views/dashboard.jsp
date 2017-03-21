<%@ include file="head.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/myTagLib.tld" prefix="myLib"%>
	
	<section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${requestScope.totalNumberComputers} Computers found"></c:out>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${pageContext.request.contextPath}/deleteComputer" method="POST">
            <input type="hidden" name="selection" value="">
        </form>
	
	<div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:forEach var="computer" items="${requestScope.listComputers}">
                	<tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
                        </td>
                        <td>
                            <a href="editComputer?computerId=${computer.id}" onclick=""><c:out value="${computer.name}"/></a>
                        </td>
                        <td><c:out value="${computer.introduced}"/></td>
                        <td><c:out value="${computer.discontinued}"/></td>
                        <td><c:out value="${computer.company.name}"/></td>

                    </tr>
                </c:forEach>
                </tbody>

		</table>
	</div>
                    
	</section>
	
	
	<footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
              <c:if test="${requestScope.page.pageNumber-1 > 0}">
              <li>
                 <a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber-1,50)}" />" aria-label="Previous">
                 	<span aria-hidden="true">&laquo;</span>
                 </a>
              </li>
              </c:if>
	          <c:if test="${requestScope.page.pageNumber-2 > 0}"><li><a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber-2,requestScope.page.numberPerPage)}" />">${requestScope.page.pageNumber-2}</a></li></c:if>
	          <c:if test="${requestScope.page.pageNumber-1 > 0}"><li><a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber-1,requestScope.page.numberPerPage)}" />">${requestScope.page.pageNumber-1}</a></li></c:if>
	          <c:if test="${requestScope.page.pageNumber+0 > 0}"><li><a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber+0,requestScope.page.numberPerPage)}" />">${requestScope.page.pageNumber+0}</a></li></c:if>
	          <c:if test="${requestScope.page.pageNumber+1 > 0}"><li><a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber+1,requestScope.page.numberPerPage)}" />">${requestScope.page.pageNumber+1}</a></li></c:if>
	          <c:if test="${requestScope.page.pageNumber+2 > 0}"><li><a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber+2,requestScope.page.numberPerPage)}" />">${requestScope.page.pageNumber+2}</a></li></c:if>
              <c:if test="${requestScope.page.pageNumber+1 > 0}">
              <li>
              	<a href="<c:out value="${pageContext.request.contextPath}${myLib:link('homepage',requestScope.page.pageNumber+1,50)}" />" aria-label="Next">
                	<span aria-hidden="true">&raquo;</span>
                </a>
           	  </li>
           	  </c:if>
        	</ul>
		
        <div class="btn-group btn-group-sm pull-right" role="group" >
        	<ul>
        		<li class="btn btn-default"><a href="<c:out value="${pageContext.request.contextPath}${myLib:pagination(requestScope.page.pageNumber,10)}" />">10</a></li>
        		<li class="btn btn-default"><a href="<c:out value="${pageContext.request.contextPath}${myLib:pagination(requestScope.page.pageNumber,50)}" />">50</a></li>
        		<li class="btn btn-default"><a href="<c:out value="${pageContext.request.contextPath}${myLib:pagination(requestScope.page.pageNumber,100)}" />">100</a></li>
        	</ul>
            <!-- <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button-->
        </div>
	</div>
    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>