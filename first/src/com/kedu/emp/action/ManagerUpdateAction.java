package com.kedu.emp.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kedu.common.Action;
import com.kedu.emp.dao.EmpDao;

public class ManagerUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		EmpDao empDao = EmpDao.getInstance();
		int result = empDao.updateManager("2016080001", "11111");
		
		
		if (result == 1){
			url = "neviGo?cmd=emp";
		} else {
			url = "neviGo?cmd=login";			
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);		
	}

}
