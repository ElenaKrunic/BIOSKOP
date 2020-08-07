var params = {
			'action' : "getUserSessionInfo"
	}
	
	$.post('KorisnikServlet',params, function(data){
		var response = JSON.parse(data); 
		
		//console.log(response);
		localStorage['uloga'] = response.uloga; 
		localStorage['userName'] = response.userName; 
		localStorage['status'] = response.status; //da li da izbacim status 
		localStorage['ID'] = response.ID; 
		
		
		if(response.uloga == 'obicanKorisnik' && response.username != null) {
			alert("Ulogovali ste se kao obican korisnik!"); 
		} else {
			alert ("Ulogovali ste se kao admin!"); 
		}
});