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
	
	if(localStorage['uloga']=="Admin"){
		$("#izvjestaj").show();
	}



var url_string = window.location.href;
var url = new URL(url_string);
var idKarta = url.searchParams.get("id");

console.log(idKarta);

var params = {
		'action' : 'ucitajKartuZaKorisnika', 
		'id' : localStorage['karta']
}

$.post('KarteServlet',params, function(data){
	var response = JSON.parse(data); 
	
	if(response.status) {
		for(i=0; i<response.jsonKarte.length;i++) {
			var jedna = response.jsonKarte[i]; 
			//console.log("Jedna" + jedna);
			
			$("#naslovKupljena").text(jedna.NazivFilma); 
			$("#tipKupljena").text(jedna.TipProjekcije); 
			$("#terminKupljena").text(jedna.Termin); 
			$("#salaKupljena").text(jedna.Sala); 
			$("#cijenaKupljena").text(jedna.Cijena); 
			$("#sjedisteKupljena").text(jedna.Sjediste);
				
			if(localStorage['uloga']!='Admin') {
				$("#obrisiKupljena").remove(); 
			}
			
		}
	}
	
}); 

$("#obrisiKupljena").on('click',function(){
	
	if(idKarta == null) {
		alert("Doslo je do greske!"); 
	}
	
	if(confirm("Da li ste sigurni da zelite da obrisete? ")) {
		var params = {
				action : 'obrisiKartu', 
				'idKarta' :  idKarta
		}
		
		$.post('KarteServlet',params,function(data){
			var response = JSON.parse(data); 
			
			if(response.status) {
				alert("Uspjesno obrisana karta"); 
			} else {
				window.location.href = "Glavna.html"; 
			}
		});
	}
}); 

});