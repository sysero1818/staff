<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<c:if test="${empty empno}">
 	<% response.sendRedirect("neviGo?cmd=login"); %>
</c:if>

<div id="header">
 	<div id="login">${empnm}&nbsp;${manager }님 로그인&nbsp;|&nbsp;<span id="logout">로그아웃</span></div>
	<div id="header_title"><h1>사원 정보 관리 시스템</h1></div>
</div>