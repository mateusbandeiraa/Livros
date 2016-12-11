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
<title>ADMIN DASHBOARD</title>
</head>
<body>
	<t:navbar></t:navbar>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<div class="panel-title">
					<h3>Admin Dashboard</h3>
				</div>
			</div>
			<div class="panel-body">
				<div class="row" style="padding-bottom: 20px;">
					<div class="col-sm-6">
						<div class="btn btn-lg btn-block btn-success"
							onclick="parent.location = './cadastro.jsp'">
							<span class="glyphicon glyphicon-floppy-disk"></span> Cadastrar
							livros e autores
						</div>
					</div>
					<div class="col-sm-6">
						<div class="btn btn-lg btn-block btn-info"
							onclick="parent.location = //EDITAR//">
							<span class="glyphicon glyphicon-pencil"></span> Editar livros e
							autores
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="btn btn-lg btn-block btn-info"
							onclick="parent.location = //EDITAR//">
							<span class="glyphicon glyphicon-user"></span> Informações de
							usuários
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>