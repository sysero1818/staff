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
//		request.setCharacterEncoding("UTF-8");
		int result = 0;

		EmpDto empDto = new EmpDto();

		String inup = request.getParameter("inup");
		
		empDto.setEmpno(request.getParameter("empno"));
		empDto.setEmpnm(request.getParameter("empnm"));   
		empDto.setPasswd(request.getParameter("passwd"));  
		empDto.setPic(request.getParameter("pic")); 
		empDto.setJumin(request.getParameter("jumin").replace("-", "").trim());   
		empDto.setBirth(request.getParameter("birth").replace("-", "").trim());   
		//empDto.setZipseq(Integer.parseInt(request.getParameter("zipseq")));
		empDto.setZipseq(1110);
		//empDto.setDetailad(request.getParameter("detailad"));
		empDto.setDetailad("부평4동");
		empDto.setMobile(request.getParameter("mobile").replace("-", "").trim());  
		empDto.setEmail(request.getParameter("email"));   
		empDto.setIndt(request.getParameter("indt").replace("-", "").trim());
		
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

//		System.out.println("전송되긴하냐??");
		/*
		empDto.setEmpnm("8888");   
		empDto.setPasswd("aasasdf");  
		empDto.setPic("afdajsdklf"); 
		empDto.setJumin("8834273080312");   
		empDto.setBirth("19701027");   
		//empDto.setZipseq(Integer.parseInt(request.getParameter("zipseq")));
		empDto.setZipseq(1110);
		//empDto.setDetailad(request.getParameter("detailad"));
		empDto.setDetailad("부평4동");
		empDto.setMobile("01077773710");  
		empDto.setEmail("sysero@dreamwiz.com");   
		empDto.setIndt("20100101");
		empDto.setDeptno(100);
		empDto.setPositno(80);
		empDto.setPayment(100000000);
		System.out.println(empDto.getEmpnm());
		empDto.toString();
		*/
		
		EmpDao empDao = EmpDao.getInstance();
		
/*		System.out.println(empDto.getEmpnm());
		System.out.println(empDto.toString());		
		System.out.println(inup);*/
		
		if (inup.equals("in")){
			result = empDao.insertEmp(empDto);
		} else if(inup.equals("up")) {
			result = empDao.updateEmp(empDto);
		}
//		System.out.println(result);
		
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
