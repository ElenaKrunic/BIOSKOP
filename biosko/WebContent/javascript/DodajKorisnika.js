$(document).ready(function(){
	$("#odjavaLink").on("click",function(event){
		$.get("OdjavaServlet",function(data){
			console.log(data);
			
			if(data.status == "neovlasceno") {
				window.location.replace("Prijava.html");
				return;
			}
		});
		event.preventDefault();
		return false;
	});
});
