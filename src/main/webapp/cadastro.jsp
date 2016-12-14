<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de usuário — Levros</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="/livros/css/style.css">
<script type="text/javascript" src="/livros/js/scripts.js"></script>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<script>
	$(document).ready(function() {
		$('form').on('submit', function() {
			var caixa1 = $('#senhaUsuario').val();
			var caixa2 = $('#confirmaSenha').val();
			if (caixa1 === caixa2) {
				$('#senhasDif').css('display', 'none');
				return true;
			}
			$('#senhasDif').css('display', '');
			return false;
		});
	});
</script>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="col-md-offset-3 col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">Cadastro de usuário</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" action="Gravar?cmd=usuario"
						method="post">
						<div class="form-group">
							<label for="nomeUsuario" class="control-label col-sm-2">Nome:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="nomeUsuario"
									name="nomeUsuario" required>
							</div>
						</div>
						<div class="form-group">
							<label for="emailUsuario" class="control-label col-sm-2">E-mail:</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="emailUsuario"
									name="emailUsuario" required>
							</div>
						</div>
						<div class="form-group">
							<label for="senhaUsuario" class="control-label col-sm-2">Senha:</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="senhaUsuario"
									name="senhaUsuario" pattern="{8,60}" required>
							</div>
						</div>
						<div class="form-group">
							<label for="confirmaSenha" class="control-label col-sm-2">Confirme
								a senha:</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="confirmaSenha"
									pattern="{8,60}" required>
							</div>
						</div>
						<div id="senhasDif" class="alert alert-danger" style="display:none;">As senhas não coincidem!</div>
						<button type="submit" class="btn btn-success btn-block">Cadastrar</button>
					</form>
					${param["msgUsuario"]}
				</div>
			</div>
		</div>
	</div>
</body>
</html>