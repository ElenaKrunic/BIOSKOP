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
	
var url = window.location.href; 
var newUrl = new URL(url); 
var id = newUrl.searchParams.get("id"); 
	
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
		$("#cijenaKarte").text(karta.cenaKarte); 
		if(karta.slobodnaSedista.length<=0) {
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
			if(karta.slobodnaSedista.includes(i+1+"")) {
				sjediste = "slobodnoMjesto";
			} else {
				 //sjediste = "zauzetoMjesto"; 
			}
			
//The includes() method determines whether an array includes a certain value among its entries,
//returning true or false as appropriate.
			jednoSjediste = document.getElementById('sjedista');
			jednoSjediste.value=karta.slobodnaSedista[0];
			//document.getElementById('sjedista').value=karta.slobodnaSedista[0];
			var div = document.createElement('div'); 
			div.className = 'sjedisteDiv ' + sjediste;
			div.setAttribute('brojMjesta', i+1);
			div.innerText = i+1;
			document.getElementById('sjedista').appendChild(div); 
		} 
		
		$(".slobodnoMjesto").on('click',function(){
	    	
		var brojMjesta = this.getAttribute('brojMjesta'); 
		var ls = localStorage['brojOdabranihSjedista']; 
		
		if(ls == undefined) {
			localStorage['brojOdabranihSjedista'] == [brojMjesta]; 
			this.classList.toggle('radiSjediste'); 
		} else {
			ls = ls.split(";");
			console.log("uslo u else i splitovano je " + ls);
			
			var klikNaJednoSjediste = false; 
			for(klik=0; klik<ls.length;klik++) {
				if(parseInt(ls[klik])+1==parseInt(brojMjesta)) {
					console.log("Kliknuto sjediste");
					klikNaJednoSjediste = true;
				}
			}
			
			
			//var fruits = ['banana','mango']
			//fruits.push('kiwi');
			
			
			if(klikNaJednoSjediste) {
				ls.push(brojMjesta); 
				console.log("Sjediste postalo radi sjediste");
				this.classList.toggle('radiSjediste'); 
			} else {
				if(ls[0]=="") {
					ls[0] = brojMjesta; 
					this.classList.toggle('radiSjediste'); 
				} else {
					alert("Morate izabrati sjedista koja se nalaze jedno kraj drugog!"); 
				}
			}
		}

		localStorage['brojOdabranihSjedista'] = ls.join(";")
		
		var cijenaKarte = parseInt(karta.cenaKarte); 
		
		$("#ukupnaCijena").text(localStorage['brojOdabranihSjedista'].split(";").length*cijenaKarte); 
	
	});		
	}
});

//na moj profil hocu kupljene karte da vidim 
$("#kupiKartuBtn").on('click',function(){
		
	var kupiKartuId = id; 
	var odabranaSjedista = localStorage['brojOdabranihSjedista']; 
	
	var params = {
			'action' : 'kupiKartu', 
			'id' : kupiKartuId,
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





