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
import com.kedu.emp.dao.EmpDao;
import com.kedu.emp.dto.EmpDto;

public class EmpTestAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int perPageRow = 10;
		String sh_empno = "";
		String sh_empnm = "";		
		String sh_indt_st = "";
		String sh_indt_ed = "";
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
			
			if(request.getParameter("sh_empnm").trim()!=null && request.getParameter("sh_empnm").trim()!=""){
				sh_empnm = request.getParameter("sh_empnm").trim();
			}		

			if(request.getParameter("sh_indt_st").trim()!=null && request.getParameter("sh_indt_st").trim()!=""){
				sh_indt_st = request.getParameter("sh_indt_st").trim();
			}

			if(request.getParameter("sh_indt_ed").trim()!=null && request.getParameter("sh_indt_ed").trim()!=""){
				sh_indt_ed = request.getParameter("sh_indt_ed").trim();
			}			
		} catch (Exception e) {
//			System.out.println("널처리 했는데.....");
		}
		
//		System.out.println(sh_empno+":"+sh_empnm+":"+sh_indt_st+":"+sh_indt_ed);
		
		EmpDao mDao = EmpDao.getInstance();
		List<EmpDto> empList = mDao.selectAllEmps(page, perPageRow, sh_empno, sh_empnm, sh_indt_st, sh_indt_ed);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(empList);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();		
		
	}

}
