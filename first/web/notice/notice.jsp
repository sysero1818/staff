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
	// 그리드와 페이징세팅
	ld(1);

	// 로우 클릭할때 이벤트
	$(document).on("click", "#mytable tbody tr", function(){
		var row = $(this);
		var seq = row.children("td:eq(0)").text();
		
		$.ajax({
			type: "post",
			url: "neviGo?cmd=noticeView",
			data: {seq: seq},
			dataType: "json",
			success : function(data) {
				$("#seq").val(data.seq);
				$("#title").val(data.title);
				$("#content").val(data.content);
				$("#btnSubmit").val("수정");
				$("#inup").val("up");
			},
		    error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
		});	
	}); 
	
	//초기화버튼
	$("#btnRefresh").on("click", function(){
		$("#regForm").clearForm();
		$("#btnSubmit").val("등록");
		$("#inup").val("in");
	});

	//글 등록/수정
	$("#regForm").validate({
		rules: {
		  title: {required: true},
		  content: {required: true}
		},
		messages: {
		  title: {required: "필수"},
		  content: {required: "필수"}
		}
	 });
	

	// submit
	var options = {
		url : 'neviGo?cmd=noticeInUp',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:""});
			return $("#regForm").valid();
		},
	
		success : function(responseText,statusText,xhr){
			var seq = $("#seq").val();
			var title = $("#title").val();
			$("#regForm").clearForm();
			$("#inup").val("in");
			$("#btnSubmit").val("등록");
			$.unblockUI();
			if (responseText.result=="0"){
				alertMsg("공지 사항", "등록/수정 실패");
			} else if (responseText.result=="1"){
				alertMsg("공지 사항", "등록 완료");
				ld(1);
			} else if (responseText.result=="2"){
				alertMsg("공지 사항", "수정 완료");
				$("#noti_"+seq+" td:eq(1)").text(title);
			}
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#regForm").ajaxForm(options);
	
	$(".btnwht").css("width","80");
});

function ld(page){
	$.ajax({
		type: "post",
		url: "neviGo?cmd=noticeList",
		data: {page: page},
		dataType: "json",
		success : function(data) {
			$("#mytable tbody *").remove();
			$("#main_paging *").remove();
			$.each(data.rows, function(i,v){
				$("#mytable tbody").append("<tr id='noti_"+v.seq+"'><td>"+v.seq+"</td><td>"+v.title+"</td><td>"+v.regdtt+"</td></tr>");
			});
			pageing(page, 3, data.total);
		},
	    error:function(request,status,error){
	        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
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
		var last = "<img src='../images/last.gif' width='20' height='20' onclick='ld("+ totalPage +")'/> ";			
	} else {
		var goPrev = prev - 1;
		var first = "<img src='../images/first.gif'  width='20' height='20' onclick='ld(1)'/> ";
	}
	
	$("#main_paging").append(first);	
	
	if(next > totalPage){
		var goNext = totalPage;
		next = totalPage;
	} else {
		var goNext = next + 1;
		var last = "<img src='../images/last.gif'  width='20' height='20' onclick='ld("+ totalPage +")'/> ";	
	}
	
	var prevStep = "<img src='../images/prev.gif'  width='20' height='20' onclick='ld("+ goPrev +")'/> ";
	var nextStep = "<img src='../images/next.gif'  width='20' height='20' onclick='ld("+ goNext +")'/> ";
	
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
	height: 170px;
	border: 1px solid #C1DAD7;
	border-radius: 5px;	
}

#main_paging{
	margin-top: 5px;
	text-align: center;
	line-height: 20px;
	color: #4f6b72;	
	font-size: 1em;
}

#main_paging img{
	vertical-align: middle;
}

#content_form{
	width: 100%;
	float: left;
	margin: 15px auto;
	padding : 0;
	border: 1px solid #C1DAD7;
	border-radius: 5px;
}

#regForm{
	width: 98%;
	margin-left: 5px;
	padding: 5px;
}

form.cmxform  p {
	font-size: 0.98em; 
	margin: 2px;
	padding: 0px;
}

form.cmxform label {
	display: inline-block;
	width: 100px;
	line-height: 2.1;
	vertical-align: middle;
	text-align: right;
	margin-right: 10px;
	color: #4f6b72;
}

#regForm > p:nth-child(4) > label{
	vertical-align: top;
}

table > tbody > tr:nth-child(even) > td{
	background: #f8f8f8;
}

table > tbody > tr:hover > td {
	background: #f2f2f2; 
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
						  <th scope="col" abbr="글번호">글번호</th>
						  <th scope="col" abbr="제목">제목</th>
						  <th scope="col" abbr="등록일">등록일</th>				  				  
						</tr>
					</thead>
					<tbody>
					</tbody>													
				</table>			
			</div>
			<div id="main_paging">

			</div>
			
	    	<div id="content_form"> 
	 			<form class="cmxform" id="regForm" name="regForm" method="post">
					<input type="hidden" name="inup" id="inup" value="in" />
					<p>
						<label for="seq">글 번호</label>
						<input type="text" id="seq" name="seq" readonly value="" />
					</p>	
					<p>
						<label for="title">제목</label>
						<input type="text" id="title" name="title" class="required" tabindex="1" />
					</p>		
					<p>
						<label for="content">글 내용</label>
						<textarea id="content" name="content" cols="60" rows="10"  tabindex="2"></textarea>
					</p>	
					<p class="btnRow">
						<input type="submit" id="btnSubmit" class="btnwht" value="등록" tabindex="3" /> <input type="button" id="btnRefresh" class="btnwht" value="초기화" />
					</p>					
				</form> 
	     	</div>					
		</div>
    </div>
							
    <div class="clear"> </div>

  <%@include file="../common/footer.jsp" %>
</div>
</body>
</html>