package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.emp.dao.SchDao;
import com.kedu.emp.dto.SchDto;

public class SchInupAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		SchDto sDto = new SchDto();
		String inup = request.getParameter("inup_sch");
		
		if (request.getParameter("schno") != null && inup.equals("up")) {
			sDto.setSchno(Integer.parseInt(request.getParameter("schno")));
		}
		
		sDto.setEmpno(request.getParameter("sch_empno"));
		sDto.setStartdt(request.getParameter("sch_startdt").replace("-", "").trim());
		sDto.setEnddt(request.getParameter("sch_enddt").replace("-", "").trim());
		sDto.setSchool(request.getParameter("school"));
		sDto.setMajor(request.getParameter("major"));
		SchDao sDao = SchDao.getInstance();
		
		if(inup.equals("in")){
			result = sDao.insertSch(sDto);
		} else if (inup.equals("up")){
			result = sDao.updateSch(sDto);
		}
		
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
