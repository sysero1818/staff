package com.kedu.notice.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.notice.dao.NoticeDao;
import com.kedu.notice.dto.NoticeDto;

public class NoticeViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int seq = 0;
		
		if(request.getParameter("seq").trim()!=null && request.getParameter("seq").trim().length() != 0){
			seq = Integer.parseInt(request.getParameter("seq").trim());
		}	
		
		NoticeDao nDao = NoticeDao.getInstance();
		NoticeDto nDto = nDao.selectOneBySeq(seq);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(nDto);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();	
	}

}
