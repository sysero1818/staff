$(document).ready(function(){
    $( "#tabs" ).tabs({
    	event:"mouseover"
    });

    $(".btnwht").css("width", "80");
	$("#startdt, #enddt, #sh_indt_st, #sh_indt_ed").datepicker();
	
	setStEd_picker("#startdt", "#enddt");
	setStEd_picker("#sh_indt_st", "#sh_indt_ed");	

	
//	********** 사원 등록/수정 부분 ************ 
	$("#regForm").validate({
		rules: {
		  prjnm: {required: true}

		},
		messages: {
			prjnm: {required: "필수"}

		}
	 });
	
	// submit
	var options = {
		url : 'neviGo?cmd=prjInUp',
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
	
	var sub_upyn;
	
    $("#user_list").jqGrid({
        url:"neviGo?cmd=prjList",
        mtype:"post",
        datatype:"json",
        caption:"사원 목록",
        height:"230",
        rowNum:10,
        rowList:[5,10,15,20],
        colNames:["프로젝트번호","프로젝트명","시작일","종료일","prjcontent","compno","compnm", "empno", "empnm", "upyn"],
        colModel:[
                  {name:"prjno", index:"prjno", align:'center', sortable:false}, 
                  {name:"prjnm", index:"prjnm", align:'center', sortable:false}, 
                  {name:"startdt", index:"startdt", align:'center', sortable:false}, 
                  {name:"enddt", index:"enddt", align:'center', sortable:false},
                  {name:"prjcontent", index:"prjcontent", hidden:true},
                  {name:"compno", index:"compno", hidden:true}, 
                  {name:"compnm", index:"compnm", hidden:true},
                  {name:"empno", index:"empno", hidden:true}, 
                  {name:"empnm", index:"empnm", hidden:true},
                  {name:"upyn", index:"upyn", hidden:true}     
                  ],
        pager:"#pager",
        autowidth:true,
        viewrecords:true,
		loadComplete: function () {
			//$("tr.jqgrow:odd").css("background", "#EAF5FE");
			//$("tr.jqgrow:even").css("background", "#FFFFFF");
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
			
			if (ret.pic == ""){
				$("#picture > img").attr("src","images/noimage_pic.gif");
			} else if (ret.pic != null) {
				$("#picture > img").attr("src","upload/"+ret.pic);
			}
			
			if (ret.upyn == "o") {
				sub_upyn = "o";
				$("#btnPic").show();
				$("#btnPicDel").show();				
				$("#btnSubmit").show();
				$("#inup").val("up");
				$("#btnSubmit").val("수정");
				$("#btnRefresh").show();				
				$(".pay").show();
				$("#tabs").show();				
			} else if (ret.upyn == "i"){
				sub_upyn = "o";
				$("#btnPic").show();
				$("#btnPicDel").show();				
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





