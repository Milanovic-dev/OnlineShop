/**
 * 
 */


$(document).ready(function()
{
	let urlVars = getUrlVars()
	
	if(urlVars == undefined)
	{
		$('#reviews_empty_message').show()
		return
	}

	
	let dealID = urlVars.deal
	
	$.ajax({
		type: 'GET',
		url: 'rest/deals/get/'+dealID,
		complete:function(data)
		{
			let reviews = data.responseJSON.reviews
			
			let list = $('#review_list')
			list.empty()
			
			for(let i = 0 ; i < reviews.length ; i++)
			{
				list.append(getReviewCardHTML(reviews[i]))
			}
		}
	})
	

})

function getReviewCardHTML(review)
{
	let forText = ""
	
	if(review.type == "Deal")
	{
		forText = review.deal
	}
	else if(review.type == "Account")
	{
		forText = review.seller
	}
	
	let isDealHonored = ""
	let DescValid = ""
		
		if(review.isDealHonored)
		{
			isDealHonored = "Yes"
		}
		else
		{
			isDealHonored = "No"
		}
		
		if(review.isDescTrue)
		{
			DescValid = "Yes"
		}
		else
		{
			DescValid = "No"
		}
	
	return "<div class='card mb-3' style='max-width: 540px;'><div class='row no-gutters'><div class='col-md-4'></div><div class='col-md-8'><div class='card-body'><h5 class='card-title'>"+review.header+"</h5><p class='card-text'>"+review.content+"</p><p class='card-text'>Description was valid: "+DescValid+"</p><p class='card-text'>Deal was honored: "+isDealHonored+"</p><p class='card-text'><small class='text-muted'>For: "+forText+"</small></p></div></div></div></div>"
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