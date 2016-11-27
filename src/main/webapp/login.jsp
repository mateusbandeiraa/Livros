<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script type="text/javascript" src="./js/scripts.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript">
	$(document).ready(function() {
		id = ${userID};
		if (id != null) {
			window.location.replace('./index.jsp');
		}
	});
</script>
<title>Login — Levros</title>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">Login</div>
			</div>
			<div class="panel-body">
				<form class="form-horizontal col-sm-12" method="post"
					action="Login?cmd=login">
					<div class="form-group">
						<input class="form-control" type="email" id="emailUsuario"
							name="emailUsuario" placeholder="E-mail" required>
					</div>
					<div class="form-group">
						<input class="form-control" type="password" id="senhaUsuario"
							name="senhaUsuario" placeholder="Senha" required>
					</div>
					<div id="msg">${logMsg}</div>
					<div class="col-lg-6 col-sm-12">
						<button class="btn btn-primary btn-block" type="submit">Entrar</button>
					</div>
					<div class="col-lg-6 col-sm-12">
						<button class="btn btn-info btn-block" type="button"
							onclick="parent.location='cadastro.jsp'">Cadastrar...</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>