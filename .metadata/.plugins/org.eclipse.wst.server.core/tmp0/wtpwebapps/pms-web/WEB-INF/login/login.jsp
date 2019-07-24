<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META Http-Equiv="Cache-Control" Content="no-cache"/>
<META Http-Equiv="Pragma" Content="no-cache"/>
<META Http-Equiv="Expires" Content="0"/>
<title>Login Page</title>
 <link rel="icon" href="<c:url value='/common/favicon.png'/>" type="image/png"/>
 <link rel="stylesheet" href="<c:url value='/lib/bootstrap-3.3.7/css/bootstrap.min.css'/>" />
 <link href="https://fonts.googleapis.com/css?family=Nunito+Sans" rel="stylesheet">
 <link rel="stylesheet" href="<c:url value='/lib/font-awesome/css/font-awesome.min.css'/>" />
 <script type="text/javascript" src="<c:url value='/lib/jquery/jquery-3.1.1.js'/>"></script>
 <script type="text/javascript" src="<c:url value='/lib/bootstrap-3.3.7/js/bootstrap.min.js'/>"></script>
</head>
<body style="font-family:Segoe UI !important;" onload="validateBack()">

<style>
input[type=text],input[type=password] {
    background: transparent;
    border: none;
    border-bottom: 1px solid #24252A;
    transition-duration:0.4s;
    width:90%;
}
/* for input box blue border*/
input {
 outline:none;
 background-color:#F9F9F9;
}
/* for yellow shadow of inpt feilds*/
@-webkit-keyframes autofill {
    to {
        color: #666;
        background: transparent;
    }
}

input:-webkit-autofill {
    -webkit-animation-name: autofill;
    -webkit-animation-fill-mode: both;
}

.z-ind{
	position: absolute;
   	left: 32%;
   	top: 22%;
   	z-index: 0;
}

html, body {
 	height: 100%;
 	margin: 0;
}

.signinbg{
    background-image: url("images/BG.png");
    height: 100%; 
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
}

#submitLogin{
background-color: white; /* Green */
  color: #3A97D3;
  padding: 9px 40px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 12px;
  font-weight:bold;
  font-family: Lato;
  margin: 8px 38px;
  transition-duration: 0.6s;
  cursor: pointer;
   border: 1px solid #3A97D3;
}

#submitLogin:hover{
 background-color:#3A97D3;
 color: white;
}



</style> 
<div class="signinbg">
<div><span><br><br></span></div>
<div class="col-sm-12" align="center">
<img src="images/logoi-white.png" style="height: 52px;width: 194px;">
</div>
<div><span><br><br><br<br><br></span></div>
<div><span><br></span></div>
<div class="col-sm-12" align="center">
<p style="height: 39px;width: 470px;color: #FFFFFF;font-family: Lato;font-size: 32px;font-weight: 300;line-height: 39px;">Performance Management System</p>
</div>
<div><span><br><br></span></div>
</div>
<div class="container" style="margin-top:5%;margin-left:30%;">
	   <form id="sign-in-form" method="post" action="<c:url value='/j_spring_security_check' />" style="text-align:center;">
			<div class="col-sm-5 z-ind" style="width:22%;height:370px;background:#F9F9F9;box-shadow: 0 0px 0px #555;width:22%;height:370px; left:39%; top:35%">
				<div class="col-sm-12"><span><br><br><br><br>
				</span></div>
				<div class="pull-left" style="margin-left:11%"><img src="images/user_profile.png"></div>
				<div class="col-sm-12" id="errorDiv" style="margin-top:7%;font-size:12px;color:red;">
			        <c:if test="${param.authFailed=='true'}">
						Please enter a valid username and password.
			        </c:if>
				</div>
				<div class="col-sm-12"><p style="height: 12px;width: 77px;opacity: 0.4;color: #1D1D26;	font-family: Lato;font-size: 10px;line-height: 12px;">USERNAME</p></div>
				<div class="col-sm-12" style="margin-top:1%;">
					<input name="username" id="username" class="psm-username" type="text" required style="border-bottom: 1px solid gray;">
				</div>
				<div class="col-sm-12"><span><br></span></div>
				<div class="col-sm-12"><p style="height: 12px;width: 77px;opacity: 0.4;color: #1D1D26;	font-family: Lato;font-size: 10px;line-height: 12px;">PASSWORD</p></div>
				<div id="passwordContainer" class="col-sm-12" style="margin-top:1%;">
					<input name="password" id="password" class="psm-password" type="password" required style="border-bottom: 1px solid gray;>
				</div>
				<div class="col-sm-12"><span><br></span></div>
				<div class="col-sm-12" style="margin:5% 0 3% 0;margin-left:5%;text-align:left;">
					<button type="submit" id="submitLogin" onclick="">SIGN IN</button>
				</div>
			</div>
		</form>
	</div>
<script>

var sendLoginRequestURL = '<c:url value="/login/logincontroller.do"/>';
var redirectToSuccessURL = '<c:url value="/login/loginsuccessful.do"/>';
var loginUrl = "<c:url value='/j_spring_security_check' />";

$(document).ready(function(){
	$("#passwordContainer").keypress(function(e) {
	    if(e.which == 13) {
	     	
	    }
	});
});	

function performLogin(username,password){
   	$.post(sendLoginRequestURL,{username:username,password:password}, function (data) {
	if(data=="success")
	{
		window.location = redirectToSuccessURL;
		if (typeof(sessionStorage) !== "undefined"){
			sessionStorage.setItem('id',username);
		} 
	}
	else{
		$('#errorDiv').html("Username or password is incorrect.");
	}
},'json');
}

function validateBack(){
	var user=sessionStorage.getItem('id');
	if(user==null|| user==""){
		
	}else{
		window.location = redirectToSuccessURL;
	} 
}

function isValidate(){
	var allValidate = false;
	var username=$("#username").val();
	var password=$("#password").val();
	if(username != undefined && username != null && username != ""){
		allValidate = true;
		if(password != undefined && password != null && password != ""){
			allValidate = true;
			performLogin(username,password);
		}else{
			$('#errorDiv').html("Password can not be empty.");
		}
	}else{
		$('#errorDiv').html("Username can not be empty.");
	}
}


</script>
</body>
</html>