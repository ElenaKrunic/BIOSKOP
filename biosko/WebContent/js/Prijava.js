/*$(document).ready(function(){
	$('#loginSubmit').click(function(){
		var userName = $("userNameID").val(); 
		var password = $("passwordID").val();
		$.ajax({
			type : "POST", 
			url : "PrijavaServlet", 
			data : {"korisnicko ime : " : userName, "sifra :" : password  },
			success : function(data) {
				if(data == "success") {
					console.log("Uspjesno poslati podaci"); 
					window.location.replace("Glavna.html");
				}
				else{
					throw new Exception("Podaci nisu poslati!"); 
				}
			}
		});
	});
});*/

$(document).ready(function() {
	var userNameID = $("#userNameID");
	var passwordID = $("#passwordID");
	
	$('#prijavaSubmit').on('click', function(event){
		var userName = userNameID.val();
		var password = passwordID.val();
		console.log('userName: ' + userName);
		console.log('password: ' + password);
		
		var params = {
				'userName' : userName,
				'password' : password
		}
		$.post('PrijavaServlet', params, function(data){
			console.log(data);
			
			if(data.status == 'failure') {
				userNameID.val(''); 
				passwordID.val('');
				return;
			}
			
			if(data.status == 'success') {
				console.log('lala');
				window.location.replace('Glavna.html');
			}
		});
		
		event.preventDefault();
		return false;
	});
});
