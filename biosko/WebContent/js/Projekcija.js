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

var id = window.location.search.slice(1).split('&')[0].split('=')[1];

	var params = {
			action : "loadProjection", 
			'idProjekcije' : id 
			}
	
	$.post("ProjekcijeServlet", params,function(data){
		var response = JSON.parse(data); 
		//console.log("Na servlet " + response); 
		if(response.status) {
			$("#nazivProjekcije").html("<a href='Film.html?id="+response.idFilma+"''>"+response.nazivFilma+"</a>");
			$("#tipProjekcije4").text(response.tipProjekcije); 
			$("#salaProjekcije4").text(response.nazivSale);  
			$("#startProjekcije4").text(response.terminProjekcije); 
			$("#cijenaKarte4").text(response.cijenaKarte); 
			$("#slobodneKarte4").text(response.brojKarata); 
			$("#statusProjekcije4").text(response.status); 
			
			if(localStorage['uloga']=="Admin") {
				var btn = document.createElement('button'); 
				btn.innerText = "Obrisi"; 
				btn.setAttribute("idProjekcije", id); 
				btn.setAttribute("ID", "obrisiDugme"); 
				btn.onclick = function(){
					if(confirm("Da li zelite da obrisete projekciju? ")) {
						var params = {
								action : 'obrisiProjekciju', 
								projekcijaId : this.getAttribute('idProjekcije')
						}
						
						$.post('ProjekcijeServlet',params, function(data){
							console.log("Podaci su poslati na servlet"); 
							var response = JSON.parse(data); 
							if(response.status) {
								window.location.href = "Glavna.html"; 
							} else {
								alert("Greska")
							}
						});
					}
				}
				document.getElementById('prikazProjekcijeBtn4').appendChild(btn); 
			}
			
			if(localStorage['uloga'] == "obicanKorisnik" || "Admin"){
				var btn = document.createElement('button'); 
				btn.innerText = 'Kupi kartu'; 
				btn.setAttribute('idProjekcije',id); 
				btn.setAttribute('ID', 'kupiKartuDugme'); 
				document.getElementById('prikazProjekcijeBtn4').appendChild(btn);	
			}
		}
		$("#kupiKartuDugme").on('click',function(data){
			//var id = this.getAttribute('idProjekcije');
			window.location.href="KupiKartu.html?id=" + this.getAttribute("idProjekcije"); 
		});
	}); 
	
});
