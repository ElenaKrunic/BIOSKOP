$(document).ready(function(){
	$('logoutLink').on('click',function(event){
		$.get('OdjavaServlet', function(data){
			if(data.status == 'unauthenticated') {
				window.location.replace('Prijava.html'); 
				return;
			}
		}); 
		event.preventDefault(); 
		return false; 
	});
	
	$("#izvjestaj").show();

		
	var params = {
			action : 'ucitajFilmProjekcija'
	}
	
$.post('ProjekcijeServlet',params,function(data){
	var response = JSON.parse(data); 
	if(response.status) {
		for(i=0; i<response.filmovi.length;i++){
			var o = document.createElement('option');
			o.setAttribute('value', response.filmovi[i].ID); 
			o.innerText = response.filmovi[i].Naziv;
			document.getElementById('filmID').appendChild(o); 
		}
	}
});

	
	var params = {
			action : "ucitajSaleSaTipovimaProjekcija"
	}
	
	$.post('ProjekcijeServlet', params, function(data){
		var response = JSON.parse(data); 
			for(i=0; i<response.sale.length;i++){
				var sala = response.sale[i];
				var option = document.createElement('option'); 
				option.setAttribute('value', sala.ID); 
				var tipovi = sala.listaTipova; 
				option.setAttribute('tipovi', JSON.stringify(tipovi)); 
				option.innerText = sala.Naziv; 
				document.getElementById('dodajSaluProjekcije').appendChild(option);
			}
		}); 
	
	/*
	 * $("button").click(function(){
	 * 		$("p").html("hello world");
	 * });
	 */
	
	
	$("#dodajSaluProjekcije").on('change',function(data){
		var sala = $("#dodajSaluProjekcije").val(); 
		var tipoviProjekcija = $("#dodajSaluProjekcije").find(":selected").attr("tipovi"); 
		$("#dodajTipProjekcijeProjekcije").html(""); 
		
		if(sala!=null) {
			tipoviProjekcija = JSON.parse(tipoviProjekcija); 
			for(i=0; i<tipoviProjekcija.length;i++) {
				var tip = tipoviProjekcija[i]; 
				var option = document.createElement('option'); 
				option.setAttribute('value', tip.ID);
				option.innerText = tip.Naziv; 
				document.getElementById("dodajTipProjekcijeProjekcije").appendChild(option);
			}
		}
	}); 
	
	/*
	var params = {
			action : "ucitajTipoveProjekcija"
	}
	
	$.post('ProjekcijeServlet', params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			for(i=0; i<response.listaTipovaProjekcija.length; i++) {
				var tip = document.createElement('option'); 
				tip.setAttribute('value', response.listaTipovaProjekcija[i]); 
				tip.innerText = response.listaTipovaProjekcija[i]; 
				document.getElementById('dodajTipProjekcijeProjekcije').append(tip); 
			}
		}
	});
	*/
	
	
	/*
	var response = JSON.parse(data); 
	if(response.status) {
		for (i=0; i<response.listaSala.length;i++) {
			var s = document.createElement('option');
			s.value = response.listaSala[i];
			s.innerText = response.listaSala[i]; 
			document.getElementById('dodajSaluProjekcije').append(s); 
		} 
	} */
	
	$('#btndodajprojekciju').on('click',function(){
		var filmID = $("#filmID :selected").val();
		var salaProjekcije = $("#dodajSaluProjekcije").val(); 
		var tipProjekcije = $("#dodajTipProjekcijeProjekcije").val();
		var pocetakProjekcije = $("#pocetakProjekcije").val();
		pocetakProjekcije = pocetakProjekcije.split("M").join(" "); 
		var cijenaProjekcije = $("#cijenaKarteProjekcije").val();
		
		var params = {
				'action' : "dodajProjekciju", 
				'projekcijaId' : filmID, 
				'salaProjekcije' : salaProjekcije, 
				'tipProjekcije' : tipProjekcije, 
				'pocetakProjekcije' : pocetakProjekcije, 
				'cijenaProjekcije' : cijenaProjekcije
		}
		
		$.post('ProjekcijeServlet', params, function(data){
			console.log("Podaci se poslali na servlet");
			var response = JSON.parse(data); 
			if(response.status==false) {
				console.log('Status u dodaj projekciju '+ status); 
				alert("Uspjesno ste dodali projekciju!"); 
				window.location.href = 'Glavna.html'; 
			} 
		}); 
	});
});