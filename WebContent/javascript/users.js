/**
 * 
 */

$(document).ready(function()
{
	setUsers()

	
	$('#update_role_button').click(function(event)
	{
		event.preventDefault()
		
		let user = $('#user_select').val()
		let role = $('#user_role_select').val()
		
		console.log(user)
		console.log(role)
		
		$.ajax({
			type: 'PUT',
			url: 'rest/account/updateRole/' + user + '/' + role,
			complete: function(data)
			{
				window.location.reload(true)
			}
		})
	})
	
	
	$('#filter_apply_button').click(function(event)
	{
		event.preventDefault()
		
		let name = $('#filter_name').val()
		let city = $('#filter_city').val()
		
		let json = JSON.stringify({"name":name,"city":city})
		
		$.ajax({
			type: 'POST',
			url: 'rest/account/getAllFilter',
			data: json,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				let users = data.responseJSON
				
				setUsersToList(users)
			}
		})
		
	})
})


function setUsers()
{
	$.ajax({
		type: 'GET',
		url: 'rest/account/getAll',
		complete: function(data)
		{
			let users = data.responseJSON
			
			setUsersToList(users)
			
			var cities = []
		
			for(let i = 0 ; i < users.length ; i++)
			{
				if(!cities.includes(users[i].city))
				{
					cities.push(users[i].city)
				}
			}
			
			let citySelect = $('#filter_city')
			citySelect.empty()
			citySelect.append($('<option>',{
				value: "",
				text: "-- Select City --"
			}))
			
			for(let i = 0 ; i < cities.length ; i++)
			{
				citySelect.append($('<option>',{
					value:cities[i],
					text: cities[i]
				}))
			}
			
		}
	})
}


function setUsersToList(users)
{
	let list = $('#user_list')
	list.empty()

	for(let i = 0 ; i < users.length ; i++)
	{
		list.append("<li class='list-group-item d-flex justify-content-between align-items-center'>" + users[i].name + "     Email:" + users[i].email + "(" + users[i].role + ")" + "<br>Contact: "+users[i].contact+"</br></li>")
		$('#user_select').append($('<option>',{
			value: users[i].email,
			text: users[i].email
		}))
	}
}