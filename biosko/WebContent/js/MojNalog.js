
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
	
	//isto za id 
//var id1 = window.location.search.slice(1).split('&')[0].split('=')[1];
	
var url_string = window.location.href;
var url = new URL(url_string);
var idKorisnik = url.searchParams.get("id");

console.log(idKorisnik);
	
	var params = {
			action: 'ucitajPodatkeZaMojProfil', 
			id : idKorisnik
	}

	$.post('KorisnikServlet', params, function(data){
	var response = JSON.parse(data); 
	console.log('response u js je ' + response); 
	if(response.status) {

		var user = response.k;
		$("#mojProfilIme").text(user.Username);
		//a mogu pass i standard
		if(user.Username == localStorage['userName']) {
			$("#mojProfilSifra").val(user.Password);
		}
		$("#mojProfilDatumReg").text(user.Datum);
		$("#mojProfilUloga").val(user.Uloga);
		
		if(localStorage['uloga']!="Admin") {
			$("#mojProfilUloga").prop('disabled',true);
		}
		$("#statusProfil").text(user.Status);
		
		if(localStorage['uloga'] == 'Admin') {
			var btn = document.createElement('button'); 
			btn.innerText = "Obrisi"; 
			btn.setAttribute('idKorisnik', response.k.ID);
			btn.setAttribute('ID', 'obrisiNalog');
			document.getElementById('btnsNalog').appendChild(btn); 
			
			$("#obrisiNalog").on('click',function(){
				if(confirm("Da li zelite da obrisete korisnika? ")) {
					var params = {
							'action' : 'obrisiKorisnika',
							'idKorisnik' : this.getAttribute('idKorisnik')
					}
					
					$.post('KorisnikServlet',params,function(data){
						var response = JSON.parse(data); 
						if(response.status) {
							alert('Korisnik uspjesno obrisan!'); 
							window.location.href = "Glavna.html"; 
						}
					});
				}
			});
 		}
		
	} else {
		alert('ne mogu se prikazati podaci o korisniku');
	}
}); 

//The prop() method sets or returns properties and values of the selected elements.

var params = {
			'action' : 'ucitajKartuZaKorisnika', 
			'id' : idKorisnik
	}
$.post('KarteServlet', params, function(data){
	var response = JSON.parse(data);
	if(response.status) {
		for(i=0;i<response.jsonKarte.length;i++) {
			var karta = response.jsonKarte[i];
			var tr  = document.createElement('tr');
			
			var nazivFilma = document.createElement('td'); 
			nazivFilma.innerText = karta.NazivFilma; 
			nazivFilma.setAttribute('idFilma', karta.FilmID); 
			console.log('naziv filma je ' + nazivFilma);
			nazivFilma.className = "karta_sredjivanje"; 
			tr.appendChild(nazivFilma); 
			
			var sjediste = document.createElement('td'); 
			sjediste.innerText = karta.Sjediste; 
			tr.appendChild(sjediste); 
			
			var termin = document.createElement('td'); 
			termin.innerText = karta.Termin; 
			tr.appendChild(termin); 
			
			var detalji = document.createElement('button'); 
			detalji.innerHTML="<button class='detaljiBtn' kartaId='"+karta.ID+"' korisnikId='"+idKorisnik+"'>Pogledaj kartu</button>";
			//detalji.innerHtml = "Pogledaj kartu";
			tr.appendChild(detalji);
			
			document.getElementById('karteTable').appendChild(tr);
			
		}
		
		$(".detaljiBtn").on('click',function(){
			var idU = this.getAttribute('korisnikId'); 
			localStorage['karta'] = idU;
			var idK = this.getAttribute("kartaId"); 
			window.location.href = "PrikazKarte.html?id=" + idK; 
		});
	}
	
}); 


$("#sacuvajPromjene").on('click',function(){
	var novaSifra = $("#mojProfilSifra").val();

	if(novaSifra==null || novaSifra == "") {
		alert("Niste dobro unijeli sifru!"); 
		var promijenjenaSifra = false;
	}
	
	var promijenjenaUloga = false;
	if(localStorage['uloga']=='Admin') {
		var novaUloga = $("#mojProfilUloga").val();
		promijenjenjenaUloga = true;
	}
	
	var params = {
			'action' : 'sacuvajIzmjene', 
			'promijenjenaSifra':promijenjenaSifra,
			'novaSifra':novaSifra, 
			'promijenjenaUloga' : promijenjenaUloga, 
			'novaUloga': novaUloga,
			'idKorisnik' : idKorisnik
	}
	
	$.post('KorisnikServlet',params,function(data){
		var response = JSON.parse(data);
		if(response.promijenjenaUloga){
			alert("Uspjesno ste izmijenili ulogu");
		}
		
		if(response.promijenjenaSifra){
			alert("Uspjesno ste promijenili sifru!");
		}
		
	}); 
});



});

