<%@page import="entity.*, persistence.*, java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Livro l = new Livro();
	final Integer id = Integer.valueOf((String) request.getParameter("id"));
	try {
		if (id != null) {
			l = new LivroDao().findByCode(id);
		}
		if (l == null)
			throw new Exception();
	} catch (Exception Ex) {
		request.getRequestDispatcher("404.jsp").forward(request, response);
	}
	int nVotos = new VotoDao().getNVotos(l);
	String strVotos;
	if (nVotos == 1) {
		strVotos = nVotos + " voto";
	} else {
		strVotos = nVotos + " votos";
	}

	Autor a = l.getAutor();
	if (a.getDescricao().length() > 140) {
		String desc = a.getDescricao();
		a.setDescricao(desc.substring(0, 140) + "...");
	}
	List<Comentario> comentarios = new ComentarioDao().findByBook(l.getId());
	request.setAttribute("temComentarios", !comentarios.isEmpty());
%>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=0">
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
<style>
.remove-com-btn {
	cursor: pointer;
	color: red;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//COR DE FUNDO DA NOTA
						if (
<%=l.getMediaVotos()%>
	<= 2) {
							$('#rate-color').addClass('label label-danger');
						} else if (
<%=l.getMediaVotos()%>
	< 4) {
							$('#rate-color').addClass('label label-warning');
						} else {
							$('#rate-color').addClass('label label-success');
						}

						//TENTA RECUPERAR VOTO DO USUARIO
<%Voto v = new Voto();
			v.setRate(0);
			Integer userID = (Integer) session.getAttribute("userID");
			Integer livroID = Integer.valueOf(request.getParameter("id"));

			if (userID != null && livroID != null) {
				VotoDao vd = new VotoDao();
				Voto v2 = vd.findByUserNBook(userID, livroID);
				if (v2 != null) {
					v = v2;
				}
			}%>
	votoUsuario =
<%=v.getRate()%>
	;

						completeStars(votoUsuario);
						//HOVERS DAS ESTRELAS
						function completeStars(n) {
							emptyStars(5);
							var i = 1;
							while (i <= n) {
								$('#voto' + i).removeClass(
										'glyphicon-star-empty');
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
							var userID =
<%=session.getAttribute("userID")%>
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

						var max = 500;
						var dig = $('#commentContent').val().length;
						$('#comm-cont-count').html(
								max - dig + ' caracteres restantes');
						$('#commentContent')
								.keyup(
										function() {
											dig = $('#commentContent').val().length;
											$('#comm-cont-count')
													.html(
															max
																	- dig
																	+ ' caracteres restantes');
										});

						//Apagar comentário
						$('.remove-com-btn').click(
								function() {
									id = $(this).attr('id').replace("remove",
											"");
									var dados = {
										cmd : "ajaxDeleteComentario",
										idComentario : id

									};
									$.post('/livros/Gravar', $.param(dados),
											function(response) {
												location.reload();
											});
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
				<div class="col-xs-6 col-sm-5 col-md-4 col-lg-3">
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
							<h5 style="text-align: center;">
								(<%=strVotos%>)
							</h5>
							<form id="form-vote" action="/livros/Gravar?cmd=voto"
								method="post">
								<h4 class="rate-star">
									<input type="hidden" id="userID" name="userID"> <input
										type="hidden" id="nota" name="nota"> <input
										type="hidden" name="livroID" value="<%=l.getId()%>">
									Sua nota: <span style="white-space: nowrap;"> <span
										class="glyphicon glyphicon-remove-circle star-vote" id="voto0"
										title="Apagar voto"></span>
										<span class="glyphicon glyphicon-star-empty star-vote" id="voto1"></span>
										<span class="glyphicon glyphicon-star-empty star-vote" id="voto2"></span>
										<span class="glyphicon glyphicon-star-empty star-vote" id="voto3"></span>
										<span class="glyphicon glyphicon-star-empty star-vote" id="voto4"></span>
										<span class="glyphicon glyphicon-star-empty star-vote" id="voto5"></span>
									</span>
								</h4>
							</form>

						</div>
					</div>
				</div>
				<div class="col-xs-6 col-sm-7 col-md-8 col-lg-6">
					<h2><%=l.getNome()%></h2>
					<h3 style="margin-top: 0;"><%=l.getAutor().getNome()%></h3>
					<p><%=l.getDescricao()%></p>
				</div>
				<div class="col-xs-12 col-md-8 col-lg-3">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="title3">
								<span class="glyphicon glyphicon-user"></span> Sobre o autor
							</h3>
						</div>
						<div class="panel-body">
							<div class="col-xs-4 col-lg-12">
								<div class="thumbnail" style="margin-bottom: 10px;">
									<img alt="Foto do autor"
										src="src/main/webapp/img/<%=a.getImagem()%>">
								</div>
							</div>
							<div class="col-xs-8 col-lg-12">
								<h3 style="margin-top: 5px;"><%=a.getNome()%></h3>
								<p><%=a.getDescricao()%></p>
								<h4 style="text-align: right;">
									<a href="./autor.jsp?id=<%=a.getId()%>">Saiba mais...</a>
								</h4>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<div class="col-sm-offset-2 col-sm-8">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="panel-title">Comentários</div>
				</div>
				<div class="panel-body">
					<c:choose>
						<c:when test="${temComentarios == false}">
							<h3>Esse livro ainda não tem comentários.</h3>
						</c:when>
						<c:otherwise>
							<c:forEach items="<%=comentarios%>" var="comentario">
								<div class="panel panel-primary">
									<div class="panel-body">
										<h4>
											<c:if
												test="${userID == comentario.usuario.id || userProf == 'adm'}">
												<span
													class="glyphicon glyphicon-remove-circle remove-com-btn"
													id="remove${comentario.id }"></span>
											</c:if>
											${comentario.usuario.nome } disse:
										</h4>
										<p>${comentario.content }</p>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="panel-footer">
					<c:choose>
						<c:when test="${userID != null }">

							<form class="horizontal-form" method="POST"
								action="/livros/Gravar?cmd=comentario">
								<input type="hidden" name="livroID" value="<%=l.getId()%>">
								<input type="hidden" name="userID"
									value="<%=session.getAttribute("userID")%>">
								<div class="form-group">
									<textarea maxlength="500" id="commentContent"
										name="commentContent" class="form-control" required
										placeholder="O que você achou desse livro?"></textarea>
									<p style="margin-top: 5px">
										<label id="comm-cont-count" class="control-label"></label>
										<button type="submit" class="btn btn-primary"
											style="position: absolute; right: 30px;">Comentar</button>
									</p>
								</div>
							</form>

						</c:when>
						<c:otherwise>
							<h4>Faça o login para comentar.</h4>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>