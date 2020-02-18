/**
 * 
 */


var pageNum = 1
var currPage = 1
var dealNum = 0


$(document).ready(function(){
		
	setContent()
	setCitiesToModal()
	
	$('#go_left_page').click(function(event){
		
		event.preventDefault()
		
		if(currPage == 1) 
		{
			return
		}
		
		currPage--
		
		setContent()
		
	})
	
	$('#go_right_page').click(function(event){
		
		event.preventDefault()
		
		if(currPage == pageNum)
		{
			return
		}	
		
		currPage++
		
		setContent()
	})
	
	$('#filter_search').click(function(event)
	{
		event.preventDefault()
		let urlVars = getUrlVars()
		
		let category = urlVars["cat"]
		
		
		let name = $('#filter_name').val()
		let startprice = $('#filter_startprice').val()
		let endprice = $('#filter_endprice').val()
		let startlikes = $('#filter_startlikes').val()
		let endlikes = $('#filter_endlikes').val()
		let startdate = $('#filter_startdate').val()
		let enddate = $('#filter_enddate').val()
		let city = $('#filter_city').val()
		let status = $('#filter_status').val()
	
		let jsonData = JSON.stringify({"name":name,"city":city,"minPrice":startprice,"maxPrice":endprice,"minLikes":startlikes,"maxLikes":endlikes,"minDate":startdate,"maxDate":enddate,"status":status})
		
		console.log(jsonData)
		
		$.ajax({
			type: 'POST',
			url: 'rest/deals/getActive',
			data: jsonData,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				let deals = data.responseJSON
							
				setDealsOnShop(deals)						
			}
		});
		
		
	})
	
});


function setContent()
{
	
	let urlVars = getUrlVars()
	
	let category = ""
	
	if(urlVars["cat"] != undefined && urlVars["cat"] != "")
	{
		category = urlVars["cat"]
	
	
		let name = $('#filter_name').val()
		let startprice = $('#filter_startprice').val()
		let endprice = $('#filter_endprice').val()
		let startlikes = $('#filter_startlikes').val()
		let endlikes = $('#filter_endlikes').val()
		let startdate = $('#filter_startdate').val()
		let enddate = $('#filter_enddate').val()
		let city = $('#filter_city').val()
		let status = $('#filter_status').val()
	
		let jsonData = JSON.stringify({"name":name,"city":city,"minPrice":startprice,"maxPrice":endprice,"minLikes":startlikes,"maxLikes":endlikes,"minDate":startdate,"maxDate":enddate,"status":status,"category":category})
		
		$.ajax({
			type: 'POST',
			url: 'rest/deals/getActive',
			data: jsonData,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				let deals = data.responseJSON
				
				$('#shop_showing').text("Showing: "+category)
			
				setDealsOnShop(deals)						
			}
		});
		
	}
	else
	{

		$.ajax({
			type:'GET',
			url: 'rest/deals/getPopular',
			complete: function(data)
			{
				let deals = data.responseJSON
							
				$('#shop_showing').text("Showing: Popular")
				
				setDealsOnShop(deals)
			}
		})
		
		
		
	}
}

function setDealsOnShop(deals)
{
	if(deals.length > 9)
	{
		pageNum = Math.ceil(deals.length / 9)	
		$('#go_left_page').show()
		$('#go_right_page').show()
		$('#max_page_number').show()
		$('#curr_page_number').show()
	}
	else
	{
		pageNum = 1	
		$('#go_left_page').hide()
		$('#go_right_page').hide()
		$('#max_page_number').hide()
		$('#curr_page_number').hide()
	}
	
	$('#max_page_number').text("/"+pageNum)	
	$('#curr_page_number').text(currPage)
				
	var index = 0
	
	for(let i = (currPage - 1) * 9 ; i < currPage * 9  ; i++)
	{
		if(i >= deals.length)
		{
			hideCard(index + 1)
			index++
			continue
		}
					
		var obj = deals[i]
								
		setCardContent(index + 1,obj)
		index++
	}
}

function setCardContent(index, deal)
{
	let archived = ""
		
	if(deal.archived == true)
	{
		archived = "  (Archived-Inactive)"
		$('#card-delete'+index).text("Restore")
	}

	
	$('#card'+index).show()
	$('#card-head'+index).text(deal.name + archived)
	$('#card-head'+index).attr('href','reviews.jsp?deal='+deal.name)
	$('#card-text'+index).text(deal.description)
	$('#card-price'+index).text(deal.price+"$")
	$('#card-city'+index).text(deal.city)
	$('#card-rating'+index).text("L: "+deal.likes + " D: "+deal.dislikes)
	$('#card-image'+index).attr("src","data/images/"+deal.image)//+deal.image)
	
	let date = deal.startDate.split("T")
	
	$('#card-date'+index).text(date[0])
	
	$('#card-order'+index).off('click')
	$('#card-order'+index).click(function(event)
	{
		event.preventDefault()
		
		$.ajax({
			type: 'PUT',
			url: 'rest/deals/order/'+deal.name,
			complete: function(data)
			{
				if(data.status == "200")
				{
					window.location.reload(true)					
				}
			}
		})
		
	})
	
	$('#card-edit'+index).off('click')
	$('#card-edit'+index).click(function(event)
	{
		event.preventDefault()
		
		window.location.href = "deal.jsp?name="+deal.name+"&price="+deal.price+"&desc="+deal.description+"&city="+deal.city+"&image="+deal.image+"&category="+deal.category;
	})
	
	
	$('#card-delete'+index).off('click')
	$('#card-delete'+index).click(function(event)
	{
		event.preventDefault()
		
		if(deal.archived)
		{
			$.ajax({
				type: 'PUT',
				url: 'rest/deals/restoreDeal/'+deal.name,
				complete: function(data)
				{
					window.location.reload(true)
				}
			})
		}
		else
		{
			$.ajax({
				type: 'DELETE',
				url: 'rest/deals/deleteDeal/'+deal.name,
				complete: function(data)
				{
					
					if(data.status == 500)
					{
						alert(data.responseText)
						return
					}
					
					window.location.reload(true)
				}
			})		
		}
	})
	
	$('#card-favorite'+index).off('click')
	$('#card-favorite'+index).click(function(event)
	{
		event.preventDefault()
		
		$.ajax({
			type: 'POST',
			url: 'rest/deals/addToFavorite/' + deal.name,
			complete: function(data)
			{
				window.location.reload(true)
			}
		})
		
	})
	
}


function hideCard(index)
{
	$('#card'+index).hide()
}


function setCitiesToModal()
{
	$.ajax({
		type: 'GET',
		url: "rest/deals/getCities",
		complete: function(data)
		{
			let cities = data.responseJSON
			
			for(var i = 0 ; i < cities.length ; i++)
			{
				$('#filter_city').append($('<option>',{
					value: cities[i],
					text: cities[i]
				}))
			}
		}
	})
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

