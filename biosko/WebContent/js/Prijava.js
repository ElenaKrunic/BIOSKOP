$(document).ready(function(){
	var userNameInput = $('#userNameInput'); 
	var passwordInput = $('#passwordInput'); 
	
	$('#loginSubmit').on('click', function(event) {
		var userName = userNameInput.val(); 
		var password = passwordInput.val(); 
		console.log('username je ' + userName); 
		console.log('password je ' + password); 
		
		var params = {
				 action : "login",
				'userName' : userName, 
				'password' : password
		}
		
		$.post('KorisnikServlet', params, function(data) {
			
			//ovo je duzi nacin 
			var response = JSON.parse(data); 
			
			if(response.status) {
				window.location.href="Glavna.html"; 
			} else {
				//pushNotification("red", response.message); 
				alert("Niste se prijavili. Pokusajte ponovo!"); 
			}
		});
		
		//console.log('Program se nastavlja prije izvrsavanja POSTa!'); 
		event.preventDefault(); 
		return false; 
	});
});
