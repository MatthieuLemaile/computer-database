<<jsp:include page="head.jsp"/>
	<footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
              <li>
                 <a href="#" aria-label="Previous">
                 	<span aria-hidden="true">&laquo;</span>
                 </a>
              </li>
	          <li><a href="#">1</a></li>
	          <li><a href="#">2</a></li>
	          <li><a href="#">3</a></li>
	          <li><a href="#">4</a></li>
	          <li><a href="#">5</a></li>
              <li>
              	<a href="#" aria-label="Next">
                	<span aria-hidden="true">&raquo;</span>
                </a>
           	  </li>
        	</ul>
		
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>
	</div>
    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>