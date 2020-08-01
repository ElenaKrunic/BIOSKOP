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

var id = window.location.search.slice(1).split('&')[0].split('=')[1];

	var params = {
			action : "loadProjection", 
			'idProjekcije' : id 
			}
	
	$.post("ProjekcijeServlet", params,function(data){
		var response = JSON.parse(data); 
		//console.log("Na servlet " + response); 
		if(response.status) {
			//console.log(status);
			//var pojedinacnaProjekcija = response.projekcija; 
			//console.log(pojedinacnaProjekcija);
			$("#nazivProjekcije").html("<a href='Film.html?id="+response.idFilma+"''>"+response.nazivFilma+"</a>");
			$("#tipProjekcije4").text(response.tipProjekcije); 
			$("#salaProjekcije4").text(response.nazivSale);  
			$("#startProjekcije4").text(response.terminProjekcije); 
			$("#cijenaKarte4").text(response.cijenaKarte); 
			$("#slobodneKarte4").text(response.brojKarata); 
			$("#statusProjekcije4").text(response.status); 
		}
	}); 
});
