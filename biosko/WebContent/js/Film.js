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
	
var url = window.location.href; 
var newUrl = new URL(url); 
var id = newUrl.searchParams.get("id"); 

ucitajFilm(id); 

function ucitajFilm(idFILMA) {
	var params = {
			action : "ucitajFilm", 
			filmId : idFILMA
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
			
			if(localStorage['status'] != "false") {
				var btn = document.createElement('button'); 
				btn.className = "confirmbtn"; 
				btn.innerText= "Kupi kartu"; 
				btn.setAttribute('idFilma', response.film.ID); 
				btn.setAttribute('ID', "kupiKartuBtn"); 
				document.getElementById('prikazFilmaBtn').appendChild(btn); 
			}
		}
	});
}

	});


/*

var id = window.location.search.slice(1).split('&')[0].split('=')[1];
ucitajFilm(id); 

function ucitajFilm(IDfilma){
	var params = {
			action : "loadMovie", 
			filmId : IDfilma
	}

	console.log("Saljem na servlet ===> " + params); //dodati params koje saljem 

	$.post('FilmoviServlet', params, function(data){
		var response = JSON.parse(data); 
		console.log("Ovo je otislo na servlet ===> " + response); //dodati response 

		if(response.status){
			var pojedinacanFilm = response.film; 
			console.log(pojedinacanFilm); 
			//probati i sa val(f.Nesto)
			//.Naziv,.Reziser itd moraju biti istih naziva kao u bazi 
			//promijeni zemlja i god
			$("#nazivFilma3").text(pojedinacanFilm.Naziv); 
			$("#reziserFilma3").text(pojedinacanFilm.Reziser); 
			$("#glumciFilma3").text(pojedinacanFilm.Glumci.join(","));//ima ih vise 
			$("#trajanje3").text(pojedinacanFilm.Trajanje);
			$("#zanrFilma3").text(pojedinacanFilm.Zanrovi.join(",")); 
			$("#distributer3").text(pojedinacanFilm.Distributer); 
			$("#zemljaPorijekla3").text(pojedinacanFilm.Zemlja_Porekla); 
			//$("#godinaProizvodnje3").text(pojedinacanFilm.GodinaProizvodnje);
			$("#godinaProizvodnje3").text(pojedinacanFilm.Godina_Proizvodnje); 
			$("#opis3").text(pojedinacanFilm.Opis);

			if(localStorage['status']!="false") {
				var btn = document.createElement("button"); 
				btn.className = "nekiBtn"; //zasto ovo nije kupikartu btn
				btn.innerText = "Kupi kartu"; 
				btn.setAttribute("filmID",response.film.ID); //film 
				btn.setAttribute("ID", "kupiKartuBtn"); //dugme 
				document.getElementById("prikazFilmaBtn").appendChild(btn); 

			}
		}
	});
}
*/








/*$(document).ready(function(){
	$("#logoutLink").on("click", function(event){
		$.get("PrijavaServlet",function(data){
			console.log(data);
			
			if(data.status == "nije autentifikovano") {
				window.location.replace("Prijava.html");
				return;
			}
		});
		event.preventDefault();
		return false;
	});
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(id);
	
	function getFilm() {
		$.get("FilmServlet", {"id" : id}, function(data){
			
			if (data.status == 'nije autentikovano') {
				window.location.replace('Prijava.html');
				return;
			}
			
			if (data.status == "uspjesno") {
				var film = data.film;
				if(data.ulogaKorisnika == 'Korisnik') {
					$('#userTable').show();
					$('#adminForm').hide();
					
					$('#nazivFilma').text(film.naziv);
					$('#zanrFilma').text(film.zanr);
					$("#trajanje").text(film.trajanje);
					$("#distributer").text(film.distributer);
					$("#zemljaPorijekla").text(film.zemljaPorijekla);
					$("#godinaProizvodnje").text(film.godinaProizvodnje);
				} else if (data.ulogaKorisnija == "Administrator") {
					$("#userTable").hide();
					$("#adminForm").show();
					
					var nazivFilma = $("#nazivFilmaAd");
					var zanrFilma = $("#zanrAd");
					var trajanjeFilma = $("#trajanjeAd");
					var distributerFilma =$("#distributerAd");
					var zemljaPorijekla = $("#zemljaPorijeklaAd");
					var godinaProizvodnje = $("#godinaProizvodnjeAd");
					
					nazivFilma.val(film.naziv);
					zanrFilma.val(film.zanr);
					trajanjeFilma.val(film.trajanje);
					distributerFilma.val(film.distributer);
					zemljaPorijekla.val(film.zemljaPorijekla);
					godinaProizvodnje.val(film.godinaProizvodnje);
					
					$("updateSubmit").on("click", function(event){
						alert("kliknut apdejt"); 
						var naziv = nazivFilma.val();
						var zanr = zanrFilma.val();
						var trajanje = trajanjeFilma.val();
						var distributer = distributerFilma.val();
						var zemljaPorijekla = zemljaPorijekla.val(); 
						var godinaProizvodnje = godinaProizvodnje.val();
						console.log("naziv " + naziv); 
						console.log("zanr " + zanr);
						console.log("trajanje " + trajanje);
						console.log("distributer " + distributer ); 
						console.log("zemljaPorijekla " + zemljaPorijekla); 
						console.log("godinaProizvodnje " + godinaProizvodnje);
						
						params = {
								"action" : "update", 
								"id" : id,
								"naziv" : naziv, 
								"zanr" : zanr,
								"trajanje" : trajanje,
								"distributer" : distributer, 
								"zemljaPorijekla" : zemljaPorijekla, 
								"godinaProizvodnje" : godinaProizvodnje
						};
						console.log(params); 
						$.post("FilmServlet", params, function(data){
							if(data.stauts == "nije aut") {
								window.location.replace("Prijava.html");
								return;
							}
							
							if(data.status == "uspjesno") {
								window.location.replace("Filmovi.html");
								return;
							}
						});
						
						event.preventDefault();
						return false;
					});
					$("#deleteSubmit").on("click", function(event){
						params = {
							"action" : "delete", 
							"id" : id,
						};
						console.log(params);
						$.post("FilmServlet", params, function(data){
							if(data.status == "nije autf"){
								window.location.replace("Prijava.html");
								return;
							}
							
							if(data.status == "uspjesno") {
								window.location.replace("Filmovi.html");
								return;
							}
						});
						event.preventDefault();
						return false;
					});		
				}
			}
			
		});
	}
	getFilm();
});
*/