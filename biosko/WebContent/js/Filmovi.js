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
			action : "loadMovies", 
			filmId : "1"
	}
	
	$.post('FilmoviServlet', params, function(data) {
		var response = JSON.parse(data); 
		if(response.status){
			for(i=0;i<response.filmovi.length;i++){
				var film = response.filmovi[i];
				var tabela = document.getElementById('tabelaFilm');
				var tr = document.createElement('tr');
				tr.className="item";
				tr.setAttribute('data-FilmID',film.ID);
				var btn = "";
				if(localStorage['uloga']=="Admin"){
					//The <span> tag is easily styled by CSS or manipulated with JavaScript using the class or id attribute.
					btn = "<span class='editMovie' data-movieID='"+film.ID+"'></span>" +
							"<span class='deleteMovie' data-movieID='"+film.ID+"'></span>";
				}
				else{
					btn = "<span class='pogledajMovie' data-movieID='"+film.ID+"'></span>"
				}
				tr.innerHTML = "<td class='movie_name' data-filmid='"+film.ID+"'>"+film.Naziv+"</td><td>"+film.Trajanje+"</td><td>"+film.Zanrovi+"</td><td>"+film.Godina_Proizvodnje+"</td><td>"+film.Distributer+"</td><td>"+film.Zemlja_Porekla+"</td><td>"+btn+"</td>";
				tabela.appendChild(tr);
			}
			$(".pogledajMovie").on('click',function(){
				var id = this.getAttribute('data-movieID');
				if(id>0 && id!=null && id!=undefined){
					window.location.href="Film.html?id="+id;
				}
			});	
		}
	});
	
	
	var params = {
			action : "loadGenres"
	}
	
	$.post('FilmoviServlet', params, function(data){
	var response = JSON.parse(data); 
	if(response.status) {
		for (i = 0; i < response.zanrovi.length; i++) {
			var z = document.createElement('option'); 
			z.value = response.zanrovi[i]; //vrijednost z ce biti opcije od zanrova
			z.innerText = response.zanrovi[i]; 
			document.getElementById('zanroviFilter').append(z);
		}
	}
});
	
	
	
});

			//$(".editMovie").on('click',function(){
				//window.location.href="dodajIzmeniFilm.html?id="+this.getAttribute('data-movieID');
			//})
			//$(".deleteMovie").on("click",function(){
				//if(confirm("Da li ste sigurni da zelite da obrisete?")){
				//	var params = {
					//		action: "obrisiFilm",
						//	filmID: this.getAttribute('data-movieID')
						//}
						// kontrola toka se račva na 2 grane
				//	$.post('FilmoviServlet', params, function(data) { // u posebnoj programskoj niti se šalje (asinhroni) zahtev
							// tek kada stigne odgovor izvršiće se ova anonimna funkcija
					//		var odg = JSON.parse(data);
						//	if(odg.status){
							//	window.location.href="Filmovi.html";
							//}
							//else{
								//alert("Greska prilikom brisanja!");
								//pushNotification('red',"Greska prilikom brisanja");
						//	}
					//});
				//}
			//})

//sortiranje : 
/*
<button onclick="w3.sortHTML('#id01', 'li')">Sort</button>
<ul id="id01">
  <li>Oslo</li>
  <li>Stockholm</li>
  <li>Helsinki</li>
  <li>Berlin</li>
  <li>Rome</li>
  <li>Madrid</li>
</ul>
 */

