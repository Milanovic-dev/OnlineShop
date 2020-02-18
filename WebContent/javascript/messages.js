/**
 * 
 */


$(document).ready(function()
{
	
	$('#message_button_send').click(function(event)
	{
		event.preventDefault()
		
		let user = $('#send_message_select').val()
		
		if(user == "") return
		
		let title = $('#send_message_title').val()
		let content = $('#send_message_content').val()
		
		let json = JSON.stringify({"header":title,"content":content})
			
		$.ajax({
			type: 'POST',
			url: 'rest/messages/sendMessage/'+ user,
			data: json,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				$('#toast_alert').toast('show')
				$('#toast_content').text("Your message has been successfully sent!")
			}
		})
	})
	
})