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
				tr.setAttribute('filmID',film.ID);
				var btn = "";
				if(localStorage['uloga']=="Admin"){
					btn = 'adminskaposla';
				}
				else{
					btn = "<span class='detalji' movieID='"+film.ID+"'></span>"
				}
				tr.innerHTML = "<td class='nazivFilma' filmID='"+film.ID+"'>"+film.Naziv+"</td><td>"+film.Trajanje+"</td><td>"+film.Zanrovi+"</td><td>"+film.Godina_Proizvodnje+"</td><td>"+film.Distributer+"</td><td>"+film.Zemlja_Porekla+"</td><td>"+btn+"</td>";
				tabela.appendChild(tr);
			}
			//ovako manipulisem elementima .
			$(".detalji").on('click',function(){
				var id = this.getAttribute('movieID');
				if(id>0 && id!=null && id!=undefined){
					window.location.href="Film.html?id="+id;
				}
			});	
			$(".nazivFilma").on("click", function(){
				var id = this.getAttribute('filmID');
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
	console.log(data); 
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
	
$("#filterBtn").on("click",function(){
	var nazivFilma = $("#nazivFilter").val(); 
	var trajanjeFilma = $("#trajanjeFilter").val(); 
	var zanroviFilma = $("#zanroviFilter").val(); 
	var godinaFilma = $("#godinaFilter").val(); 
	var distributerFilma = $("#distributerFilter").val(); 
	var zemljaPorijeklaFilma = $("#zemljaPorijeklaFilter").val(); 
	
	zanroviFilma.sort(); 
	zanroviFilma = zanroviFilma.join(";"); 
	
	var params = {
			action : "filtrirajFilm", 
			naziv:nazivFilma, 
			trajanje:trajanjeFilma,
			zanr:zanroviFilma,
			opis:"", 
			glumci:"",
			reziser:"", 
			godina:godinaFilma, 
			distributer:distributerFilma, 
			zemlja:zemljaPorijeklaFilma
	}
	
	$.post('FilmoviServlet', params, function(data){
		console.log("Film po odabranom kriterijumu: " + data); 
		var res = JSON.parse(data); 
		if(res.status) {
			if(res.odredjeniFilmovi.length>0) {
				$('tr').slice(2).remove(); 
				for(i=0; i<res.odredjeniFilmovi.length;i++) {
					var movie = res.odredjeniFilmovi[i]; 
					var table = document.getElementById('tabelaFilm'); 
					var tr = document.createElement('tr');
					tr.setAttribute("filmID", movie.ID); 
					
					if(localStorage['uloga']=="Admin") {
						var btn = "<span class='urediFilm' movieID='"+movie.ID+"'></span>" +
								"<span class='obrisiFilm' movieID='"+movie.ID+"'></span>" + 
								"<span class='dodajFilm' movieID='"+movie.ID+"'></span>";
					}
					else {
						var btn =  "<span class='detalji' movieID='"+movie.ID+"'></span>";
					}
					
					tr.innerHTML = "<td class='nazivFilma' filmID='"+movie.ID+"'>"+movie.Naziv+"</td><td>"+movie.Trajanje+"</td><td>"+movie.Zanrovi+"</td><td>"+movie.Godina_Proizvodnje+"</td><td>"+movie.Distributer+"</td><td>"+movie.Zemlja_Porekla+"</td><td>"+btn+"</td>";
					table.appendChild(tr); 
				}
				
				$(".detalji").on('click',function(){
					var id = this.getAttribute("movieID"); 
					if(id>0) {
						window.location.href= "Film.html?id=" + id; 
					}
				});
				
				$(".nazivFilma").on('click', function(){
					var id = this.getAttribute("filmID"); 
					if(id>0) {
						window.location.href = "Film.html?id" + id; 
					}
				});
				
				$(".urediFilm").on('click',function(){
					var id = this.getAttribute("movieID"); 
					window.location.href = "urediFilm.html?id" + id;  
				});
				
				$(".obrisiFilm").on('click', function(){
					var id = this.getAttribute("movieID"); 
					window.location.href = "obrisiFilm.html?id" + id; 
				});
				
				$(".dodajFilm").on('click',function(){
					var id = this.getAttribute("movieID"); 
					window.location.href = "dodajFilm.html?id" + id; 
				}); 
			}
		}
	});
	
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


//slice operator 
/*
 * The slice() method returns the selected elements in an array, as a new array object.

The slice() method selects the elements starting at the given start argument, and ends at, but does not include, the given end argument.
 * 
 * 
 *  var fruits = ["Banana", "Orange", "Lemon", "Apple", "Mango"];
  var citrus = fruits.slice(1, 3); daje 1 i 2 
  document.getElementById("demo").innerHTML = citrus; 
 * 
 */


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

