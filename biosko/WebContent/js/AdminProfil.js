$(document).ready(function(){
	$('logoutLink').on('click',function(event){
		$.get('OdjavaServlet', function(data){
			if(data.status == 'unauthenticated') {
				window.location.replace('Prijava.html'); 
				return;
			}
		}); 
		event.preventDefault(); 
		return false; 
	});
	
	var params = {
			action : 'ucitajKorisnike'
	}

	$.post('KorisnikServlet', params, function(data){
		var response = JSON.parse(data); 
		console.log(response);
		if(response.status) {
			for(i=0; i<response.listaKorisnikaSvi.length;i++) {
				var jedanKorisnik = response.listaKorisnikaSvi[i]; 
				var tr = document.createElement('tr'); 
				tr.innerHTML="<td korisnikId='"+jedanKorisnik.ID+"' class='korisnickoImeKlik'>"+jedanKorisnik.Username+"</td><td>"+jedanKorisnik.Datum+"</td><td>"+jedanKorisnik.Uloga+"</td>";
				var tabela = document.getElementById('sviKorisnici');
				tabela.appendChild(tr);
			}
			
			$("#korisnickoImeKlik").on('click',function(){
				var id = this.getAttribute('korisnikId'); 
				if(id>0) {
					window.location.href="MojNalog2.html?id="+id;
				}
			});
		}
	}); 
	
	
	
});