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
			document.getElementById('urediZanrovi').append(z);
		}
	}
});
	
	var url = window.location.href; 
	var newUrl = new URL(url); 
	var id = newUrl.searchParams.get("id"); 
	var idF = id; 
	ucitajFilm(id); 

	function ucitajFilm(idFILMA) {
		var params = {
				action : "ucitajFilm", 
				filmId : idFILMA
		}
	
	$.post('FilmoviServlet', params, function(data){
		var response = JSON.parse(data); 
		
		if(response.status) {
			var pojedinacanFilm = response.film; 
			$("#urediNaziv").val(pojedinacanFilm.Naziv);
			$("#urediReziser").val(pojedinacanFilm.Reziser);
			$("#urediGlumci").val(pojedinacanFilm.Glumci.join(","));
			$("#urediTrajanje").val(pojedinacanFilm.Trajanje);
			$("#urediZanrovi").val(pojedinacanFilm.Zanrovi.join(","));
			$("#urediDistributer").val(pojedinacanFilm.Distributer);
			$("#urediZemljaPorijekla").val(pojedinacanFilm.Zemlja_Porekla);
			$("#urediGodina").val(pojedinacanFilm.Godina_Proizvodnje);
			$("#urediOpis").val(pojedinacanFilm.Opis);
		}
		else {
			alert("Film se ne moze ucitati !"); 
		}
	}); 
	}
	
	$("#urediFilmBtn").on('click',function(data){
		var nazivF = $("#urediNaziv").val(); 
		var reziserF = $("#urediReziser").val(); 
		var glumciF = $("#urediGlumci").val(); 
		var trajanjeF = $("#urediTrajanje").val(); 
		var zanrF = $("#urediZanrovi").val(); 
		var distributerF = $("#urediDistributer").val(); 
		var zemljaF = $("#urediZemljaPorijekla").val(); 
		var godinaF = $("#urediGodina").val(); 
		var opisF = $("#urediOpis").val();
		
		var zanroviJoin = zanrF.join(";"); 
		var glumciJoin = glumciF.split(",").join(";"); 
		
		var params = {
				action : "editMovie", 
				id:idF,
				naziv:nazivF, 
				reziser:reziserF,
				glumci:glumciJoin,
				trajanje:trajanjeF,
				zanr:zanroviJoin,
				distributer:distributerF, 
				zemlja:zemljaF,
				godina:godinaF,
				opis:opisF
		}
		
		$.post('FilmoviServlet', params,function(data){
			var response = JSON.parse(data); 
			if(response.status) {
				window.location.href = "Filmovi.html"; 
			} 
			else {
				alert("Uspjesno ste izmijenili Film!"); 
			}
		}); 
	});
	
	$("#obrisiFilmBtn").on('click',function(){
		if(confirm("Da li zelite da obrisete film? ")) {
			var params = {
					action : "deleteMovie",
					filmId : idF
			}
			
			console.log("Id koji se salje servletu " + idF); 
			
			$.post('FilmoviServlet',params,function(data){
				var response = JSON.parse(data); 
				if(response.status) {
					window.location.href = "Filmovi.html"; 
				} 
			});
		}
	});
});