<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.nav-link {
	color: #fff;
}

.nav-title {
	color: #fff;
}

.nav-title:hover {
	color: #fff;
	background-color: #0f5163;
}

.btn-primary {
	border-color: #fff;
}
</style>

<nav class="navbar navbar-dark bg-primary">
	<div class="container">

		<div class="navbar-header">
			<button type="button" class="btn btn-primary navbar-toggle"
				data-toggle="collapse" data-target=".navbar-collapse">
				<span class="glyphicon glyphicon-menu-hamburger"></span>
			</button>
			<a class="navbar-brand nav-title" href="/livros/">Levros</a>
		</div>
		<div class="collapse navbar-collapse" id="navCollapse">
			<ul class="nav navbar-nav">
				<li><a class="nav-link" href="/livros/"><span
						class="glyphicon glyphicon-home"></span> Home</a></li>
			</ul>
			<c:if test="${userProf == 'adm' }">
				<ul class="nav navbar-nav" id="link-dashboard">
					<li><a class="nav-link" href="/livros/adm/"><span
							class="glyphicon glyphicon-dashboard"></span> Dashboard</a></li>
				</ul>
			</c:if>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${userID == null }">
						<li><a class="nav-link" id="nav-login"
							href="/livros/login.jsp?ref="><span
								class="glyphicon glyphicon-log-in"></span> Login</a></li>
								<script>
								ref = window.location.href;
								$('#nav-login').attr('href', 'login.jsp?ref=' + ref);
								</script>
					</c:when>
					<c:otherwise>
						<li><a class="nav-link" id="nav-logout"
							href="/livros/Login?cmd=logout"><span
								class="glyphicon glyphicon-log-out"></span> Logout</a></li>
					</c:otherwise>
				</c:choose>


			</ul>
		</div>
	</div>
</nav>