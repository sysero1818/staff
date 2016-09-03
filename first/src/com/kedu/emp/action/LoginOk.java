package com.kedu.emp.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kedu.common.Action;
import com.kedu.common.SecurityUtil;
import com.kedu.emp.dao.EmpDao;
import com.kedu.emp.dto.EmpDto;

public class LoginOk implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String empno = "";
		String passwd = "";
		String manager = "";
		String mpasswd = "";
		
		if (request.getParameter("empno") != null) {
			empno = request.getParameter("empno");
		}
		if (request.getParameter("passwd") != null) {
			passwd = request.getParameter("passwd");
		}		
		if (request.getParameter("manager") != null) {
			manager = request.getParameter("manager");
		}
		if (request.getParameter("mpasswd") != null) {
			mpasswd = request.getParameter("mpasswd");
		}				
		
//		System.out.println(empno +":"+passwd+":"+manager+":"+mpasswd);
		
		empno = SecurityUtil.makeSecureString(empno);
		passwd = SecurityUtil.makeSecureString(passwd);
		mpasswd = SecurityUtil.makeSecureString(mpasswd);
		
		String url = "neviGo?cmd=go&url=login.jsp";
		EmpDao empDao = EmpDao.getInstance();
		int result = empDao.login(empno, passwd, manager, mpasswd);
//		System.out.println(result);
		if (result == 1){
			EmpDto eDto = empDao.getEmpOneByEmpno(empno);
			HttpSession session = request.getSession();
			
			session.setAttribute("empno", empno);
			session.setAttribute("empnm", eDto.getEmpnm());
			
			if(manager.equals("1")) {
				session.setAttribute("manager", "manager");
			}
			url = "neviGo?cmd=go&url=emp/emp.jsp";
//			System.out.println(eDto.getEmpno());
//			System.out.println(url+":"+session.getAttribute("empno"));
		} else {
//			System.out.println("로그인 결과" + result);
		}
		
		response.sendRedirect(url);
	}

}
