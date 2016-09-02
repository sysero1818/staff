package com.kedu.emp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.common.DBManager;
import com.kedu.emp.dto.SchDto;

public class SchDao {

	private SchDao(){
		
	}
	
	private static SchDao instance = new SchDao();
	
	public static SchDao getInstance(){
		return instance;
	}
	
	public int getCountRow(String sh_empno) {
		int x= 0;
		String sql = "{call schcareer_totalrow(?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try{
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, sh_empno);
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
	
	
	
	public List<SchDto> selectAllSch(int page, int perPageRow, String sh_empno) {
		String sql = "{call schcareer_list(?,?,?,?)}";
		List<SchDto> list = new ArrayList<SchDto>();

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
			cstmt.setString(3, sh_empno);
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(4);			

			while (rs.next()) {
				SchDto schDto = new SchDto();
				schDto.setSchno(rs.getInt("schno"));
				schDto.setEmpno(rs.getString("empno"));
				schDto.setStartdt(rs.getString("startdt"));
				schDto.setEnddt(rs.getString("enddt"));
				schDto.setPeriod(rs.getString("period"));
				schDto.setSchool(rs.getString("school"));
				schDto.setMajor(rs.getString("major"));
				list.add(schDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}
	
	
	public int insertSch(SchDto sDto){
		int result = 0;
		String sql="{call schcareer_insert(?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setString(1, sDto.getEmpno());
			cstmt.setString(2, sDto.getStartdt());
			cstmt.setString(3, sDto.getEnddt());
			cstmt.setString(4, sDto.getSchool());
			cstmt.setString(5, sDto.getMajor());
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
	
	
	public int updateSch(SchDto sDto){
		int result = 0;
		String sql="{call schcareer_update(?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, sDto.getSchno());
			cstmt.setString(2, sDto.getEmpno());
			cstmt.setString(3, sDto.getStartdt());
			cstmt.setString(4, sDto.getEnddt());
			cstmt.setString(5, sDto.getSchool());
			cstmt.setString(6, sDto.getMajor());
			cstmt.registerOutParameter(7, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(7);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}		
}
