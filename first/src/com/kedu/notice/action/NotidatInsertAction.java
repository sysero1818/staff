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

public class NotidatInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;

		NotidatDto nDto = new NotidatDto();
		
		if (request.getParameter("Oseq") != null && request.getParameter("Oseq").trim().length() != 0){
			nDto.setSeq(Integer.parseInt(request.getParameter("Oseq")));
		}
		String dcontent = request.getParameter("dcontent");
		
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");
		
		nDto.setDcontent(dcontent);
		if (manager.equals("manager")){
			nDto.setDgubun("m");
			nDto.setManager(ss_empno);
		} else {
			nDto.setDgubun("e");
			nDto.setEmpno(ss_empno);
		}
		System.out.println("aaa");
		System.out.println(nDto.toString());
		NoticeDao nDao = NoticeDao.getInstance();
		
		result = nDao.insertNotidat(nDto);
		
		nDto.setResult(result);
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
