package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.prj.dao.PrjDao;
import com.kedu.prj.dto.PrjDto;

public class PrjInUpAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		PrjDto pDto = new PrjDto();
		
		HttpSession session = request.getSession();
		String ss_empno = (String) session.getAttribute("empno");
		String manager	= (String) session.getAttribute("manager");
		
		String inup = request.getParameter("inup");
		
		pDto.setEmpno(ss_empno);
		
		if (request.getParameter("prjno") !=null ){
			pDto.setPrjno(Integer.parseInt(request.getParameter("prjno")));
		} 
		
		pDto.setPrjnm(request.getParameter("prjnm"));
		pDto.setStartdt(request.getParameter("startdt").replace("-", "").trim());
		pDto.setEnddt(request.getParameter("enddt").replace("-", "").trim());
		pDto.setPrjcontent(request.getParameter("prjcontent"));
		
		if (request.getParameter("compno") !=null ){
			pDto.setCompno(Integer.parseInt(request.getParameter("compno")));
		} 
		
		System.out.println(pDto.toString());

		PrjDao pDao = PrjDao.getInstance();
		//if (manager.equals("manager")){
			if (inup.equals("in")){
				result = pDao.insertPrj(pDto);
			} else if(inup.equals("up")) {
				result = pDao.updatePrj(pDto)+1;
			}
		//}
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
