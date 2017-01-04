<%@page import="persistence.TicketDao"%>
<%@page import="entity.TicketSenha"%>
<%@page import="entity.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	TicketSenha ts = new TicketSenha();
	TicketDao td = new TicketDao();
	try {
		ts = td.findByPass(request.getParameter("code"));
		if(ts == null)
			throw new Exception();
	} catch (Exception ex) {
		request.getRequestDispatcher("404.jsp").forward(request, response);
	}
%>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="css/style.css">
<title>Redefinição de senha</title>
<script>
	$(document).ready(function() {
		$('form').on("submit", function() {
			var caixa1 = $('#senhaUsuario').val();
			var caixa2 = $('#confirmaSenha').val();
			if (caixa1 === caixa2) {
				$('#senhasDif').css('display', 'none');

				var dados = {
					cmd : "ajaxRescSenha",
					ticketCode : "${param.code}",
					senhaUsuario : caixa1
				}
				$.post('/livros/Login', $.param(dados), function(response) {
					$('#resp-serv-resc-senha').html(response);
				});

				return false;

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
					<div class="panel-title">
						<h4>Redefinição de senha</h4>
					</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal">
						<input type="hidden" name="ticketCode" value="${param.code }">

						<div class="form-group">
							<label for="senhaUsuario" class="control-label col-sm-2">Senha:</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="senhaUsuario"
									name="senhaUsuario" required>
							</div>
						</div>
						<div class="form-group">
							<label for="confirmaSenha" class="control-label col-sm-2">Confirme
								a senha:</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="confirmaSenha" required>
							</div>
						</div>
						<div id="senhasDif" class="alert alert-danger"
							style="display: none;">As senhas não coincidem!</div>
						<button type="submit" id="btn-submit"
							class="btn btn-success btn-block">Alterar a senha</button>
					</form>
					<p id="resp-serv-resc-senha"></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>