<%@page import="java.util.*, entity.Usuario, persistence.UsuarioDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<Usuario> usuarios = new ArrayList<>();
	UsuarioDao ud = new UsuarioDao();

	try {
		usuarios = ud.findAll();
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
<script type="text/javascript" src="/js/scripts.js"></script>
<!-- TABLES -->
<link rel="stylesheet" 
href="https://cdn.datatables.net/1.10.13/css/dataTables.bootstrap.min.css">
<script type="text/javascript" 
src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" 
src="https://cdn.datatables.net/1.10.13/js/dataTables.bootstrap.min.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="/css/style.css">
<script>
	$(document).ready(function() {
		$('#tabela-users').DataTable({
			 'language': {
		            'url': '//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Portuguese.json'
			 }
			
		});
		
		$('.btn-delete').click(function(){
			id = $(this).attr('id').replace("delete", "");
			var dados = {
					cmd:"ajaxDeleteUsuario",
					idUsuario:id
					
			};
			$.post('../Gravar', $.param(dados), function(response){
					alert(response);
					location.reload();
				});
		});
		
		$('.btn-edit').click(function(){
			id = $(this).attr('id').replace("edit", "");
			parent.location = "./edit.jsp?item=usuario&id=" + id +"&ref=users.jsp";
		});
	});
</script>
<title>Usuários</title>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">Usuários</div>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-hover" id="tabela-users">
					<thead>
						<tr>
							<th>id</th>
							<th>Nome</th>
							<th>E-mail</th>
							<th>Perfil</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="<%=usuarios%>" var="usr">
							<tr>
								<td>${usr.id }</td>
								<td>${usr.nome }</td>
								<td>${usr.email }</td>
								<td>${usr.perfil }</td>
								<td width="27px"><button class="btn btn-info btn-edit" id="edit${usr.id }">
										<span class="glyphicon glyphicon-pencil"></span>
									</button></td>
								<td width="27px"><button class="btn btn-danger btn-delete" id="delete${usr.id }">
										<span class="glyphicon glyphicon-trash"></span>
									</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<t:return></t:return>
	</div>
</body>
</html>