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
import com.kedu.notice.dao.NoticeDao;
import com.kedu.notice.dto.NotidatDto;
public class NotidatListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int seq = 0;
		try {
			if(request.getParameter("seq").trim()!=null && request.getParameter("seq").trim().length() != 0){
				seq = Integer.parseInt(request.getParameter("seq").trim());
			}

		} catch (Exception e) {

		}
/*		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");*/
		
		NoticeDao nDao = NoticeDao.getInstance();
		List<NotidatDto> ndList = nDao.selectBySeqNotidatList(seq);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(ndList);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();	
	}

}
