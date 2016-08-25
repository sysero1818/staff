<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>사원 정보 관리 시스템</title>
<%@include file="../common/jscss.jsp" %>
<script type="text/javascript">
/* $(document).ready(function(){
	$("#manager").
}
 */

function enter_check() {
   if (event.keyCode == 13){
		sendit();
	}
}

function sendit() {

	if (document.getElementById("empno").value ==""){
		alert("아이디를 입력하세요");
		document.getElementById("empno").focus();
		return false;
	}
	
	if (document.getElementById("passwd").value ==""){
		alert("암호를 입력하세요");
		document.getElementById("passwd").focus();
		return false;
	}

	return true;
}
  
function MemberChk(){
   var IDChk = document.getElementById("empno").value
	if (IDChk.search(/[^0-9|a-z|A-Z]/) != -1 ) {
		alert("허용되지않는 문자열입니다! 숫자,영문만 허용");
		document.getElementById("empno").value ="";
		document.getElementById("empno").focus();
		
		return false; 
	}
}

window.onload = function(){
	document.getElementById("empno").focus();
}
</script>

<style type="text/css">
body { 
	background: #ffffff;
}

#login { 
	position:absolute; 
	top:50%; 
	left:50%; 
	width:470px; 
	height:400px; 
	overflow:hidden;  
	margin: -200px 0 0 -250px;
}
#LoginForm {
	border: 10px solid #00CCFF;
	text-align: left;
	padding: 20px;
	font-weight: bold;
	font-size: 1.2em;
	line-height: 250%;
	border-radius: 15px;
	background: #ffffff;
}

#LoginForm label {
	display: inline-block;
	width: 120px;
	vertical-align: middle;
	text-align: right;
	margin-right: 10px;
	color: #4f6b72;	
}

#LoginForm input{
	height: 1.5em;
}

#LoginForm p.btnRow {
	display:	block;
	text-align: center;
	margin-top: 5px;
	position:relative;
}

h1{
	color: #675d90;
}
</style>

</head>
<body>
<div id="login">
	<h1>사원 정보 관리 시스템</h1>
	<form id="LoginForm" name="LoginForm" action="neviGo" method="post" style="margin:0px" autocomplete="off" >
		<input type="hidden" id="cmd" name="cmd" value="loginOk" />
		<p>
			<label for="empno">사번</label>
			<input type="text" id="empno" name="empno" maxlength="10" />
		</p>
		<p>
			<label for="passwd">비밀번호</label>
			<input type="password" id="passwd" name="passwd" maxlength="12" />
		</p>
		<p>
			<label for="manager">구분</label>
			<input type="radio" id="manager" name="manager" value="0" />일반 사원
			<input type="radio" id="manager" name="manager" value="1" />관리자
		</p>
		<p>
			<label for="mpasswd">관리자비밀번호</label>
			<input type="password" id="mpasswd" name="mpasswd" maxlength="12" />
		</p>		
		<p class="btnRow">
			<input type="submit" id="btnSubmit" value="로그인" onclick="return sendit()" />
		</p>		
	</form>
</div>
</body>
</html>