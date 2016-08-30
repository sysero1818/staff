package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.addr.dao.AddrDao;
import com.kedu.addr.dto.AddrDto;
import com.kedu.common.Action;


public class SearchAddr implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dong = request.getParameter("dong").trim();
		AddrDao addrDao = AddrDao.getInstance();
		List<AddrDto> addrDto = addrDao.selectAddrByDong(dong);
	
		//HashMap sendData = new HashMap();
		Gson gson = new GsonBuilder().create();
		
		//sendData.put("size", addrDto.size());
		//sendData.put("list", addrDto);
		
		String json = gson.toJson(addrDto);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();		
	}

}
