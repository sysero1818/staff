<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본</title>
<%@include file="../common/jscss.jsp" %>
<link rel="stylesheet" type="text/css" href="css/prj.css" />
<script type="text/javascript" src="js/prj.js"></script>
</head>
<body>
<div id="alert_msg" title="">
  <p></p>
</div>
<div id="wrapper">
	<%@ include file="../common/header.jsp" %>  
  	<div id="navigation"> 홈 > 회사프로젝트 </div>
  	
  	<div id="main">

    <div id="leftcolumn">
    <nav>
		<%@ include file="../common/menu.jsp" %>
    </nav>
	</div>
    <div id="rightcolumn">
	    <div id="content_table">
    		<div id="table_search">
	 				<label for="sh_prjnm">프로젝트명 : </label>
					<input type="text" id="sh_prjnm" name="sh_prjnm" maxlength="10" value="" onkeyup="doSearch(arguments[0]||event)" />&nbsp;

					&nbsp;&nbsp;<span><input type="button" id="btnSearch" value="검색"  onclick="doSearch(arguments[0]||event)" /></span>
    		</div>
    		<div id="table_body">
				<table id="user_list" class="jqtable"></table>
    			<div id="pager"></div>
    		</div>
		</div>
		
		
    	<div id="content_form"> 
 			<form class="cmxform" id="regForm" name="regForm" method="post">
				<input type="hidden" name="inup" id="inup" value="in" />
				<p>
					<label for="prjno">프로젝트 번호</label>
					<input type="text" id="prjno" name="prjno" readonly value="" />
				</p>	
				<p>
					<label for="prjnm">프로젝트명</label>
					<input type="text" id="prjnm" name="prjnm" class="required" tabindex="1" />
				</p>		
				<p>
					<label for="startdt">프로젝트기간</label>
					<input type="text" id="startdt" name="startdt"   tabindex="2" />
					<input type="text" id="enddt" name="enddt"  tabindex="3" />					
				</p>			
				<p>
					<label for="prjcontent">프로젝트내용</label>
					<textarea id="prjcontent" name="prjcontent" cols="60" rows="10"  tabindex="4"></textarea>
				</p>	
				<p class="btnRow">
					<input type="submit" id="btnSubmit" class="btnwht" value="등록" /> <input type="button" id="btnRefresh" class="btnwht" value="초기화" />
				</p>					
			</form> 
     	</div>
     	
		<div id="tabs">
		  <ul>
		    <li><a href="#tabs-1">수행자</a></li>
		    <li><a href="#tabs-4">개발 환경</a></li>	    
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
		  
		  </div>
		  <div id="tabs-3">
		  
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