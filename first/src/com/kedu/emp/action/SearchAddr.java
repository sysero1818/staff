package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.addr.dao.AddrDao;
import com.kedu.addr.dto.AddrDto;
import com.kedu.common.Action;
import com.kedu.common.GridJson;


public class SearchAddr implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int page = 1;
		int perPageRow = 10;
		String dong = "";
		try {
			if (request.getParameter("dong").trim() != null  && request.getParameter("dong").trim()!="") {
				dong = request.getParameter("dong").trim();
			}
			if(request.getParameter("page").trim()!=null && request.getParameter("page").trim()!=""){
				page = Integer.parseInt(request.getParameter("page").trim());
			}
			
			if(request.getParameter("rows").trim()!=null && request.getParameter("rows").trim()!=""){
				perPageRow = Integer.parseInt(request.getParameter("rows").trim());
			}
		} catch (Exception e) {

		}
		
		if(dong != "") {
			AddrDao addrDao = AddrDao.getInstance();
			List<AddrDto> addrList = addrDao.selectAddrByDong(page, perPageRow, dong);
			
			int records = addrDao.getCountRow(dong);
			int total = (int)Math.ceil((double)records/(double)perPageRow);		
			
			GridJson<AddrDto> addrJson = new GridJson<AddrDto>();
			addrJson.setTotal(total);
			addrJson.setRecords(records);
			addrJson.setPage(page);
			addrJson.setRows(addrList);
			
			/*
			Gson gson = new GsonBuilder().create();
			String json = "";
			int addrListSize = addrDto.size();
			if (addrListSize == 0) {
				json = gson.toJson("9999");
			} else {
				json = gson.toJson(addrDto);	
			}
			*/
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(addrJson);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();	
		}
	}

}
