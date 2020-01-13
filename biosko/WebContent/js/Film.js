$(document).ready(function(){
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