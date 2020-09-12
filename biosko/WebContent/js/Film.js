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
	
	if(localStorage['uloga'] == "Admin"){
		$("#izvjestaj").show();
	}
	
	if(localStorage['uloga'] == 'null') {
		$("#izvjestaj").remove();
		$("#logoutLink").remove();
	}
	
var url = window.location.href; 
var newUrl = new URL(url); 
var id = newUrl.searchParams.get("id"); 

ucitajFilm(id); 

function ucitajFilm(idFILMA) {
	var params = {
			action : "ucitajFilm", 
			filmId : idFILMA
	}
	
	if(id==null){
		alert("Doslo do greske");
	}
	
	$.post('FilmoviServlet', params, function(data){
		var response = JSON.parse(data);
		
		//console.log("response sa servleta " + response); 
		
		if(response.status) {
			var pojedinacanFilm  = response.film; 
			//console.log("var jedan film " + pojedinacanFilm ); 
			$("#nazivFilma3").text(pojedinacanFilm.Naziv);
			$("#reziserFilma3").text(pojedinacanFilm.Reziser);
			$("#glumciFilma3").text(pojedinacanFilm.Glumci.join(","));
			$("#trajanje3").text(pojedinacanFilm.Trajanje);
			$("#zanrFilma3").text(pojedinacanFilm.Zanrovi.join(","));
			$("#distributer3").text(pojedinacanFilm.Distributer);
			$("#zemljaPorijekla3").text(pojedinacanFilm.Zemlja_Porekla);
			$("#godinaProizvodnje3").text(pojedinacanFilm.Godina_Proizvodnje);
			$("#opis3").text(pojedinacanFilm.Opis);
					
		}
	});
}

	});
