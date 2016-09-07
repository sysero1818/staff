package com.kedu.cert.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.cert.dao.CertDao;
import com.kedu.cert.dto.CertDto;
import com.kedu.common.Action;

public class CertInUpAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = 0;
		CertDto cDto = new CertDto();
		String inup = request.getParameter("inup_cert");
		
		if (request.getParameter("cno") != null && inup.equals("up")) {
			cDto.setCno(Integer.parseInt(request.getParameter("cno")));
		}
		
		cDto.setEmpno(request.getParameter("cert_empno"));
		cDto.setCertno(request.getParameter("certno"));
		cDto.setCertnm(request.getParameter("certnm"));
		cDto.setAqdt(request.getParameter("aqdt").replace("-", ""));
		cDto.setIssuorgan(request.getParameter("issuorgan"));
		cDto.setRenewstdt(request.getParameter("cert_renewstdt").replace("-", ""));
		cDto.setReneweddt(request.getParameter("cert_reneweddt").replace("-", ""));
		
		CertDao sDao = CertDao.getInstance();
		if(inup.equals("in")){
			result = sDao.insertCert(cDto);
		} else if (inup.equals("up")){
			result = sDao.updateCert(cDto);
		}
		System.out.println(inup);
		System.out.println(cDto);
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
