<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
<meta charset="UTF-8">
<title>Levros</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript" src="js/scripts.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@page import="entity.*, persistence.*"%>
<jsp:useBean id="mb" class="manager.HomeManagerBean"></jsp:useBean>

<%
	Integer id = (Integer) session.getAttribute("userID");
	Usuario u = new Usuario();

	if (id != null) {
		u = new UsuarioDao().findByCode(id);
	}
%>
</head>
<body>
	<!-- CABEÇALHO -->
	<div class="jumbotron">
		<div class="container hidden-sm-down">
			<h1>Levros</h1>
			<h4>O menor banco de dados de livros na internet.</h4>
			<div class="container" id="con-search">
				<form id="search-form">
					<div class="form-group">
						<div class="input-group" id="search-group">

							<input type="text" class="form-control search-field"
								placeholder="Buscar" id="search-text">
							<div class="input-group-btn">
								<button class="btn btn-default search-field" type="submit">
									<div class="glyphicon glyphicon-search" style="height: 20px"></div>
								</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- CORPO -->
	<div class="col-md-3 hidden-sm-down">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">Top autores</div>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Autor</th>
							<th>Nota</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${mb.topAutores}" var="autor" varStatus="loop">
							<tr class="clickable-row" data-href="./autor.jsp?id=${autor.id}">
								<td>${loop.count}</td>
								<td>${autor.nome}</td>
								<td><fmt:formatNumber type="number" maxIntegerDigits="1"
										maxFractionDigits="1" value="${autor.mediaAutor}" /><span
									class="glyphicon glyphicon-star" style="padding-left: 5px;"></span></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">Top Livros</div>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Livro</th>
							<th>Nota</th>
						</tr>
					</thead>
					<c:forEach items="${mb.topLivros}" var="livro" varStatus="loop">
						<tr class="clickable-row" data-href='./livro.jsp?id=${livro.id}'>
							<td>${loop.count}</td>
							<td>${livro.nome}</td>
							<td><fmt:formatNumber type="number" maxIntegerDigits="1"
									maxFractionDigits="1" value="${livro.mediaVotos}" /><span
								class="glyphicon glyphicon-star" style="padding-left: 5px;"></span></td>

						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<div class="col-md-3">
		<div class="panel panel-primary">
			<c:choose>
				<c:when test="${userID != null}">
					<div class="panel-heading">
						<div class="panel-title">Perfil</div>
					</div>
					<div class="panel-body">
						<h3>
							Olá,
							<%=u.getNome()%></h3>
						<%
							if (u.getPerfil().equals("adm")) {
										out.print(
												"<h4><a href=\"adm/index.jsp\">Ir para o painel de controle do administrador</a></h4>");
									}
						%>
						<form action="Login?cmd=logout" method="post">
							<button type="submit" class="btn btn-danger btn-block">Logout</button>
						</form>
					</div>
				</c:when>
				<c:otherwise>
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
							<div id="msg">${param.logMsg}</div>
							<div class="col-lg-6">
								<button class="btn btn-primary btn-block" type="submit">Entrar</button>
							</div>
							<div class="col-lg-6">
								<button class="btn btn-info btn-block" type="button"
									onclick="parent.location='cadastro.jsp'">Cadastrar...</button>
							</div>
							<div class="col-sm-12" style="text-align: right; margin-top: 5px">
								<a href="#" onclick="$('#modalRecSenha').modal()">Esqueceu
									sua senha?</a>
							</div>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<t:modalResc></t:modalResc>

</body>
</body>
</html>