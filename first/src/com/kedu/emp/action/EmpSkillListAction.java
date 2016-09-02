package com.kedu.emp.action;

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
import com.kedu.emp.dao.EmpSkillDao;
import com.kedu.emp.dto.EmpSkillDto;

public class EmpSkillListAction implements Action {

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
//			System.out.println("널처리 했는데.....");
		}

		
		EmpSkillDao sDao = EmpSkillDao.getInstance();
		List<EmpSkillDto> empSkillList = sDao.selectAllEmpSkills(page, perPageRow, sh_empno);
		
		int records = sDao.getCountRow(sh_empno);
		int total = (int)Math.ceil((double)records/(double)perPageRow);
		
		GridJson<EmpSkillDto> empJson = new GridJson<EmpSkillDto>();
		empJson.setTotal(total);
		empJson.setRecords(records);
		empJson.setPage(page);
		empJson.setRows(empSkillList);
		
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
