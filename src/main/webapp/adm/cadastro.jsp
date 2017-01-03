<%@page import="entity.*, persistence.*, java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Levros — Cadastro</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.js"></script>
<script type="text/javascript"
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript" src="/livros/js/scripts.js"></script>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	List<Autor> autores = new ArrayList<>();
	autores = new AutorDao().findAll();
%>
<!-- FILE INPUT -->
<link rel="stylesheet" href="/livros/file-upload/css/fileinput.min.css">
<script type="text/javascript"
	src="/livros/file-upload/js/fileinput.min.js"></script>
<script type="text/javascript"
	src="/livros/file-upload/js/locales/pt-BR.js"></script>
<!-- MASK -->
<script type="text/javascript"
	src="/livros/js/jquery.maskedinput.min.js"></script>
<!-- SELECT -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>
<!-- /SELECT -->
<script>
	$(document).ready(function() {
		var max = 1500;
		var dig = $('#descricaoLivro').val().length;
		$('#desc-liv-count').html(max - dig + ' caracteres restantes');

		$('#descricaoLivro').keyup(function() {
			dig = $('#descricaoLivro').val().length;

			$('#desc-liv-count').html(max - dig + ' caracteres restantes');
		});

		var dig2 = $('#descricaoAutor').val().length;
		$('#desc-aut-count').html(max - dig2 + ' caracteres restantes');

		$('#descricaoAutor').keyup(function() {
			dig2 = $('#descricaoAutor').val().length;

			$('#desc-aut-count').html(max - dig2 + ' caracteres restantes');
		});

		$('#btn-return').click(function() {
			window.location = "./dashboard.jsp"
		});
	});
</script>
</head>
<body>
	<t:navbar></t:navbar>

	<div class="container">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">Cadastro de livros</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" action="../Gravar?cmd=livro"
						method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label for="nomeLivro" class="control-label col-sm-2">Nome:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="nomeLivro"
									name="nomeLivro" required>
							</div>
						</div>
						<div class="form-group">
							<label for="nome" class="control-label col-sm-2">ISBN:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="isbn" name="isbn"
									required>
								<script type="text/javascript">
									$('#isbn').mask('9?9?9?-?99-999-9999-9');
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="editora" class="control-label col-sm-2">Editora:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="editora"
									name="editora" required>
							</div>
						</div>
						<div class="form-group">
							<label for="capaLivro" class="control-label col-sm-2">Capa:</label>
							<div class="col-sm-10">
								<input type="file" id="capaLivro" name="capaLivro"
									accept="image/*" required>
								<script type="text/javascript">
									$('#capaLivro')
											.fileinput(
													{
														language : 'pt-BR',
														showUpload : false,
														showPreview : false,
														showRemove : false,
														browseIcon : '<span class="glyphicon glyphicon-picture"></span>',
														browseImage : 'Selecionar...',
													});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="descricaoLivro" class="control-label col-sm-2">Descrição:</label>
							<div class="col-sm-10">
								<textarea maxlength="1500" id="descricaoLivro"
									name="descricaoLivro" class="form-control" required></textarea>
								<p>
									<label id="desc-liv-count" class="control-label"></label>
								</p>
							</div>
						</div>
						<div class="form-group">
							<label for="autor" class="control-label col-sm-2">Autor:</label>
							<div class="col-sm-10">
								<select class="form-control selectpicker" data-width="100%"
									id="autor" name="autor" required data-live-search="true"
									title="Selecione um autor..." data-dropup-auto="false"
									data-container=".body">
									<option value="" selected>Selecione um autor</option>
									<c:forEach items="<%=autores%>" var="autor">
										<option value="${autor.id}">${autor.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<button type="submit" class="btn btn-primary btn-block">
							<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
						</button>
					</form>
				</div>
				<div class="panel-footer">
					<p>${param['msgLivro']}
						<%
							if (request.getParameter("sucessoLivro") != null) {
								out.print("<a href=\"/livros/livro.jsp?id=" + request.getParameter("idCriada")
										+ "\">Acessar página do livro cadastrado</a>");
							}
						%>
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">Cadastro de autores</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" action="../Gravar?cmd=autor"
						method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label for="nomeAutor" class="control-label col-sm-2">Nome:
							</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="nomeAutor"
									name="nomeAutor">
							</div>
						</div>
						<div class="form-group">
							<label for="fotoAutor" class="control-label col-sm-2">Foto:</label>
							<div class="col-sm-10">
								<input type="file" id="fotoAutor" name="fotoAutor"
									accept="image/*" required>
								<script type="text/javascript">
									$('#fotoAutor')
											.fileinput(
													{
														language : 'pt-BR',
														showUpload : false,
														showPreview : false,
														showRemove : false,
														browseIcon : '<span class="glyphicon glyphicon-picture"></span>',
														browseImage : 'Selecionar...',
													});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label for="descricaoAutor" class="control-label col-sm-2">Descrição:</label>
							<div class="col-sm-10">
								<textarea maxlength="1500" id="descricaoAutor"
									name="descricaoAutor" class="form-control" required></textarea>
								<p>
									<label id="desc-aut-count" class="control-label"></label>
								</p>
							</div>
						</div>
						<button type="submit" class="btn btn-primary btn-block">
							<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
						</button>
					</form>
				</div>
				<div class="panel-footer">
					<p>${param['msgAutor']}
						<%
							if (request.getParameter("sucessoAutor") != null) {
								out.print("<a href=\"/livros/autor.jsp?id=" + request.getParameter("idCriada")
										+ "\">Acessar página do autor cadastrado</a>");
							}
						%>
					</p>
				</div>
			</div>
		</div>
		<t:return></t:return>
	</div>
</body>
</html>