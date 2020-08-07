$(document).ready(function(){
	//ako korisnik klikne na logout 
	$('#logoutLink').on('click', function(event){
		$.get('OdjavaServlet', function(data){
			console.log(data); 
			
			if(data.status == 'unauthenticated') {
				window.location.replace('Prijava.html'); 
				return; 
			}
		});
		
		event.preventDefault(); 
		return false; 
	});
	
	var params = {
			action : "loadGenres"
	}
	
	$.post('FilmoviServlet', params, function(data){
	console.log(data); 
	var response = JSON.parse(data); 
	if(response.status) {
		for (i = 0; i < response.zanrovi.length; i++) {
			var z = document.createElement('option'); 
			z.value = response.zanrovi[i]; //vrijednost z ce biti opcije od zanrova
			z.innerText = response.zanrovi[i]; 
			document.getElementById('zanroviFilter').append(z);
		}
	}
});
});