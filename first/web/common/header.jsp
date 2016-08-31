<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	 <div id="header">
	 <%
	 	String empnm = "";
	 	String manager = "";
	 	String msg = "";
	 	
	 	empnm = (String) session.getAttribute("empnm");
		manager = (String) session.getAttribute("manager");
		if (manager != null){
			msg = "관리자";
		}
	 %>
	 	<div id="login"><%=empnm %><%=msg %>님 로그인&nbsp;|&nbsp;<a href="#" id="logout">로그아웃</a></div>
	 	<div id="header_title"><h1>사원 정보 관리 시스템</h1></div>
	 </div>