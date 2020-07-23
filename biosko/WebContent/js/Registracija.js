$(document).ready(function(){
	var userNameInput = $('#userNameInput0'); 
	var passwordInput = $('#passwordInput0'); 
	var repeatedPasswordInput = $('#repeatedPasswordInput0'); 
	
	var messageParagraph = $('#messageParagraph'); 
	
	$('#registrationSubmit').on('click', function(event) {
		var userName = userNameInput.val(); 
		var password = passwordInput.val(); 
		var repeatedPassword = repeatedPasswordInput.val(); 
		
		console.log('username je' + userName); 
		console.log('password je ' + password); 
		console.log('repeated password je ' + repeatedPassword); 
		
		if(password != repeatedPassword) {
			alert('Lozinke se ne podudaraju!') ; 
			event.preventDefault(); 
			return false; 
		}
		
		var params = {
				'action' : 'registration',
				'username' : userName,  
				'password' : password,
		}
		
		$.post('KorisnikServlet', params, function(data) {
			console.log(status); 
			var response = JSON.parse(data); 
			
			if(response.status) {
				window.location.href = "Prijava.html"; 
			}
			else {
				alert("Niste se uspjeli ulogovati! Probajte ponovo!"); 
			}
			
			/*
			if(data.status == 'failure') {
				alert(data.message);
				return; 
			}
			if(data.status == 'success') {
				alert('Uspjesna registracija! Cestitamo! '); 
				window.location.replace('Prijava.html'); 
			}
			*/
		});
		
		event.preventDefault(); 
		return false; 
	});
}); 