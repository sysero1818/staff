<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본</title>
<%@ include file="../common/jscss1.jsp" %>
<link rel="stylesheet" type="text/css" href="../css/index.css" />
<script type="text/javascript">
$(document).ready(function(){
    $( "#tabs" ).tabs();
	
	$("#indt, #outdt").datepicker();
	
	$("#birth").datepicker({
		changeMonth: true,
		changeYear: true
	});
	
    $("#indt").datepicker("option", "maxDate", $("#outdt").val());
    $("#indt").datepicker("option", "onClose", function ( selectedDate ) {
        $("#outdt").datepicker( "option", "minDate", selectedDate );
    });

    $("#outdt").datepicker("option", "minDate", $("#indt").val());
    $("#outdt").datepicker("option", "onClose", function ( selectedDate ) {
        $("#indt").datepicker( "option", "maxDate", selectedDate );
	});
    
	$("#mobile").mask("999-9999-9999");
	$("#jumin").mask("999999-9999999");	

	$("#regForm").validate({
		rules: {
		  empno: {required: true, minlength: 5, maxlength: 10},
		  passwd: {required: true, minlength: 5, maxlength: 20},
		  empname: {required: true, maxlength: 10 },
		  deptcd: {required: true},
		  positcd: {required: true},
		  upperid: {required: true},			  
		  email: {email: true}
		},
		messages: {
		  empno: {required: "필수", minlength: "5자리 이상", maxlength: "10자리까지" },
		  passwd: {required: "필수", minlength: "5자리 이상", maxlength: "20자리까지" },
		  empname: {required: "필수", maxlength: "10자리까지" },
		  deptcd: {required: "필수"},
		  positcd: {required: "필수"},
		  upperid: {required: "필수"},
		  email: {email: "오류"},
		}
	 });
	
	// submit
	var options = {
		url : 'projectList.html',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:null});
			return $("#regForm").valid();
			alert("a");
		},
	
		success : function(responseText,statusText,xhr){
			$("#regForm").clearForm();
			$("#inup").val("in");
			$("#btnSubmit").val("등록");
			$("#btnRefresh").hide();
			$.unblockUI();
	
			if (responseText!=""){
				alert(responseText);
			}
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#regForm").ajaxForm(options);
	
	$("#btnRefresh").bind("click", function(){
		$("#regForm").clearForm();
		$("#regForm_left > p > label.error").hide();
		$("#regForm_right > p > label.error").hide();			
		$("#deptcd").val("");
		$("#positcd").val("");
	});		

    $("#user_list").jqGrid({
        url:"memberList.do",
        mtype:"post",
        datatype:"json",
        caption:"사원 목록",
        height:"auto",
        rowNum:10,
        rowList:[3,5,10,15,20],
        colNames:["name","userid","pwd","email","phone","amdin"],
        colModel:[
                  {name:"name", index:"name", align:"center"},
                  {name:"userid", index:"userid", align:"center"},
                  {name:"pwd", index:"pwd", align:"center"},                          
                  {name:"email", index:"email", align:"center"},
                  {name:"phone", index:"phone", align:"center"},                          
                  {name:"amdin", index:"amdin", align:"center"}
                  ],
        pager:"#pager",
        autowidth:true,
        //width: auto,
        viewrecords:true
        //editurl: "/UserEditAction"
    });
   
    $("#user_list").jqGrid.navGrid("#pager",{add:false,edit:false,del:false, search:false});	
});
</script>
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
	    <div id="content_table">
    		<div id="table_search">
	 			<p>
					<label for="sh_compname">검색 : </label>
					<input type="text" id="sh_prjname" name="sh_prjname" maxlength="10" value=""  />
					&nbsp;&nbsp;<input type="button" id="btnSearch" value="검색"  />
				</p>
    		</div>	
    		<div id="table_body">
				<table id="user_list" style="box-sizing: initial" ></table>
    			<div id="pager"></div>
    		</div>
		</div>
		
    	<div id="content_form"> 
 			<div id="regForm_title" class="ui-widget-header">사원 정보</div>
    		<div id="pic">
				<img src="images/noimage_pic.gif" />
				<p class="pic_btnRow">
					<input type="button" id="btnPicSubmit" value="수정" /> <input type="button" id="btnDelPic" value="삭제" />
				</p>
    		</div>
 			<form class="cmxform" id="regForm" name="regForm" method="post" enctype="multipart/form-data">
 				<div id="regForm_left">
					<input type="hidden" name="inup" id="inup" value="in" />
					<input type="hidden" name="before_empno" id="before_empno" value="" /> 
					<p>
						<label for="empno">사번</label>
						<input type="text" id="empno" name="empno" style="ime-mode:disabled"  class="required"  value="" />
					</p>	
					<p>
						<label for="passwd">패스워드</label>
						<input type="password" id="passwd" name="passwd" style="ime-mode:disabled" class="required"  value="" />
					</p>		
					<p>
						<label>부서</label>
							<select id="deptcd" name="deptcd" class="required">
								<option value="">선택</option>
								<option value="A">총무부</option>
								<option value="B">기획부</option>
								<option value="C">개발부</option>
								<option value="D">영업부</option>																												
						</select>	
					</p>			
					<p>
						<label for="indt">입사일</label>
						<input type="text" id="indt" name="indt"  value="" />
					</p>			
					<p>
						<label>휴대폰</label>
						<input type="text" id="mobile" name="mobile" value="" />
					</p>	
					<p>
						<label for="birth">생일</label>
						<input type="text" id="birth" name="birth"  value="" />
					</p>																							
 				</div>
 				<div id="regForm_right">
					<p>
						<label for="empname">사원명</label>
						<input type="text" id="empname" name="empname"  class="required" value="" />
					</p>
					<p>
						<label for="jumin">주민번호</label>
						<input type="text" id="jumin" name="jumin"  value="" />
					</p>		
					<p>		
						<label>직급</label>
							<select id="positcd" name="positcd" class="required">
								<option value="">선택</option>
								<option value="7">CEO</option>
								<option value="6">이사</option>
								<option value="5">부장</option>
								<option value="4">차장</option>
								<option value="3">과장</option>
								<option value="2">대리</option>
								<option value="1">주임</option>
								<option value="0">사원</option>							
						</select>						
					</p>
					<p>
						<label for="outdt">퇴사일</label>
						<input type="text" id="outdt" name="outdt"  value="" />
					</p>
					<p>
						<label for="email">이메일</label>
						<input type="text" id="email"  name="email" style="ime-mode:disabled" class="email" value="" />
					</p>					
 				</div>
 				<div id="regForm_bottom">
					<p>
						<label for="zipcode">우편번호</label>
							<input type="text" id="zipcode" name="zipcode"  value="" />
					</p>
					<p>
						<label for="zipcode">기본주소</label>
							<input type="text" id="basicad" name="basicad"  value="" />
					</p>
					<p>
						<label for="zipcode">상세주소</label>
							<input type="text" id="detailad" name="detailad"  value="" />
						
					</p>
					<p class="btnRow">
						<input type="submit" id="btnSubmit" value="등록" /> <input type="button" id="btnRefresh" value="초기화" />
					</p>					
 				</div>
			</form> 
     	</div>
     	
		<div id="tabs">
		  <ul>
		    <li><a href="#tabs-1">학 력</a></li>
		    <li><a href="#tabs-2">경 력</a></li>
		    <li><a href="#tabs-3">자 격 증</a></li>
		    <li><a href="#tabs-4">보유 기술</a></li>	    
		  </ul>
		  <div id="tabs-1">
		  </div>
		  <div id="tabs-2">
		  </div>
		  <div id="tabs-3">
		  </div>
		  <div id="tabs-4">
		  </div>	  
		</div>     	
    </div>
							
    <div class="clear"> </div>
  </div>

  <%@include file="../common/footer.jsp" %>

 </div>
</body>
</html>