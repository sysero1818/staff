package com.kedu.notice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.common.DBManager;
import com.kedu.notice.dto.NoticeDto;
import com.kedu.notice.dto.NotidatDto;


public class NoticeDao {
	private NoticeDao() {
	}

	private static NoticeDao instance = new NoticeDao();

	public static NoticeDao getInstance() {
		return instance;
	}
	
	public int getCountRow(String sh_title) {
		int x= 0;
		String sql = "{call notice_totalrow(?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try{
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, sh_title);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeQuery();
			x = cstmt.getInt(2);
			
		}catch(Exception ex){
			System.out.println("getListCount 에러: " + ex);			
		}finally{
			DBManager.close(conn, cstmt);
		}
		return x;
	}
	

	public List<NoticeDto> selectAllNotices(int page, int perPageRow, String sh_title, String ss_empno, String manager) {
		String sql = "{call notice_list(?,?,?,?)}";
		List<NoticeDto> list = new ArrayList<NoticeDto>();

		int startrow=(page - 1) * perPageRow + 1;
		int endrow=startrow + perPageRow - 1;

		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, startrow);
			cstmt.setInt(2, endrow);
			cstmt.setString(3, sh_title);
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(4);			

			while (rs.next()) {
				NoticeDto nDto = new NoticeDto();
				nDto.setSeq(rs.getInt("seq"));
				nDto.setTitle(rs.getString("title"));
				nDto.setContent(rs.getString("content"));
				nDto.setCnt(rs.getInt("cnt"));
				nDto.setManager(rs.getString("manager"));
				nDto.setRegdtt(rs.getString("regdtt"));
				
				if ( manager != null && (ss_empno !=null && ss_empno.equals(rs.getString("manager"))) ){
					nDto.setUpyn("o");
				} else {
					nDto.setUpyn("x");
				}
				
				list.add(nDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}	
	
	
	public NoticeDto selectOneBySeq(int seq){
		NoticeDto nDto = null;
		String sql="{call notice_content(?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, seq);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(2);
			if (rs.next()) {
				nDto = new NoticeDto();
				nDto.setSeq(rs.getInt("seq"));
				nDto.setTitle(rs.getString("title"));
				nDto.setContent(rs.getString("content"));
				nDto.setRegdtt(rs.getString("regdtt"));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return nDto;
	}
	
	
	public int insertNotice(NoticeDto nDto){
		int result = 0;
		String sql="{call notice_insert(?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, nDto.getTitle());
			cstmt.setString(2, nDto.getContent());
			cstmt.setString(3, nDto.getManager());
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}
	

	public int updateNotice(NoticeDto nDto){
		int result = 0;
		String sql="{call notice_update(?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, nDto.getSeq());
			cstmt.setString(2, nDto.getTitle());
			cstmt.setString(3, nDto.getContent());
			cstmt.setString(4, nDto.getManager());
			cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(5);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}	
	
	public List<NotidatDto> selectBySeqNotidatList(int seq){
		String sql = "{call notidat_list(?,?)}";
		List<NotidatDto> list = new ArrayList<NotidatDto>();

		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, seq);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(2);			

			while (rs.next()) {
				NotidatDto nDto = new NotidatDto();
				nDto.setSeq(rs.getInt("seq"));
				nDto.setDseq(rs.getInt("dseq"));
				nDto.setDcontent(rs.getString("dcontent"));
				nDto.setDgubun(rs.getString("dgubun"));
				nDto.setEmpno(rs.getString("empno"));
				nDto.setManager(rs.getString("manager"));
				nDto.setDregdtt(rs.getString("dregdtt"));
				
				if (rs.getString("dgubun").equals("e")) {
					nDto.setEmpnm(rs.getString("empnm"));
				} else if (rs.getString("dgubun").equals("m")) {
					nDto.setEmpnm("관리자");
				}				
/*				
				if ( manager != null && (ss_empno !=null && ss_empno.equals(rs.getString("manager"))) ){
					nDto.setUpyn("o");
				} else {
					nDto.setUpyn("x");
				}
*/			
				list.add(nDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}
	
	
	public int insertNotidat(NotidatDto nDto){
		int result = 0;
		String sql="{call notidat_insert(?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, nDto.getSeq());
			cstmt.setString(2, nDto.getDcontent());
			cstmt.setString(3, nDto.getDgubun());
			
//			if (nDto.getDgubun().equals("e")) {
				cstmt.setString(4, nDto.getEmpno());
//			} else if (nDto.getDgubun().equals("m") ){
//				cstmt.setNull(4, java.sql.Types.NULL);
//			}

//			if (nDto.getDgubun().equals("e")) {
//				cstmt.setNull(5, java.sql.Types.NULL);
//			} else if (nDto.getDgubun().equals("m") ){
				cstmt.setString(5, nDto.getManager());
//			}
			
			cstmt.registerOutParameter(6, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(6);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}
	
	
	public NotidatDto selectOneNotidat(int seq, int dseq){
		NotidatDto ndtDto = null;
		
		String sql="{call notidat_content(?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, seq);
			cstmt.setInt(2, dseq);			
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(3);
			if (rs.next()) {
				ndtDto = new NotidatDto();
				ndtDto.setSeq(rs.getInt("seq"));
				ndtDto.setDseq(rs.getInt("dseq"));
				ndtDto.setDcontent(rs.getString("dcontent"));
				ndtDto.setDregdtt(rs.getString("dregdtt"));
				ndtDto.setDgubun(rs.getString("dgubun"));
				ndtDto.setEmpno(rs.getString("empno"));
				
				if (rs.getString("dgubun").equals("e")) {
					ndtDto.setEmpnm(rs.getString("empnm"));
				} else if (rs.getString("dgubun").equals("m")) {
					ndtDto.setEmpnm("관리자");
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return ndtDto;		

	}
	

	public int deleteNotidat(int seq, int dseq, String empno){
		int result = 0;
		String sql="{call notidat_delete(?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, seq);
			cstmt.setInt(2, dseq);
			cstmt.setString(3, empno);
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(4);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}	

	public int deleteNotice(int seq, String ss_empno){
		int result = 0;
		String sql="{call notice_delete(?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, seq);
			cstmt.setString(2, ss_empno);
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(3);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}		
}
