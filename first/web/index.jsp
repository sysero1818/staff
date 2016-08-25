<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본</title>
<%@include file="../common/jscss.jsp" %>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<script type="text/javascript" src="js/index.js"></script>
<style type="text/css">
#rightcolumn{
	height: 752px;
}
</style>
</head>
<body>

<div id="wrapper">
	<%@include file="../common/header.jsp" %>
	
  	<div id="navigation"> 홈  </div>
  	
  	<div id="main">

    <div id="leftcolumn">
    <nav>
		<%@include file="../common/menu.jsp" %>
    </nav>
	</div>

    <div id="rightcolumn">
	
 	
    </div>
							
    <div class="clear"> </div>
  </div>

  <%@include file="../common/footer.jsp" %>

 </div>
</body>
</html>