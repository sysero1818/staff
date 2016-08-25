<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jQuery UI Dialog -Modal form</title>
<link rel="stylesheet" type="text/css" href="common/css/jquery-ui.min.css" />
<script type="text/javascript" src="common/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="common/js/jquery-ui.min.js"></script>

<script type="text/javascript">
function fn_movePage(page){
	location.href="MemberServlet?command=member_list&page="+page;	
}
</script>
<style>
	body { font-size: 62.5%; }
	label, input { display:block; }
	input.text { margin-bottom:12px; width:95%; padding: .4em; }
	fieldset { padding:0; border:0; margin-top:25px; }
	h1 { font-size: 1.2em; margin: .6em 0; }
	div#users-contain { width: 350px; margin: 20px 0; }
	div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
	div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
	.ui-dialog .ui-state-error { padding: .3em; }
	.validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>
<script>
$(function() {
	var dialog, form,
	emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
	name = $( "#name" ),
	email = $( "#email" ),
	password = $( "#password" ),
	allFields = $( [] ).add( name ).add( email ).add( password ),
	tips = $( ".validateTips" );
		
	function updateTips( t ) {
		tips
		.text( t )
		.addClass( "ui-state-highlight" );
		setTimeout(function() {
		tips.removeClass( "ui-state-highlight", 1500 );
		}, 500 );
	}
	
	function checkLength( o, n, min, max ) {
		if ( o.val().length > max || o.val().length < min ) {
			o.addClass( "ui-state-error" );
			updateTips( "Length of " + n + " must be between " +
			min + " and " + max + "." );
			return false;
		} else {
			return true;
		}
	}
	
	function checkRegexp( o, regexp, n ) {
		if ( !( regexp.test( o.val() ) ) ) {
			o.addClass( "ui-state-error" );
			updateTips( n );
		return false;
		} else {
			return true;
		}
	}
	
	function addUser() {

		var valid = true;
		allFields.removeClass( "ui-state-error" );
		valid = valid && checkLength( name, "username", 3, 16 );
		valid = valid && checkLength( email, "email", 6, 80 );
		valid = valid && checkLength( pwd, "pwd", 5, 16 );
		valid = valid && checkRegexp( name, /^[a-z가-힣]([0-9a-z가-힣_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
		valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
		valid = valid && checkRegexp( pwd, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
		if ( valid ) {
			var frmData=$("#memberfrm").serialize();
			$.ajax({
				type: "post",
				url: "MemberServlet",
				data: frmData,
				dataType: "json",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(data) {
					//data = JSON.parse(json);
					$("#users tbody").prepend("<tr><td>"+data.name+"</td><td>"+data.userid+"</td><td>"+data.pwd+"</td><td>"+data.email+"</td></tr>");					
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert(xhr.status+" : "+thrownError);
				},
		        complete: function(){
					
		        }
			}); 
				
			dialog.dialog( "close" );
		}
		
		return valid;
	}
	
	dialog = $( "#dialog-form" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			"Create an account": addUser,
	
			Cancel: function() {
				dialog.dialog( "close" );
			}
		},
		close: function() {
			form[ 0 ].reset();
			allFields.removeClass( "ui-state-error" );
		}
	});
	
	form = dialog.find( "form" ).on( "submit", function( event ) {
		event.preventDefault();
		addUser();
	});
	
	$( "#create-user" ).button().on( "click", function() {
		dialog.dialog( "open" );
	});
});
</script>
</head>
<body>
<div id="dialog-form" title="Create new user">
	<p class="validateTips">All form fields are required.</p>
	<form id="memberfrm" name="memberfrm">
		<fieldset>
			<input type="hidden" name="command" value="member_insert" />
			<label for="userid">아이디</label>
			<input type="text" name="userid" id="userid" value="Jane Smith" class="text ui-widget-content ui-corner-all">
			<label for="name">이름</label>
			<input type="text" name="name" id="name" value="Jane Smith" class="text ui-widget-content ui-corner-all">			
			<label for="email">이메일</label>
			<input type="text" name="email" id="email" value="jane@smith.com" class="text ui-widget-content ui-corner-all">
			<label for="pwd">패스워드</label>
			<input type="password" name="pwd" id="pwd" value="xxxxxxx" class="text ui-widget-content ui-corner-all">
			<!--Allow form submission with keyboard without duplicating the dialog button -->
			<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
		</fieldset>
	</form>
</div>
<div id="users-contain" class="ui-widget">
	<h1>Existing Users:</h1>
	<table id="users" class="ui-widget ui-widget-content">
		<thead>
			<tr class="ui-widget-header ">
				<th>이름</th>
				<th>아이디</th>
				<th>패스워드</th>
				<th>이메일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="member" items="${memberList }">
				<tr class="record">
					<td>${member.name }</td>
					<td>${member.userid }</td>
					<td>${member.pwd }</td>
					<td>${member.email }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	    <div id="page">
	    <c:if test="${paging.pageNo != 0}">
	        <c:if test="${paging.pageNo > paging.pageBlock}">
	            <a href="javascript:fn_movePage(${paging.firstPageNo})" style="text-decoration: none;">[첫 페이지]</a>
	       </c:if>
	       <c:if test="${paging.pageNo != 1}">
	           <a href="javascript:fn_movePage(${paging.prevPageNo})" style="text-decoration: none;">[이전]</a>
	        </c:if>
	        <span>
	            <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
	                <c:choose>
	                    <c:when test="${i eq paging.pageNo}">
	                        <a href="javascript:fn_movePage(${i})" style="text-decoration: none;">
	                            <font style="font-weight: bold;">${i}</font>
	                        </a>
	                    </c:when>
	                    <c:otherwise>
	                        <a href="javascript:fn_movePage(${i})" style="text-decoration: none;">${i}</a>
	                    </c:otherwise>
	                </c:choose>
	            </c:forEach>
	        </span>
	        <c:if test="${paging.pageNo != paging.finalPageNo }">
	            <a href="javascript:fn_movePage(${paging.nextPageNo})" style="text-decoration: none;">[다음]</a>
	        </c:if>
	        <c:if test="${paging.endPageNo < paging.finalPageNo }">
	            <a href="javascript:fn_movePage(${paging.finalPageNo})" style="text-decoration: none;">[마지막 페이지]</a>
	        </c:if>
	    </c:if>
	    </div>	
</div>
<button id="create-user">Create new user</button>
</body>
</html>
