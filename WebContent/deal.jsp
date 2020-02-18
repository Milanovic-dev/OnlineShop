<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href=”bootstrap/css/bootstrap.min.css” rel=”stylesheet” type=”text/css” />
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="javascript/messages.js"></script>
<script src="javascript/navbar.js"></script>
<script src="javascript/account.js"></script>
<script src="javascript/deals.js"></script>
<script src="javascript/jquery-3.3.1.min.js"></script>
<link rel="stylesheet" href = "css/starter-template.css">
<meta charset="ISO-8859-1">
<title>Add new Deal</title>
</head>
<body>
<!----------------------------MAIN NAVBAR--------------------------->
<header>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <a class="navbar-brand" href="index.jsp">Shopify</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
       <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Categories
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown" id="categories_select">
        </div>
      </li> 
    </ul>
    <form class="form-inline my-2 my-lg-0" id="log_buttons" hidden="true">
      <button class="btn btn-outline-light" type="submit" id="sign_in_button">Sign In</button>
      <button class="btn btn-outline-light" type="submit" id="sign_up_button">Sign Up</button>
    </form>
    <form class="form-inline my-2 my-lg-0" id="account_buttons" hidden="true">
    <ul class="navbar-nav mr-auto">
    <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="acc_settings_button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Account</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01" id="dropdown_options">
          <h6 class="dropdown-header">Messages</h6>
          	<a class="dropdown-item" data-toggle="modal" data-target="#message_modal_send">Send Message</a>
          	<a class="dropdown-item" href="messages.jsp">Inbox
          		<span class="badge badge-primary" id="account_badge">9</span>
      			<span class="sr-only">unread messages</span>
          	</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      	<button class="btn btn-outline-light" type="submit" id="logout_button">Log out</button>
      	</ul>
    </form>
  </div>
</nav>
</header>
<!---------------------------MAIN NAVBAR END----------------------------->

<main role="main" class="container">
<h3 id="deal_page_header">Publish a new deal</h3>
<form>
  <div class="form-group">
    <label for="deal_title">Title</label>
    <input type="text" class="form-control" id="deal_title" placeholder="Title">
  </div>
<div class="form-row">
    <div class="form-group col-md-6">
      <label for="deal_price">Price</label>
      <input type="text" class="form-control" id="deal_price" placeholder="Price">
    </div>
    <div class="form-group col-md-6">
      <label for="deal_city">City</label>
      <input type="text" class="form-control" id="deal_city"  placeholder="City">
    </div>
  </div>
  <div class="form-group">
  <label for="deal_desc">Description</label>
  <textarea class="form-control" id="deal_desc" rows="8"></textarea>
</div>
	<div class="form-group">
  			<select class="form-control" id="deal_category">
  				<option value=""> -- Select Category -- </option>				
  			</select>
		</div>
  <div class="form-group">
    <label for="deal_image" name="files[]">Image</label>
    <input type="file" id="deal_image">
  </div>
  <span id="deal_error">
  
  </span>
  <button class="btn btn-primary" id="deal_publish_btn" type="button">Publish</button>
</form>
</main>


<!-- Message Modal -->
<div class="modal fade" id="message_modal_send" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Send Message</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      	<div class="form-group">
  			<select class="form-control" id="send_message_select">
  				<option value=""> -- Send to -- </option>				
  			</select>
		</div>
		<div class="form-group"> 			
 			 <input type="text" class="form-control" placeholder="Title" aria-label="Name" aria-describedby="basic-addon1" id="send_message_title">
		</div> 		
		<div class="form-group"> 			
 			 <textarea class="form-control" id="send_message_content" placeholder="Content" rows="6"></textarea>
		</div> 	  
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal"  id="message_button_send">Send</button>
      </div>
    </div>
  </div>
</div>
<!-- Message Modal End-->

</body>
</html>