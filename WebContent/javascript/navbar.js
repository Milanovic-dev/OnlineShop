/**
 * 
 */


$(document).ready(function(){
	
	setCategories()
	
	$('#sign_in_button').click(function(event)
	{
		event.preventDefault()
		window.location.href = "login.jsp"
		
	})
	
	$('#sign_up_button').click(function(event)
	{
		event.preventDefault()
		window.location.href = "register.jsp"
	})

	
	$('#logout_button').click(function(event){
		
		event.preventDefault();
		
		
		$.ajax({
			type : 'POST',
			url: 'rest/account/logout',
			complete: function(data)
			{	
				window.location.href = "index.jsp"			
			}
		})
	});
	

});

function setCategories()
{
	
	$.ajax({
		type: 'GET',
		url: "rest/categories/getAll",
		complete: function(data)
		{
			let cats = data.responseJSON
			
			console.log(data)
			
			let dd = $('#categories_select')
			dd.empty()
			
			dd.append("<a class='dropdown-item' href='index.jsp'>Popular</a>")
			
			for(var i = 0 ; i < cats.length ; i++)
			{
				let name = cats[i].name
				dd.append("<a class='dropdown-item' href='index.jsp?cat="+name+"'>"+name+"</a>")
			}
		}
	})
}