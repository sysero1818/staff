package com.kedu.notice.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.common.GridJson;
import com.kedu.notice.dao.BoardDao;
import com.kedu.notice.dto.BoardDto;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int pageSize = 5;

		try {
			if(request.getParameter("page").trim()!=null && request.getParameter("page").trim()!=""){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			
		} catch (Exception e) {

		}
		
        int startRowNo = ( (page-1) * pageSize ) + 1;
        int endRowNo = page * pageSize;
        
		BoardDao mDao = BoardDao.getInstance();
		List<BoardDto> boardList = mDao.selectAllBoards(startRowNo, endRowNo);
		
		int records = mDao.totalCount();
		int total = (int)Math.ceil((double)records/(double)pageSize);
		
		GridJson<BoardDto> empJson = new GridJson<BoardDto>();
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
