$(document).ready(function() {
	var userNameID = $("#userNameID");
	var passwordID = $("#passwordID");
	
	$("#prijavaSubmit").on("click", function(event){
		var userName = userNameID.val();
		var password = passwordID.val();
		console.log("userName: " + userName);
		console.log("password: " + password);
		
		var params = {
				"userName" : userName,
				"password" : password
		}
		$.post("PrijavaServlet", params, function(data){
			console.log("Stigao odgovor");
			console.log(data); //served at: /bioskop
			
			if(data.status == "failure") {
				userNameID.val(""); //prazn strng
				passwrodID.val("");
				
				return;
			}
			
			if(data.status == "success") {
				console.log("lala");
				window.location.replace("Glavna.html");
			}
		});
		
		console.log("otislo");
		event.preventDefault();
		return false;
	});
});