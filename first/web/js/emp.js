$(document).ready(function(){
	
	var dl, form,
	dong = $( "#dong" ),
	allFields = $( [] ).add( dong ),
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
	
	dl = $( "#modalAddrDiv" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			Cancel: function() {
				dl.dialog( "close" );
			}
		},
		close: function() {
			form[ 0 ].reset();
			allFields.removeClass( "ui-state-error" );
		}
	});
	
	form = dl.find( "#addrSerarchForm" ).on( "submit", function( event ) {
		event.preventDefault();
		addUser();
	});	
	
	$("#btndong").on("click", function(){
		var frmData=$("#addrSerarchForm").serialize();
		$.ajax({
			type: "post",
			url: "neviGo",
			data: frmData,
			dataType: "json",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(data) {
				$("#addr_table tbody").children().remove();
				$.each(data, function(i){
					$("#addr_table tbody").prepend("<tr><td>"+data[i].empno+"</td><td>"+data[i].empnm+"</td><td>"+data[i].birth+"</td><td>"+data[i].email+"</td><td>"+data[i].jumin+"</td></tr>");
				})
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert(xhr.status+" : "+thrownError);
			}
		}); 
	});
	
	var open_addr = $("#btnaddr").on("click", function(){
		$("#addr_table tbody").children().remove();
		dl.dialog("open");
	});

	
//	여기까지 주소 찾기 모달 폼
	
//  ************* 사진 업로드 모달 폼 ******************
	
	var pic, open_pic
	pic = $( "#modalPicDiv" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			Cancel: function() {
				pic.dialog( "close" );
			}
		},
		close: function() {
			form[ 0 ].reset();
			allFields.removeClass( "ui-state-error" );
		}
	});

	
/*    $("#picSend").click(function(){
  	  var formData = new FormData($("#picForm"));
  	  formData.append("cmd", $("input[name=cmd]").val());
  	  formData.append("pic_empno", $("input[name=pic_empno]").val());
  	  formData.append("picFile", $("input[name=picFile]")[0].files[0]);
  	 
  	  $.ajax({
  		type: "post",
  	    url: "neviGo",
  	    data: formData,
  	    processData: false,
  	    contentType: false,
  	    type: 'POST',
  	    success: function(data){
  	    	alert("EE");
  	    }
  	  });

    });*/
	
	var frmData=$("#picForm").serialize();
	$("#picForm").ajaxForm({
		type: "post",
		url: "neviGo?cmd=picUpload",
//		enctype: "multipart/form-data",
		data: frmData,
		dataType: "json",
		beforeSubmit: function (data, frm, opt) {
             alert("전송전!!");
        },		
		success : function(data) {
			alert(data);
		},
		error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.status+" : "+thrownError);
		}
	});

	
	open_pic = $("#btnPic").on("click", function(){
		pic.dialog("open");
	});
	
//  ************* 사진 업로드 모달 폼 끝*****************	
    $( "#tabs" ).tabs({
    	event:"mouseover"
    });
	
	$("#indt, #outdt").datepicker();
	
	$("#birth").datepicker({
		changeMonth: true,
		changeYear: true
	});
	
    $("#indt").datepicker("option", "maxDate", $("#outdt").val());
    $("#indt").datepicker("option", "onClose", function ( selectedDate ) {
        $("#outdt").datepicker( "option", "minDate", selectedDate );
    });

    $("#outdt").datepicker("option", "minDate", $("#indt").val());
    $("#outdt").datepicker("option", "onClose", function ( selectedDate ) {
        $("#indt").datepicker( "option", "maxDate", selectedDate );
	});
    
	$("#mobile").mask("999-9999-9999");
	$("#jumin").mask("999999-9999999");	

	$("#regForm").validate({
		rules: {
		  passwd: {required: true, minlength: 5, maxlength: 12},
		  confirm_passwd:{required: true, minlength: 5, maxlength: 12, equalTo: "#passwd"},
		  empnm: {required: true, maxlength: 10 },
		  deptno: {required: true},
		  positno: {required: true},
		  upperno: {required: true},			  
		  email: {email: true}
		},
		messages: {
		  passwd: {required: "필수", minlength: "5자리 이상", maxlength: "12자리까지" },
		  confirm_passwd: {required: "필수", minlength: "5자리 이상", maxlength: "12자리까지", equalTo: "불일치"},
		  empnm: {required: "필수", maxlength: "10자리까지" },
		  deptno: {required: "필수"},
		  positno: {required: "필수"},
		  upperno: {required: "필수"},
		  email: {email: "오류"},
		}
	 });
	
	// submit
	var options = {
		url : 'neviGo?cmd=empInUp',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:""});
			return $("#regForm").valid();
		},
	
		success : function(responseText,statusText,xhr){
			$("#regForm").clearForm();
			$("#inup").val("in");
			$("#btnSubmit").val("등록");
			//$("#btnRefresh").hide();
			$.unblockUI();
			
			if (responseText=="0"){
				alert("등록실패");
			} else if (responseText=="1"){
				alert("등록완료");
			}

			$("#user_list").trigger("reloadGrid");
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#regForm").ajaxForm(options);
	
	$("#btnRefresh").bind("click", function(){
		$("#regForm").clearForm();
		$("#inup").val("in");
		$("#btnSubmit").val("등록");		
		$("#regForm_left > p > label.error").hide();
		$("#regForm_right > p > label.error").hide();			
		$("#deptcd").val("");
		$("#positcd").val("");
	});		

    $("#user_list").jqGrid({
        url:"neviGo?cmd=empJsonList",
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
			var gsr = $("#user_list").jqGrid('getGridParam','selrow');
			$("#user_list").jqGrid('GridToForm',gsr,"#regForm");
			var ret = $("#user_list").getRowData( ids );
			$("#pic_empno").val($.trim(ret.empno));
			$("#jumin").val($.trim(ret.jumin));
			$("#indt").val($.trim(ret.indt));
			$("#outdt").val($.trim(ret.outdt));
			$("#mobile").val($.trim(ret.mobile));
			$("#birth").val($.trim(ret.birth));
//			$("#passwd").hide();
//			$("#confirm_passwd").hide();
			
			$("#inup").val("up");
			$("#btnSubmit").val("수정");
        }
    });
   
    $("#user_list").jqGrid.navGrid("#pager",{add:false,edit:false,del:false, search:false});	
});

function doSearch(ev){
	var timeoutHnd;
	if(timeoutHnd)
		clearTimeout(timeoutHnd)
	timeoutHnd = setTimeout(gridReload,100)
}

function gridReload(){
	var sh_empno = $("#sh_empno").val();
	var sh_empnm = $("#sh_empnm").val();
	var sh_indt_st = $("#sh_indt_st").val();
	var sh_indt_ed = $("#sh_indt_ed").val();
	$("#user_list").jqGrid('setGridParam',{url:"empListJson.do?sh_empno="+sh_empno+"&sh_empnm="+sh_empnm+"&sh_indt_st="+sh_indt_st+"&sh_indt_ed="+sh_indt_ed,page:1}).trigger("reloadGrid");
}