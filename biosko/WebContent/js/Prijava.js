$(document).ready(function() {
	alert("otvoren dokument");
	var userNameID = $("#userNameID");
	var passwordID = $("#passwordID");
	
	$("#prijavaSubmit").on("click", function(event){
		alert("klik na dugme");
		var userName = userNameID.val();
		var password = passwordID.val();
		console.log("userName: " + userName);
		console.log("password: " + password);
		
		var params = {
				"userName" : userName,
				"password" : password
		}
		$.post("OdjavaServlet", params, function(data){
			console.log("Stigao odgovor");
			console.log(data); //served at: /bioskop
			
			if(data.status == "neuspjeh") {
				userNameID.val(""); //prazn strng
				passwrodID.val("");
				
				return;
			}
			
			if(data.status == "uspjesno") {
				window.location.replace("Bisokop.html");
			}
		});
		
		console.log("Poslat zahtjev");
		event.preventDefault();
		return false;
	});
});