package com.kedu.notice.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.notice.dao.NoticeDao;
import com.kedu.notice.dto.NotidatDto;

public class NotidatDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		int seq = 0;
		int dseq = 0;
		
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
//		String manager	= (String) session.getAttribute("manager");
		
		if (request.getParameter("seq") != null && request.getParameter("seq").trim().length() != 0){
			seq = Integer.parseInt(request.getParameter("seq"));
		}

		if (request.getParameter("dseq") != null && request.getParameter("dseq").trim().length() != 0){
			dseq = Integer.parseInt(request.getParameter("dseq"));
		}		
		
		NoticeDao nDao = NoticeDao.getInstance();
		result = nDao.deleteNotidat(seq, dseq, ss_empno);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(result);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();		
	}

}
