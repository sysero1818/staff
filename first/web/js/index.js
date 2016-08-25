$(document).ready(function(){
    $( "#tabs" ).tabs();
	
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
		  empno: {required: true, minlength: 5, maxlength: 10},
		  passwd: {required: true, minlength: 5, maxlength: 20},
		  empname: {required: true, maxlength: 10 },
		  deptcd: {required: true},
		  positcd: {required: true},
		  upperid: {required: true},			  
		  email: {email: true}
		},
		messages: {
		  empno: {required: "필수", minlength: "5자리 이상", maxlength: "10자리까지" },
		  passwd: {required: "필수", minlength: "5자리 이상", maxlength: "20자리까지" },
		  empname: {required: "필수", maxlength: "10자리까지" },
		  deptcd: {required: "필수"},
		  positcd: {required: "필수"},
		  upperid: {required: "필수"},
		  email: {email: "오류"},
		}
	 });
	
	// submit
	var options = {
		url : 'projectList.html',
		type : 'POST',
		beforeSubmit: function(){
			$.blockUI({overlayCSS:{opacity:0.0}, message:null});
			return $("#regForm").valid();
			alert("a");
		},
	
		success : function(responseText,statusText,xhr){
			$("#regForm").clearForm();
			$("#inup").val("in");
			$("#btnSubmit").val("등록");
			$("#btnRefresh").hide();
			$.unblockUI();
	
			if (responseText!=""){
				alert(responseText);
			}
		}, 
		error: function (jqXHR, textStatus, errorThrown) {
			$.unblockUI();
			alert(errorThrown);
		}
	}
	$("#regForm").ajaxForm(options);
	
	$("#btnRefresh").bind("click", function(){
		$("#regForm").clearForm();
		$("#regForm_left > p > label.error").hide();
		$("#regForm_right > p > label.error").hide();			
		$("#deptcd").val("");
		$("#positcd").val("");
	});		

    $("#user_list").jqGrid({
        url:"memberList.do",
        mtype:"post",
        datatype:"json",
        caption:"사원 목록",
        height:"auto",
        rowNum:10,
        rowList:[3,5,10,15,20],
        colNames:["name","userid","pwd","email","phone","amdin"],
        colModel:[
                  {name:"name", index:"name", align:"center"},
                  {name:"userid", index:"userid", align:"center"},
                  {name:"pwd", index:"pwd", align:"center"},                          
                  {name:"email", index:"email", align:"center"},
                  {name:"phone", index:"phone", align:"center"},                          
                  {name:"amdin", index:"amdin", align:"center"}
                  ],
        pager:"#pager",
        autowidth:true,
        //width: auto,
        viewrecords:true
        //editurl: "/UserEditAction"
    });
   
    $("#user_list").jqGrid.navGrid("#pager",{add:false,edit:false,del:false, search:false});	
});