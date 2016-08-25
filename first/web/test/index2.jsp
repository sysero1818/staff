<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본</title>
<link rel="stylesheet" type="text/css" href="../css/base.css" />
<link rel="stylesheet" type="text/css" href="../css/navs.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="../css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="../css/index.css" />


<script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/i18n/grid.locale-kr.js"></script>
<script type="text/javascript" src="../js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/jquery.maskedinput.js"></script>
<script type="text/javascript" src="../js/nav.js"></script>
<script type="text/javascript" src="../js/index.js"></script>
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
				<img src="../images/noimage_pic.gif" />
				<p class="pic_btnRow">
					<input type="submit" id="btnPicSubmit" value="수정" /> <input type="button" id="btnDelPic" value="삭제" />
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