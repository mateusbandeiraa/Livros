<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Autor a = new Autor();
	List<Livro> livros = new ArrayList<>();

	try {

		final Integer id = Integer.valueOf((String) request.getParameter("id"));
		if (id != null) {
			a = new AutorDao().findByCode(id);
		}

		if (a == null)
			throw new Exception();

		livros = a.getLivros();
	} catch (Exception ex) {
		request.getRequestDispatcher("404.jsp").forward(request, response);
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
<script type="text/javascript" src="/livros/js/scripts.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="/livros/css/style.css">
<%@page
	import="entity.*, persistence.*, java.util.ArrayList, java.util.Comparator, java.util.List"%>

<title><%=a.getNome()%></title>
</head>
<body>
	<!-- NAVBAR -->
	<t:navbar></t:navbar>
	<!-- CONTEÚDO -->
	<div class="container">
		<div class="col-md-12">
			<div class="panel panel-primary">

				<div class="panel-heading"></div>

				<div class="panel-body">
					<div class="col-md-3">
						<div class="thumbnail">
							<img alt="Foto do autor"
								src="src/main/webapp/img/<%=a.getImagem()%>">
						</div>
					</div>
					<div class="col-md-5">
						<h2><%=a.getNome()%></h2>
						<p><%=a.getDescricao()%></p>
					</div>
					<div class="col-md-4">
						<div class="panel panel-info">
							<div class="panel-heading">
								<h3 class="title3">
									<span class="glyphicon glyphicon-book"></span> Obras de
									<%=a.getNome()%>
								</h3>
							</div>
							<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>Nome</th>
											<th>Nota</th>
										</tr>
									</thead>
									<c:forEach items="<%=livros%>" var="livro">
										<tr class="clickable-row"
											data-href='./livro.jsp?id=${livro.id}'>
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
				</div>

			</div>
		</div>
	</div>
</body>
</html>