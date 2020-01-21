$(document).ready(function(){
	
	var korisnickoImeKorisnik = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(korisnickoImeKorisnik);
	
	var korisnickoImeID = $("#korisnickoImeID"); 
	var ulogaID = $("#ulogaID"); 
	console.log(korisnickoIme); 
	console.log(uloga);
	
	$("logoutLink").on("click", function(event){
		$.get("OdjavaServlet", function(data){
			if(data.status == "unauthenticated"){
				window.location.replace("Prijava.html"); 
				return;
			}
		});
		event.preventDefault(); 
		return false;
	});
	
	function getKorisnik() {
		var params = {"korisnickoImeKorisnik" : korisnickoImeKorisnik}
	};
	
	$.get("KorisnikServlet",params, function(data){
		if(data.status == "unauthenticated"){
			window.location.replace("Prijava.html"); 
			return;
		}
		
		if(data.status == "success") {
			var korisnik = data.korisnik; 
			console.log(korisnik); 
			
			korisnickoImeID.text(korisnik.korisnickoIme); 
			ulogaID.text(korisnik.uloga);
		}
	});
	
	getKorisnik();
});