/**
 * 
 */


$(document).ready(function(){
	
	$('#signin_btn').click(function(event){
		event.preventDefault();
		let user = $('input[name="email"]').val();
		let pass = $('input[name="password"]').val();
		let logindata = JSON.stringify({"email": user, "password" : pass});
		console.log(logindata);
		//$('#error').hide();
		$.ajax({
			type: 'post' ,
			url:'rest/account/login',
			data: logindata,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				if(data["status"] == 200)
				{
					window.location.href = "index.jsp"
				}
				else
				{
					console.log("Neuspesno")
					$("login_page_error").show()
					$('#login_page_error').text("Email or password is invalid.").css('color', 'red')
				}
			}
		});
	});
	
	$('#signup_btn').click(function(event){
		
		event.preventDefault();
		
		let user = $('input[name="usernameReg"]').val();
		let pass = $('input[name="passwordReg"]').val();
		let name = $('input[name="nameReg"]').val();
		let lastName = $('input[name="lastNameReg"]').val();
		let email = $('input[name="emailReg"]').val();
		let city = $('input[name="cityReg"]').val();
		let phone = $('input[name="phoneReg"]').val();
		
		let flag = true
	
		if(city == "")
		{
			$('#register_error').text("Please enter a City!").css('color','red')
			flag = false
		}
		if(phone == "")
		{
			$('#register_error').text("Please enter a contact phone!").css('color','red')
			flag = false
		}
		if(name == "")
		{
			$('#register_error').text("Please enter a name!").css('color','red')
			flag = false
		}
		if(lastName == "")
		{
			$('#register_error').text("Please enter a lastName!").css('color','red')
			flag = false
		}
		if(pass == "")
		{
			$('#register_error').text("Please enter a password!").css('color','red')
			flag = false
		}
		if(email == "" || (email.indexOf('@') == -1) || (email.indexOf('.com') == -1))
		{
			$('#register_error').text("Please enter a valid email adress!").css('color','red')
			flag = false
		}
		if(user == "")
		{
			$('#register_error').text("Please enter a username!").css('color','red')
			flag = false
		}
		if(user.length < 3)
		{
			$('#register_error').text("Username must be longer than 3 characters!").css('color','red')
			flag = false
		}
		
		
		if(flag)
		{
			let registerData = JSON.stringify({"username":user,"email":email,"password":pass,"name":name,"lastName":lastName,"city":city,"contact":phone});
			console.log(registerData)
			
			$.ajax({
				type : 'post',
				url: 'rest/account/register',
				data : registerData,
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				complete: function(data)
				{
					console.log(data)
					if(data["status"] == 400)
					{
						if(data["responseText"] == "Email")
						{
							$('#register_error').text("Email already in use.").css('color','red')
						}
						if(data["responseText"] == "Username")
						{
							$('#register_error').text("Username already in use.").css('color','red')				
						}
					}
					else
					{
						console.log("Done")
						window.location.href = "login.jsp"
					}
				}
			
			})		
		}
		
				
	});
		
	
})
	
