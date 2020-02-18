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
<script src="javascript/singing.js"></script>
<script src="javascript/navbar.js"></script>
<script src="javascript/account.js"></script>
<script src="javascript/jquery-3.3.1.min.js"></script>
<meta charset="ISO-8859-1">
<link href="css/signin.css" rel="stylesheet">
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

<main role="main" class="container">

<!---------------------STARTPAGEEND--------------------------------------------->
  
<!---------------------LOGIN--------------------------------------------->
  <form class="form-signin" id="login_page">
  <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
  <label for="inputEmail" class="sr-only">Email address</label>
  <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required="" autofocus="">
  <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
  <label for="inputPassword"  class="sr-only">Password</label>
  <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required="">
    <span id="login_page_error">
    
    </span>
  <button class="btn btn-lg btn-primary btn-block" id="signin_btn">Sign in</button>
  <p class="mt-5 mb-3 text-muted">Shopify 2019-2019</p>
</form>
</main>
</body>
</html>