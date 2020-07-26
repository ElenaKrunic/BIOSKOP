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
				f.appendChild(tr);
			}
			$(".pogledajMovie").on('click',function(){
				var id = this.getAttribute('data-movieID');
				if(id>0 && id!=null && id!=undefined){
					window.location.href="Film.html?id="+id;
				}
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
		}
	});
});

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


	/*
	
	var nazivFilter = $('#nazivFilter'); 
	var trajanjeFilter = $('#trajanjeFilter'); 
	var zanrFilter = $('#zanrFilter'); 
	var godinaFilter = $('#godinaFilter'); 
	var distributerFilter = $('#distributerFilter'); 
	var zemljaPorijeklaFilter = $('#zemljaPorijeklaFilter'); 
	var filterBtn = $('#filterBtn'); 
	var filmoviTabela = $('#filmoviTabela'); 
	var logoutLink = $('#logoutLink'); 
	var adminParagraph = $('#adminParagraph'); 
	
	function getFilmovi() {
		var naziv = nazivFilter.val(); 
		var trajanje = trajanjeFilter.val(); 
		var zanr = zanrFilter.val(); 
		var godina = godinaFilter.val(); 
		var distributer = distributerFilter.val(); 
		var zemljaPorijekla = zemljaPorijeklaFilter.val(); 
		
		console.log('naziv : ' + naziv); 
		console.log('trajanje : ' + trajanje); 
		console.log('zanr : ' + zanr); 
		console.log('godina : ' + godina); 
		console.log('distributer : ' + distributer); 
		console.log('zemljaPorijekla : ' + zemljaPorijekla); 
		
		
		var params = {
				filmId : "1", 
				'naziv' : naziv, 
				'trajanje' : trajanje, 
				'zanr' : zanr, 
				'godina' : godina, 
				'distributer' : distributer, 
				'zemljaPorijekla' : zemljaPorijekla,
				action : "loadMovies"
		}; 
		
		$.get('FilmoviServlet', params, function(data){
			console.log(data); 
			
			if(data.status = 'unauthenticated') {
				window.location.replace('Prijava.html'); 
				return; 
			}
			
			if(data.status == 'success') {
				filmoviTabela.find('tr:gt(1)').remove(); 
				
				var pronadjeniF = data.pronadjeniF; 
				for(it in pronadjeniF) {
					filmoviTabela.append(
							'<tr>' + 
								'<td><a href="Film.html?id=' + pronadjeniF[it].id + '">' + 
								pronadjeniF[it].naziv + '</a></td>' + 
								'<td>' + pronadjeniF[it].trajanje + '</td>' + 
								'<td>' + pronadjeniF[it].zanr + '</td>' + 
								'<td>' + pronadjeniF[it].godina + '</td>' + 
								'<td>' + pronadjeniF[it].distributer + '</td>' + 
								'<td>' + pronadjeniF[it].zemljaPorijekla + '</td>' +
							'</tr>' 
					);
				}
			}
		}); 
	}
	
	
	
});

*/

/*
function ucitajFilm(idFilm) {
	var params = {
		action : "loadMovie",
		filmId : idFilm
	}
	
	$.post('FilmoviServlet', params, function(data){
		console.log(data); 
		return data; 
	});
}

	var params = {
			action : "loadMovies", 
			filmId : "1"
	}
	
	$.post('FilmoviServlet', params, function(data){
		var response = JSON.parse(data); 
		if(response.status) {
			for(i=0; i<response.filmovi.length; i++) {
				var film1 = response.filmovi[i]; 
				var f = document.getElementById('filmoviTabela');
				var tr = document.createElement('tr'); 
				tr.className = 'item'; 
			}
		}

*/


/*
$(document).ready(function(){ //klik na link odjava 
	$("#logoutLink").on("click", function(event){
		$.get("OdjavaServlet", function(data){
			console.log(data); //odjavljuje korisnika i cuva podatke  
			
			if(data.status == "unauthenticated"){ 
				window.location.replace("Prijava.html"); 
				return;
			}
		});
		
		event.preventDefault(); 
		return false; 
	});
	//kupljenje elemenata iz html-a
	var nazivFilterID = $("#nazivFilterID");
	var zanrFilterID = $("#zanrFilterID");
	var najmanjaMinutazaFilterID = $("#najmanjaMinutazaFilterID");
	var najvecaMinutazaFilterID = $("#najvecaMinutazaFilterID");
	var distributerFilterID = $("#distributerFilterID");
	var zemljaPorijeklaFilterID = $("#zemljaPorijeklaFilterID");
	var najranijaGodinaFilterID = $("#najranijaGodinaFilterID");
	var posljednjaGodinaFilterID = $("#posljednjaGodinaFilterID");
	var filmoviTabelaID = $("#filmoviTabelaID");
	var adminParagraphID = $("#adminParagraphID");
	var logoutLinkID = $("#logoutLinkID");
	
	function getFilmovi() {
		var nazivFilter = nazivFilterID.val();
		var zanrFilter = zanrFilterID.val();
		var najmanjaMinutazaFilter = najmanjaMinutazaFilterID.val();
		var najvecaMinutazaFilter = najvecaMinutazaFilterID.val();
		var distributerFilter = distributerFilterID.val();
		var zemljaPorijeklaFilter = zemljaPorijeklaFilterID.val();
		var najranijaGodinaFilter = najranijaGodinaFilterID.val();
		var posljednjaGodinaFilter = posljednjaGodinaFilterID.val();
		
		//ovo mi ispisuje u konzoli 
		console.log("nazivFilter " + nazivFilter);
		console.log("zanrFilter " + zanrFilter);
		console.log("najmanjaMinutazaFilter " + najmanjaMinutazaFilter);
		console.log("najvecaMinutazaFilter " + najvecaMinutazaFilter);
		console.log("distributerFilter " + distributerFilter); 
		console.log("zemljaPorijeklaFilter " + zemljaPorijeklaFilter);
		console.log("najranijaGodinaFilter " + najranijaGodinaFilter);
		console.log("posljednjaGodinaFilter " + posljednjaGodinaFilter );
		
		//pravljenje parametara
		var params = {
				"nazivFilter" : nazivFilter,
				"zanrFilter" : zanrFilter,
				"najmanjaMinutazaFilter" : najmanjaMinutazaFilter,
				"najvecaMinutazaFilter" : najvecaMinutazaFilter,
				"distributerFilter" : distributerFilter,
				"zemljaPorijeklaFilter" : zemljaPorijeklaFilter, 
				"najranijaGodinaFilter" : najranijaGodinaFilter, 
				"posljednjaGodinaFilter" : posljednjaGodinaFilter
		};
		//gadjam get metodu i prosljedjujem joj filtrirane parametre 
		$.get("FilmoviServlet", params, function(data){
			console.log(data); 
			
			if(data.status == "unauthenticated"){
				window.location.replace("Prijava.html"); 
				return;
			}
			
			if(data.status == "success"){
				filmoviTabela.find("tr:gt(1)").remove(); 
				
				var filtriraniFilmovi = data.filtriraniFilmovi; 
				for(it in filtriraniFilmovi) {
					filmoviTabela.append(
							'<tr>' + 
							'<td><a href="Film.html?id=' + filtriraniFilmovi[it].id + '">' + filtriraniFilmovi[it].naziv + '</a></td>' + 
							'<td>' + filteredProducts[it].zanr + '</td>' +
							'<td>' + filteredProducts[it].trajanje + '</td>' +
							'<td>' + filteredProducts[it].distributer + '</td>' +
							'<td>' + filteredProducts[it].zemljaPorijekla + '</td>' +
							'<td>' + filteredProducts[it].godinaProizvodnje + '</td>' +	
							'<td>' + 
									'<input type="submit" value="Izaberi film" class="addToCartSubmit" productID="' + filtriraniFilmovi[it].id + '">' + 
							'</td>' + 
						'</tr>'
							);
				}
			}
		});
		
		nazivFilterID.on("keyup", function(event){
			getFilmovi();
			event.preventDefault(); 
			return false; 
		});
		
		zanrFilterID.on("keyup", function(event){
			getFilmovi(); 
			event.preventDefault();
			return false; 
		});
		
		najmanjaMinutazaFilterID.on("keyup",function(event){
			getFilmovi();
			event.preventDefault(); 
			return false;
		});
		
		najvecaMinutazaFilterID.on("keyup", function(event){
			getFilmovi();
			event.preventDefault(); 
			return false;
		});
		
		distributerFilterID.on("keyup", function(event){
			getFilmovi();
			event.preventDefault(); 
			return false; 
		});
		
		zemljaPorijeklaFilterID.on("keyup", function(event){
			getFilmovi();
			event.preventDefault(); 
			return false; 
		});
		
		najranijaGodinaFilterID.on("keyup", function(event){
			getFilmovi(); 
			event.preventDefault();
			return false; 
		});
		
		posljednjaGodinaFilterID.on("keyup", function(event){
			getFilmovi(); 
			event.preventDefault();
			return false;
		});
		
		filmoviTabelaID.on("click", function(event){
			var filmID = $(this).attr('filmID'); 
			console.log('filmID: ' + filmID);
			
			params = {
				'action': 'add', 
				'filmID': filmID,
			}
			
		});
	}
	getFilmovi(); 
});
*/