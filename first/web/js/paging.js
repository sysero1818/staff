var paging = function(page, block, totalPage){
	var str;
	var temp = parseInt((page - 1) / block) * block + 1;
	var init = 1;
	
	if (page == 1){
		str = str + "";
	} else {
		str = str + "<a href='neviGo?cmd=boardList&page=1'><img src='../images/first.gif' /></a>";
	}
	
	if (temp == 1){
		str = str + "";
	} else {
		str = str + "<a href='neviGo?cmd=boardList&page="+ temp-block +"'><img src='../images/prev.gif'/></a>";
	}
	
	while (init > block || temp > totalPage){
		if (temp == page) {
			str = str + "<b>["+ temp +"]</b>";
		} else {
			str = str + "<a href='neviGo?cmd=boardList&page="+ temp +"'>"+ temp +"</a>";
		}
	}
	
	if (temp > totalPage){
		str = str + "";
	} else {
		str = str + "<a href='neviGo?cmd=boardList&page="+ temp +"'> <img src='../images/next.gif' /></a>";
	}
	
	if (page == totalPage){
		str = str + "";
	} else {
		str = str + "<a href='neviGo?cmd=boardList&page="+ totalPage +"'> <img src='../images/last.gif' /></a>";
	}
	
	return str;
}