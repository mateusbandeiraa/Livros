<%@ tag language="java" pageEncoding="UTF-8"%>
<script>
	
	$(document).ready(function() {
		$('#btn-enviar-resc').click(function() {
			$('.loader').css('display', '') //Mostra o loader
			$('#btn-enviar-resc').css('display', 'none') //oculta o botão enviar
			var email = $('#rescEmail').val();
			var dados = {
				cmd : "ajaxCreateTicket",
				userEmail : email

			};
			$.post('/livros/Login', $.param(dados), function(response) {
				$('#resp-server-resc-senha').html(response);
				$('.loader').css('display', 'none') //oculta o loader
				$('#btn-fechar-resc').css('display', '') //oculta o botão enviar
			});

		});
	});
</script>

<style>
.loader {
    border: 4px solid #f3f3f3; /* Light grey */
    border-top: 4px solid #3498db; /* Blue */
    border-radius: 50%;
    width: 30px;
    height: 30px;
    animation: spin 2s linear infinite;
    margin: auto;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>

<div class="modal fade" id="modalRecSenha" role="dialog">
	<div class="vertical-alignment-helper">
		<div class="modal-dialog modal-sm vertical-align-center">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>Esqueceu sua senha?</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="email" class="form-control" name="rescEmail"
							id="rescEmail" placeholder="Digite seu e-mail"
							required="required">
					</div>
					<div class="loader" style="display: none;"></div>
					<p id="resp-server-resc-senha"></p>
					<button type="button" class="btn btn-info" id="btn-enviar-resc">Enviar</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal" style="display: none;" id="btn-fechar-resc">Fechar</button>
				</div>
			</div>
		</div>
	</div>
</div>