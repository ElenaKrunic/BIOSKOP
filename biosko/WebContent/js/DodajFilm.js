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
	
	//svakako samo admin moze da doda film 
	
	$("#izvjestaj").show();
	
	//console.log("DODAJ FILM JS");
	
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
			document.getElementById('dodajZanrFilma').append(z);
		}
	}
});
	
	$("#dodajFilmBtnStranica").on('click',function(){
		
		var params = {
				'action' : "addMovie", 
				'nazivFilma' : $("#dodajNazivFilma").val(), 
				'reziserFilma' : $("#dodajReziserFilma").val(), 
				'glumciFilma' : $("#dodajGlumciFilma").val(), 
				'trajanjeFilma' : $("#dodajTrajanje").val(), 
				'zanrFilma' : $("#dodajZanrFilma").val().join(";"), 
				'distributerFilma' : $("#dodajDistributer").val(), 
				'zemljaPorijeklaFilma' : $("#dodajZemljaPorijekla").val(), 
				'godinaProizvodnjeFilma' : $("#dodajGodinaProizvodnje").val(),
				'opisFilma' : $("#dodajOpis").val() 
		}
		//console.log("Pokupljeni parametri " + params); 
		
		$.post('FilmoviServlet',params,function(data){
			var response = JSON.parse(data); 
			if(response.status) {
				window.location.href = "Filmovi.html"; 
			} 
		});
	}); 
});
