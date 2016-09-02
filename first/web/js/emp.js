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
		height: 600,
		width: 610,
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
	});	
	
    $("#addr_list").jqGrid({
        url:"neviGo?cmd=searchAddr",
        datatype:"json",
        caption:"주소 찾기",
        height:"250",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["seq","우편번호","주소", "번지"],
        colModel:[
                  {name:"seq", index:"seq", hidden:true}, 
                  {name:"zipcode", index:"zipcode", align:"center", sortable:false}, 
                  {name:"address1", index:"address1", align:"center", sortable:false}, 
                  {name:"address2", index:"address2", align:"center", sortable:false}                   
                  ],
        pager:"#addr_pager",
        sortname: "seq",        
        width: 575,
        viewrecords:true,
        onSelectRow: function(ids) {
        	var ret = $("#addr_list").getRowData( ids );
        	$("#md_zipseq").val(ret.seq);
        	$("#md_zipcode").val(ret.zipcode);
        	$("#md_basicad").val(ret.address1);
        }
    });
    $("#addr_list").jqGrid("navGrid","#addr_pager",{add:false,edit:false,del:false, search:false});

    $("#btnUseAddr").on("click", function(){
    	$("#zipseq").val($("#md_zipseq").val());
    	$("#zipcode").val($("#md_zipcode").val());
    	$("#basicad").val($("#md_basicad").val());
    	$("#addrSerarchForm").clearForm();
    	$("#mdAddrForm").clearForm();
    	$("#addr_list").jqGrid("clearGridData");
    	dl.dialog( "close" );
    });

    var open_addr = $("#btnaddr").on("click", function(){
		$("#addr_table tbody").children().remove();
		dl.dialog("open");
	});

	
//	여기까지 주소 찾기 모달 폼
	
//  ************* 사진 업로드 모달 폼 ******************

	var pic, open_pic, picform
	pic = $( "#modalPicDiv" ).dialog({
		autoOpen: false,
		height: 150,
		width: 350,
		modal: true,
		buttons: {
			Cancel: function() {
				pic.dialog( "close" );
			}
		},
		close: function() {
			$("#picForm").clearForm();
		}
	});


	var frmData=$("#picForm").serialize();
	$("#picForm").ajaxForm({
		type: "post",
		url: "neviGo?cmd=picUpload",
		data: frmData,
		dataType: "json",
		beforeSubmit: function () {
			if($("#pic_empno").val() == ""){
				alertMsg("사진수정", "사원을 선택해 주세요...");
				return false;
			}
			
			if($("#picFile").val() == ""){
				alertMsg("사진수정", "파일을 선택해 주세요...");
				return false;
			}
        },		
		success : function(data) {
			$("#picture > img").attr("src","upload/"+data.updateFile);
			$("#user_list").trigger("reloadGrid");
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

    $("#btnSubmit, #btnRefresh").css("width", "80");
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
				alertMsg("사원 목록", "등록/수정 실패");
			} else if (responseText=="1"){
				alertMsg("사원 목록", "등록 완료");
			} else if (responseText=="2"){
				alertMsg("사원 목록", "수정 완료");
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
		$(".pass").show();		
		$("#regForm").clearForm();
		$("#inup").val("in");
		$("#btnSubmit").val("등록");		
		$("#regForm_left > p > label.error").hide();
		$("#regForm_right > p > label.error").hide();			
		$("#deptcd").val("");
		$("#positcd").val("");
		$("#picture > img").attr("src","images/noimage_pic.gif");
	});		

    $("#user_list").jqGrid({
        url:"neviGo?cmd=empJsonList",
        mtype:"post",
        datatype:"json",
        caption:"사원 목록",
        height:"230",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["사원번호","사원명","pic","jumin","생일","zipseq","zipcode","basicad","detailad","전화","이메일","입사일","outdt","empgb","deptno","positno","payment","upyn"],
        colModel:[
                  {name:"empno", index:"empno", align:'center', sortable:false}, 
                  {name:"empnm", index:"empnm", align:'center', sortable:false}, 
                  {name:"pic", index:"pic", hidden:true},                         
                  {name:"jumin", index:"jumin", hidden:true},
                  {name:"birth", index:"birth", hidden:true},
                  {name:"zipseq", index:"zipseq", hidden:true},                         
                  {name:"zipcode", index:"zipcode", hidden:true},    
                  {name:"basicad", index:"basicad", hidden:true},                      
                  {name:"detailad", index:"detailad", hidden:true},                  
                  {name:"mobile", index:"mobile", align:'center', sortable:false}, 
                  {name:"email", index:"email", align:'center', sortable:false},
                  {name:"indt", index:"indt", align:'center', sortable:false}, 
                  {name:"outdt", index:"outdt", hidden:true},
                  {name:"empgb", index:"empgb", hidden:true}, 
                  {name:"deptno", index:"deptno", hidden:true},
                  {name:"positno", index:"positno", hidden:true}, 
                  {name:"payment", index:"payment", hidden:true},
                  {name:"upyn", index:"upyn", hidden:true}                  
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
			$("#pic_empno").val(ret.empno);
			$("#jumin").val($.trim(ret.jumin));
			$("#indt").val($.trim(ret.indt));
			$("#outdt").val($.trim(ret.outdt));
			$("#mobile").val($.trim(ret.mobile));
			$("#birth").val($.trim(ret.birth));
			$("#payment").val(set_comma(ret.payment));
			$(".pass").hide();
			
			if (ret.pic == ""){
				$("#picture > img").attr("src","images/noimage_pic.gif");
			} else if (ret.pic != null) {
				$("#picture > img").attr("src","upload/"+ret.pic);
			}
			
			if (ret.upyn == "o") {
				$("#btnPic").show();
				$("#btnPicDel").show();				
				$("#btnSubmit").show();
				$("#inup").val("up");
				$("#btnSubmit").val("수정");
				$(".pay").show();
			} else {
				$("#btnPic").hide();
				$("#btnPicDel").hide();
				$("#btnSubmit").hide();
				$(".pay").hide();
			}

			
			$("#sch_list").jqGrid('setGridParam',{url:"neviGo?cmd=searchAddr&dong="+ret.empnm,page:1});
			$("#sch_list").jqGrid('setCaption', "테스트").trigger('reloadGrid');
        }
    });
   
    $("#user_list").jqGrid("navGrid","#pager",{add:false,edit:false,del:false, search:false});
   
    
    /*$("#sch_list").jqGrid({
        url:"neviGo?cmd=searchAddr&dong=부평",
        mtype:"post",
        datatype:"json",
        caption:"주소 찾기",
        height:"300",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["seq","우편번호","시도","시군구","동","리","건물명","번지"],
        colModel:[
                  {name:"seq", index:"seq", hidden:true}, 
                  {name:"zipcode", index:"zipcode", align:"center", sortable:false}, 
                  {name:"sido", index:"sido", align:"center", sortable:false},                     
                  {name:"sigungu", index:"sigungu", align:"center", sortable:false},
                  {name:"dong", index:"dong", align:"center", sortable:false},
                  {name:"ri", index:"ri", align:"center", sortable:false},                         
                  {name:"bldg", index:"bldg", align:"center", sortable:false},                 
                  {name:"bungi", index:"bungi", align:"center", sortable:false}               
                  ],
        pager:"#sch_pager",
        autowidth:true,
        viewrecords:true
    });
    $("#sch_list").jqGrid.navGrid("#sch_pager",{add:false,edit:false,del:false, search:false});*/

    
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
	$("#user_list").jqGrid('setGridParam',{url:"neviGo?cmd=empJsonList&sh_empno="+sh_empno+"&sh_empnm="+sh_empnm+"&sh_indt_st="+sh_indt_st+"&sh_indt_ed="+sh_indt_ed,page:1}).trigger("reloadGrid");
}



function doAddr(ev){
	var timeoutHnd;
	if(timeoutHnd)
		clearTimeout(timeoutHnd)
	timeoutHnd = setTimeout(gridAddr,100)
}

function gridAddr(){
	var dong = $("#dong").val();
	$("#addr_list").jqGrid('setGridParam',{url:"neviGo?cmd=searchAddr&dong="+dong,page:1}).trigger("reloadGrid");
}    
