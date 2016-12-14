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
		if (<%=(session.getAttribute("userID") != null)%>) {
			$('#nav-login').attr('style', 'display: none;');
		} else {
			ref = window.location.href;
			ref = ref.substring(29);
			$('#nav-login').attr('href', 'login.jsp?ref=' + ref);
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
			<ul class="nav navbar-nav navbar-right">
				<li><a class="nav-link" id="nav-login" href="/livros/login.jsp?ref="><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</ul>
		</div>
	</div>
</nav>