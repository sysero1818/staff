package com.kedu.career.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.career.dao.CareerDao;
import com.kedu.career.dto.CareerDto;
import com.kedu.common.Action;
import com.kedu.common.GridJson;


public class CareerListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int perPageRow = 10;
		String sh_empno = "";

		try {
			if(request.getParameter("page").trim()!=null && request.getParameter("page").trim()!=""){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			
			if(request.getParameter("rows").trim()!=null && request.getParameter("rows").trim()!=""){
				perPageRow = Integer.parseInt(request.getParameter("rows").trim());
			}
			
			if(request.getParameter("sh_empno").trim()!=null && request.getParameter("sh_empno").trim()!=""){
				sh_empno = request.getParameter("sh_empno").trim();
			}
		
		} catch (Exception e) {
		}

		
		CareerDao sDao = CareerDao.getInstance();
		List<CareerDto> careerList = sDao.selectAllCareer(page, perPageRow, sh_empno);
		
		int records = sDao.getCountRow(sh_empno);
		int total = (int)Math.ceil((double)records/(double)perPageRow);
		
		GridJson<CareerDto> careerJson = new GridJson<CareerDto>();
		careerJson.setTotal(total);
		careerJson.setRecords(records);
		careerJson.setPage(page);
		careerJson.setRows(careerList);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(careerJson);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();		
	}

}
