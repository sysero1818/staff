<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../common/jscss1.jsp" %>
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function(){
	$("#user_list").jqGrid({
	    url:"neviGo?cmd=empJsonList",
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
	              {name:"upyn", index:"upyn"}                 
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
			$("#sch_empno").val(ret.empno);
			
			if (ret.pic == ""){
				$("#picture > img").attr("src","images/noimage_pic.gif");
			} else if (ret.pic != null) {
				$("#picture > img").attr("src","upload/"+ret.pic);
			}
			
		
			$("#sch_list").jqGrid('setGridParam',{url:"neviGo?cmd=schList&sh_empno="+ret.empno,page:1}).trigger('reloadGrid');
		    $("#gview_sch_list > .ui-jqgrid-titlebar").css("display", "none");
		    
			//$("#empskill_list").jqGrid('setGridParam',{url:"neviGo?cmd=empSkillList&sh_empno="+ret.empno,page:1}).trigger('reloadGrid');
		    //$("#gview_empskill_list > .ui-jqgrid-titlebar").css("display", "none");
	
			//$("#skill_list").jqGrid('setGridParam',{url:"neviGo?cmd=skillList",page:1}).trigger('reloadGrid');
		    //$("#gview_skill_list > .ui-jqgrid-titlebar").css("display", "none");		    
	    }
	});
	$("#user_list").jqGrid("navGrid","#pager",{add:false,edit:false,del:false, search:false});
	
	
	$("#sch_list").jqGrid({
	    url:"neviGo?cmd=schList",
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
	    sortname: "schno",
	    sortorder: "desc",	    
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
	
	$("#empskill_list").jqGrid({
	    url:"neviGo?cmd=empSkillList",
	    mtype:"post",
	    datatype:"json",
	    caption:"보유기술",
	    height:"200",
	    rowNum:10,
	    rowList:[5,10,15,20],
	    colNames:["empno","ctno","분류명","skillno","스킬명"],
	    colModel:[
	              {name:"empno", index:"empno", hidden:true}, 
	              {name:"ctno", index:"ctno", hidden:true},           
	              {name:"ctnm", index:"ctnm", align:"center", sortable:false},
	              {name:"skillno", index:"skillno", hidden:true},                    
	              {name:"skillnm", index:"skillnm", align:"center", sortable:false}              
	              ],
	    pager:"#empskill_pager",
	    autowidth:true,
	    viewrecords:true,
	    sortname: "skillno",
	    sortorder: "desc"
	});
	$("#empskill_list").jqGrid("navGrid","#empskill_pager",{add:false,edit:false,del:false, search:false});	
	
	$("#skill_list").jqGrid({
	    url:"neviGo?cmd=skillList",
	    mtype:"post",
	    datatype:"json",
	    caption:"기술목록",
	    height:"200",
	    rowNum:10,
	    rowList:[5,10,15,20],
	    colNames:["ctno","분류명","skillno","스킬명"],
	    colModel:[
	              {name:"ctno", index:"ctno", hidden:true},           
	              {name:"ctnm", index:"ctnm", align:"center", sortable:false},
	              {name:"skillno", index:"skillno", hidden:true},                    
	              {name:"skillnm", index:"skillnm", align:"center", sortable:false}              
	              ],
	    pager:"#skill_pager",
	    autowidth:true,
	    viewrecords:true,
	    sortname: "skillno",
	    sortorder: "desc"	    
	});
	$("#skill_list").jqGrid("navGrid","#skill_pager",{add:false,edit:false,del:false, search:false});
});
</script>
</head>
<body>
  		<div id="table_body">
			<table id="user_list"></table>
  			<div id="pager"></div>
  		</div>
   		<div id="sch_table">
			<table id="sch_list"></table>
   			<div id="sch_pager"></div>
   		</div>  
   		<div id="empskill_table">
			<table id="empskill_list"></table>
   			<div id="empskill_pager">jkj</div>
   		</div>
   		<div id="skill_table">
			<table id="skill_list"></table>
   			<div id="skill_pager">jj</div>    		
   		</div>	   				
</body>
</html>