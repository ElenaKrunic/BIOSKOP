$(document).ready(function(){
	var korisnickoImeID = $("#korisnickoImeID");
	var lozinkaID = $("#lozinkaID");
	var ponovljenaLozinkaID = $("#ponovljenaLozinkaID");
	var messageParagraph = $('#messageParagraph');
	
	$("#registracijaSubmit").on("click", function(event) {
		var korisnickoIme = korisnickoImeID.val();
		var lozinka = lozinkaID.val();
		var ponovljenaLozinka = ponovljenaLozinkaID.val();
		console.log("korisnicko ime: " + korisnickoIme);
		console.log("lozinka: " + lozinka);
		console.log("ponovljena lozinka: " + ponovljenaLozinka);

		if (lozinka != ponovljenaLozinka) {
			messageParagraph.text('Lozinke se ne podudaraju!');

			event.preventDefault();
			return false;
		}
		
		var params = {
				"korisnickoIme" : korisnickoIme,
				"lozinka" : lozinka
		}
		$.post("RegistracijaServlet", params, function(data) {
			console.log(data);
			
			if (data.status == 'failure') {
				messageParagraph.text(data.message);
				return;
			}
			
			if (data.status == 'success') {
				window.location.replace('Glavna.html');
			}
		});
		event.preventDefault();
		return false;
	});
});
		