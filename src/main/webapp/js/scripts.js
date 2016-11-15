$(document).ready(function() {
		$('#search-field').focus(function() {
			$('#search-group').css("width", "100% ");
		});
		$('#search-field').hover(function() {
			$('#search-group').css("width", "100%");
		}, function() {
			$('#search-group').css("width", "50%");
		});
		$('#search-field').blur(function() {
			$('#search-group').css("width", "50%");
		});

		$('.clickable-row').click(function() {
			window.location = $(this).data('href');
		});
	});