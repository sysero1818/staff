<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	 <div id="header">
	 <%
	 String empnm = "";
	 empnm = (String) session.getAttribute("empnm");
	 %>
	 	<div id="login"><%=empnm %>님 로그인&nbsp;|&nbsp;로그아웃</div>
	 	<div id="header_title"><h1>사원 정보 관리 시스템</h1></div>
	 </div>