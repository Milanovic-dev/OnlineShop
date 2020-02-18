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
<script src="javascript/shop.js"></script>
<script src="javascript/navbar.js"></script>
<script src="javascript/account.js"></script>
<script src="javascript/jquery-3.3.1.min.js"></script>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href = "css/album.css">
<title>Insert title here</title>
</head>
<body>

<header>
<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
  <a class="navbar-brand" href="index.jsp">Shopify</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item ">
        <a class="nav-link" href="shop.jsp">Shop<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">About Us</a>
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

<!-----------MODAL FILTERING --------------->

<div class="modal fade" id="filter_modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Filtered Search</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <div class="modal-body">
      
      <div class="form-group">
  			<select class="form-control" id="filter_category">
  				<option value=""> -- Select Category -- </option>				
  			</select>
		</div>
		
		<div class="input-group mb-3"> 			
 			 <input type="text" class="form-control" placeholder="Name" aria-label="Name" aria-describedby="basic-addon1" id="filter_name">
		</div> 	
		
		<div class="input-group mb-3">
  			<input type="text" class="form-control" placeholder="Starting price" aria-label="Starting price" id="filter_startprice">
  			<div class="input-group-append">
    			<span class="input-group-text">$</span>
  			</div>
		</div>
		
		<div class="input-group mb-3">
  			<input type="text" class="form-control" placeholder="End price" aria-label="End price" id="filter_endprice">
  			<div class="input-group-append">
    			<span class="input-group-text">$</span>
  			</div>
		</div>
		
		
		<div class="input-group mb-3">
  			<input type="text" class="form-control" placeholder="Starting likes" aria-label="Starting likes" id="filter_startlikes">
		</div>
		
		<div class="input-group mb-3">
  			<input type="text" class="form-control" placeholder="End likes" aria-label="End likes" id="filter_endlikes">
		</div>
		
		<div class="input-group mb-3">
  			<input type="text" class="form-control" placeholder="Start date" aria-label="Start date" id="filter_startdate">
		</div>
		
		<div class="input-group mb-3">
  			<input type="text" class="form-control" placeholder="End date" aria-label="End date" id="filter_enddate">
		</div>
				
		<div class="form-group">
  			<select class="form-control" id="filter_status">
  				<option value=""> -- Select Status -- </option>
  				<option>Active</option>
  				<option>In Process</option>
  				<option>Delivered</option>
  			</select>
		</div>
		 <div class="form-group">
    		<select class="form-control" id="filter_city" >
    			<option value=""> -- Select City -- </option>
    		</select>
  		</div>
	
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="filter_search">Apply</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-----------MODAL FILTERING END --------------->


<main role="main">

  <section class="jumbotron text-center">
    <div class="container">
      <h1 class="jumbotron-heading">Shop</h1>
      <p class="lead text-muted">Welcome to the Shop! Browse our deals freely.</p>
      <p>
        <a href="#" class="btn btn-primary my-2" data-toggle="modal" data-target="#filter_modal">Search</a>
        <!--  <a href="#" class="btn btn-secondary my-2">Secondary action</a> -->
      </p>
    </div>
  </section>

  <div class="album py-5 bg-light">
    <div class="container">

      <div class="row">
        <div class="col-md-4" id="card1">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image1" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>
            <div class="card-body">
              <h5 class="card-text" id="card-head1">Naslov</h5>
              <p class="card-text" id="card-text1">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order1">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit1">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete1">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite1">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date1">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4" id="card2">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image2" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head2">Naslov</h5>
              <p class="card-text" id="card-text2">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order2">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit2">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete2">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite2">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date2">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4" id="card3">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image3" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head3">Naslov</h5>
              <p class="card-text" id="card-text3">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order3">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit3">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete3">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite3">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date3">9 mins</small>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4" id="card4">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image4" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head4">Naslov</h5>
              <p class="card-text" id="card-text4">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order4">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit4">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete4">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite4">Add to Favorites</button>                
                </div>
                <small class="text-muted" id="card-date4">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4" id="card5">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image5" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head5">Naslov</h5>
              <p class="card-text" id="card-text5">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order5">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit5">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete5">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite5">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date5">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4" id="card6">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image6" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head6">Naslov</h5>
              <p class="card-text" id="card-text6">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order6">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit6">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete6">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite6">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date6">9 mins</small>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-4" id="card7">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image7" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head7">Naslov</h5>
              <p class="card-text" id="card-text7">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order7">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit7">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete7">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite7">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date7">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4" id="card8">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image8" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head8">Naslov</h5>
              <p class="card-text" id="card-text8">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order8">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit8">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete8">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite8">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date8">9 mins</small>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-4" id="card9">
          <div class="card mb-4 shadow-sm">
            <img class="bd-placeholder-img card-img-top" id="card-image9" src="data/images/logo.png" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail"><rect width="100%" height="100%" fill="#55595c"></rect></img>            <div class="card-body">
            <h5 class="card-text" id="card-head9">Naslov</h5>
              <p class="card-text" id="card-text9">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-order9">Order</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-edit9">Edit</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-delete9">Delete</button>
                  <button type="button" class="btn btn-sm btn-outline-secondary" id="card-favorite9">Add to Favorites</button>
                </div>
                <small class="text-muted" id="card-date9">9 mins</small>
              </div>
            </div>
          </div>
        </div>
      </div>
      <button class="btn btn-outline-dark" type="submit" id="go_left_page">Back</button>
      <label id="curr_page_number"></label>
      <label id="max_page_number"></label>
      <button class="btn btn-outline-dark" type="submit" id="go_right_page">Next</button>
    </div>
  </div>
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