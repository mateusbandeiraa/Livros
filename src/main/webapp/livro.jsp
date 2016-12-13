<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Livro l = new Livro();
	try {
		final Integer id = Integer.valueOf((String) request.getParameter("id"));

		if (id != null) {
			l = new LivroDao().findByCode(id);
		}
		if (l == null)
			throw new Exception();
	} catch (Exception Ex) {
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
<script type="text/javascript" src="./js/scripts.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<link rel="stylesheet" href="css/style.css">
<%@page import="entity.*, persistence.*"%>

<script type="text/javascript">
	$(document).ready(function() {

		//COR DE FUNDO DA NOTA
		if (<%=l.getMediaVotos()%>	<= 2) {
			$('#rate-color').addClass('label label-danger');
		} else if (<%=l.getMediaVotos()%> < 4) {
			$('#rate-color').addClass('label label-warning');
		} else {
			$('#rate-color').addClass('label label-success');
		}
		
		//TENTA RECUPERAR VOTO DO USUARIO
		<% 
		Voto v = new Voto();
		v.setRate(0);
			Integer userID = (Integer) session.getAttribute("userID");
			Integer livroID = Integer.valueOf(request.getParameter("id"));
			System.out.println(userID);
			System.out.println(livroID);
			
			if(userID != null && livroID != null){
			System.out.println("certo");
			VotoDao vd = new VotoDao();
			Voto v2 = vd.findByUserNBook(userID, livroID);
				if (v2 != null){
				v = v2;
				}
			}
			

		%>
		votoUsuario = <%=v.getRate() %>;
		
		
		completeStars(votoUsuario);

		//HOVERS DAS ESTRELAS
		function completeStars(n) {
			emptyStars(5);
			var i = 1;
			while (i <= n) {
				$('#voto' + i).removeClass('glyphicon-star-empty');
				$('#voto' + i).addClass('glyphicon-star');
				i++;
			}
		}
		function emptyStars(n) {
			var i = 1;
			while (i <= n) {
				$('#voto' + i).removeClass('glyphicon-star');
				$('#voto' + i).addClass('glyphicon-star-empty');
				i++;
			}
		}

		$('.star-vote').hover(function() {
			voto = this.id.charAt(4);
			completeStars(voto);
		}, function() {
			voto = this.id.charAt(4);
			emptyStars(voto);
			completeStars(votoUsuario);
		});

		//Voto
		$('.star-vote').click(function() {
			var userID = <%=session.getAttribute("userID")%>;
	;
			if (userID === null) {
				alert('Você precisa estar logado para votar');
				return;
			}
			voto = this.id.charAt(4);
			$('#userID').val(userID);
			$('#nota').val(voto)
			
			$('#form-vote').submit();

		});

	});
</script>
<title><%=l.getNome()%></title>
</head>
<body>
	<!-- NAVBAR -->
	<t:navbar></t:navbar>
	<!-- CONTEÚDO -->
	<div class="container">
		<div class="panel panel-primary">

			<div class="panel-heading"></div>

			<div class="panel-body">
				<div class="col-xs-3 col-md-4 col-lg-3">
					<div class="thumbnail">
						<img alt="Capa do Livro"
							src="src/main/webapp/img/<%=l.getImagem()%>">
						<div class="caption">
							<h3 class="rate-star">
								<%
									Integer rate = l.getMediaVotos().intValue();
								%>
								<c:forEach begin="1" end="<%=rate%>">
									<span class="glyphicon glyphicon-star"></span>
								</c:forEach>

							</h3>
							<h2 class="rate-number">
								<span id="rate-color"> <fmt:formatNumber type="number"
										maxIntegerDigits="1" maxFractionDigits="1"
										value="<%=l.getMediaVotos()%>" />
								</span>
							</h2>
							<h4 class="rate-star">
								<form id="form-vote" action="Gravar?cmd=voto" method="post">
								<input type="hidden" id="userID" name="userID">
								<input type="hidden" id="nota" name="nota">
								<input type="hidden" name="livroID" value="<%=l.getId() %>">
									Sua nota: <span style="white-space: nowrap;"><span
										class="glyphicon glyphicon-star-empty star-vote" id="voto1"></span>
										<span class="glyphicon glyphicon-star-empty star-vote"
										id="voto2"></span> <span
										class="glyphicon glyphicon-star-empty star-vote" id="voto3"></span>
										<span class="glyphicon glyphicon-star-empty star-vote"
										id="voto4"></span> <span
										class="glyphicon glyphicon-star-empty star-vote" id="voto5"></span></span>

								</form>
							</h4>

						</div>
					</div>
				</div>
				<div class="col-md-5 col-lg-6">
					<h2><%=l.getNome()%></h2>
					<h3 style="margin-top: 0;"><%=l.getAutor().getNome()%></h3>
					<p><%=l.getDescricao()%></p>
				</div>
				<div class="hidden-xs hidden-sm col-md-3">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="title3">
								<span class="glyphicon glyphicon-user"></span> Sobre o autor
							</h3>
						</div>
						<div class="panel-body">
							<div class="thumbnail" style="margin-bottom: 10px;">
								<img alt="Foto do autor"
									src="src/main/webapp/img/<%=l.getAutor().getImagem()%>">
							</div>
							<h3><%=l.getAutor().getNome()%></h3>
							<p><%=l.getAutor().getDescricao()%></p>
							<a href="./autor.jsp?id=<%=l.getAutor().getId()%>"
								style="text-align: right;"><h4>Saiba mais...</h4></a>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>