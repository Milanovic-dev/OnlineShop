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
			let categories = data.responseJSON
			
			
			for(let i = 0 ; i < categories.length ; i++)
			{
				$('#deal_category').append($('<option>',{
					value: categories[i].name,
					text: categories[i].name
				}))			
			}
			
			setPublishEvents()
		}
	})
	
})


function setPublishEvents()
{
	
	let urlData = getUrlVars()
	$('#deal_publish_btn').off('click')
	
	if(urlData["name"] != undefined && urlData["name"] != "")
	{
		let name = urlData["name"].replace("%20"," ")
		let price = urlData["price"]
		let desc = urlData["desc"].replace("%20"," ")
		let city = urlData["city"].replace("%20"," ")
		let category = urlData["category"].replace("%20"," ")
		
		console.log("PUBLISH UPDATE")
		
		$('#deal_title').val(name)
		$('#deal_price').val(price)
		$('#deal_desc').val(desc)
		$('#deal_city').val(city)
		
		$('#deal_category').filter(function(){
			
			return $(this).text() == category
		}).prop('selected',true)
		
		$('#deal_category').val(category)
			
		$('#deal_publish_btn').click(function(event)
		{
			event.preventDefault()
						
			$.ajax
			({
				type:'get',
				url:'rest/account/currentUser',
				complete: function(data)	
				{				
					processImage(data.responseJSON,true,name)
				}
			})
		})
		
		
	}
	else
	{
		console.log("PUBLISH NEW")
		
		$('#deal_publish_btn').click(function(event)
		{
			event.preventDefault()
					
			
			$.ajax({
				type:'get',
				url:'rest/account/currentUser',
				complete: function(data)	
				{				
					processImage(data.responseJSON,false,name)
				}
			})
		})
	}


}



function processImage(user,isEdit,dealName)
{
	
	let file;
	if (($('#deal_image'))[0].files.length > 0 ) 
	{
	    file = ($('#deal_image'))[0].files[0];
	    
	    var formData = new FormData();
	    formData.append('fileToUpload', file);
	    formData.append('name',file.name)
	
	    $.ajax({
	    	type: 'POST',
	    	url: 'rest/deals/uploadImage',
	    	data: formData,
	    	contentType: false,
	    	processData: false,
	    	enctype: 'multipart/form-data',
	    	complete: function(data)
	    	{
			
	    		if(data.status != 200)
	    		{
	    			console.log("error")
	    			return
	    		}
			
	    		let path = data.responseJSON
	    		console.log(path)
	    		publishDeal(user,file.name,isEdit,dealName)
	    	}
	    })
		
	}
	else
	{
		
		if(isEdit)
		{
			publishDeal(user,"",isEdit,dealName)
		}
		else
		{
			$('#deal_error').text("You must enter the image.").css('color','red')		
		}
	}
}


function publishDeal(user,path,isEdit,dealName)
{
	
	let title = $('#deal_title').val()
	let price = $('#deal_price').val()
	let desc = $('#deal_desc').val()
	let city = $('#deal_city').val()
	let category = $('#deal_category').val()
	
	let flag = true
	
	if(title == "")
	{
		$('#deal_error').text("You must enter the title.").css('color','red')
		flag = false
	}
	if(price == "")
	{
		$('#deal_error').text("You must enter the price.").css('color','red')
		flag = false
	}
	if(category == "")
	{
		$('#deal_error').text("You must select a category.").css('color','red')
		flag = false
	}
	if(city == "")
	{
		$('#deal_error').text("You must enter the City.").css('color','red')
		flag = false
	}
	if(category == "")
	{
		$('#deal_error').text("You must select a category").css('color','red')
		flag = false
	}
	
	let dealdata = JSON.stringify({"name":title,"price":price,"description":desc,"city":city,"image":path,"category":category})
	
	if(flag)
	{
		let url = ""
			let type = ""
				
				if(!isEdit)
				{
					url = 'rest/deals/addDeal/'+title
					type = 'POST'
				}
				else
				{
					url = 'rest/deals/updateDeal/'+dealName
					type = 'PUT'
				}
		
		$.ajax({
			type: type,
			url: url,
			data: dealdata,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(response)
			{
				if(response.status == 200)
					window.location.href = "index.jsp"
			}
		})	
	}
}



function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}