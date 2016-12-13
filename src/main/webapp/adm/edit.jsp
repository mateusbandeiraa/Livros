<%@page
	import="java.util.List, java.util.ArrayList, entity.*, persistence.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<Livro> livros = new ArrayList<>();

	try {
		livros = new LivroDao().findAll();
	} catch (Exception ex) {
		ex.printStackTrace();
	}
%>
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<!-- SELECT -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>
<!-- /SELECT -->
<link rel="stylesheet" href="css/style.css">
<title>Editar</title>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="col-sm-offset-1 col-sm-5">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">Editar Livros</div>
				</div>
				<div class="panel-body">
					<select id="selectLivro" class="selectpicker" data-width="100%" data-live-search="true" title="Selecione um livro..." data-dropup-auto="false" data-container=".body">
						<c:forEach items="<%=livros%>" var="livro">
							<option>${livro.nome }</option>
						</c:forEach>
					</select>
					<div class="btn btn-primary btn-block btn-md" style="margin-top:15px;">Editar livro</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>