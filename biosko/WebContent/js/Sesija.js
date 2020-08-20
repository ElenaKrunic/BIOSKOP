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
		
		if(response.status) {
			document.getElementById('mojp').href="MojNalog2.html?id="+response.ID;
		}
});