<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List, java.util.ArrayList, entity.*, persistence.*"%>
<%
	List<Livro> livros = new ArrayList<>();
	List<Autor> autores = new ArrayList<>();

	try {
		livros = new LivroDao().findAll();
		autores = new AutorDao().findAll();
	} catch (Exception ex) {
		ex.printStackTrace();
	}
%>
<!-- SAI CAGADA -->
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script type="text/javascript" src="/livros/js/scripts.js"></script>
<!-- SELECT -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>
<!-- /SELECT -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="/livros/css/style.css">
<style>
.modal .modal-dialog .modal-content {
	background-color: #000;
}

.close {
	text-color: #eeeeee;
}
</style>
<title>ADMIN DASHBOARD</title>
<script>
$(document).ready(function(){
	$('#btn-edit-livro').click(function(){
		idLivro = $('#selectLivro').val();
		if(idLivro != "")
		window.location = "edit.jsp?item=livro&id=" + idLivro;
	});
	
	$('#btn-edit-autor').click(function(){
		idAutor = $('#selectAutor').val();
		if(idAutor != "")
		window.location = "edit.jsp?item=autor&id=" + idAutor;
	});
});
</script>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">
					<h3>Admin Dashboard</h3>
				</div>
			</div>
			<div class="panel-body">
				<div class="row" style="padding-bottom: 20px;">
					<div class="col-sm-6">
						<div class="btn btn-lg btn-block btn-success"
							onclick="parent.location = './cadastro.jsp'">
							<span class="glyphicon glyphicon-floppy-disk"></span> Cadastrar
							livros e autores
						</div>
						<div class="btn btn-lg btn-block btn-info"
							onclick="parent.location = './users.jsp'" style="margin-top: 15px;">
							<span class="glyphicon glyphicon-user"></span> Informações de
							usuários
						</div>
					</div>
					<div class="col-sm-6">
						<div class="panel panel-info">
							<div class="panel-heading">
								<div class="panel-title">Editar</div>
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-md-6">
										<select id="selectLivro" class="selectpicker"
											data-width="100%" data-live-search="true"
											title="Selecione um livro..." data-dropup-auto="false"
											data-container=".body">
											<c:forEach items="<%=livros%>" var="livro">
												<option value="${livro.id }">${livro.nome }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-6">
										<div class="btn btn-primary btn-block" id="btn-edit-livro">Editar
											livro...</div>
									</div>
								</div>
								<div class="row" style="margin-top: 20px;">
									<div class="col-md-6">
										<select id="selectAutor" class="selectpicker"
											data-width="100%" data-live-search="true"
											title="Selecione um autor..." data-dropup-auto="false"
											data-container=".body">
											<c:forEach items="<%=autores%>" var="autor">
												<option value="${autor.id }">${autor.nome }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-6">
										<div class="btn btn-primary btn-block" id="btn-edit-autor">Editar
											autor...</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<a href="#" data-toggle="modal" data-target="#modal-the-book">The
			Book is on the table.</a>
		<div class="modal fade" id="modal-the-book" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<button type="button" class="close" data-dismiss="modal">
							<span class="glyphicon glyphicon-remove" style="color: #eee"></span>
						</button>

						<img src="/livros/img/the_book_is_on_the_table.png">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>