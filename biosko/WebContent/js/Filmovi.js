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
