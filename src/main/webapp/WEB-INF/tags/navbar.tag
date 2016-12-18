<%@ tag pageEncoding="UTF-8"%>
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
<script>
	$(document).ready(function() {
		if (
<%=(session.getAttribute("userID") != null)%>
	) {
			$('#nav-login').attr('style', 'display: none;');
			
		} else {
			ref = window.location.href;
			ref = ref.substring(29);
			$('#nav-login').attr('href', 'login.jsp?ref=' + ref);
			$('#nav-logout').attr('style', 'display: none;');
		}
	});
</script>
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
			<%
				try {
					String prof = (String) session.getAttribute("userProf");
					if (prof.equalsIgnoreCase("adm")) {
						out.print("<ul class=\"nav navbar-nav\" id=\"link-dashboard\">"
								+ "<li><a class=\"nav-link\" href=\"/livros/adm/\">"
								+ "<span class=\"glyphicon glyphicon-dashboard\"></span> Dashboard</a></li></ul>");
					}
				} catch (Exception ex) {

				}
			%>
			<ul class="nav navbar-nav navbar-right">
				<li><a class="nav-link" id="nav-login"
					href="/livros/login.jsp?ref="><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
						<li><a class="nav-link" id="nav-logout"
					href="/livros/Login?cmd=logout"><span
						class="glyphicon glyphicon-log-out"></span> Logout</a></li>
			</ul>
		</div>
	</div>
</nav>