<%@page
	import="java.util.List, java.util.ArrayList, entity.*, persistence.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String item = request.getParameter("item");
	Integer itemID = 0;
	Livro l = null;
	Autor a = null;
	Usuario u = null;
	try {
		itemID = new Integer(request.getParameter("id"));
		if (item.equalsIgnoreCase("livro")) {
			l = new LivroDao().findByCode(itemID);
		} else if (item.equalsIgnoreCase("autor")) {
			a = new AutorDao().findByCode(itemID);
		} else if (item.equalsIgnoreCase("usuario")) {
			u = new UsuarioDao().findByCode(itemID);
		} else {
			throw new Exception();
		}

		if (l == null && a == null && u == null)
			throw new Exception();
	} catch (Exception ex) {
		request.getRequestDispatcher("../404.jsp").forward(request, response);
	}

	List<Autor> autores = new ArrayList<>();
	autores = new AutorDao().findAll();
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
<script type="text/javascript" src="/livros/js/scripts.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<!-- SELECT -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>
<!-- /SELECT -->
<!-- FILE INPUT -->
<link rel="stylesheet" href="/livros/file-upload/css/fileinput.min.css">
<script type="text/javascript"
	src="/livros/file-upload/js/fileinput.min.js"></script>
<script type="text/javascript"
	src="/livros/file-upload/js/locales/pt-BR.js"></script>
<!-- MASK -->
<script type="text/javascript"
	src="/livros/js/jquery.maskedinput.min.js"></script>
<link rel="stylesheet" href="/livros/css/style.css">
<script>
	$(document).ready(function() {
		var max = 1500;
		
		$('#btn-return').click(function(){
			window.location = "./dashboard.jsp"
		});
	});
</script>
<title>Editar</title>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="col-sm-offset-2 col-sm-8">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">
						Editar
						<%=item%></div>
				</div>
				<div class="panel-body">
					<c:if test="${param['item'] == 'livro' }">
						<form class="form-horizontal" action="../Gravar?cmd=editLivro"
							method="post" enctype="multipart/form-data">
							<div class="form-group">
								<label for="idLivro" class="control-label col-sm-2">ID:</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="idLivro"
										name="idLivro" required readonly value="<%=l.getId()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="nomeLivro" class="control-label col-sm-2">Nome:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="nomeLivro"
										name="nomeLivro" required value="<%=l.getNome()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="nome" class="control-label col-sm-2">ISBN:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="isbn" name="isbn"
										required value="<%=l.getIsbn()%>">
									<script type="text/javascript">
									$('#isbn').mask('9?9?9?-?99-999-9999-9');
								</script>
								</div>
							</div>
							<div class="form-group">
								<label for="editora" class="control-label col-sm-2">Editora:</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="editora"
										name="editora" required value="<%=l.getEditora()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="capaLivro" class="control-label col-sm-2">Capa:</label>
								<div class="col-sm-10">
									<input type="file" id="capaLivro" name="capaLivro"
										accept="image/*">
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
										name="descricaoLivro" class="form-control" required><%=l.getDescricao()%></textarea>
									<p>
										<label id="desc-liv-count" class="control-label"></label>
									</p>
									<script>
								var max = 1500;
								var dig = $('#descricaoLivro').val().length;
								$('#desc-liv-count').html(max - dig + ' caracteres restantes');

								$('#descricaoLivro').keyup(function() {
									dig = $('#descricaoLivro').val().length;

									$('#desc-liv-count').html(max - dig + ' caracteres restantes');
								});
								</script>
								</div>
							</div>
							<div class="form-group">
								<label for="autorLivro" class="control-label col-sm-2">Autor:</label>
								<div class="col-sm-10">
									<select class="form-control selectpicker" data-width="100%"
										id="autorLivro" name="autorLivro" required
										data-live-search="true" title="Selecione um autor..."
										data-dropup-auto="false" data-container=".body">
										<c:forEach items="<%=autores%>" var="autor">
											<option value="${autor.id}">${autor.nome}</option>
										</c:forEach>
									</select>
									<script>
								$('#autorLivro').selectpicker('val', '<%=l.getAutor().getId()%>');
								</script>
								</div>
							</div>
							<div class="col-sm-8">
								<button type="submit" class="btn btn-primary btn-block">
									<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
								</button>
							</div>
						</form>
						<div class="col-sm-4">
							<form action="/livros/Gravar?cmd=deleteLivro&id=${param['id'] }"
								method="POST" id="form-apagar-livro">
								<button type="button" class="btn btn-danger btn-block"
									id="btn-apagar-livro">
									<span class="glyphicon glyphicon-remove-circle"></span> Apagar
								</button>
							</form>
							<script>
								$('#btn-apagar-livro').click(function(){
									if(confirm("Deseja realmente apagar esse livro?")){
										$("#form-apagar-livro").submit();
									}
								});
							</script>
						</div>
					
					${param['msgLivro']}
					</c:if>
					<c:if test="${param['item'] == 'autor' }">
						<form class="form-horizontal"
							action="/livros/Gravar?cmd=editAutor" method="post"
							enctype="multipart/form-data">
							<div class="form-group">
								<label for="idAutor" class="control-label col-sm-2">ID:</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="idAutor"
										name="idAutor" required readonly value="<%=a.getId()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="nomeAutor" class="control-label col-sm-2">Nome:
								</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="nomeAutor"
										name="nomeAutor" value="<%=a.getNome()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="fotoAutor" class="control-label col-sm-2">Foto:</label>
								<div class="col-sm-10">
									<input type="file" id="fotoAutor" name="fotoAutor"
										accept="image/*">
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
										name="descricaoAutor" class="form-control" required><%=a.getDescricao()%></textarea>
									<p>
										<label id="desc-aut-count" class="control-label"></label>
									</p>
									<script>
								var dig2 = $('#descricaoAutor').val().length;
								$('#desc-aut-count').html(max - dig2 + ' caracteres restantes');

								$('#descricaoAutor').keyup(function() {
									dig2 = $('#descricaoAutor').val().length;

									$('#desc-aut-count').html(max - dig2 + ' caracteres restantes');
								});
								</script>
								</div>
							</div>
							<div class="col-sm-8">
								<button type="submit" class="btn btn-primary btn-block">
									<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
								</button>
							</div>
						</form>
						<div class="col-sm-4">
							<form action="/livros/Gravar?cmd=deleteAutor&id=${param['id'] }"
								method="POST" id="form-apagar-autor">
								<button type="button" class="btn btn-danger btn-block"
									id="btn-apagar-autor">
									<span class="glyphicon glyphicon-remove-circle"></span> Apagar
								</button>
							</form>
							<script>
								$('#btn-apagar-autor').click(function(){
									if(confirm("Deseja realmente apagar esse autor?")){
										$("#form-apagar-autor").submit();
									}
								});
							</script>
						</div>
						${param['msgAutor']}
					</c:if>
					<c:if test="${param['item'] == 'usuario' }">

						<form class="form-horizontal"
							action="/livros/Gravar?cmd=editUsuario" method="post">
							<div class="form-group">
								<label for="idUsuario" class="control-label col-sm-2">ID:</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" id="idUsuario"
										name="idUsuario" required readonly value="<%=u.getId()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="nomeUsuario" class="control-label col-sm-2">Nome:
								</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="nomeUsuario"
										name="nomeUsuario" value="<%=u.getNome()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="emailUsuario" class="control-label col-sm-2">E-mail:
								</label>
								<div class="col-sm-10">
									<input type="email" class="form-control" id="emailUsuario"
										name="emailUsuario" value="<%=u.getEmail()%>">
								</div>
							</div>
							<div class="form-group">
								<label for="perfilUsuario" class="control-label col-sm-2">Perfil:
								</label>
								<div class="col-sm-10">
									<select class="form-control selectpicker" id="perfilUsuario"
										name="perfilUsuario" required data-dropup-auto="false"
										data-container=".body">
										<option value="adm">ADM</option>
										<option value="usu">USU</option>
									</select>

									<script>
								$('#perfilUsuario').selectpicker('val', '<%=u.getPerfil()%>');
									</script>
								</div>
							</div>
							<button type="submit" class="btn btn-primary btn-block">
								<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
							</button>
						</form>
						${param['msgUsuario']}
						

					</c:if>
				</div>
			</div>
		</div>
		<c:if test="${param['ref'] == 'users.jsp'}">
			<div class="col-xs-12">
				<div class="btn btn-info btn-lg"
					onclick="parent.location = './users.jsp';">
					<span class="glyphicon glyphicon-chevron-left"></span>Voltar à
					lista de usuários
				</div>
			</div>
		</c:if>
		<c:if test="${param['ref'] != 'users.jsp'}">
			<t:return></t:return>
		</c:if>
	</div>
</body>
</html>