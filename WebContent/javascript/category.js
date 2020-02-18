/**
 * 
 */

$(document).ready(function()
{
	$.ajax({
		type: 'GET',
		url: 'rest/categories/getAll',
		complete: function(data)
		{
			let cats = data.responseJSON
			
			let list = $('#cat_list')
			list.empty()
			
			let removeList = $('#cat_remove_list')
			removeList.empty()
			let updateList = $('#cat_update_list')
			updateList.empty()
			for(let i = 0 ; i < cats.length ; i++)
			{
				list.append("<li class='list-group-item d-flex justify-content-between align-items-center' data-toggle='tooltip' data-placement='right' title='Tooltip'>"+ cats[i].name + "</li>")
				removeList.append($('<option>',{
					value: cats[i].name,
					text: cats[i].name
				}))
				updateList.append($('<option>',{
					value: cats[i].name,
					text: cats[i].name
				}))
			}
		
			
		}
	})
	
	
	$('#create_button').click(function(event)
	{
		event.preventDefault()
		
		let name = $('#cat_add_name').val()
		let desc = $('#cat_add_desc').val()
		
		let json = JSON.stringify({"name":name,"description":desc})
		
		$.ajax({
			type: 'POST',
			url: "rest/categories/add",
			data: json,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				window.location.reload(true)
			}
		})
	})
	
	
	$('#remove_button').click(function(event)
	{
		event.preventDefault()
		
		let name = $('#cat_remove_list').val()
				
		$.ajax({
			type: 'DELETE',
			url: 'rest/categories/delete/'+name,
			complete: function(data)
			{
				window.location.reload(true)
			}
		})
	})
	
	$('#update_button').click(function(event)
	{
		event.preventDefault()
		
		let name = $('#cat_update_list').val()
		let newName = $('#cat_update_name').val()
		let newDesc = $('#cat_update_desc').val()
		
		let json = JSON.stringify({"name":newName,"description":newDesc})
		
		$.ajax({
			type: 'PUT',
			url: 'rest/categories/update/'+name,
			data: json,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				window.location.reload(true)
			}
		})
	})
	
})

