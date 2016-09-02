$(document).ready(function(){
  $("#menu > li > a").on("click", function(e){
    if($(this).parent().has("ul")) {
      e.preventDefault();
    }
    
    if(!$(this).hasClass("open")) {
      // hide any open menus and remove all other classes
      $("#menu li ul").slideUp(350);
      $("#menu li a").removeClass("open");
      
      // open our new menu and add the open class
      $(this).next("ul").slideDown(350);
      $(this).addClass("open");
    }
    
    else if($(this).hasClass("open")) {
      $(this).removeClass("open");
      $(this).next("ul").slideUp(350);
    }
  });

	$.datepicker.regional['ko'] = {
			   closeText: '닫기',
			   prevText: '이전',
			   nextText: '다음',
			   currentText: '오늘',
			   monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			   monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
			   dayNames: ['일','월','화','수','목','금','토'],
			   dayNamesShort: ['일','월','화','수','목','금','토'],
			   dayNamesMin: ['일','월','화','수','목','금','토'],
			   weekHeader: 'Wk',
			   dateFormat: 'yy-mm-dd',
			   firstDay: 0,
			   isRTL: false,
			   showMonthAfterYear: true,
			   yearSuffix: ''};
	
	$.datepicker.setDefaults($.datepicker.regional['ko']); 
	
	$( "input[type=submit], :button" ).button().css({
			"padding":"2px 5px", 
			"font-size":"1em", 
			"color":"#4f6b72",
			"border-radius":"2px"
		  });
	
	$.each($('tr:odd'), function(i){ // 홀수 번째 
		$(this).css("backgroundColor", "#FFFFFF"); 
		}); 
		$.each($('tr:even'), function(i){ //짝수 번째 
		$(this).css("backgroundColor", "#F7F7F7"); 
	}); 
		
	
	$("#logout").on("click", function(){
		if(confirm("로그아웃 하시겠습니까?")){
			location.href="neviGo?cmd=logOut";
		}
	});
		
});

function alertMsg(title, msg){
	$("#alert_msg").attr("title", title);
	$("#alert_msg > p").text(msg);
	$("#alert_msg").dialog();	
}

function set_comma(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;
	n += '';
	while (reg.test(n))
	n = n.replace(reg, '$1' + ',' + '$2');
	return n;
}