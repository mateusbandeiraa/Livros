function adjustISBN() {
			var radio = PF('RadioISBN').getJQ().find(':checked').val();
			var mask = PF('isbn');
			if (radio == "0") {
				mask.jq.mask('9-9999-9999-9');
				mask.jq.focus();
			} else {
				var anterior = mask.jq.val();
				mask.jq.mask('999-9-9999-9999-9');
				mask.jq.focus();
			}
		}