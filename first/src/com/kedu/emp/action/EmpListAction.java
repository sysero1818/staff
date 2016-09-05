package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.common.GridJson;
import com.kedu.emp.dao.EmpDao;
import com.kedu.emp.dto.EmpDto;

public class EmpListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int perPageRow = 10;
		String sh_empno = "";
		String sh_empnm = "";
		String sh_indt_st = "";
		String sh_indt_ed = "";
		try {
			if(request.getParameter("page").trim() != null && request.getParameter("page").trim().length() != 0){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			
			if(request.getParameter("rows").trim() != null && request.getParameter("rows").trim().length() != 0){
				perPageRow = Integer.parseInt(request.getParameter("rows").trim());
			}
			
			if(request.getParameter("sh_empno").trim() != null && request.getParameter("sh_empno").trim().length() != 0){
				sh_empno = request.getParameter("sh_empno").trim();
			}
			
			if(request.getParameter("sh_empnm").trim() != null && request.getParameter("sh_empnm").trim().length() != 0){
				sh_empnm = request.getParameter("sh_empnm").trim();
			}		

			if(request.getParameter("sh_indt_st").trim() != null && request.getParameter("sh_indt_st").trim().length() != 0){
				sh_indt_st = request.getParameter("sh_indt_st").trim();
			}

			if(request.getParameter("sh_indt_ed").trim()!=null && request.getParameter("sh_indt_ed").trim().length() != 0){
				sh_indt_ed = request.getParameter("sh_indt_ed").trim();
			}			
		} catch (Exception e) {

		}
		
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");
		
		EmpDao mDao = EmpDao.getInstance();
		List<EmpDto> empList = mDao.selectAllEmps(page, perPageRow, sh_empno, sh_empnm, sh_indt_st, sh_indt_ed, ss_empno, manager);
		
		int records = mDao.getCountRow(sh_empno, sh_empnm, sh_indt_st, sh_indt_ed);
		int total = (int)Math.ceil((double)records/(double)perPageRow);
		
		GridJson<EmpDto> empJson = new GridJson<EmpDto>();
		empJson.setTotal(total);
		empJson.setRecords(records);
		empJson.setPage(page);
		empJson.setRows(empList);
		
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
