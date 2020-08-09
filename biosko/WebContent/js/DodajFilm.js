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
	
	console.log("DODAJ FILM JS");
	
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








/*
$(document).ready(function(){
	("#logoutLink").on("click", function(event){
		$.get("LogoutServlet", function(data){
			console.log(data); 
			
			if(data.status == "unauthenticated") {
				window.location.replace("Prijava.html"); 
				return;
			}
		});
		event.preventDefault(); 
		return false; 
	});
	
	var nazivFilma = $("#nazivFilma"); 
	var zanrFilma = $("#zanrFilma"); 
	var trajanje = $("#trajanje"); 
	var distributer = $("#distributer"); 
	var zemljaPorijekla = $("#zemljaPorijekla"); 
	var godinaProizvodnje = $("#godinaProizvodnje"); 
	
	$("#addSubmit").on("click", function(event){
		var naziv = nazivFilma.val(); 
		var zanr = zanrFilma.val(); 
		var trajanje = trajanje.val(); 
		var distributer = distributer.val(); 
		var zemljaPorijekla = zemljaPorijekla.val(); 
		var godinaProizvodje = godinaProizvodnje.val(); 
		
		console.log("naziv" + naziv); 
		console.log("znar" + zanr); 
		console.log("trajanje" + trajanje);
		console.log("distributer" + distributer); 
		console.log("zemljaPorijekla" + zemljaPorijekla); 
		console.log("godinaProizvodnje" + godinaProizvodnje); 
		
		params = {
				"action" : "add", 
				"naziv" : naziv, 
				"zanr" : zanr, 
				"trajanje" : trajanje,
				"distributer" : distributer,
				"zemljaPorijekla" : zemljaPorijekla, 
				"godinaProizvodnje" : godinaProizvodnje
		}; 
		$.post("FilmServlet", params, function(event){
			console.log(data); 
			
			if(data.status == "unauthenticated") {
				window.location.return("Prijava.html"); 
				return;
			}
			if(data.status == "success") {
				window.location.replace("Filmovi.html"); 
			}
		}); 
		
		event.preventDefault(); 
		return false;
	});
});
*/