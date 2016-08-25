<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
			<table id="mytable" summary="프로젝트에 사용">
		  	<thead>
				<tr>
				  <th scope="col" abbr="이름">이름</th>
				  <th scope="col" abbr="아이디">아이디</th>
				  <th scope="col" abbr="패스워드">패스워드</th>
				  <th scope="col" abbr="이메일">이메일</th>
				  <th scope="col" abbr="전화">전화</th>				  
				  <th scope="col" abbr="Admin">Admin</th>				  				  
				</tr>
			</thead>
			<tbody>
		  		<c:forEach var="m" items="${member }">
				<tr>
				  <td>${m.name }</td>
				  <td>${m.userid }</td>
				  <td>${m.pwd }</td>
				  <td>${m.email }</td>
				  <td>${m.phone }</td>				  				  
				  <td>${m.admin }</td>
				</tr>
				</c:forEach>
			</tbody>													
			</table>
</body>
</html>