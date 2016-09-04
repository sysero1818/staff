<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본</title>
<%@include file="../common/jscss.jsp" %>
<link rel="stylesheet" type="text/css" href="css/prj.css" />
<link rel="stylesheet" type="text/css" href="css/tables2.css" />

<script type="text/javascript">
$(document).ready(function(){
	ld(1);

});

function ld(page){
	$.ajax({
		type: "post",
		url: "neviGo?cmd=boardList",
		data: {page: page},
		dataType: "json",
		success : function(data) {
			$("#mytable tbody *").remove();
			$("#main_paging *").remove();
			$.each(data.rows, function(i,v){
				$("#mytable tbody").append("<tr><td>"+v.num+"</td><td>"+v.name+"</td><td>"+v.email+"</td><td>"+v.pass+"</td><td>"+v.title+"</td><td>"+v.writedate+"</td></tr>");
			});
			pageing(page, 10, data.total);
		}
	});
}

function pageing(page, block, totalPage){
	var pageGroup = Math.ceil(page/block);
	var next = pageGroup*block;
	var prev = next - (block - 1);
	var str = "";
	$("#main_paging").empty();
	
	var goNext = next+1;
	if(prev-1 <= 0){
		var goPrev = 1;
		var last = "<img src='../images/last.gif' onclick='ld("+ totalPage +")'/>";			
	} else {
		var goPrev = prev - 1;
		var first = "<img src='../images/first.gif' onclick='ld(1)'/>";
	}
	
	$("#main_paging").append(first);	
	
	if(next > totalPage){
		var goNext = totalPage;
		next = totalPage;
	} else {
		var goNext = next + 1;
		var last = "<img src='../images/last.gif' onclick='ld("+ totalPage +")'/>";	
	}
	
	var prevStep = "<img src='../images/prev.gif' onclick='ld("+ goPrev +")'/>";
	var nextStep = "<img src='../images/next.gif' onclick='ld("+ goNext +")'/>";
	
	$("#main_paging").append(prevStep);
	
	for(var i=prev; i<=next; i++){
		if (i == page){
			str = "<strong><span onclick='ld("+i+")'>["+i+"]</span></strong> ";
		} else {
			str = "<span onclick='ld("+i+")'>["+i+"]</span> ";
		}
		$("#main_paging").append(str);
	}
	
	$("#main_paging").append(nextStep);
	$("#main_paging").append(last);	
}

</script>
<style>
#member_table{
	margin-top: 5px;
	width: 100%;
	height: 500px;
	border: 1px solid #C1DAD7;	
}

#main_paging{
	margin-top: 5px;
	text-align: center;
	line-height: 20px;
	color: #4f6b72;	
}
#main_paging img{
	vertical-align: middle;
}
</style>
</head>
<body>
<div id="alert_msg" title="">
  <p></p>
</div>
<div id="wrapper">
	<%@ include file="../common/header.jsp" %>  
  	<div id="navigation"> 홈 > 공지사항 </div>
  	
  	<div id="main">
	    <div id="leftcolumn">
		    <nav>
				<%@ include file="../common/menu.jsp" %>
		    </nav>
		</div>
	    <div id="rightcolumn">
	    	<div id="member_table">
				<table id="mytable">
					<thead>
						<tr>
						  <th scope="col" abbr="사번">num</th>
						  <th scope="col" abbr="이름">이름</th>
						  <th scope="col" abbr="부서">이메일</th>
						  <th scope="col" abbr="직급">pass</th>
						  <th scope="col" abbr="상급자">제목</th>
						  <th scope="col" abbr="입사일">등록일</th>				  				  
						</tr>
					</thead>
					<tbody>
					</tbody>													
				</table>			
			</div>
			<div id="main_paging">
		       <!-- <img src="../images/first.gif"/>
		       <img src="../images/prev.gif"/>
		       <span id="main_num">&nbsp;1&nbsp;|&nbsp;2&nbsp;|&nbsp;3&nbsp;|&nbsp;4&nbsp;|&nbsp;5&nbsp;</span>
		       <img src="../images/next.gif"/>
		       <img src="../images/last.gif"/> -->
			</div>
		</div>
    </div>
							
    <div class="clear"> </div>

  <%@include file="../common/footer.jsp" %>
</div>
</body>
</html>