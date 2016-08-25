package com.kedu.emp.list;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.GridJson;
import com.kedu.emp.dao.MemberDao;
import com.kedu.emp.dto.MemberDto;

@WebServlet("/memberList.do")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		int perPageRow = 10;
		
		try {
			if(request.getParameter("page").trim()!=null && request.getParameter("page").trim()!=""){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			
			if(request.getParameter("rows").trim()!=null && request.getParameter("rows").trim()!=""){
				perPageRow = Integer.parseInt(request.getParameter("rows").trim());
			}
		} catch (Exception e) {
			System.out.println("널처리 했는데.....");
		}
		
		MemberDao mDao = MemberDao.getInstance();
		List<MemberDto> memberList = mDao.selectAllMembers(page, perPageRow);
		
		int records = mDao.getCountRow();
		int total = (int)Math.ceil((double)records/(double)perPageRow);
		
		GridJson<MemberDto> memberJson = new GridJson<MemberDto>();
		memberJson.setTotal(total);
		memberJson.setRecords(records);
		memberJson.setPage(page);
		memberJson.setRows(memberList);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(memberJson);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
