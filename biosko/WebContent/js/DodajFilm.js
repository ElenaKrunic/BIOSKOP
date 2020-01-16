$(document).ready(function(){
	("#logoutLink").on("click", function(event){
		$.get("LogoutServlet", function(data){
			console.log(data); 
			
			if(data.status == "nije autentifikovano") {
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
			
			if(data.status == "nije autentifikovano") {
				window.location.return("Prijava.html"); 
				return;
			}
			if(data.status == "uspjesno") {
				window.location.replace("Filmovi.html"); 
			}
		}); 
		
		event.preventDefault(); 
		return false;
	});
});