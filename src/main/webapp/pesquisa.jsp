<%@page import="java.util.List"%>
<%@page import="entity.*, persistence.*, java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="css/style.css">
<title>Pesquisa:</title>
<%
	String termos = request.getParameter("terms");

	List<Livro> livros = new LivroDao().findListByName(termos);
	List<Autor> autores = new AutorDao().findByName(termos);

	//System.out.println(livros);
	//System.out.println(autores);

	List<Pesquisavel> results = new ArrayList<>();
	results.addAll(livros);
	results.addAll(autores);

	//Reduz tamanho das descrições
	for (Pesquisavel p : results) {
		String descricao = p.getDescricao();

		if (descricao.length() > 95) {
			descricao = descricao.substring(0, 95) + "...";
		}

		p.setDescricao(descricao);
	}

	System.out.println(results);
%>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="jumbotron" style="margin-top: -20px;">
		<div class="container">
			<h2 style="text-align: center;">Resultado da pesquisa</h2>
			<div class="container" id="con-search">
				<form id="search-form">
					<div class="form-group">
						<div class="input-group" id="search-group">

							<input type="text" class="form-control search-field"
								placeholder="Buscar" id="search-text" value="<%=termos %>">
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
	<div class="container">
		<c:forEach items="<%=results%>" var="result">
			<%
				Pesquisavel p = (Pesquisavel) pageContext.getAttribute("result");
					String pag;
					if (p.getClass().equals(Livro.class)) {
						pag = "livro.jsp?id=" + p.getId();
					} else {
						pag = "autor.jsp?id=" + p.getId();
					}
					pageContext.setAttribute("pag", pag);
			%>
			<div class="col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading"></div>
					<div class="panel-body" style="height: 225px;">

						<a href="<%=pag%>"> <img alt="Capa do livro"
							src="src/main/webapp/img/${result.imagem }" height="100%"
							style="max-width: 195px; float: left; padding-right: 15px">
						</a>

						<h3 style="margin-top: 0px;">
							<a href="<%=pag%>">${result.nome }</a>
						</h3>
						<c:if test="<%=p.getClass().equals(Livro.class)%>">
							<h4>
								por <a href="autor.jsp?id=${result.autor.id }">${result.autor.nome }</a>
							</h4>
						</c:if>
						<p>${result.descricao }</p>
						<div class="btn btn-info" onclick="location.href = '${pag}'">Leia
							Mais...</div>

					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>