<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="css/style.css">
<%@page import="entity.*, persistence.*"%>
<%
	final Integer id = Integer.valueOf((String) request.getParameter("id"));
	Livro l = new Livro();
	if (id != null) {
		l = new LivroDao().findByCode(id);
	}
%>
<title><%=l.getNome()%></title>
</head>
<body>
	<!-- CONTEÚDO -->
	<div class="col-sm-7 col-center">
		<div class="panel panel-primary">

			<div class="panel-heading"></div>

			<div class="panel-body">
				<div class="col-sm-3">
					<div class="thumbnail">
						<img alt="Capa do Livro" src="img/Perdido em Marte.jpg">
						<div class="caption"></div>
						<h3 class="rate">
							<%
								Integer rate = l.getMediaVotos().intValue();
							%>
							<c:forEach begin="1" end="<%=rate%>">
								<span class="glyphicon glyphicon-star"></span>
							</c:forEach>

						</h3>
						<h2 class="rate">
							<fmt:formatNumber type="number" maxIntegerDigits="1"
								maxFractionDigits="1" value="<%=l.getMediaVotos()%>" />
							<br />
						</h2>

					</div>
				</div>
				<div class="col-sm-9">
					<h2><%=l.getNome()%></h2>
					<h3 style="margin-top: 0;"><%=l.getAutor().getNome()%></h3>
					<p><%=l.getDescricao()%></p>
				</div>
			</div>

		</div>
	</div>
</body>
</html>