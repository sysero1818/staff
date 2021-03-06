package com.kedu.prj.action;

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
import com.kedu.prj.dao.PrjDao;
import com.kedu.prj.dto.PrjDto;

public class PrjListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int page = 1;
		int perPageRow = 10;
		String sh_prjnm = "";

		try {
			if(request.getParameter("page").trim()!=null && request.getParameter("page").trim()!=""){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			
			if(request.getParameter("rows").trim()!=null && request.getParameter("rows").trim()!=""){
				perPageRow = Integer.parseInt(request.getParameter("rows").trim());
			}
			if(request.getParameter("sh_prjnm") != null && request.getParameter("sh_prjnm").trim().length() != 0){
				sh_prjnm = request.getParameter("sh_prjnm").trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");
		
		PrjDao mDao = PrjDao.getInstance();
		List<PrjDto> prjList = mDao.selectAllPrjs(page, perPageRow, sh_prjnm, ss_empno, manager);
		
		int records = mDao.getCountRow(sh_prjnm);
		int total = (int)Math.ceil((double)records/(double)perPageRow);
		
		GridJson<PrjDto> empJson = new GridJson<PrjDto>();
		empJson.setTotal(total);
		empJson.setRecords(records);
		empJson.setPage(page);
		empJson.setRows(prjList);
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
