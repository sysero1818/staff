package com.kedu.emp.action;

import java.io.IOException;
import java.io.PrintWriter;

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
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 여기를 바꿔주면 다운받는 경로가 바뀜
		String savePath = "upload";
		// 최대 업로드 파일 크기 5MB로 제한
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
			if (pic == null) { // 파일이 업로드 되지 않았을때
				System.out.print("파일 업로드 되지 않았음");
				EmpDao eDao = EmpDao.getInstance();
				eDao.updatePic(empno, pic);
			} else { // 파일이 업로드 되었을때
				out.println("<br> 파일명 : " + pic);
			}// else
		} catch (Exception e) {
			System.out.print("예외 발생 : " + e);
		}// catch		
	}

}
