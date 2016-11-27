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
<nav class="navbar navbar-dark bg-primary">
	<div class="container">

		<div class="navbar-header">
			<button type="button" class="btn btn-primary navbar-toggle"
				data-toggle="collapse" data-target=".navbar-collapse">
				<span class="glyphicon glyphicon-menu-hamburger"></span>
			</button>
			<a class="navbar-brand nav-title" href=".">Levros</a>
		</div>
		<div class="collapse navbar-collapse" id="navCollapse">
			<ul class="nav navbar-nav">
				<li><a class="nav-link" href="."><span
						class="glyphicon glyphicon-home"></span> Home</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a class="nav-link" href="/login.jsp"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</ul>
		</div>
	</div>
</nav>