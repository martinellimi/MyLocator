<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" type="text/css" href="styles/styles.css">
<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="scripts/scripts.js"></script>
<title>My Locator</title>
<script type="text/javascript"> 
$( document ).ready(function() {
	$( "#login" ).click(function() {
		facebookLogin();
	});

	$( "#webpage" ).click(function() {
		document.location.href = "simpleMainPage.jsp";
	});

	$(document).on("click", "#buttonChangePassword", function(){
		event.preventDefault();// using this page stop being refreshing 
		$.ajax({
		    type: 'post',
		    url: '/Login',
		    data: {'name':name, 'id': id},
		    success:function(data){alert(data);},
		    error:function(){alert('error');}
		});
	});
	
});

window.fbAsyncInit = function() {
  FB.init({
    appId      : '606454276155875',
    xfbml      : true,
    version    : 'v2.2'
  });
};

(function(d, s, id){
   var js, fjs = d.getElementsByTagName(s)[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement(s); js.id = id;
   js.src = "https://connect.facebook.net/en_US/sdk.js";
   fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
</head>
<body>
<div data-role="page" id="login-page" data-url="login-page" style="overflow: hidden;">
    <div role="main" class="ui-content" id="main" style="background-color: rgb(207, 216, 229);">
	    <div style="text-align: center; padding-bottom: 20%; padding-top: 8%;">
	    	<h1 style="color: #3B5998; display: inline; margin-left: -40%; font-size: 260%;">My Locator</h1>
	    	<img src="images/marker-blue.png" class="marker" style="width: 5%; display: inline; float: left; margin-left: 35%;margin-top: -2%;">    
	    	<!-- <img src="images/Facebook-logo.jpg" style="width: 50%;"/> -->
	    </div>
	    <div>
	    	<button id="login" type='submit' data-role="button">Login</button>
	    	<button id="webpage" type='submit' data-role="button">Sign In without Facebook</button>
	    	<!--  <a href="javascript:facebookLogin();" data-inline="true" data-role="button" style="width: 8%;"><img alt="" src="images/facebook-logo-icon.png">Login</a>-->
	    </div>
    </div>
</div>
</body>
</html>