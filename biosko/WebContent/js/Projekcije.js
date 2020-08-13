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
	
	var params = {
			action : 'ucitajFilmProjekcija'
	}
	
$.post('ProjekcijeServlet',params,function(data){
	var response = JSON.parse(data); 
	if(response.status) {
		for(i=0; i<response.filmovi.length;i++){
			var film = response.filmovi[i];
			var o = document.createElement('option'); 
			o.setAttribute('idFilma', film.ID); 
			o.innerText = film.Naziv; 
			document.getElementById('filterFilm').appendChild(o); 
		}
	}
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
		
	
$("#filterProjekcijaBtn").on("click",function(){
	var idProjekcije = $("#filterFilm :selected").attr('idFilma'); 
	var idSala = $("#filterSala :selected").attr("idSale"); 
	var terminProjekcijePocetak = $("#terminProjekcijeStart").val().split("M").join(" ");
	var terminProjekcijeKraj = $("#terminProjekcijeEnd").val().split("M").join(" ");
	var tipProjekcije = $("#filterTipProjekcije").val(); 
	var cijenaMin = $("#cijenaNajmanja").val(); 
	var cijenaMax = $("#cijenaNajveca").val(); 
	
	var params = {
			action : "filtrirajProjekciju", 
			'filmId' : idProjekcije,
			'pocetakProjekcije':terminProjekcijePocetak, 
			'krajProjekcije':terminProjekcijeKraj, 
			'idSala': idSala, 
			'tipProjekcije':tipProjekcije,
			'cijenaMin' : cijenaMin, 
			'cijenaMax' : cijenaMax
	}
	
    $.post('ProjekcijeServlet',params,function(data){
        //console.log(id_Filma);
        var response = JSON.parse(data);
        if(response.status){
          $('tr').slice(1).remove();
          console.log(response);
          for(i=0;i<response.lista.length;i++){
            var p = response.lista[i];
            var tr = document.createElement('tr');
            tr.setAttribute('idProjekcije',p.ID);
            
            var naziv = document.createElement('td');
            naziv.innerText = p.Naziv_Filma;
            naziv.setAttribute('idFilma',p.ID_Filma);
            naziv.className="linkDoProjekcije";
            tr.appendChild(naziv);

            var termin = document.createElement('td');
            termin.innerText = p.Termin;
            tr.appendChild(termin);

            var sala = document.createElement('td');
            sala.innerText = p.Sala;
            tr.appendChild(sala);

            var tip = document.createElement('td');
            tip.innerText = p.TipProjekcije;
            tr.appendChild(tip);

            var cijena = document.createElement('td');
            cijena.innerText = p.Cena;
            tr.appendChild(cijena);

            var btn = document.createElement('td');
            var btndetalji = document.createElement('button');
            btndetalji.innerText = "Detalji";
            btndetalji.className="confirmbtn detaljiBtn"; 
            btndetalji.setAttribute('idProjekcije',p.ID); 
            btn.appendChild(btndetalji);
            
            tr.appendChild(btn);
            document.getElementById('tabelaProjekcije').append(tr);
          }
          
          if(localStorage['uloga'] == "Admin" || "obicanKorisnik") {
              $(".detaljiBtn").on('click',function(data){
                  window.location.href="PrikazProjekcije.html?id="+this.getAttribute('idProjekcije');
                });
                $(".linkDoProjekcije").on('click',function(data){
                  window.location.href="Film.html?id="+this.getAttribute('idFilma');
                });
          }
        } else {
        	alert("Projekcija se nije ucitala");
        }
    });
});
});