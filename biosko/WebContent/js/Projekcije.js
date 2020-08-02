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
	
	function ucitajProjekciju(idProjekcije) {
		var params = {
				action : 'loadProjection', 
				projekcijaID : idProjekcije
		}
		
		$.post('ProjekcijeServlet', params, function(data){
			console.log(data); 
			return data; 
		});
	}
	
	var params = {
			'action' : "projekcijeZaDanas"
	}
	
	$.post('ProjekcijeServlet',params,function(data){
		  var odg = JSON.parse(data);
		  if(odg.status){
		    for(i=0;i<odg.listaProjekcija.length;i++){
		      var p = odg.listaProjekcija[i];
		      var tr = document.createElement('tr');
		      tr.setAttribute('idProjekcije',p.id_projekcije);
		      tr.className="item";
		      
		      var nazivFilma = document.createElement('td');
		      nazivFilma.innerText = p.naziv_filma;
		      nazivFilma.setAttribute('idFilma',p.id_filma);
		      nazivFilma.className="ponudjenFilmIzProjekcije";
		      tr.appendChild(nazivFilma);

		      var termin = document.createElement('td');
		      termin.innerText = p.terminProjekcije;
		      tr.appendChild(termin);

		      var sala = document.createElement('td');
		      sala.innerText = p.nazivSale;
		      tr.appendChild(sala);

		      var tipP = document.createElement('td');
		      tipP.innerText = p.tipProjekcije;
		      tr.appendChild(tipP);

		      var cijenaKarte = document.createElement('td');
		      cijenaKarte.innerText = p.cijena;
		      tr.appendChild(cijenaKarte);
		      
		      var td6 = document.createElement('td');
		      var btn1 = document.createElement('button');
		      btn1.innerText = "Detalji";
		      btn1.className="confirmbtn detaljiBtn";
		      btn1.setAttribute('idProjekcije',p.id_projekcije);
		      td6.appendChild(btn1);
		      tr.appendChild(td6);

		      document.getElementById('tabelaProjekcije').appendChild(tr);

		      $(".ponudjenFilmIzProjekcije").on('click',function(data){
		        window.location.href="Film.html?id="+this.getAttribute('idFilma');
		      });
		      
		      $(".detaljiBtn").on('click',function(data){
		          window.location.href="PrikazProjekcije.html?id="+this.getAttribute('idProjekcije');
		        })
		    }
		  }
	});
	
	$("filterProjekcijaBtn").on('click', function(){
		var idFilma = $('#filterFilm').val(); 
		var pocetakPrikazivanja = $('#pocetakProjekcije').val(); 
		var krajProjekcije = $('#krajProjekcije').val(); 
		var sala = $('#filterSala').val(); 
		var tipProjekcije = $('#filterTipProjekcije'); 
		var najmanjaCijenaKarte = $('#najmanjaCijena'); 
		var najvecaCijenaKarte = $('#najvecaCijena'); 
		
		var params = {
				//dodati ostale parametre 
				action : "filterProjekcije", 
				'idFilm' : idFilma, 
				'pocetakProjekcije' : pocetakPrikazivanja, 
				'zavrsetakProjekcije' : krajProjekcije, 
				'salaProjekcije' : sala, 
				'tipP' : tipProjekcije, 
				'najmanjaCijena' : najmanjaCijenaKarte, 
				'najvecaCijena' : najvecaCijenaKarte
		}
		
		$.post("ProjekcijeServlet", params, function(data){
			var response = JSON.parse(data); 
			
			if(response.status) {
				if(response.projekcije.length>0) {
					$("tr").slice(2).remove(); 
					for(i=0; i<response.projekcije.length; i++) {
						var projekcija = response.projekcije[i]; 
						var tabela = document.getElementById('tabelaProjekcije'); 
						var tr = document.createElement("tr"); 
						tr.className = "item"; 
						tr.setAttribute("projekcijaId", projekcija.ID); 
						var btn = ""; 
						
						  var nazivFilma = document.createElement('td');
					      nazivFilma.innerText = p.naziv_filma;
					      nazivFilma.setAttribute('idFilma',p.id_filma);
					      nazivFilma.className="ponudjenFilmIzProjekcije";
					      tr.appendChild(nazivFilma);

					      var termin = document.createElement('td');
					      termin.innerText = p.terminProjekcije;
					      tr.appendChild(termin);

					      var sala = document.createElement('td');
					      sala.innerText = p.nazivSale;
					      tr.appendChild(sala);

					      var tipP = document.createElement('td');
					      tipP.innerText = p.tipProjekcije;
					      tr.appendChild(tipP);

					      var cijenaKarte = document.createElement('td');
					      cijenaKarte.innerText = p.cijena;
					      tr.appendChild(cijenaKarte);
					      
					      var td6 = document.createElement('td');
					      var btn1 = document.createElement('button');
					      btn1.innerText = "Detalji";
					      btn1.className="confirmbtn detaljiBtn";
					      btn1.setAttribute('idProjekcije',p.id_projekcije);
					      td6.appendChild(btn1);
					      tr.appendChild(td6);

					      document.getElementById('tabelaProjekcije').appendChild(tr);

					      $(".ponudjenFilmIzProjekcije").on('click',function(data){
					        window.location.href="Film.html?id="+this.getAttribute('idFilma');
					      });
					
					}
				}
			}
		}); 
	});
	
	var params = {
			action : "ucitajTipoveProjekcija"
	}
	
	$.post('ProjekcijeServlet', params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			for(i=0; i<response.listaTipovaProjekcija.length; i++) {
				var tip = document.createElement('option'); 
				tip.value = response.listaTipovaProjekcija[i];
				tip.innerText = response.listaTipovaProjekcija[i]; 
				document.getElementById('filterTipProjekcije').append(tip); 
			}
		}
	});
	
	var params = {
			action : "ucitajSale"
	}
	
	$.post('ProjekcijeServlet', params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			for (i=0; i<response.listaSala.length;i++) {
				var s = document.createElement('option');
				s.value = response.listaSala[i];
				s.innerText = response.listaSala[i]; 
				document.getElementById('filterSala').append(s); 
			}
		}
	});
});
		   