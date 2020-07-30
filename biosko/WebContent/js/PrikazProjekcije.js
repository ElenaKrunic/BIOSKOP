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
		action : 'loadProjection',
		'idProjekcije' : id
}

$.post("ProjekcijeServlet", params, function(data){
	var response = JSON.parse(data); 
	
}); 

});