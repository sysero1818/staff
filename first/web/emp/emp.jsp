<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본</title>
<%@include file="../common/jscss.jsp" %>
<link rel="stylesheet" type="text/css" href="css/emp.css" />
<script type="text/javascript" src="js/emp.js"></script>
</head>
<body>
<div id="modalAddrDiv" title="우편번호 찾기">
	<p class="validateTips">&nbsp;</p>
	<form id="addrSerarchForm" name="addrSerarchForm">
		<fieldset>
			<input type="hidden" name="cmd" value="empTest" />
			<label for="dong">동이름 : </label>
			<input type="text" name="dong" id="dong" class="text ui-widget-content ui-corner-all">
			<input type="button" id="btndong" value="검색" />
		</fieldset>
	</form>
	<table id="addr_table">
		<thead>
			<tr>
				<th>우편번호</th><th>시도</th><th>시군구</th><th>동</th><th>리</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
</div>
<div id="modalPicDiv" title="사진 수정">
	<form id="picForm" name="picForm" enctype="multipart/form-data">
		<input type="hidden" name="cmd" value="picUpload" />
		<input type="hidden" name="pic_empno" value="" />
		<input type="file" id="picFile" name="picFile" value="" />
		<input type="submit" id="picSend" name="picSend" value="수정" />
	</form>	
</div>

<div id="wrapper">
	<%@include file="../common/header.jsp" %>
	
  	<div id="navigation"> 홈 > 사원관리 </div>
  	
  	<div id="main">

    <div id="leftcolumn">
    <nav>
		<%@include file="../common/menu.jsp" %>
    </nav>
	</div>
    <div id="rightcolumn">
	    <div id="content_table">
    		<div id="table_search">
	 				<label for="sh_empno">사원번호 : </label>
					<input type="text" id="sh_empno" name="sh_empno" maxlength="10" value=""  />&nbsp;
					<label for="sh_empnm">사원명 : </label>
					<input type="text" id="sh_empnm" name="sh_empnm" maxlength="10" value=""  onkeyup="doSearch(arguments[0]||event)" />&nbsp;
					<label>입사일 : </label>
					<input type="text" id="sh_indt_st" name="sh_indt_st" maxlength="8" value="" /> ~
					<input type="text" id="sh_indt_ed" name="sh_indt_ed" maxlength="8" value="" />					
					&nbsp;&nbsp;<input type="button" id="btnSearch" value="검색"  onclick="doSearch(arguments[0]||event)" />
    		</div>
    		<div id="table_body">
				<table id="user_list"></table>
    			<div id="pager"></div>
    		</div>
		</div>
		
    	<div id="content_form"> 
 			<div id="regForm_title" class="ui-widget-header">사원 정보</div>
    		<div id="picture">
				<img src="images/noimage_pic.gif" />
				<p class="pic_btnRow">
					<input type="button" id="btnPic" value="수정" /> <input type="button" id="btnPicDel" value="삭제" />
				</p>
    		</div>
 			<form class="cmxform" id="regForm" name="regForm" method="post">
 				<div id="regForm_left">
					<input type="hidden" name="inup" id="inup" value="in" />
					<p>
						<label for="empno">사번</label>
						<input type="text" id="empno" name="empno" readonly  value="" />
					</p>	
					<p>
						<label for="passwd">패스워드</label>
						<input type="password" id="passwd" name="passwd" class="required"  value="121212" />
					</p>		
					<p>
						<label for="deptno">부서</label>
						<select id="deptno" name="deptno" class="required">
								<option value="">선택</option>
								<option value="100">총무부</option>
								<option value="101" selected="selected">영업부</option>
								<option value="102">기획부</option>
								<option value="103">개발부</option>																												
						</select>	
					</p>	
					<p>
						<label for="indt">입사일</label>
						<input type="text" id="indt" name="indt"  value="2016-08-01" />
					</p>			
					<p>
						<label for="mobile">휴대폰</label>
						<input type="text" id="mobile" name="mobile" value="01022223333" />
					</p>	
					<p>
						<label for="birth">생일</label>
						<input type="text" id="birth" name="birth"  value="" />
					</p>																							
 				</div>
 				<div id="regForm_right">
					<p>
						<label for="empname">사원명</label>
						<input type="text" id="empnm" name="empnm"  class="required" value="" />
					</p>
					<p>
						<label for="confirm_passwd">패스워드</label>
						<input type="password" id="confirm_passwd" name="confirm_passwd" class="required"  value="121212" />
					</p>						
					<p>
						<label for="jumin">주민번호</label>
						<input type="text" id="jumin" name="jumin"  value="" />
					</p>		
					<p>		
						<label for="positno">직급</label>
							<select id="positno" name="positno" class="required">
								<option value="">선택</option>
								<option value="10">CEO</option>
								<option value="20">이사</option>
								<option value="30">부장</option>
								<option value="40">차장</option>
								<option value="50">과장</option>
								<option value="60" selected="selected">대리</option>
								<option value="70">주임</option>
								<option value="80">사원</option>							
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
							<input type="text" id="zipcode" name="zipcode"  value="" /> <input type="button" id="btnaddr" value="찾기" />
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