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
	
	if(localStorage['uloga']=="Admin") {
		$("#izvjestaj").show();
	}
	
var url = window.location.href; 
var newUrl = new URL(url); 
var id = newUrl.searchParams.get("id"); 
//var id = window.location.search.slice(1).split('&')[0].split('=')[1];

	
var params = {
			'action' : 'izaberiSjediste', 
			'idProjekcije' : id
		}
	
localStorage['brojOdabranihSjedista']="";

$.post('KarteServlet', params, function(data){
	var response = JSON.parse(data);
	console.log(response);
	if(response.status){
		var karta = response.jsonFilm;
		//var naziv = $("#naslovFilma").text(karta.nazivFilma); 
		//console.log(naziv); 
		$("#naslovFilma").text(karta.nazivFilma); 
		$("#tipProjekcije").text(karta.tipProjekcije); 
		$("#termin").text(karta.termin); 
		$("#sala").text(karta.nazivSale); 
		$("#cijenaKarte").text(karta.cijenaKarte); 
		if(karta.slobodnaSjedista.length<=0) {
			alert("Zao nam je,nema slobodnih sjedista"); 
		}
		console.log("Dole " + karta.slobodnaSedista);

		//console.log(karta.maxSjedista);
		//console.log("Dole" + karta.slobodnaSedista);

		maksBrSjedista = parseInt(karta.maxSjedista);
		console.log(maksBrSjedista);
		
		for(i=0;i<maksBrSjedista;i++) {
			var sjediste = "zauzetoMjesto"; 
			//var sjediste = ""; 
			if(karta.slobodnaSjedista.includes(i+1+"")) {
				sjediste = "slobodnoMjesto";
			} else {
				 //sjediste = "zauzetoMjesto"; 
			}
			
//The includes() method determines whether an array includes a certain value among its entries,
//returning true or false as appropriate.
			jednoSjediste = document.getElementById('sjedista');
			jednoSjediste.value=karta.slobodnaSjedista[0];
			//document.getElementById('sjedista').value=karta.slobodnaSedista[0];
			var div = document.createElement('div'); 
			div.className = 'sjedisteDiv ' + sjediste; //srediti malo sjediste div u css, npr obojiti il sta god
			div.setAttribute('brojMjesta', i+1);
			div.innerText = i+1;
			document.getElementById('sjedista').appendChild(div); 
		} 
		
		$(".slobodnoMjesto").on('click',function(){
	    	
		var brojMjesta = this.getAttribute('brojMjesta'); 
		var ls = localStorage['brojOdabranihSjedista']; 
		
		if(ls == null) {
			localStorage['brojOdabranihSjedista'] == [brojMjesta]; 
			//alert("Izabrali ste sjediste pod brojem  " + brojMjesta);
		} else {
			alert("Izabrali ste sjediste pod brojem " + brojMjesta); 
			console.log("Broj sjedista je " + brojMjesta);
			
			ls = ls.split(";");
			
			var duzinaNizaLs = ls.length; //1
			var klikNaDrugoSjediste = false; 
			for(i=0; i<duzinaNizaLs;i++) {
				if(parseInt(ls[i])+1 == parseInt(brojMjesta)) {
					//mjesto do njega
					//console.log("Odabrano jos jedno sjediste pod brojem " + brojMjesta);
					klikNaDrugoSjediste = true;
				}
			}
			
			//var fruits = ['banana','mango']
			//fruits.push('kiwi')
			if(klikNaDrugoSjediste) {
				ls.push(brojMjesta); 
				console.log("Drugo sjediste ima vrijednost  " + brojMjesta);
			} else {
				if(ls[0]=="") {
					ls[0] = brojMjesta; 
					console.log("Broj prvog mjesta je " + ls[0]); 
				} else {
					alert("Morate izabrati sjedista koja se nalaze jedno kraj drugog!"); 
				}
			}
		}

		localStorage['brojOdabranihSjedista'] = ls.join(";")
		
		//localstorrage 13 14, 340 pomnozim sa duzinom niza 1
		//1 puta 340 + 1 puta 340 = 680 
		
		var cijenaKarte = parseInt(karta.cijenaKarte); 
		var mjesto = localStorage['brojOdabranihSjedista'].split(";").length;
		
		$("#ukupnaCijena").text(mjesto*cijenaKarte); 
	
	});		
	}
});

//na moj profil hocu kupljene karte da vidim 
$("#kupiKartuBtn").on('click',function(){
		
	//var kupiKartuId = id; 
	var odabranaSjedista = localStorage['brojOdabranihSjedista']; 
	
	if(id==null || id==undefined) {
		alert("Doslo je do greske!"); 
	}
	
	if(odabranaSjedista==null || odabranaSjedista == undefined) {
		alert("Doslo je do greske!"); 
	}
	
	var params = {
			'action' : 'kupiKartu', 
			'id' : id,
			'odabranaSjedista' : odabranaSjedista
	}
	
	$.post('KarteServlet', params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			alert("Uspjesno ste kupili kartu!"); 
		}
		
	});
	
	
});

});
