<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>jQuery UI Dialog -Modal confirmation</title>
<link rel="stylesheet" type="text/css" href="../css/jquery-ui.css" />
<script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<link rel="stylesheet" href="/css/style.css">
<script>
$(function() {
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		buttons: {
			"삭제": function() {
				$( this ).dialog( "close" );
			},
			"취소": function() {
				$( this ).dialog( "close" );
			}
		}
	});
});
</script>
</head>
<body>
<div id="dialog-confirm" title="Empty the recycle bin?">
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		These items will be permanently deleted and cannot be recovered. Are you sure?
	</p>
</div>
<p>
	Sed vel diam id libero <a href="http://example.com">rutrum convallis</a>. 
	Donec aliquet leo vel magna. Phasellus rhoncus faucibus ante. 
	Etiam bibendum, enim faucibus aliquet rhoncus, arcu felis ultricies neque, sit amet auctor elit eros a lectus.
</p>
