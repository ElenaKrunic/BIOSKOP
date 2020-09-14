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
		
		var params = {
				action : 'ucitajKorisnike'
		}

		$.post('KorisnikServlet', params, function(data){
			var response = JSON.parse(data); 
			console.log(response);
			if(response.status) {
				for(i=0; i<response.listaKorisnikaSvi.length;i++) {
					var jedanKorisnik = response.listaKorisnikaSvi[i]; 
					var tr = document.createElement('tr'); 
					var tabela = document.getElementById('sviKorisnici');
					
					var imeKorisnika = document.createElement('td'); 
					imeKorisnika.innerText= jedanKorisnik.Username;
					imeKorisnika.setAttribute('korisnikId', jedanKorisnik.ID); 
					imeKorisnika.className = "linkDoKorisnika"; 
					tr.appendChild(imeKorisnika); 
					
					var datum = document.createElement('td'); 
					datum.innerText = jedanKorisnik.Datum; 
					tr.appendChild(datum); 
					
					var uloga = document.createElement('td'); 
					uloga.innerText = jedanKorisnik.Uloga; 
					tr.appendChild(uloga); 
					
					tabela.appendChild(tr);
					
				}
				
				$(".linkDoKorisnika").on('click',function(){
					var id = this.getAttribute('korisnikId'); 
					if(id>0) {
						window.location.href="MojNalog2.html?id="+id;
					}
				});
			}
			else {
				alert("Nije moguce izvrsiti pretragu po datom kriterijumu. Provjerite unijete podatke");
			}
		}); 
		
		$("#filtrirajKorisnike").on('click',function(){
			
			var korisnickoIme = $("#imeKorisnika").val(); 
			var ulogaKorisnika = $("#uloge").val();
			
			var params = {
					'action' : 'filtrirajKorisnike', 
					'userName' : korisnickoIme, 
					'uloga' : ulogaKorisnika, 
					'datum' : ''
			}
			
			$.post('KorisnikServlet', params, function(data){
				
				var response = JSON.parse(data); 
				if(response.status) {
					if(response.korisnici.length>0) {
						$('tr').slice(1).remove(); 
						for(i=0; i<response.korisnici.length;i++) {
							var korisnik = response.korisnici[i]; 
							var tabela = document.getElementById('sviKorisnici');
							var tr = document.createElement('tr'); 
						
							var imeKorisnika = document.createElement('td'); 
							imeKorisnika.innerText= korisnik.Username;
							imeKorisnika.setAttribute('korisnikId', korisnik.ID); 
							imeKorisnika.className = "linkDoKorisnika"; 
							tr.appendChild(imeKorisnika); 
							
							var datum = document.createElement('td'); 
							datum.innerText = korisnik.Datum; 
							tr.appendChild(datum); 
							
							var uloga = document.createElement('td'); 
							uloga.innerText = korisnik.Uloga; 
							tr.appendChild(uloga); 
							
							tabela.appendChild(tr);
						}
						
						$(".linkDoKorisnika").on('click',function(){
							var id = this.getAttribute('korisnikId'); 
							if(id>0) {
								window.location.href="MojNalog2.html?id="+id;
							}
						});
					}
				}
			}); 
		});
		
	});


