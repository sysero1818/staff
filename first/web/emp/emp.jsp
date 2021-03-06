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
<div id="alert_msg" title="">
  <p></p>
</div>
<div id="modalAddrDiv" title="우편번호 찾기">
	<p class="validateTips">&nbsp;</p>
	<form id="addrSerarchForm" name="addrSerarchForm">
			<input type="hidden" name="cmd" value="searchAddr" />
			<label for="dong">동이름 : </label>
			<input type="text" name="dong" id="dong" class="text ui-widget-content ui-corner-all" onkeyup="doAddr(arguments[0]||event)">
			<input type="button" id="btndong" value="검색" onclick="doAddr(arguments[0]||event)" />
	</form>
	<table id="addr_list" class="jqtable"></table>
	<div id="addr_pager"></div>	
	
	<div id="insert_addr">
		<form id="mdAddrForm"  class="cmxform">
			<input type="hidden" id="md_zipseq" name="md_zipseq" />
			<p>
				<label for="md_zipcode">우편번호</label>
				<input type="text" id="md_zipcode" name="md_zipcode" value="" />
			</p>
			<p>
				<label for="md_basicad">기본 주소</label>
				<input type="text" id="md_basicad" name="md_basicad" value="" />
			</p>
			<p>
				<input type="button" id="btnUseAddr" value="주소 사용" />
			</p>
		</form>
	</div>	

</div>
<!-- <div id="modalPicDel" title="사진 삭제">
	<span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span><p>사진을 삭제하시겠습니까?</p>
</div> -->
<div id="modalPicDiv" title="사진 수정">
	<form id="picForm" name="picForm" method="post" action="neviGo?cmd=picUpload" enctype="multipart/form-data">
		<input type="hidden" id="pic_empno" name="pic_empno" value="" />
		<input type="file" id="picFile" name="picFile" value="" />
		<input type="submit" id="picSend" name="picSend" value="수정" />
	</form>	
</div>

<div id="wrapper">
	<%@ include file="../common/header.jsp" %>  
  	<div id="navigation"> 홈 > 사원관리 </div>
  	
  	<div id="main">

    <div id="leftcolumn">
    <nav>
		<%@ include file="../common/menu.jsp" %>
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
					&nbsp;&nbsp;<span><input type="button" id="btnSearch" value="검색"  onclick="doSearch(arguments[0]||event)" /></span>
    		</div>
    		<div id="table_body">
				<table id="user_list" class="jqtable"></table>
    			<div id="pager"></div>
    		</div>
		</div>
		
		
    	<div id="content_form"> 
 			<div id="regForm_title" class="ui-widget-header">사원 정보</div>
    		<div id="picture">
				<img src="images/noimage_pic.gif" width="100"  height="100"/>
				<p class="pic_btnRow">
					<input type="button" id="btnPic" value="수정" /> <input type="button" id="btnPicDel" value="삭제" />
				</p>
    		</div>
 			<form class="cmxform" id="regForm" name="regForm" method="post">
 				<div id="regForm_left">
					<input type="hidden" name="inup" id="inup" value="in" />
					<p>
						<label for="empno">사번</label>
						<input type="text" id="empno" name="empno" readonly value="" />
					</p>	
					<p class="pass">
						<label for="passwd">패스워드</label>
						<input type="password" id="passwd" name="passwd" class="required"   tabindex="2" value="121212" />
					</p>		
					<p>
						<label for="deptno">부서</label>
						<select id="deptno" name="deptno" class="required"  tabindex="4" >
								<option value="">선택</option>
								<option value="100">총무부</option>
								<option value="101" selected="selected">영업부</option>
								<option value="102">기획부</option>
								<option value="103">개발부</option>																												
						</select>	
					</p>	
					<p>
						<label for="indt">입사일</label>
						<input type="text" id="indt" name="indt"  value="2016-08-01"  tabindex="6" />
					</p>			
					<p>
						<label for="mobile">휴대폰</label>
						<input type="text" id="mobile" name="mobile" value=""  tabindex="8" />
					</p>	
					<p>
						<label for="birth">생일</label>
						<input type="text" id="birth" name="birth"  value="" tabindex="10" />
					</p>																							
 				</div>
 				<div id="regForm_right">
					<p>
						<label for="empname">사원명</label>
						<input type="text" id="empnm" name="empnm"  class="required"  tabindex="1" value="" />
					</p>
					<p class="pass">
						<label for="confirm_passwd">패스워드</label>
						<input type="password" id="confirm_passwd" name="confirm_passwd" class="required"  tabindex="3" value="121212" />
					</p>						
					<p>
						<label for="jumin">주민번호</label>
						<input type="text" id="jumin" name="jumin"  value=""  tabindex="5" />
					</p>		
					<p>		
						<label for="positno">직급</label>
							<select id="positno" name="positno" class="required"  tabindex="7" >
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
						<input type="text" id="outdt" name="outdt"  value="" tabindex="9" />
					</p>
					<p>
						<label for="email">이메일</label>
						<input type="text" id="email"  name="email"  class="email" value=""  tabindex="11" />
					</p>	
					<p class="pay">
						<label for="payment">급여</label>
						<input type="text" id="payment"  name="payment" value="" tabindex="12" />
					</p>										
 				</div>
 				<div id="regForm_bottom">
					<p>
						<label for="zipcode">우편번호</label>
							<input type="hidden" id="zipseq" name="zipseq" value="" />
							<input type="text" id="zipcode" name="zipcode"  value="" tabindex="13" /> <input type="button" id="btnaddr" value="찾기" />
					</p>
					<p>
						<label for="zipcode">기본주소</label>
						<input type="text" id="basicad" name="basicad"  value="" tabindex="14" />
					</p>
					<p>
						<label for="zipcode">상세주소</label>
							<input type="text" id="detailad" name="detailad"  value="" tabindex="15" />
						
					</p>
					<p class="btnRow">
						<input type="submit" id="btnSubmit" class="btnwht" value="등록" /> <input type="button" id="btnRefresh" class="btnwht" value="초기화" />
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
    		<div id="sch_table">
				<table id="sch_list" class="jqtable"></table>
    			<div id="sch_pager"></div>
    		</div>
    		<div id="sch_inup">
    			<form id="schForm" class="cmxform" method="post">
    				<input type="hidden" id="inup_sch" name="inup_sch"  value="in" />
    				<input type="hidden" id="schno" name="schno" value="" />
    				<input type="hidden" id="sch_empno" name="sch_empno" value=""/>
    				<div id="sch_left">
    					<p>
							<label for="sch_startdt">시작</label>
							<input type="text" id="sch_startdt" name="sch_startdt" class="required" value="" />
						</p>
						<p>
							<label for="sch_enddt">종료</label>
							<input type="text" id="sch_enddt" name="sch_enddt"  class="required" value="" />
						</p>    				
    				</div>
    				<div id="sch_right">
    					<p>
							<label for="school">학교명</label>
							<input type="text" id="school" name="school" class="required" value="" />
						</p>
						<p>
							<label for="major">전공</label>
							<input type="text" id="major" name="major" class="required" value="" />
						</p>	    				
    				</div>
					<div id="sch_bottom">
						<p>
							<input type="submit" id="btnSchSubmit" class="btnwht" value="등록" /> <input type="button" id="btnSchRefresh" class="btnwht" value="초기화" />
						</p>
					</div>
    			</form>
    		</div>
		  </div>
		  <div id="tabs-2">

    		<div id="career_table">
				<table id="career_list"></table>
    			<div id="career_pager"></div>
    		</div>
    		<div id="career_inup">
    			<form id="careerForm" class="cmxform" method="post">
    				<input type="hidden" id="inup_career" name="inup_career"  value="in" />
    				<input type="hidden" id="careerno" name="careerno" value="" />
    				<input type="hidden" id="career_empno" name="career_empno" value=""/>
    				<div id="career_left">
    					<p>
							<label for="career_startdt">시작</label>
							<input type="text" id="career_startdt" name="career_startdt" class="required" value="" />
						</p>
						<p>
							<label for="career_enddt">종료</label>
							<input type="text" id="career_enddt" name="career_enddt"  class="required" value="" />
						</p>    				
    				</div>
    				<div id="career_right">
    					<p>
							<label for="compnm">직장명</label>
							<input type="text" id="compnm" name="compnm" class="required" value="" />
						</p>
						<p>
							<label for="positnm">직위</label>
							<input type="text" id="positnm" name="positnm" class="required" value="" />
						</p>
						<p>
							<label for="charge">직무</label>
							<input type="text" id="charge" name="charge" value="" />
						</p>						
    				</div>
					<div id="career_bottom">
						<p>
							<input type="submit" id="btnCareerSubmit" class="btnwht" value="등록" /> <input type="button" id="btnCareerRefresh" class="btnwht" value="초기화" />
						</p>
					</div>
    			</form>
    		</div>
		  
		  </div>
		  <div id="tabs-3">
		  
		     <div id="cert_table">
				<table id="cert_list"></table>
    			<div id="cert_pager"></div>
    		</div>
    		<div id="cert_inup">
    			<form id="certForm" class="cmxform" method="post">
    				<input type="hidden" id="inup_cert" name="inup_cert"  value="in" />
    				<input type="hidden" id="cno" name="cno" value="" />
    				<input type="hidden" id="cert_empno" name="cert_empno" value=""/>
    				<div id="cert_left">
	    				<p>
							<label for="certno">증번호</label>
							<input type="text" id="certno" name="certno" class="required" tabindex="40" value="" />
						</p>
	    				<p>
							<label for="aqdt">취득일</label>
							<input type="text" id="aqdt" name="aqdt" class="required" tabindex="42" value="" />
						</p> 						    				
    					<p>
							<label for="cert_renewstdt">갱신시작일</label>
							<input type="text" id="cert_renewstdt" name="cert_renewstdt" class="required" tabindex="44" value="" />
						</p>
    				</div>
    				<div id="cert_right">
	    				<p>
							<label for="certnm">자격증명</label>
							<input type="text" id="certnm" name="certnm" class="required" tabindex="41" value="" />
						</p>    
	    				<p>
							<label for="issuorgan">발급기관</label>
							<input type="text" id="issuorgan" name="issuorgan" class="required" tabindex="43" value="" />
						</p> 	
						<p>
							<label for="cert_reneweddt">갱신종료일</label>
							<input type="text" id="cert_reneweddt" name="cert_reneweddt"  class="required" tabindex="45" value="" />
						</p>    					
    				</div>
					<div id="cert_bottom">
						<p>
							<input type="submit" id="btnCertSubmit" class="btnwht" value="등록" /> <input type="button" id="btnCertRefresh" class="btnwht" value="초기화" />
						</p>
					</div>
    			</form>
    		</div>
		  
		  </div>
		  <div id="tabs-4">
    		<div id="empskill_table">
				<table id="empskill_list" class="jqtable"></table>
    			<div id="empskill_pager"></div>
    		</div>
    		<div id="skill_table">
				<table id="skill_list" class="jqtable"></table>
    			<div id="skill_pager"></div>    		
    		</div>		  
		  </div>	  
		</div>     	
    </div>
							
    <div class="clear"> </div>
  </div>

  <%@include file="../common/footer.jsp" %>

 </div>
</body>
</html>