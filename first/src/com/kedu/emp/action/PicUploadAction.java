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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class PicUploadAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String savePath = "upload";
		int uploadFileSizeLimit = 5 * 1024 * 1024;
		String encType = "UTF-8";
		ServletContext context = request.getSession().getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		System.out.println("서버상의 실제 디렉토리 :");
		System.out.println(uploadFilePath);
		
		try {
			MultipartRequest multi = new MultipartRequest(request, 
					uploadFilePath, 
					uploadFileSizeLimit,
					encType, 
					new DefaultFileRenamePolicy());

			String pic = multi.getFilesystemName("picFile");
			String empno	= multi.getParameter("pic_empno");
			
			System.out.println(pic);
			System.out.println(empno);
			
			if (pic == null) { 
				System.out.print("파일 업로드 되지 않았음");
			} else { 

//				기존에 사진 파일이 있는지 DB에서 가져오기
				EmpDao eDao = EmpDao.getInstance();
				EmpDto eDto = eDao.getEmpOneByEmpno(empno);
				String old_pic = eDto.getPic();
				
//				기존의 데이터가 있으면 해당 파일을 삭제하기
				if(old_pic != null){
					File file = new File( savePath +"/"+ old_pic );
					if (file.exists()){
						file.delete();
					}
				}
				
//				새로 업로드한 사진 DB에 update하기
				eDao.updatePic(empno, pic);	
				
//				새로 update한 데이터를 json으로 세팅해주기
				Gson gson = new GsonBuilder().create();
				String json = gson.toJson(pic);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				PrintWriter out = response.getWriter();
				out.write(json);
				out.flush();
				out.close();	
			}
		} catch (Exception e) {
			System.out.print("예외 발생 : " + e);
		}		
	}

}
