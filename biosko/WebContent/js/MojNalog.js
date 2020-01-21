$(document).ready(function(){
	alert("ucitan dokument"); 
	$("#izmijeni").click(function(){
		alert("kliknuto izmijeni"); 
		var ime = $("#ime").val(); 
		var sifra = $("#sifra").val(); 
		var uloga = $("#uloga").val(); 
		
		$.ajax({
			type : "post", 
			data : "korisnickoIme=" + "&sifra=" + sifra + "&uloga" + uloga, 
			url : "mojNalog", 
			success : function(msg) {
				alert("Podaci uspjesno izmijenjeni"); 
				return false; 
			},
		error : function(e) {
			alert("doslo je do greske pri izmjeni podataka"); 
			return false;
		}
		});
	});
});