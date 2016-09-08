$(document).ready(function(){
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
	

	
// ***************** 사진 삭제 *************************
	$("#btnPicDel").on("click", function(){
		/*$( "#modalPicDel" ).dialog({
			autoOpen: false,
	        height: "auto",
	        width: 400,
	        modal: true,
	        buttons: {
	          "삭제하기": function() {
       
	          },
	          Cancel: function() {
	            $( this ).dialog( "close" );
	          }
	        }
	      });*/
  		var empno=$("#empno").val();
		if (empno==""){
			alertMsg("사진삭제", "사원을 선택해 주세요...");
			return false;	
		}
		$.ajax({
			type: "post",
			url: "neviGo?cmd=picDelete",
			data: {empno:empno},
			dataType: "json",
			success : function(result) {
				if(result == 1){
					$("#picture > img").attr("src","images/noimage_pic.gif");
					$("#user_list").trigger("reloadGrid");
					$("#btnPicDel").hide();
					$( this ).dialog( "close" );
				} else {
					alertMsg("사진삭제",  "삭제실패");
				}
			},
		    error:function(request,status,error){
		        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
		});	
	});
	
// ***************** 사진 삭제 끝*************************	

    $( "#tabs" ).tabs({
    	event:"mouseover"
    });

    $("#btnPicDel").hide();
    
    $(".btnwht").css("width", "80");
	$("#indt, #outdt, #sh_indt_st, #sh_indt_ed").datepicker();
	
	$("#birth, #sch_startdt, #sch_enddt, #career_startdt, #career_enddt, #aqdt, #cert_renewstdt, #cert_reneweddt").datepicker({
		changeMonth: true,
		changeYear: true,
		yearRange: '1950:2020'
	});
	
	setStEd_picker("#indt", "#outdt");
	setStEd_picker("#sh_indt_st", "#sh_indt_ed");	
	setStEd_picker("#sch_startdt", "#sch_enddt");
	setStEd_picker("#career_startdt", "#career_enddt");
	setStEd_picker("#cert_renewstdt, #cert_reneweddt");	

    
	$("#mobile").mask("999-9999-9999");
	$("#jumin").mask("999999-9999999");	

	
//	********** 사원 등록/수정 부분 ************ 
	$("#regForm").validate({
		rules: {
		  passwd: {required: true, minlength: 5, maxlength: 12},
		  confirm_passwd:{required: true, minlength: 5, maxlength: 12, equalTo: "#passwd"},
		  empnm: {required: true, maxlength: 10 },
		  deptno: {required: true},
		  positno: {required: true},
		  email: {email: true}
		},
		messages: {
		  passwd: {required: "필수", minlength: "5자리 이상", maxlength: "12자리까지" },
		  confirm_passwd: {required: "필수", minlength: "5자리 이상", maxlength: "12자리까지", equalTo: "불일치"},
		  empnm: {required: "필수", maxlength: "10자리까지" },
		  deptno: {required: "필수"},
		  positno: {required: "필수"},
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
//	********** 사원 등록/수정 부분 끝 ************	
	
//	********** 학력 등록/수정 부분 *************
	$("#schForm").validate({
		rules: {
		  sch_startdt: {required: true, dateISO: true},
		  sch_enddt:{required: true, dateISO: true},
		  school: {required: true },
		  major: {required: true}
		},
		messages: {
			sch_startdt: {required: "필수", dateISO: "형식오류"},
			sch_enddt: {required: "필수", dateISO: "형식오류"},
			school: {required: "필수"},
			major: {required: "필수"}
		}
	 });
	
	// submit
	var options = {
		url : 'neviGo?cmd=schInup',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:""});
			return $("#schForm").valid();
		},
	
		success : function(responseText,statusText,xhr){
			var empno = $("#sch_empno").val();
			$("#schForm").clearForm();
			$("#sch_inup").val("in");
			$("#btnSchSubmit").val("등록");
			//$("#btnSchRefresh").hide();
			$.unblockUI();
			
			if (responseText=="0"){
				alertMsg("학력 목록", "등록/수정 실패");
			} else if (responseText=="1"){
				alertMsg("학력 목록", "등록 완료");
			} else if (responseText=="2"){
				alertMsg("학력 목록", "수정 완료");
			}
			$("#sch_empno").val(empno);
			$("#sch_list").trigger("reloadGrid");
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#schForm").ajaxForm(options);
//	********** 학력 등록/수정 부분 끝*************
	
	
	
//	********** 경력 등록/수정 부분 *************
	$("#careerForm").validate({
		rules: {
		  career_startdt: {required: true, dateISO: true},
		  career_enddt:{required: true, dateISO: true},
		  compnm: {required: true },
		  positnm: {required: true}
		},
		messages: {
			career_startdt: {required: "필수", dateISO: "형식오류"},
			career_enddt: {required: "필수", dateISO: "형식오류"},
			compnm: {required: "필수"},
			positnm: {required: "필수"}
		}
	 });
	
	// submit
	var options = {
		url : 'neviGo?cmd=careerInup',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:""});
			return $("#careerForm").valid();
		},
	
		success : function(responseText,statusText,xhr){
			var empno = $("#career_empno").val();
			$("#careerForm").clearForm();
			$("#career_inup").val("in");
			$("#btncareerSubmit").val("등록");
			//$("#btncareerRefresh").hide();
			$.unblockUI();
			
			if (responseText=="0"){
				alertMsg("경력 목록", "등록/수정 실패");
			} else if (responseText=="1"){
				alertMsg("경력 목록", "등록 완료");
			} else if (responseText=="2"){
				alertMsg("경력 목록", "수정 완료");
			}
			$("#career_empno").val(empno);
			$("#career_list").trigger("reloadGrid");
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#careerForm").ajaxForm(options);	
//	********** 경력 등록/수정 부분 끝************

	
//	********** 자격증 등록/수정 부분 *************

	$("#certForm").validate({
		rules: {
		  certno:{required: true},
		  certnm:{required: true},
		  aqdt:{required: true, dateISO: true},
		  issuorgan:{required: true},
		  cert_renewstdt: {required: true, dateISO: true},
		  cert_reneweddt:{required: true, dateISO: true}
		},
		messages: {
		    certno:{required: "필수"},
		    certnm:{required: "필수"},			
			aqdt: {required: "필수", dateISO: "형식오류"},
			issuorgan:{required: "필수"},			
			cert_renewstdt: {required: "필수", dateISO: "형식오류"},
			cert_reneweddt: {required: "필수", dateISO: "형식오류"}
		}
	 });
	
	// submit
	var options = {
		url : 'neviGo?cmd=certInup',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:""});
			return $("#certForm").valid();
		},
	
		success : function(responseText,statusText,xhr){
			var empno = $("#cert_empno").val();
			$("#certForm").clearForm();
			$("#cert_inup").val("in");
			$("#btncertSubmit").val("등록");
			//$("#btncertRefresh").hide();
			$.unblockUI();
			
			if (responseText=="0"){
				alertMsg("경력 목록", "등록/수정 실패");
			} else if (responseText=="1"){
				alertMsg("경력 목록", "등록 완료");
			} else if (responseText=="2"){
				alertMsg("경력 목록", "수정 완료");
			}
			$("#cert_empno").val(empno);
			$("#cert_list").trigger("reloadGrid");
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#certForm").ajaxForm(options);
	
//	********** 자격증 등록/수정 부분 끝************	
	
	$("#btnRefresh").bind("click", function(){
		$(".pass").show();		
		$("#regForm").clearForm();
		$("#inup").val("in");
		$("#btnSubmit").val("등록");		
		$("#regForm label.error").hide();
		$("#deptcd").val("");
		$("#positcd").val("");
		$("#picture > img").attr("src","images/noimage_pic.gif");
	});		

	$("#btnSchRefresh").bind("click", function(){
		var empno = $("#sch_empno").val();
		$("#schForm").clearForm();
		$("#schForm label.error").hide();		
		$("#inup_sch").val("in");
		$("#btnSchSubmit").val("등록");
		$("#sch_empno").val(empno);
	});	
	
	$("#btnCareerRefresh").bind("click", function(){
		var empno = $("#career_empno").val();
		$("#careerForm").clearForm();
		$("#careerForm label.error").hide();		
		$("#inup_career").val("in");
		$("#btnCareerSubmit").val("등록");
		$("#career_empno").val(empno);
	});	
	
	$("#btnCertRefresh").bind("click", function(){
		var empno = $("#cert_empno").val();
		$("#certForm").clearForm();
		$("#certForm label.error").hide();		
		$("#inup_cert").val("in");
		$("#btnCertSubmit").val("등록");
		$("#cert_empno").val(empno);
	});	
	
	var sub_upyn;
	
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
			$("#sch_empno").val(ret.empno);
			$("#career_empno").val(ret.empno);	
			$("#cert_empno").val(ret.empno);
			
			if (ret.pic == ""){
				$("#btnPicDel").hide();
				$("#picture > img").attr("src","images/noimage_pic.gif");
			} else if (ret.pic != "") {
				$("#btnPicDel").show();
				$("#picture > img").attr("src","upload/"+ret.pic);
			}
			
			if (ret.upyn == "o") {
				sub_upyn = "o";
				$("#btnPic").show();
				$("#btnSubmit").show();
				$("#inup").val("up");
				$("#btnSubmit").val("수정");
				$("#btnRefresh").show();				
				$(".pay").show();
				$("#tabs").show();				
			} else if (ret.upyn == "i"){
				sub_upyn = "o";
				$("#btnPic").show();
				$("#btnSubmit").show();
				$("#inup").val("up");
				$("#btnSubmit").val("수정");
				$(".pay").show();
				$("#tabs").show();	
				$("#btnRefresh").hide();
			} else {
				sub_upyn = "x";
				$("#tabs").hide();
				$("#btnPic").hide();
				$("#btnPicDel").hide();
				$("#btnSubmit").hide();
				$(".pay").hide();
			}

			
			$("#sch_list").jqGrid('setGridParam',{url:"neviGo?cmd=schList&sh_empno="+ret.empno,page:1}).trigger('reloadGrid');
		    $("#gview_sch_list > .ui-jqgrid-titlebar").css("display", "none");
		    
			$("#career_list").jqGrid('setGridParam',{url:"neviGo?cmd=careerList&sh_empno="+ret.empno,page:1}).trigger('reloadGrid');
		    $("#gview_career_list > .ui-jqgrid-titlebar").css("display", "none");		    

			$("#cert_list").jqGrid('setGridParam',{url:"neviGo?cmd=certList&sh_empno="+ret.empno,page:1}).trigger('reloadGrid');
		    $("#gview_cert_list > .ui-jqgrid-titlebar").css("display", "none");	
		    
			$("#empskill_list").jqGrid('setGridParam',{url:"neviGo?cmd=empSkillList&sh_empno="+ret.empno,page:1}).trigger('reloadGrid');
		    $("#gview_empskill_list > .ui-jqgrid-titlebar").css("display", "none");

			$("#skill_list").jqGrid('setGridParam',{url:"neviGo?cmd=skillList",page:1}).trigger('reloadGrid');
		    $("#gview_skill_list > .ui-jqgrid-titlebar").css("display", "none");		    
        }
    });
    $("#user_list").jqGrid("navGrid","#pager",{add:false,edit:false,del:false, search:false});
   
    
    $("#sch_list").jqGrid({
        url:"neviGo?cmd=schList",
        mtype:"post",
        datatype:"json",
        caption:"학력",
        height:"200",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["schno","empno","startdt","enddt","학업기간","학교명","전공"],
        colModel:[
                  {name:"schno", index:"schno", hidden:true}, 
                  {name:"empno", index:"empno", hidden:true}, 
                  {name:"startdt", index:"startdt", hidden:true},           
                  {name:"enddt", index:"enddt", hidden:true},
                  {name:"period", index:"period", align:"center", sortable:false},
                  {name:"school", index:"school", align:"center", sortable:false},                         
                  {name:"major", index:"major", align:"center", sortable:false}               
                  ],
        pager:"#sch_pager",
        autowidth:true,
        viewrecords:true,
        onSelectRow: function(ids) {  
			var gsr = $("#sch_list").jqGrid('getGridParam','selrow');
			$("#sch_list").jqGrid('GridToForm',gsr,"#schForm");
			var ret = $("#sch_list").getRowData( ids );
			$("#sch_startdt").val($.trim(ret.startdt));
			$("#sch_enddt").val($.trim(ret.enddt));			

			if (sub_upyn == "o") {
				$("#sch_bottom").show();
				$("#btnSchSubmit").show();
				$("#btnSchRefresh").show();
				$("#inup_sch").val("up");
				$("#btnSchSubmit").val("수정");
			} else {
				$("#sch_bottom").hide();
			}
        }        
    });
    $("#sch_list").jqGrid("navGrid","#sch_pager",{add:false,edit:false,del:false, search:false});
    $("#gview_sch_list > .ui-jqgrid-titlebar").css("display", "none");


    $("#career_list").jqGrid({
        url:"neviGo?cmd=careerList",
        mtype:"post",
        datatype:"json",
        caption:"경력",
        height:"200",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["careerno","empno","startdt","enddt","경력기간","직장명","직위","직무"],
        colModel:[
                  {name:"careerno", index:"careerno", hidden:true}, 
                  {name:"empno", index:"empno", hidden:true}, 
                  {name:"startdt", index:"startdt", hidden:true},           
                  {name:"enddt", index:"enddt", hidden:true},
                  {name:"period", index:"period", width: 210, align:"center", sortable:false},
                  {name:"compnm", index:"compnm", align:"center", sortable:false},                         
                  {name:"positnm", index:"positnm", align:"center", sortable:false},
                  {name:"charge", index:"charge", align:"center", sortable:false}, 
                  ],
        pager:"#career_pager",
        autowidth:true,
        //width: 660,
        viewrecords:true,
        onSelectRow: function(ids) {  
			var gsr = $("#career_list").jqGrid('getGridParam','selrow');
			$("#career_list").jqGrid('GridToForm',gsr,"#careerForm");
			var ret = $("#career_list").getRowData( ids );
			$("#career_startdt").val($.trim(ret.startdt));
			$("#career_enddt").val($.trim(ret.enddt));			

			if (sub_upyn == "o") {
				$("#career_bottom").show();
				$("#btncareerSubmit").show();
				$("#btncareerRefresh").show();
				$("#inup_career").val("up");
				$("#btncareerSubmit").val("수정");
			} else {
				$("#career_bottom").hide();
			}
        }        
    });
    $("#career_list").jqGrid("navGrid","#career_pager",{add:false,edit:false,del:false, search:false});
    $("#gview_career_list > .ui-jqgrid-titlebar").css("display", "none");
    

    $("#cert_list").jqGrid({
        url:"neviGo?cmd=certList",
        mtype:"post",
        datatype:"json",
        caption:"자격증",
        height:"200",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["cno","empno","증번호","자격증명","취득일","발급기관","renewstdt","reneweddt","갱신일"],
        colModel:[
                  {name:"cno", index:"cno", hidden:true}, 
                  {name:"empno", index:"empno", hidden:true}, 
                  {name:"certno", index:"certno", align:"center", sortable:false},
                  {name:"certnm", index:"certnm", align:"center", sortable:false},
                  {name:"aqdt", index:"aqdt", align:"center", sortable:false},  
                  {name:"issuorgan", index:"issuorgan", align:"center", sortable:false},                    
                  {name:"renewstdt", index:"renewstdt", hidden:true},           
                  {name:"reneweddt", index:"reneweddt", hidden:true},
                  {name:"period", index:"period", width: 210, align:"center", sortable:false}
                  ],
        pager:"#cert_pager",
//        autowidth:true,
        width: 660,
        viewrecords:true,
        onSelectRow: function(ids) {  
			var gsr = $("#cert_list").jqGrid('getGridParam','selrow');
			$("#cert_list").jqGrid('GridToForm',gsr,"#certForm");
			var ret = $("#cert_list").getRowData( ids );
			$("#aqdt").val($.trim(ret.aqdt));
			$("#cert_renewstdt").val($.trim(ret.renewstdt));
			$("#cert_reneweddt").val($.trim(ret.reneweddt));			

			if (sub_upyn == "o") {
				$("#cert_bottom").show();
				$("#btncertSubmit").show();
				$("#btncertRefresh").show();
				$("#inup_cert").val("up");
				$("#btncertSubmit").val("수정");
			} else {
				$("#cert_bottom").hide();
			}
        }        
    });
    $("#cert_list").jqGrid("navGrid","#cert_pager",{add:false,edit:false,del:false, search:false});
    $("#gview_cert_list > .ui-jqgrid-titlebar").css("display", "none");    
    
    $("#empskill_list").jqGrid({
        url:"neviGo?cmd=empSkillList",
        mtype:"post",
        datatype:"json",
        caption:"보유기술",
        height:"250",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["empno","ctno","분류명","skillno","보유스킬명","삭제"],
        colModel:[
                  {name:"empno", index:"empno", hidden:true}, 
                  {name:"ctno", index:"ctno", hidden:true},           
                  {name:"ctnm", index:"ctnm", width: 130, align:"center", sortable:false},
                  {name:"skillno", index:"skillno", hidden:true},                    
                  {name:"skillnm", index:"skillnm", width: 140, align:"center", sortable:false},             
                  {name:'del', index:'del', align:"center", width:"50", sortable:false, formatter:formatOpt2}
                  ],
        pager:"#empskill_pager",
        autowidth:true,
        viewrecords:true,
		sortname: "skillno",
		viewrecords: false,
		sortorder: "asc"        
    });
    $("#empskill_list").jqGrid("navGrid","#empskill_pager",{add:false,edit:false,del:false, search:false});
    $("#gview_empskill_list > .ui-jqgrid-titlebar").css("display", "none");
    
    $("#skill_list").jqGrid({
        url:"neviGo?cmd=skillList",
        mtype:"post",
        datatype:"json",
        caption:"기술목록",
        height:"250",
        rowNum:10,
        rowList:[3,5,10,15,20],
        colNames:["ctno","분류명","skillno","스킬명", "선택"],
        colModel:[
                  {name:"ctno", index:"ctno", hidden:true},           
                  {name:"ctnm", index:"ctnm", align:"center", width: 130, sortable:false},
                  {name:"skillno", index:"skillno", hidden:true},                    
                  {name:"skillnm", index:"skillnm", align:"center", width: 150, sortable:false},
                  {name:'choice', index:'choice', align:"center", width:"50", sortable:false, formatter:formatOpt1}
                  ],
        pager:"#skill_pager",
        autowidth:true,
        viewrecords:true,
		sortname: "skillno",
		viewrecords: false,
		sortorder: "asc"           
    });
    $("#skill_list").jqGrid("navGrid","#skill_pager",{add:false,edit:false,del:false, search:false});  
    $("#gview_skill_list > .ui-jqgrid-titlebar").css("display", "none");
    
    $("#skill_list a").on("click", function(e){
         e.preventDefault();
    });
        
    function formatOpt1(cellvalue, options, rowObject){ 
    	var str = "";
    	var skillno = rowObject.skillno;	
    	str += "<span  onclick=\"choice('"+ skillno +"')\">선택</span>";
      	return str;
    }    

    function formatOpt2(cellvalue, options, rowObject){ 
    	var str = "";
    	var empno = rowObject.empno;
    	var skillno = rowObject.skillno;	
    	str += "<span  onclick=\"empskilldel('"+ empno +"', '"+ skillno +"')\">삭제</span>";
      	return str;
    }        
});

function choice(skillno){
		empno = $("#sch_empno").val();
		$.ajax({
		  type: "POST",
		  url: "neviGo?cmd=empSkillInsert",
		  data: {gubun:"insert", empno:empno,skillno:skillno},
		  dataType: "json",
		  beforeSend:function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:null});
		  },
		  success:function(data){
			if (data == 1){
				$("#empskill_list").jqGrid('setGridParam',{url:"neviGo?cmd=empSkillList&sh_empno="+empno,page:1}).trigger('reloadGrid');
			}
		  }, 
		  error: function (jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		  },
          complete: function(){
            $.unblockUI();
          }
		});

}

function empskilldel(empno, skillno){
	$.ajax({
	  type: "POST",
	  url: "neviGo?cmd=empSkillInsert",
	  data: {gubun:"del", empno:empno,skillno:skillno},
	  dataType: "json",
	  beforeSend:function(){
		$.blockUI({overlayCSS:{opacity:0.0}, message:null});
	  },
	  success:function(data){
		if (data == 1){
			$("#empskill_list").jqGrid('setGridParam',{url:"neviGo?cmd=empSkillList&sh_empno="+empno,page:1}).trigger('reloadGrid');
		}
	  }, 
	  error: function (jqXHR, textStatus, errorThrown) {
		alert(errorThrown);
	  },
      complete: function(){
        $.unblockUI();
      }
	});

}


function doSearch(ev){
	var timeoutHnd;
	if(timeoutHnd)
		clearTimeout(timeoutHnd)
	timeoutHnd = setTimeout(gridReload,100)
}

function gridReload(){
	var sh_empno = $("#sh_empno").val();
	var sh_empnm = $("#sh_empnm").val();
	var sh_indt_st = $("#sh_indt_st").val().replace(/-/g, '');
	var sh_indt_ed = $("#sh_indt_ed").val().replace(/-/g, '');
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





