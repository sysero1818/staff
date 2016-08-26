package com.kedu.emp.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kedu.common.Action;
import com.kedu.emp.dao.EmpDao;
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
			MultipartRequest multi = new MultipartRequest(request, // request 객체
					uploadFilePath, // 서버상의 실제 디렉토리
					uploadFileSizeLimit, // 최대 업로드 파일 크기
					encType, // 인코딩 방법
					// 동일한 이름이 존재하면 새로운 이름이 부여됨
					new DefaultFileRenamePolicy());
			// 업로드된 파일의 이름 얻기
			String pic = multi.getFilesystemName("picFile");
			String empno	= multi.getParameter("pic_empno");
			
			System.out.println(pic);
			System.out.println(empno);
			
			if (pic == null) { 
				System.out.print("파일 업로드 되지 않았음");
			} else { 
				EmpDao eDao = EmpDao.getInstance();
				eDao.updatePic(empno, pic);				
			}
		} catch (Exception e) {
			System.out.print("예외 발생 : " + e);
		}		
	}

}
