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
	//izbrisati varijablu uloga i ostaviti localStoragesamo
	//vratiti u Sesija.js deklaraciju prethodnu 
	//provejriti localStorage
	//var uloga = localStorage['uloga']; 
	//sta sve neprijavljen korisnik vidi 
	
	if(localStorage['uloga'] == "null"){
		$("#adminProfil").remove();
		$("#izvjestaj").remove();
		$("#dodajProjekcijuBtn").remove();
		$("#mojp").remove();
		$("#logoutLink").remove();
	} 
	if(localStorage['uloga'] == "obicanKorisnik"){
		$("#prijava").remove();
		$("#registracija").remove(); 
		$("#adminProfil").remove(); 
		$("#izvjestaj").remove();
		$("#dodajProjekcijuBtn").remove();
	}
	if(localStorage['uloga'] == "Admin") {
		$("#prijava").remove();
		$("#registracija").remove(); 
	//	$("#mojp").remove();
	}
	
	//sta sve obican korisnik moze da vidi 
	/*
	if(localStorage['uloga'] != 'Admin') {
		$("#adminProfil").remove();
		$("#izvjestaj").remove();
		$("#dodajProjekcijuBtn").remove();
		$("#prijava").remove(); 
		$("#registracija").remove();
		//$("#logoutLink").show();
		console.log("obican");
	} 
	
	if(localStorage['uloga'] != 'obicanKorisnik') {
		$("#prijava").remove(); 
		$("#registracija").remove();
		console.log("admin");
		$("#adminProfil").show();
	}
		*/
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
		      tr.setAttribute('idProjekcije',p.idProjekcije);
		      
		      var nazivFilma = document.createElement('td');
		      nazivFilma.innerText = p.nazivFilma;
		      nazivFilma.setAttribute('idFilma',p.idFilma);
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
		      btn1.className="detaljiBtn";
		      btn1.setAttribute('idProjekcije',p.idProjekcije);
		      td6.appendChild(btn1);
		      tr.appendChild(td6); 
		      
		      document.getElementById('tabelaProjekcije').appendChild(tr);

		      $(".ponudjenFilmIzProjekcije").on('click',function(){
		        window.location.href="Film.html?id="+this.getAttribute('idFilma');
		      });
		      
		      $(".detaljiBtn").on('click',function(){
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
	var idSala = $("#filterSala :selected").attr('idSale'); 
	var terminProjekcijePocetak = $("#terminProjekcijeStart").val().split("M").join(" ");
	var terminProjekcijeKraj = $("#terminProjekcijeEnd").val().split("M").join(" ");
	var tipProjekcije = $("#filterTipProjekcije").val(); 
	var cijenaMin = $("#cijenaNajmanja").val(); 
	var cijenaMax = $("#cijenaNajveca").val(); 
	
	if(idProjekcije == null) {
		alert("idProjekcije je null"); 
	}
	//if(idSala == null) {
		//alert("idSale je null"); 
	//}
	
	if(terminProjekcijePocetak == null) {
		alert("pocetakProjekcije je null"); 
	}
	if(terminProjekcijeKraj == null) {
		alert("krajprojekcije je null"); 
	}
	if(tipProjekcije == null) {
		alert("tipProjekcije je null"); 
	}
	if(cijenaMin == null) {
		alert("minCijena je null"); 
	}
	if(cijenaMax == null) {
		alert("maxCijena je null"); 
	}
	
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
        console.log(response);
        if(response.status){
          $('tr').slice(1).remove();
          console.log(response);
          for(i=0;i<response.lista.length;i++){
            var p = response.lista[i];
            var tr = document.createElement('tr');
            tr.setAttribute('idProjekcije',p.ID);
            
            var naziv = document.createElement('td');
            naziv.innerText = p.nazivFilma;
            naziv.setAttribute('idFilma',p.filmId);
            naziv.className="linkDoProjekcije";
            tr.appendChild(naziv);

            var termin = document.createElement('td');
            termin.innerText = p.termin;
            tr.appendChild(termin);

            var sala = document.createElement('td');
            sala.innerText = p.sala;
            tr.appendChild(sala);

            var tip = document.createElement('td');
            tip.innerText = p.tip;
            tr.appendChild(tip);
            
//            console.log(tip);

            var cijena = document.createElement('td');
            cijena.innerText = p.cijena;
            tr.appendChild(cijena);

            var btn = document.createElement('td');
            var btndetalji = document.createElement('button');
            btndetalji.innerText = "Detalji";
            btndetalji.className="detaljiBtn"; 
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
        	alert("Nije moguce filtrirati po datom kriterijumu. Provjerite unijete podatke!");
        }
    });
});
});