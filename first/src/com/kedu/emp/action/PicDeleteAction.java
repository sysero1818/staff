package com.kedu.emp.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kedu.common.Action;
import com.kedu.emp.dao.EmpDao;
import com.kedu.emp.dto.EmpDto;

public class PicDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String empno = request.getParameter("empno");
		String savePath = "upload";
		ServletContext context = request.getSession().getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		
		EmpDao eDao = EmpDao.getInstance();
		EmpDto eDto = eDao.getEmpOneByEmpno(empno);
		String old_pic = eDto.getPic();
		
//		기존의 데이터가 있으면 해당 파일을 삭제하기
		if(old_pic != null){
			File file = new File( uploadFilePath +"/"+ old_pic );
			if (file.exists()){
				file.delete();
			}
		}
		
//		새로 업로드한 사진 DB에 update하기
		int result = eDao.deletePic(empno);
//		새로 update한 데이터를 json으로 세팅해주기
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
