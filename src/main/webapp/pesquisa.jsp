<%@page import="java.util.List"%>
<%@page import="entity.*, persistence.*, java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="/livros/css/style.css">
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
// 	for (Pesquisavel p : results) {
// 		String descricao = p.getDescricao();

// 		if (descricao.length() > 140) {
// 			descricao = descricao.substring(0, 140) + "...";
// 		}

// 		p.setDescricao(descricao);
// 	}

// 	System.out.println(results);
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
			<div class="col-sm-6">
				<div class="panel panel-primary">
					<div class="panel-heading"></div>
					<div class="panel-body panel-search">
						<div class="col-sm-4">
								<a href="<%=pag%>"> <img alt="Capa do livro"
									src="src/main/webapp/img/${result.imagem }" width="100%">
								</a>
						</div>
						<div class="col-sm-8 div-panel-search" id="panel-search-${result.id }">
							<h3 style="margin-top: 0px;" id="titulo-search-${result.id }">
								<a href="<%=pag%>">${result.nome }</a>
							</h3>
							<c:if test="<%=p.getClass().equals(Livro.class)%>">
								<h4 id="subtitulo-search-${result.id }">
									por <a href="autor.jsp?id=${result.autor.id }">${result.autor.nome }</a>
								</h4>
							</c:if>
							<div class="txt-field-search" id="div-txt-${result.id }">
								${result.descricao }
							</div>
							<div class="btn btn-info" style="margin-top: 10px" onclick="location.href = '${pag}'">Leia
								Mais...</div>
						</div>
						<script>
						result = ${result.id}
						var painelInteiro = 215;
						var titulo = parseInt($('#titulo-search-' + result).css("height").replace("px", ""));
						var subtitulo = 0;
						try{
							subtitulo = parseInt($('#subtitulo-search-' + result).css("height").replace("px", ""));
							subtitulo += parseInt($('#subtitulo-search-' + result).css("margin-top").replace("px", ""));
							subtitulo += parseInt($('#subtitulo-search-' + result).css("margin-bottom").replace("px", ""));
						} catch (err){
							
						}
						var divTxt = painelInteiro - (titulo + subtitulo + 35);
						$("#div-txt-" + result).attr("style", "height: " + divTxt + "px");
						</script>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>
