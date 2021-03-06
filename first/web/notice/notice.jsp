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
		var tbody = $("#mytable tbody").find("tr");
		var seq = row.children("td:eq(0)").text();
		tbody.find("td").css("background","white");
		row.children().css("background","#FBEC88");
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
				$("#Oseq").val(seq);
			},
		    error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
		});
		
		$.ajax({
			type: "post",
			url: "neviGo?cmd=notidatList",
			data: {seq: seq},
			dataType: "json",
			success : function(data) {
				$("#notiDat_table tbody *").remove();
				$.each(data, function(i,v){
					$("#notiDat_table tbody").append("<tr id='"+seq+"_dat_"+v.dseq+"'><td>"
														+v.dseq+"</td><td>"
														+v.dcontent+"</td><td>"
														+v.empnm+"</td><td>"
														+v.dregdtt+"</td><td>"
														+"<input type='button' value='삭제' id='dat_"+seq+"_"+v.dseq+"' onclick=\"noti_del('"+seq+"', '"+v.dseq+"')\" /></td></tr>"							
													);
				});
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
	
	$("#btnDel").on("click", function(){
		var seq = $("#seq").val();
		var curPage = $("#curPage").val();
		var curTotalPage = $("#curTotalPage").val();
		$.ajax({
			type: "post",
			url: "neviGo?cmd=noticeDelete",
			data: {seq:seq},
			dataType: "json",
			success : function(result) {
				if(result == 1){
					alertMsg("공지사항 삭제", "삭제완료");
					ld(curPage);
					$("#regForm").clearForm();
					$("#notiDat_table tbody *").remove();
				} else {
					alertMsg("공지사항 삭제",  "삭제실패");
				}
			},
		    error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
		});
	});

	
	//댓글 등록
	$("#datForm").validate({
		rules: {
		  dcontent: {required: true}
		},
		messages: {
		  dcontent: {required: "필수"}
		}
	 });
	// submit
	var options = {
		url : 'neviGo?cmd=notidatInsert',
		type : 'POST',
		beforeSubmit: function(){
			if ($("#Oseq").val()==""){
				alertMsg("댓글 입력", "글번호가 없습니다.");
				return false;
			}
			$.blockUI({overlayCSS:{opacity:0.0}, message:""});
			return $("#datForm").valid();
		},
	
		success : function(rt,statusText,xhr){
			var seq = $("#Oseq").val();
			$("#datForm").clearForm();
			$.unblockUI();
			if (rt.result=="0"){
				alertMsg("댓글 등록", "등록 실패");
			} else if (rt.result != "0"){
				alertMsg("공지 사항", "등록 완료");
				$("#notiDat_table tbody").append("<tr id='"+seq+"_dat_"+rt.dseq+"'><td>"
													+rt.dseq+"</td><td>"
													+rt.dcontent+"</td><td>"
													+rt.empnm+"</td><td>"
													+rt.dregdtt+"</td><td>"
													+"<input type='button' value='삭제' id='dat_"+seq+"_"+rt.dseq+"' onclick=\"noti_del('"+seq+"', '"+rt.dseq+"')\" /></td></tr>"
												);				
			}
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#datForm").ajaxForm(options);
	
	
	$("document").find("#notiDat_table input[type=button]").button().css({
		"font-size":"1em", 
		"color":"#4f6b72",
		"border-radius":"2px"
	});
	
	$(".btnwht").css("width","80");
	
	//tableToGrid("#mytable");
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
			$("#curPage").val(page);
			$("#curTotalPage").val(data.total);
		},
	    error:function(request,status,error){
	        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        } 
	});
}

function pageing(page, block, totalPage){
	var maxPageGroup = Math.ceil(totalPage/block);
	var pageGroup = Math.ceil(page/block);
	var next = pageGroup*block;
	var prev = next - (block - 1);
	var goPrev;
	var goNext;
	
	var str = "";
	var first = "";
	var last = "";	
	var prevStep = "";
	var nextStep = "";
	
	$("#main_paging").empty();
	
	if (page > 1) {
		first = "<img src='../images/first.gif'  width='20' height='20' onclick='ld(1)'/> ";
	}
	
	goNext = next+1;
	if(prev-1 <= 0){
		goPrev = 1;
		last = "<img src='../images/last.gif' width='20' height='20' onclick='ld("+ totalPage +")'/> ";			
	} else {
		goPrev = prev - 1;
		first = "<img src='../images/first.gif'  width='20' height='20' onclick='ld(1)'/> ";
	}
	
	$("#main_paging").append(first);	
	
	if(next > totalPage){
		goNext = totalPage;
		next = totalPage;
		last = "";		
	} else {
		goNext = next + 1;
		last = "<img src='../images/last.gif'  width='20' height='20' onclick='ld("+ totalPage +")'/> ";	
	}
	
	if(pageGroup == 1) {
		prevStep = " ";
	} else {
		prevStep = "<img src='../images/prev.gif'  width='20' height='20' onclick='ld("+ goPrev +")'/> ";	
	}
	
	$("#main_paging").append(prevStep);
	
	for(var i=prev; i<=next; i++){
		if (i == page){
			str = "<strong><span onclick='ld("+i+")'>["+i+"]</span></strong> ";
		} else {
			str = "<span class='num' onclick='ld("+i+")'>["+i+"]</span> ";
		}
		$("#main_paging").append(str);
	}
	
	if (pageGroup == maxPageGroup){
		nextStep = "";
	} else {
		nextStep = "<img src='../images/next.gif'  width='20' height='20' onclick='ld("+ goNext +")'/> ";
	}
	
	if (page == totalPage){
		last = "";
	}
	
	$("#main_paging").append(nextStep);
	$("#main_paging").append(last);	
}

function noti_del(seq, dseq){
	if (confirm("삭제하시겠습니까?")){
		$.ajax({
			type: "post",
			url: "neviGo?cmd=notidatDelete",
			data: {seq:seq, dseq:dseq},
			dataType: "json",
			success : function(data) {
				if (data==1){
					alertMsg("댓글삭제", "삭제성공");
					$("#"+seq+"_dat_"+dseq).remove();
				} else {
					alertMsg("댓글삭제", "삭제실패");
				}
			},
		    error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
		});
	}
}

function gfnChgRowColor(pTarget, pObj) {
    var viewCnt = 0;
    $.each($(pTarget), function(idx, obj) {
        if("none" != $(this).css("display")) viewCnt++;    //실제 보이는 row count 체크
        
        if($(pObj).index() == idx) {
            if(viewCnt%2 == 0) {
                $(obj).removeClass("table-blue");    //짝수 row 컬러 지정 class
            }
            $(obj).css("background","yellow");    //선택한 row 컬러 지정 class
        } else {
            //삭제한 row 컬러 지정 class 는 남겨두고 다른 row들의 class 변경
            if(!$(obj).hasClass("deleted")) {
                $(obj).removeClass("selected");
                if(viewCnt%2 == 0) {
                    $(obj).addClass("table-blue");
                }
            }
        }
    });
}
</script>
<style>

#table_title, #notiDatIn_title, #notiDat_title{
	width: 100%;
	height: 27px;
	vertical-align: middle;
	line-height: 30px;
	padding-left: 5px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px; 
	border: 0;
}
#member_table{
	margin-top: 5px;
	width: 100%;
	height: 200px;
	border: 1px solid #C1DAD7;
	border-radius: 5px;	
}

#main_paging{
	margin-top: 5px;
	text-align: center;
	line-height: 20px;
	color: #4f6b72;	
	font-size: 1.05em;
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

#regForm_title{
	width: 100%;
	height: 27px;
	vertical-align: middle;
	line-height: 30px;
	padding-left: 5px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px; 
	border: 0;
}

#notiDatIn{
	float: right;
	margin: 10px auto;
	width: 100%;
	height: 150px;
	border: 1px solid #C1DAD7;
	border-radius: 5px;
}

#notiDat{
	float: right;
	margin-top: 5px;
	width: 100%;
	height: 100%;
	border: 1px solid #C1DAD7;
	border-radius: 5px;
}

#notiDat_table{
	margin-top: 5px;
	width: 100%;
	height: 100%;
}

.btnRow{
	text-align: center;
}

#datForm{
	margin-top: 18px;
}

.num:hover {
	cursor: default;
}

#notiDat_table input[type=button] {
  background: #3498db;
  background-image: -webkit-linear-gradient(top, #3498db, #2980b9);
  background-image: -moz-linear-gradient(top, #3498db, #2980b9);
  background-image: -ms-linear-gradient(top, #3498db, #2980b9);
  background-image: -o-linear-gradient(top, #3498db, #2980b9);
  background-image: linear-gradient(to bottom, #3498db, #2980b9);
  -webkit-border-radius: 2;
  -moz-border-radius: 2;
  border-radius: 2px;
  font-family: Arial;
  color: #ffffff;
  font-size: 0.8em;
  padding: 2px;
  width: 40px;
  margin: 0;
  text-align: center;
  text-decoration: none;
}

#notiDat_table input[type=button]:hover {
  background: #3cb0fd;
  background-image: -webkit-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -moz-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -ms-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -o-linear-gradient(top, #3cb0fd, #3498db);
  background-image: linear-gradient(to bottom, #3cb0fd, #3498db);
  text-decoration: none;
}

.trbg{
	background: "yellow";
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
	    		<div id="table_title" class="ui-widget-header">공지 목록</div> 
	    		<table id="mytable">
					<thead>
						<tr>
						  <th scope="col" abbr="글번호" width="80">글번호</th>
						  <th scope="col" abbr="제목">제목</th>
						  <th scope="col" abbr="등록일" width="100">등록일</th>				  				  
						</tr>
					</thead>
					<tbody>
					</tbody>													
				</table>			
			</div>
			<div id="main_paging"></div>
			
	    	<div id="content_form">
	    		<div id="regForm_title" class="ui-widget-header">공지 내용</div> 
	 			<form class="cmxform" id="regForm" name="regForm" method="post">
					<input type="hidden" name="inup" id="inup" value="in" />
					<input type="hidden" name="curPage" id="curPage" value="" />
					<input type="hidden" name="curTotalPage" id="curTotalPage" value="" />			
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
						<input type="submit" id="btnSubmit" class="btnwht" value="등록" tabindex="3" /> 
						<input type="button" id="btnRefresh" class="btnwht" value="초기화" />
						<input type="button" id="btnDel" class="btnwht" value="삭제" />
					</p>					
				</form> 
	     	</div>
	     	<div id="notiDatIn">
	     		<div id="notiDatIn_title" class="ui-widget-header">댓글 입력</div> 
	 			<form class="cmxform" id="datForm" name="datForm" method="post">
					<input type="hidden" name="Oseq" id="Oseq" value=""/>
					<p>
						<label for="dcontent">댓글 내용</label>
						<textarea id="dcontent" name="dcontent" cols="60" rows="3"  tabindex="4"></textarea>
					</p>	
					<p class="btnRow">
						<input type="submit" id="btnDatSubmit" class="btnwht" value="등록" tabindex="5" />
					</p>					
				</form> 	     		
	     	</div>
	     	<div id="notiDat">
	    		<div id="notiDat_title" class="ui-widget-header">댓글 목록</div> 
	    		<table id="notiDat_table">
					<thead>
						<tr>
						  <th scope="col" abbr="번호" width="60">번호</th>
						  <th scope="col" abbr="내용">내용</th>
						  <th scope="col" abbr="작성자"width="70">작성자</th>
						  <th scope="col" abbr="등록일" width="100">등록일</th>
						  <th scope="col" abbr="삭제" width="50">삭제</th>				  				  
						</tr>
					</thead>
					<tbody>
					</tbody>													
				</table>
	     	</div>
	     	
	     						
		</div>
    </div>
							
    <div class="clear"> </div>

  <%@include file="../common/footer.jsp" %>
</div>
</body>
</html>