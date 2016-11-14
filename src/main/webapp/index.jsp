<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Levros</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#search-field').focus(function() {
			$('#col-search').addClass("col-sm-offset-3 col-sm-6");
			$('#col-search').removeClass("col-sm-offset-4 col-sm-4");
		});
		$('#search-field').blur(function() {
			$('#col-search').addClass("col-sm-offset-4 col-sm-4");
			$('#col-search').removeClass("col-sm-offset-3 col-sm-6");
		});
	});
</script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="mb" class="manager.HomeManagerBean"></jsp:useBean>
</head>
<body>
	<!-- CABEÇALHO -->
	<div class="jumbotron">
		<div class="container">
			<h1>Levros</h1>
			<h4>O menor banco de dados de livros na internet.</h4>
			<div class="col-sm-offset-4 col-sm-4" id="col-search">
				<form>
					<div class="form-group">
						<div class="input-group">

							<input type="text" class="form-control" placeholder="Buscar"
								id="search-field">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
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
	<div class="col-md-3">
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
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>J. R. R. Tolkien</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">Conteúdo</div>
			</div>
		</div>
	</div>
	<div class="col-md-3">
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
						<tr>
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
	<div class="col-md-1"></div>
</body>
</body>
</html>