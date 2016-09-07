package com.kedu.empskill.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.empskill.dao.EmpSkillDao;
import com.kedu.empskill.dto.EmpSkillDto;


public class EmpSkillInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		EmpSkillDto esDto = new EmpSkillDto();
		String gubun = request.getParameter("gubun");
		String empno = request.getParameter("empno");
		int skillno = 0;
		
		if (request.getParameter("skillno") != null ) {
			skillno = Integer.parseInt(request.getParameter("skillno"));
		}
		
		esDto.setEmpno(empno);
		esDto.setSkillno(skillno);
		EmpSkillDao esDao = EmpSkillDao.getInstance();
		
		if (gubun.equals("insert")){
			result = esDao.insertEmpSkill(esDto);
		} else if (gubun.equals("del")){
			result = esDao.deleteEmpSkill(esDto);
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
