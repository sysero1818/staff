package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.emp.dao.EmpDao;
import com.kedu.emp.dto.EmpDto;

public class EmpInUpAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;

		EmpDto empDto = new EmpDto();

		String inup = request.getParameter("inup");
		
		empDto.setEmpno(request.getParameter("empno"));
		empDto.setEmpnm(request.getParameter("empnm"));   
		empDto.setPasswd(request.getParameter("passwd"));  
		empDto.setPic(request.getParameter("pic")); 
		empDto.setJumin(request.getParameter("jumin").replace("-", "").trim());   
		empDto.setBirth(request.getParameter("birth").replace("-", "").trim()); 
		

		if(request.getParameter("zipseq").trim()!=null && request.getParameter("zipseq").trim().length() != 0){
			empDto.setZipseq(Integer.parseInt(request.getParameter("zipseq")));
		}

		empDto.setDetailad(request.getParameter("detailad"));
		empDto.setMobile(request.getParameter("mobile").replace("-", "").trim());  
		empDto.setEmail(request.getParameter("email"));   
		empDto.setIndt(request.getParameter("indt").replace("-", "").trim());
		
		if (request.getParameter("payment") != null) {
			empDto.setPayment(Integer.parseInt(request.getParameter("payment").replace(",", "")));	
		}
				
		if (request.getParameter("deptno") != null){
			empDto.setDeptno(Integer.parseInt(request.getParameter("deptno")));
		} else {
			empDto.setDeptno(300);
		}
		if (request.getParameter("positno") != null){
			empDto.setPositno(Integer.parseInt(request.getParameter("positno"))); 
		} else {
			empDto.setPositno(80);
		}

		
		EmpDao empDao = EmpDao.getInstance();
		
		
		if (inup.equals("in")){
			result = empDao.insertEmp(empDto);
		} else if(inup.equals("up")) {
			result = empDao.updateEmp(empDto)+1;
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
