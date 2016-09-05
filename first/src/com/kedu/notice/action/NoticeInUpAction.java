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
import com.kedu.notice.dto.NoticeDto;

public class NoticeInUpAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
//		String sh_title = "";
//		int pageSize = 5;
		
		NoticeDto nDto = new NoticeDto();
		
		String inup = request.getParameter("inup");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");
		
		if (request.getParameter("seq") != null && request.getParameter("seq").trim().length() != 0){
			nDto.setSeq(Integer.parseInt(request.getParameter("seq")));
		} 
		nDto.setManager(ss_empno);
		nDto.setTitle(title);
		nDto.setContent(content);
		
		NoticeDao nDao = NoticeDao.getInstance();
//		int records = nDao.getCountRow(sh_title);
//		int total = (int)Math.ceil((double)records/(double)pageSize);
		
		if (manager.equals("manager")){
			if (inup.equals("in")){
				result = nDao.insertNotice(nDto);
			} else if(inup.equals("up")) {
				result = nDao.updateNotice(nDto)+1;
			}
		}
		nDto.setResult(result);
//		nDto.setTotal(total);
		
		
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
