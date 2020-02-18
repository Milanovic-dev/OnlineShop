
/**
 * 
 */

var currUser = null

function sessionCheck(){
	
    $.ajax({
    	type: 'get',
        url:'rest/account/currentUser',
        complete:function(data){
        	      	
        	console.log(data)
           	
        	if(data["responseText"] != undefined && data["responseText"] != "")
        	{
        		console.log(data.responseJSON.role)
        		setCurrentPageToLoggedOnUser(data.responseJSON)	     		
        	}	
        	else
        	{
        		setUpCurrentPageToLoggedOffUser()
        	}	
        		      	    
        }

    });

}


function setCurrentPageToLoggedOnUser(user)
{
	$('#log_buttons').hide()
	$('#account_buttons').show()	
	$('#log_buttons').removeAttr('hidden')
	$('#account_buttons').removeAttr('hidden')
	setMessageBadge()
	setDropdownOptions(user)
	setMessageModal(user)
	
	if(window.location.pathname.indexOf("index.jsp") !== -1)
	{
		setShopButtonsToLoggedOnUser(user)		
	}
	else if(window.location.pathname.indexOf("users.jsp") !== -1)
	{
		setUserPage(user)
	}
	else if(window.location.pathname.indexOf("messages.jsp") !== -1)
	{
		setUserMessages(user)
	}
	else if(window.location.pathname.indexOf("buyer.jsp") !== -1)
	{
		setBuyerOrderedDeals(user)
	
		$('#ordered_button').click(function(event)
		{
			event.preventDefault()
			
			setBuyerOrderedDeals(user)
		})
		
		$('#delivered_button').click(function(event)
		{
			event.preventDefault()
			
			setBuyerDeliveredDeals(user)
		})
		
		$('#favorites_button').click(function(event)
		{
			event.preventDefault()
			
			setBuyerFavoritesDeals(user)
		})
		
		$('#reviewed_button').click(function(event)
		{
			event.preventDefault()
			
			setBuyerReviewedDeals()
		})
		
		setReviewModalButtons()
		
	}
	else if(window.location.pathname.indexOf("seller.jsp") !== -1)
	{
		setSellerPublishedDeals(user)
		setSellerRatings(user)
		
		$('#published_button').click(function(event)
		{
			event.preventDefault()
			
			setSellerPublishedDeals(user)
		})
		
		$('#reviews_button').click(function(event)
		{
			event.preventDefault()
			
			setSellerReviewDeals(user)
		})
		
	}
	
}

function setReviewModalButtons()
{

	$('#submit_deal_review').click(function(event)
	{
		event.preventDefault()
		
		
		let file;
		if (($('#review_deal_image'))[0].files.length > 0 ) 
		{
			file = ($('#review_deal_image'))[0].files[0];
							    
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
					console.log(data)
					if(data.status == 500)
						alert(data.responseText)
					
					let dealName = $('#review_deal_name').text()
					
					let title = $('#review_deal_title').val()
					let content =$('#review_deal_content').val()
					let desc = $('#review_deal_desc').val()
					let honored = $('#review_deal_honored').val()	
					let like = $('#like_deal_radio').is(":checked")
					let dislike = $('#dislike_deal_radio').is(":checked")

					
					let rating = "None"
					
					if(like)
					{
						rating = "Like"
					}
					else if(dislike)
					{
						rating = "Dislike"
					}
									
					let jsonData = JSON.stringify({"header":title,"content":content,"imageSrc":file.name,"isDescTrue":desc,"isDealHonored":honored})
					
					console.log(jsonData)
										
					$.ajax({
						type: 'POST',
						url: 'rest/deals/addReviewDeal/'+dealName+'/'+rating,
						data: jsonData,
						dataType: "json",
						contentType: "application/json; charset=utf-8",
						complete: function(data)
						{
							if(data.responseText.indexOf('Error') != -1)
							{
								alert(data.responseText)
							}
						}
					})
				}
		
			})
		}
		else
		{
			let dealName = $('#review_deal_name').text()
			
			let title = $('#review_deal_title').val()
			let content =$('#review_deal_content').val()
			let desc = $('#review_deal_desc').val()
			let honored = $('#review_deal_honored').val()	
			let like = $('#like_deal_radio').is(":checked")
			let dislike = $('#dislike_deal_radio').is(":checked")

			
			let rating = "None"
			
			if(like)
			{
				rating = "Like"
			}
			else if(dislike)
			{
				rating = "Dislike"
			}
							
			let jsonData = JSON.stringify({"header":title,"content":content,"imageSrc":file.name,"isDescTrue":desc,"isDealHonored":honored})
			
			console.log(jsonData)
								
			$.ajax({
				type: 'POST',
				url: 'rest/deals/addReviewDeal/'+dealName+'/'+rating,
				data: jsonData,
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				complete: function(data)
				{
					if(data.responseText.indexOf('Error') != -1)
					{
						alert(data.responseText)
					}
				}
			})
		}
	})
			
	$('#submit_seller_review').click(function(evnet)
	{
		event.preventDefault()
		
		let sellerEmail = $('#review_seller_name').text()
		
		let title = $('#review_seller_title').val()
		let content =$('#review_seller_content').val()
		let desc = $('#review_seller_desc').is(":checked")
		let honored = $('#review_seller_honored').is(":checked")
		let like = $('#like_seller_radio').is(":checked")
		let dislike = $('#dislike_seller_radio').is(":checked")
		
		let rating = "None"
		
		if(like)
		{
			rating = "Like"
		}
		else if(dislike)
		{
			rating = "Dislike"
		}
						
		let jsonData = JSON.stringify({"header":title,"content":content,"imageSrc":"","isDescTrue":desc,"isDealHonored":honored})
		
		console.log(jsonData)
		
		$.ajax({
			type: 'POST',
			url: 'rest/deals/addReviewSeller/'+sellerEmail+'/'+rating,
			data: jsonData,
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			complete: function(data)
			{
				if(data.responseText.indexOf('Error') != -1)
				{
					alert(data.responseText)
				}	
			}
		})
	})
}

function setUpCurrentPageToLoggedOffUser()
{
	$('#log_buttons').show()
	$('#account_buttons').hide()	
	$('#log_buttons').removeAttr('hidden')
	$('#account_buttons').removeAttr('hidden')
	
	setShopButtonsToLoggedOffUser()	
}


function setUserPage(user)
{
	if(user.role == "Admin")
	{
		$('#admin_update_button').show()
	}
	else
	{
		$('#admin_update_button').hide()
	}
}


function setMessageBadge()
{
	$.ajax({
		type: 'get',
		url: 'rest/account/getUnreadMessages',
		complete: function(data)
		{
			let messages = JSON.parse(data.responseText)
			
			let num = messages.length;
			
			
			if(num > 0)
			{
				$('#account_badge').show()
				$('#account_badge').text(num)				
			}
			else
			{
				$('#account_badge').hide()
			}
			
			
		}
			
	})
}


function setUserMessages(user)
{
	
	$.ajax({
		type: 'PUT',
		url: 'rest/account/markMessagesAsRead',
		complete: function(data)
		{
			
		}
	})
	
	let messageList = $('#message_list')
	
	let messages = user.messages
	
	if(messages.length == 0)
	{
		$('#inbox_empty_message').show()
	}
	else
	{
		$('#inbox_empty_message').hide()
	}
	
	for(let i = 0 ; i < messages.length ; i++)
	{
		if(messages[i].archived) continue
		
		messageList.append(getMessageContent(messages[i],i,user))
		
		$('#message_delete'+i).click(function(event)
		{
			event.preventDefault()
			let message = messages[i]
			
			$.ajax({
				type: 'DELETE',
				url: 'rest/messages/deleteMessage/'+message.header+'/'+message.content,
				complete: function(data)
				{
					window.location.reload(true)
				}
			})
		})
		
		$('#message_reply'+i).attr('data-toggle','modal')
		$('#message_reply'+i).attr('data-target','#message_modal_send')
		
		$('#message_reply'+i).click(function(event)
	    {
			event.preventDefault()
			
			let replyTo = messages[i].sender
			$('#send_message_select').val(replyTo)		
	    })
	}

}

function getMessageContent(message,i,user)
{	
	let replyHTML = ""
	let deleteHTML = "<button class='btn btn-primary' id='message_delete"+i+"'>Delete</button>"
		
	if(user.role == "Seller" && message.sender.indexOf("koko") == -1)
	{
		replyHTML = "<button class='btn btn-primary' id='message_reply"+i+"'>Reply</button>"
	}
	
	return "<div class='card text-center'><div class='card-header' id='message_from'>"+"From: "+message.sender+"("+message.dealName+")</div><div class='card-body'><h5 class='card-title' id='message_title'>"+message.header+"</h5><p class='card-text' id='message_content'>"+message.content+"</p>"+deleteHTML+replyHTML+"</div><div class='card-footer text-muted' id='message_date'>"+message.dateSent+"</div></div>"
}


function setBuyerOrderedDeals(user)
{
	$('#current_showing').text("Currently showing: Ordered")
	let deals = user.ordered
	
	$('#deal_list').empty()
	
	if(deals.length == 0)
	{
		$('#no_deals_message').show()
	}
	else
	{
		$('#no_deals_message').hide()
	}
	
	for(let i = 0 ; i  < deals.length ; i++)
	{
		$('#deal_list').append(getBuyerOrderedDealHTML(deals[i],i))
		
		$('#button_ordered'+i).click(function(event)
		{
			event.preventDefault()
			
			let name = deals[i].name
			console.log(name)
					
			$.ajax({
				type: 'PUT',
				url: 'rest/deals/setDelivered/'+name,
				complete: function(data)
				{
					if(data.responseText.indexOf('Error') != -1)
					{
						alert(data.responseText)
						return
					}
					
					window.location.reload(true)
				}
			})
		})
		
	}
	
}


function getBuyerOrderedDealHTML(deal,i)
{
	console.log(deal.image)
	return "<div class='card mb-3' style='width: 20rem;'><img src='data/images/"+deal.image+"' class='card-img-top' alt='data/images/logo.png'><div class='card-body'><h5 class='card-title'>"+deal.name+"(In Process)"+"</h5><h6>"+deal.price+"</h6><p class='card-text'>"+deal.description+"</p><button class='btn btn-primary' id='button_ordered"+i+"'>Set Delivered</button></div></div>"
}

function setBuyerDeliveredDeals(user)
{
	$('#current_showing').text("Currently showing: Delivered")
	let deals = user.delivered
	
	$('#deal_list').empty()
	
	if(deals.length == 0)
	{
		$('#no_deals_message').show()
	}
	else
	{
		$('#no_deals_message').hide()
	}
	
	for(let i = 0 ; i  < deals.length ; i++)
	{
		$('#deal_list').append(getBuyerDeliveredDealHTML(deals[i],i))
		$('#review_deal_button'+i).click(function(event)
		{
			event.preventDefault()
			
			$('#review_deal_name').text(deals[i].name)
		})
		
		$('#review_seller_button'+i).click(function(event)
		{
			event.preventDefault()
			
			$('#review_seller_name').text(deals[i].seller)
		})
	}
}

function getBuyerDeliveredDealHTML(deal,i)
{
	return "<div class='card mb-3' style='width: 20rem;'><img src='data/images/"+deal.image+"' class='card-img-top' alt='...''><div class='card-body'><h5 class='card-title'>"+deal.name+"(Delivered)"+"</h5><h6>"+deal.price+"</h6><p class='card-text'>"+deal.description+"</p><button class='btn btn-primary' id='review_seller_button"+i+"' data-toggle='modal' data-target='#review_modal_seller'>Review Seller</button><button class='btn btn-primary' id='review_deal_button"+i+"' data-toggle='modal' data-target='#review_modal_deal'>Review Deal</button></div></div>"
}

function setBuyerFavoritesDeals(user)
{
	$('#current_showing').text("Currently showing: Favorite")
	let deals = user.favorites
	
	$('#deal_list').empty()
	
	if(deals.length == 0)
	{
		$('#no_deals_message').show()
	}
	else
	{
		$('#no_deals_message').hide()
	}
	
	for(let i = 0 ; i  < deals.length ; i++)
	{
		$('#deal_list').append(getBuyerFavoriteDealHTML(deals[i],i))
		
		$('#button_favorite'+i).click(function(event)
		{
			event.preventDefault()
			let name = deals[i].name
		})
	}
}

function getBuyerFavoriteDealHTML(deal,i)
{
	return "<div class='card mb-3' style='width: 20rem;'><img src='data/images/"+deal.image+"'' class='card-img-top' alt='...''><div class='card-body'><h5 class='card-title'>"+deal.name+"("+deal.status+")"+"</h5><h6>"+deal.price+"</h6><p class='card-text'>"+deal.description+"</p><button class='btn btn-primary' id='button_favorite"+i+"'>Buy</button></div></div>"
}

function setBuyerReviewedDeals(user)
{
	$('#current_showing').text("Currently showing: Reviews")
	$('#submit_seller_review').text("Update")	
	$('#submit_deal_review').off()
	$('#submit_seller_review')
	$('#deal_list').empty()
	
	$.ajax({
		type:'GET',
		url: 'rest/deals/getBuyerReviews',
		complete: function(data)
		{
			let reviews = data.responseJSON
			
	
			for(let i = 0 ; i < reviews.length ; i++)
			{
				if(reviews[i].archived) continue
				
				$('#deal_list').append(getBuyerReviewHTML(reviews[i],i))
				
				$('#buyer_review_delete'+i).click(function(event)
				{
					event.preventDefault()
						
					
					let jsondata = JSON.stringify(reviews[i])
					
					$.ajax({
						type: 'DELETE',
						url: 'rest/deals/deleteReview',
						data: jsondata,
						dataType: "json",
						contentType: "application/json; charset=utf-8",
						complete: function(data)
						{
							window.location.reload(true)
						}
					})
					
				})		
				
				
				if(reviews[i].type == "Deal")
				{
					$('#buyer_review_edit'+i).attr('data-toggle','modal')
					$('#buyer_review_edit'+i).attr('data-target','#review_modal_deal')
				}
				else
				{
					$('#buyer_review_edit'+i).attr('data-toggle','modal')
					$('#buyer_review_edit'+i).attr('data-target','#review_modal_seller')
				}
				
				$('#buyer_review_edit'+i).click(function(event)
				{
					event.preventDefault()
										
					if(reviews[i].type == "Deal")
					{
						$('#review_deal_name').text(reviews[i].deal)
						$('#review_deal_title').val(reviews[i].header)
						$('#review_deal_content').val(reviews[i].content)
						$('#review_deal_desc').prop('checked',reviews[i].isDescTrue)
						$('#review_deal_honored').prop('checked',reviews[i].isDealHonored)	
					}
					else
					{
						$('#review_seller_name').text(reviews[i].seller)
						$('#review_seller_title').val(reviews[i].header)
						$('#review_seller_content').val(reviews[i].content)
						$('#review_seller_desc').prop('checked',reviews[i].isDescTrue)
						$('#review_seller_honored').prop('checked',reviews[i].isDealHonored)
					}
					
					
					$('#submit_deal_review').off()
					$('#submit_deal_review').click(function(event)
					{
						
							event.preventDefault()
							
							let file;
							if (($('#review_deal_image'))[0].files.length > 0 ) 
							{
								file = ($('#review_deal_image'))[0].files[0];
							    
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
			
							    		
							    		let dealName = $('#review_deal_name').text()
										
										let title = $('#review_deal_title').val()
										let content =$('#review_deal_content').val()
										let desc = $('#review_deal_desc').is(":checked")
										let honored = $('#review_deal_honored').is(":checked")	
										
										
										let jsonData = JSON.stringify({"deal":dealName,"header":title,"content":content,"imageSrc":file.name,"isDescTrue":desc,"isDealHonored":honored,"type":"Deal"})
															
										$.ajax({
											type: 'PUT',
											url: 'rest/deals/updateReview/'+reviews[i].header+'/'+reviews[i].content,
											data: jsonData,
											dataType: "json",
											contentType: "application/json; charset=utf-8",
											complete: function(data)
											{
												if(data.responseText.indexOf('Error') != -1)
												{
													alert(data.responseText)
												}
												
												window.location.reload(true)
											}
										})
							    		
							    						    		
							    	}
							    })
							}
							else
							{
								let dealName = $('#review_deal_name').text()
								
								let title = $('#review_deal_title').val()
								let content =$('#review_deal_content').val()
								let desc = $('#review_deal_desc').is(":checked")
								let honored = $('#review_deal_honored').is(":checked")	
								
								
								let jsonData = JSON.stringify({"header":title,"content":content,"imageSrc":file.name,"isDescTrue":desc,"isDealHonored":honored,"type":"Deal"})
													
								$.ajax({
									type: 'PUT',
									url: 'rest/deals/updateReview/'+reviews[i].header+'/'+reviews[i].content,
									data: jsonData,
									dataType: "json",
									contentType: "application/json; charset=utf-8",
									complete: function(data)
									{
										if(data.responseText.indexOf('Error') != -1)
										{
											alert(data.responseText)
										}
										
										window.location.reload(true)
									}
								})
							}
								
											
				
					})
					
					
					$('#submit_seller_review').off()
					$('#submit_seller_review').click(function(event)
					{
						event.preventDefault()

						let sellerEmail = $('#review_seller_name').text()
							
							let title = $('#review_seller_title').val()
							let content =$('#review_seller_content').val()
							let desc = $('#review_seller_desc').is(":checked")
							let honored = $('#review_seller_honored').is(":checked")
					
																	
							let jsonData = JSON.stringify({"seller":reviews[i].seller,"header":title,"content":content,"imageSrc":"","isDescTrue":desc,"isDealHonored":honored,"type":"Account"})
									
							
							console.log(jsonData)
							
							$.ajax({
								type: 'PUT',
								url: 'rest/deals/updateReview/'+reviews[i].header+'/'+reviews[i].content,
								data: jsonData,
								dataType: "json",
								contentType: "application/json; charset=utf-8",
								complete: function(data)
								{
									if(data.responseText.indexOf('Error') != -1)
									{
										alert(data.responseText)
									}	
									
									window.location.reload(true)
								}
							})					
						
					})
								
																			
				})
			}
			
		}
	})
}




function getBuyerReviewHTML(review,i)
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
	
	return "<div class='card mb-3' style='max-width: 540px;'><div class='row no-gutters'><div class='col-md-4'><img src='data/images/"+review.imageSrc+"' class='card-img' alt='data/images/logo.png'></div><div class='col-md-8'><div class='card-body'><h5 class='card-title'>"+review.header+"</h5><p class='card-text'>"+review.content+"</p><p class='card-text'>Description was valid: "+DescValid+"</p><p class='card-text'>Deal was honored: "+isDealHonored+"</p><p class='card-text'><small class='text-muted'>For: "+forText+"</small></p><button class='btn btn-primary' id='buyer_review_edit"+i+"'>Edit</button><button class='btn btn-primary' id='buyer_review_delete"+i+"'>Delete</button></div></div></div></div>"
}

function setSellerPublishedDeals(user)
{
	$('#current_showing').text("Currently showing: Published")
	let deals = user.published.concat(user.delivered)
	
	$('#deal_list').empty()
	
	if(deals.length == 0)
	{
		$('#no_deals_message').show()
	}
	else
	{
		$('#no_deals_message').hide()
	}
	
	for(let i = 0 ; i  < deals.length ; i++)
	{
		if(deals[i].archived == true)
		{
			continue
		}
		
		$('#deal_list').append(getSellerPublishedDealHTML(deals[i],i))
		$('#button_published_delete'+i).click(function(event)
		{
			event.preventDefault()
			let name = deals[i].name
			
			$.ajax({
				type: 'DELETE',
				url: 'rest/deals/deleteDeal/'+name,
				complete: function(data)
				{
					if(data.responseText.indexOf('Error') != -1)
					{
						alert(data.responseText)
						return
					}
					window.location.reload(true)
				}
			})
			
		})
		$('#button_published_edit'+i).click(function(event)
		{
			event.preventDefault()
			let deal = deals[i]
			
			window.location.href = "deal.jsp?name="+deal.name+"&price="+deal.price+"&desc="+deal.description+"&city="+deal.city+"&image="+deal.image+"&category="+deal.category;
		})
	}

}

function getSellerPublishedDealHTML(deal,i)
{
	let deleteButtonHTML = ""
	let editButtonHTML = ""
		
	if(deal.status == "Active")
	{
		deleteButtonHTML = "<button class='btn btn-primary' id='button_published_delete"+i+"'>Delete</button>"
		editButtonHTML = "<button class='btn btn-primary' id='button_published_edit"+i+"'>Edit</button>"
	}
		
	return "<div class='card mb-3' style='width: 20rem;'><img src='data/images/"+deal.image+"' class='card-img-top' alt='...''><div class='card-body'><h5 class='card-title'>"+deal.name+"("+deal.status+")"+"  L:"+deal.likes+" D:"+deal.dislikes+"</h5><h6>"+deal.price+"</h6><p class='card-text'>"+deal.description+"</p>"+editButtonHTML+deleteButtonHTML+"</div></div>"
}


function setSellerReviewDeals(user)
{
	$('#current_showing').text("Currently showing: Reviews")
	let deals = user.delivered
	
	$('#deal_list').empty()
	
	for(let i = 0 ; i < deals.length ; i++)
	{
		let reviews = deals[i].reviews
		
		
		if(reviews.length == 0)
		{
			$('#no_deals_message').show()
		}
		else
		{
			$('#no_deals_message').hide()
		}
		
		for(let j = 0 ; j < reviews.length ; j++)
		{
			if(reviews[j].archived) continue
			
			$('#deal_list').append(getSellerReviewHTML(reviews[j],j,false))		
		}
	}
	
	
	let myReviews = user.reviews
	
	for(let i = 0 ; i < myReviews.length ; i++)
	{
		if(myReviews[i].archived) continue
		
		$('#deal_list').append(getSellerReviewHTML(myReviews[i],i,true))	
	}

}


function getSellerReviewHTML(review,i,forSeller)
{
	
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
			
	if(forSeller)
	{
		return "<div class='card mb-3' style='max-width: 540px;'><div class='row no-gutters'><div class='col-md-4'></div><div class='col-md-8'><div class='card-body'><h5 class='card-title'>"+review.header+"</h5><p class='card-text'>"+review.content+"</p><p class='card-text'>Deal was honored: "+isDealHonored+"</p><p class='card-text'>Description was valid: "+DescValid+"</p><p class='card-text'><small class='text-muted'>By: "+review.reviewer+"   For You </small></p></div></div></div></div>"
	}
	else
	{
		return "<div class='card mb-3' style='max-width: 540px;'><div class='row no-gutters'><div class='col-md-4'><img src='data/images/"+review.imageSrc+"' class='card-img' alt='data/images/logo.png'></div><div class='col-md-8'><div class='card-body'><h5 class='card-title'>"+review.header+"</h5><p class='card-text'>"+review.content+"</p><p class='card-text'>Deal was honored: "+isDealHonored+"</p><p class='card-text'>Description was valid: "+DescValid+"</p><p class='card-text'><small class='text-muted'>By: "+review.reviewer+"   For Deal: "+review.deal+"</small></p></div></div></div></div>"
	}
	
}


function setSellerRatings(user)
{	
	let likes = user.likes
	let dislikes = user.dislikes
	
	$('#ratings').text("Likes: " + likes + " Dislikes: " + dislikes)
}

function setMessageModal(user)
{
	let list = $('#send_message_select')
	
	
	$.ajax({
		type: 'GET',
		url: 'rest/account/getAll',
		complete: function(data)
		{
			let users = data.responseJSON
			
			if(user.role == "Admin")
			{
				for(let i = 0 ; i < users.length ; i++)
				{
					if(users[i].email == user.email) continue
					
					list.append($('<option>',{
						value: users[i].email,
						text: users[i].email
					}))
				}
			}
			else if(user.role == "Buyer")
			{
				for(let i = 0 ; i < users.length ; i++)
				{
					if(users[i].email == user.email || users[i].role != "Seller") continue
					
					list.append($('<option>',{
						value: users[i].email,
						text: users[i].email
					}))
				}
			}
			else if(user.role == "Seller")
			{
				for(let i = 0 ; i < users.length ; i++)
				{
					if(users[i].email == user.email || users[i].role != "Admin") continue
					
					list.append($('<option>',{
						value: users[i].email,
						text: users[i].email
					}))
				}
			}
		}
	})
	
}


function setDropdownOptions(user)
{
	let dd = $('#dropdown_options')
	
	if(user.role == "Admin")
	{
		dd.append("<h6 class='dropdown-header'>Account</h6>")
		dd.append("<a class='dropdown-item' href='users.jsp'>Users</a>")
		dd.append("<a class='dropdown-item' href='categories.jsp'>Categories</a>")
	}
	else if(user.role == "Buyer")
	{
		dd.append("<h6 class='dropdown-header'>Account</h6>")
		dd.append("<a class='dropdown-item' href='buyer.jsp'>MyDeals</a>")
		dd.append("<a class='dropdown-item' href='users.jsp'>Users</a>")
	}
	else if(user.role == "Seller")
	{
		dd.append("<h6 class='dropdown-header'>Sell</h6>")
		dd.append("<a class='dropdown-item' href='deal.jsp'>New Deal</a>")
		dd.append("<div class='dropdown-divider'></div>")
		dd.append("<h6 class='dropdown-header'>Account</h6>")
		dd.append("<a class='dropdown-item' href='seller.jsp'>My Deals</a>")
		dd.append("<a class='dropdown-item' href='users.jsp'>Users</a>")
	}
}

function setShopButtonsToLoggedOffUser()
{
	for(let i = 1 ; i <= 9 ; i++)
	{
		$('#card-edit'+i).hide()
		$('#card-order'+i).hide()
		$('#card-delete'+i).hide()
		$('#card-favorite'+i).hide()
	}		
}

function setShopButtonsToLoggedOnUser(user)
{
	for(let i = 1 ; i <= 9 ; i++)
	{
		
		let editBtn = $('#card-edit'+ i)
		let orderBtn = $('#card-order'+i)
		let deleteBtn = $('#card-delete'+i)
		let favoriteBtn = $('#card-favorite'+i)
		
		if(user.role == "Admin")
		{
			editBtn.show()
			deleteBtn.show()			
			orderBtn.hide()
			favoriteBtn.hide()
		}
		
		if(user.role == "Buyer")
		{
			
			favoriteBtn.show()				
			orderBtn.show()
			deleteBtn.hide()
			editBtn.hide()
		}
		
		if(user.role == "Seller")
		{
			editBtn.hide()
			deleteBtn.hide()
			orderBtn.hide()
			favoriteBtn.hide()
		}
		
	}
}


$(document).ready(function()
{
	sessionCheck()
	
})

function getUser()
{
	return currUser
}
