$(document).ready(function(){
	alert("otvoren dokument");
	var userName = $("#userName");
	var password = $("#password");
	var repeatedPassword = $("#repeatedPassword");
	var messageParagraph = $('#messageParagraph');
	
	$("#registracijaSubmit").on("click", function(event) {
		alert("kliknuto dugme");
		var userName = userName.val();
		var password = password.val();
		var repeatedPassword = repeatedPassword.val();
		console.log("userName: " + userName);
		console.log("password: " + password);
		console.log("repeatedPassword: " + repeatedPassword);

		if (password != repeatedPassword) {
			messageParagraph.text('Lozinke se ne podudaraju!');

			event.preventDefault();
			return false;
		}
		
		var params = {
				"username" : username,
				"password" : password
		}
		$.post("RegistracijaServlet", params, function(data) {
			console.log(data);
			
			if (data.status == 'nije uspjelo') {
				messageParagraph.text(data.message);
				return;
			}
			
			if (data.status == 'uspjelo') {
				window.location.replace('Bioskop.html');
			}
		});
		event.preventDefault();
		return false;
	});
});
		