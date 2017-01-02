$(document).ready(function() {
			$('.search-field').focus(function() {
				$('#search-group').css("width", "100%");
			});
			$('.search-field').hover(function() {
				$('#search-group').css("width", "100%");
			}, function() {

				if (!$('.search-field').is(':focus')) {
					$('#search-group').css("width", "50%");
				}

			});
			$('.search-field').blur(function() {
				$('#search-group').css("width", "50%");
			});

			$('#search-form').submit(
					function(evt) {
						evt.preventDefault();
						var loc = 'pesquisa.jsp?terms='
								+ encodeURIComponent($('#search-text').val());
						window.location = loc;
					});

			$('.clickable-row').click(function() {
				parent.location = $(this).data('href');
			});
		});