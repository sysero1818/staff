<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jQuery UI Dialog -Modal form</title>
<%@include file="../common/jscss.jsp" %>

<!-- <link rel="stylesheet" type="text/css" href="../common/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="../common/css/ui.jqgrid.css" />

<script type="text/javascript" src="../common/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../common/js/jquery-ui.js"></script>
<script type="text/javascript" src="../common/js/jquery.jqGrid.min.js"></script> -->
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
		valid = valid && checkLength( password, "password", 5, 16 );
		valid = valid && checkRegexp( name, /^[a-z]([0-9a-z_\s])+$/i, "Username may consist of a-z, 0-9, underscores, spaces and must begin with a letter." );
		valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
		valid = valid && checkRegexp( email, emailRegex, "eg. ui@jquery.com" );
		valid = valid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );
	
		/* if ( valid ) {
			$( "#users tbody" ).append( "<tr>" +
			"<td>" + name.val() + "</td>" +
			"<td>" + email.val() + "</td>" +
			"<td>" + password.val() + "</td>" +
			"</tr>" );
			dialog.dialog( "close" );
		} */
		
		if(valid){
			
		    $("#user_list").jqGrid({
		        url:"empListJson.do",
		        mtype:"post",
		        datatype:"json",
		        caption:"사원 목록",
		        height:"300",
		        rowNum:10,
		        rowList:[3,5,10,15,20],
		        colNames:["사원번호","사원명","pic","jumin","생일","zipseq","detailad","전화","이메일","입사일","outdt","empgb","deptno","positno","payment"],
		        colModel:[
		                  {name:"empno", index:"empno", align:'center', sortable:false}, 
		                  {name:"empnm", index:"empnm", align:'center', sortable:false}, 
		                  {name:"pic", index:"pic", hidden:true},                         
		                  {name:"jumin", index:"jumin", hidden:true},
		                  {name:"birth", index:"birth", hidden:true},
		                  {name:"zipseq", index:"zipseq", hidden:true},                         
		                  {name:"detailad", index:"detailad", hidden:true},                  
		                  {name:"mobile", index:"mobile", align:'center', sortable:false}, 
		                  {name:"email", index:"email", align:'center', sortable:false},
		                  {name:"indt", index:"indt", align:'center', sortable:false}, 
		                  {name:"outdt", index:"outdt", hidden:true},
		                  {name:"empgb", index:"empgb", hidden:true}, 
		                  {name:"deptno", index:"deptno", hidden:true},
		                  {name:"positno", index:"positno", hidden:true}, 
		                  {name:"payment", index:"payment", hidden:true}                  
		                  ],
		        pager:"#pager",
		        autowidth:true,
		        viewrecords:true,
				loadComplete: function () {
					$(this).find(">tbody>tr.jqgrow:odd").addClass("myAltRowClassEven");
					$(this).find(">tbody>tr.jqgrow:even").addClass("myAltRowClassOdd");
				},		        
		        onSelectRow: function(ids) {  
					/* var gsr = $("#user_list").jqGrid('getGridParam','selrow');
					$("#user_list").jqGrid('GridToForm',gsr,"#regForm");
					
					$("#jumin").val($.trim(ret.jumin));
					$("#indt").val($.trim(ret.indt));
					$("#outdt").val($.trim(ret.outdt));
					$("#mobile").val($.trim(ret.mobile));
					$("#birth").val($.trim(ret.birth));
					
					$("#inup").val("up");
					$("#btnSubmit").val("수정"); */
					var ret = $("#user_list").getRowData( ids );
					alert(ret.empnm);
		        }
		    });
		   
		    $("#user_list").jqGrid.navGrid("#pager",{add:false,edit:false,del:false, search:false});
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
	<form>
		<fieldset>
		<label for="name">Name</label>
			<input type="text" name="name" id="name" value="Jane Smith" class="text ui-widget-content ui-corner-all">
			<label for="email">Email</label>
			<input type="text" name="email" id="email" value="jane@smith.com" class="text ui-widget-content ui-corner-all">
			<label for="password">Password</label>
			<input type="password" name="password" id="password" value="xxxxxxx" class="text ui-widget-content ui-corner-all">
			<!--Allow form submission with keyboard without duplicating the dialog button -->
			<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
		</fieldset>
	</form>
	<div id="result_table">
		<table id="user_list"></table>
	 	<div id="pager"></div>	
	</div>
</div>
<div id="users-contain" class="ui-widget">
	<h1>Existing Users:</h1>
	<table id="users" class="ui-widget ui-widget-content">
		<thead>
			<tr class="ui-widget-header ">
				<th>Name</th>
				<th>Email</th>
				<th>Password</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>John Doe</td>
				<td>john.doe@example.com</td>
				<td>johndoe1</td>
			</tr>
		</tbody>
	</table>
</div>
<button id="create-user">Create new user</button>
</body>
</html>
