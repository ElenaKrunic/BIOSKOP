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

	var params = {
			action : 'danasProjekcije'
	}
	
	//idFilma i idProjekcije su data setovani atributi 
	$.post('ProjekcijeServlet',params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			for(i=0; i < response.listaProjekcija.length; i++) {
				var projekcija = response.listaProjekcija[i]; 
				var tr = document.createElement('tr'); 
				var tabela = document.getElementById('tabelaProjekcije');
				tr.setAttribute('idProjekcije', projekcija.idProjekcije); 
				var film = document.createElement('td'); 
				film.innerText = projekcija.nazivFilma;  
				film.setAttribute("idFilma", projekcija.idFilma); 
				film.className = "ponudjenFilmIzProjekcije"; 
				
				var termin = document.createElement('td'); 
				termin.innerText = projekcija.terminProjekcije; 
				
				var sala = document.createElement('td'); 
				sala.innerText = projekcija.nazivSale;
				
				var tipProjekcije = document.createElement('td'); 
				tipProjekcije.innerText = projekcija.tipProjekcije; 
				
				var cijenaKarte = document.createElement('td'); 
				cijenaKarte.innerText = projekcija.cijenaKarte; 
				
				tr.appendChild(film); 
				tr.appendChild(termin);
				tr.appendChild(sala); 
				tr.appendChild(tipProjekcije); 
				tr.appendChild(cijenaKarte); 
				tabela.appendChild(tr); 
				
				
				$(".ponudjenFilmIzProjekcije").on('click', function(data){
					window.location.href = "Film.html?id=" + this.getAttribute('idFilma'); 
				});
			}
		}
		else {
			alert("Zao nam je,za danasnji dan nema projekcija! "); 
		}
	});
//var id = window.location.search.slice(1).split('&')[0].split('=')[1];
});