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
	
	$(document).ready(function(){
		var datum = $("#izvjestajDatum").val(); 
		
		$("#izvjestajBtn").on('click',function(){
			var params = {
					'action' : 'izvjestaj', 
					'datum' : datum
			}
			
			$.post('KorisnikServlet', params,function(data){
				var response = JSON.parse(data); 
				for(i=0;i<response.listaIzvjestaj.length;i++) {
					var film = response.listaIzvjestaj[i]; 
					var tr = document.createElement('tr'); 
					var tabela = document.getElementById('izvjestajTabela');
					
					var naziv = document.createElement('td'); 
					naziv.innerText = film.NazivFilma; 
					naziv.setAttribute("filmId", film.ID); 
					naziv.className = "linkDoFilma";
					tr.appendChild(naziv); 
					
					var brojProjekcija = document.createElement('td'); 
					brojProjekcija.innerText = film.BrojProjekcijaZaFilm; 
					tr.appendChild(brojProjekcija);
					
					var prodateKarte = document.createElement('td'); 
					prodateKarte.innerText = film.ProdateKarte; 
					tr.appendChild(prodateKarte); 
					
					var zarada = document.createElement('td'); 
					zarada.innerText = film.Zarada; 
					tr.appendChild(zarada); 
					
					tabela.appendChild(tr);
				}
				
				$(".linkDoFilma").on('click',function(){
					var id = this.getAttribute('filmId');
					if(id!=null || id!=undefined) {
						window.location.href="Film.html?id=" + id;
					}
				});
			}); 
		});
	});
});