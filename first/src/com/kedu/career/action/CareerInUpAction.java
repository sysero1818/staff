package com.kedu.career.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.career.dao.CareerDao;
import com.kedu.career.dto.CareerDto;
import com.kedu.common.Action;


public class CareerInUpAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		CareerDto cDto = new CareerDto();
		String inup = request.getParameter("inup_career");
		
		if (request.getParameter("careerno") != null && inup.equals("up")) {
			cDto.setCareerno(Integer.parseInt(request.getParameter("careerno")));
		}
		
		cDto.setEmpno(request.getParameter("career_empno"));
		cDto.setStartdt(request.getParameter("career_startdt").replace("-", ""));
		cDto.setEnddt(request.getParameter("career_enddt").replace("-", ""));
		cDto.setCompnm(request.getParameter("compnm"));
		cDto.setPositnm(request.getParameter("positnm"));
		cDto.setCharge(request.getParameter("charge"));
		CareerDao sDao = CareerDao.getInstance();
		
		if(inup.equals("in")){
			result = sDao.insertCareer(cDto);
		} else if (inup.equals("up")){
			result = sDao.updateCareer(cDto);
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
