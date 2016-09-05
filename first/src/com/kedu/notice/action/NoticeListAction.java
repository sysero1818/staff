package com.kedu.notice.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.common.GridJson;
import com.kedu.notice.dao.NoticeDao;
import com.kedu.notice.dto.NoticeDto;

public class NoticeListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int pageSize = 5;
		String sh_title = "";
		
		try {
			if(request.getParameter("page").trim()!=null && request.getParameter("page").trim().length() != 0){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			if(request.getParameter("sh_title").trim()!=null && request.getParameter("sh_title").trim().length() != 0){
				sh_title = request.getParameter("sh_title").trim();
			}
			
		} catch (Exception e) {

		}
	
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");
		
		NoticeDao nDao = NoticeDao.getInstance();
		List<NoticeDto> boardList = nDao.selectAllNotices(page, pageSize, sh_title, ss_empno, manager);
		
		int records = nDao.getCountRow(sh_title);
		int total = (int)Math.ceil((double)records/(double)pageSize);
		GridJson<NoticeDto> empJson = new GridJson<NoticeDto>();
		empJson.setTotal(total);
		empJson.setRecords(records);
		empJson.setPage(page);
		empJson.setRows(boardList);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(empJson);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();		
	}

}

