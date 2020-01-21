$(document).ready(function(){
	var korisnickoImeFilter = $("#korisnickoImeFilter"); 
	var ulogaFilter = $("#ulogaFilter"); 
	var tabelaKorisnika = $("#tabelaKorisnika"); 
	
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
	
	function getKorisnici(){
		var korisnickoImeF = korisnickoImeFilter.val(); 
		var ulogaF = ulogaFilter.val(); 
		
		var params = {
			"korisnickoImeF" : korisnickoImeF, 
			"ulogaF" : ulogaF
		};
		
		$.get("KorisniciServlet", params, function(data){
			if(data.status == "unauthenticated"){
				window.location.replace("Prijava.html");
				return;
			}
			
			if(data.stauts == "success"){
				tabelaKorisnika.find("tr:gt(1)").remove(); 
				var filtriraniKorisnici = data.filtriraniKorisnici; 
				for(fk in filtriraniKorisnici){
					tabelaKorisnika.append(
							'<tr>' + 
							'<td><a href="Korisnik.html?korisnickoIme=' + filtriraniKorisnici[fk].korisnickoIme + '</a></td>' +
							 '<td>' +  filtriraniKorisnici[fk].uloga + '</a></td>' + 
							'<td>' + '</td>' + //OVDE DODATI JOS DATUM REGISTRACIJE KORISNIKA 	
						'</tr>'
					);
				}
			}
		});
	}
	korisnickoImeFilter.on("keyup", function(event){
		getKorisnici(); 
		event.preventDefault();
		return false;
	});
	
	ulogaFilter.on("keyup", function(event){
		getKorisnici(); 
		event.preventDefault(); 
		return false;
	});
	getKorisnici();
});	
