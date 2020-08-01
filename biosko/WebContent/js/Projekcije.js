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
});
		   
	
	/*
	$.post('ProjekcijeServlet',params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			for(i=0; i<response.listaProjekcija.length; i++) {
				var projekcija = response.listaProjekcija[i];
				var tr = document.createElement('tr');
				//var tabela = document.getElementById('tabelaProjekcije');
				tr.setAttribute('idProjekcije', projekcija.idProjekcije); 
				tr.className="item"; 
				
				var nazivFilma = document.createElement('td'); 
				nazivFilma.innerText = projekcija.nazivFilma; 
				nazivFilma.setAttribute("idFilma", projekcija.idFilma); 
				nazivFilma.className = "ponudjenFilmIzProjekcije"; 
				tr.appendChild(nazivFilma); 
				
				var terminProjekcije = document.createElement('td'); 
				terminProjekcije.innerText = projekcija.terminProjekcije;
				tr.appendChild(terminProjekcije); 
				
				var nazivSale = document.createElement('td'); 
				nazivSale.innerText = projekcija.nazivSale; 
				tr.appendChild(nazivSale); 
				
				var tipProjekcije = document.createElement('td'); 
				tipProjekcije.innerText = projekcija.tipProjekcije; 
				tr.appendChild(tipProjekcije);
				
				var cijenaKarte = document.createElement('td'); 
				cijenaKarte.innerText = projekcija.cijenaKarte; 
				retrd.appendChild(cijenaKarte);
				
			     document.getElementById('tabelaProjekcije').appendChild(tr);
				
				$(".ponudjenFilmIzProjekcije").on('click',function(data){
					window.location.href = "Film.html?id=" + this.getAttribute("idFilma"); 
				});
			}
			*/
			
	
	


























/*
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
			    tr.className="item";
				var film = document.createElement('td'); 
				film.innerText = projekcija.nazivFilma;  
				film.setAttribute("idFilma", projekcija.idFilma); 
				film.className = "ponudjenFilmIzProjekcije"; 
				tr.appendChild(film); 
				
				var termin = document.createElement('td'); 
				termin.innerText = projekcija.terminProjekcije; 
				tr.appendChild(termin);

				var sala = document.createElement('td'); 
				sala.innerText = projekcija.nazivSale;
				tr.appendChild(sala); 

				var tipProjekcije = document.createElement('td'); 
				tipProjekcije.innerText = projekcija.tipProjekcije; 
				tr.appendChild(tipProjekcije); 

				var cijenaKarte = document.createElement('td'); 
				cijenaKarte.innerText = projekcija.cijenaKarte; 
				tr.appendChild(cijenaKarte); 
				
				tabela.appendChild(tr); 
				
				//poslije dodati dugme i vidjeti da li mogu bez getAttribute 
				$(".ponudjenFilmIzProjekcije").on('click', function(data){
					window.location.href = "Film.html?id=" + this.getAttribute('idFilma'); 
				});
			}
		}
		else {
			alert("Zao nam je,danas nema projekcija! "); 
		}
	});
//var id = window.location.search.slice(1).split('&')[0].split('=')[1];
});
*/