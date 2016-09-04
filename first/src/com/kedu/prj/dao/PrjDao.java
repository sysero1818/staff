package com.kedu.prj.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.common.DBManager;
import com.kedu.prj.dto.PrjDto;

public class PrjDao {
	private PrjDao(){
		
	}
	
	private static PrjDao instance = new PrjDao();
	
	public static PrjDao getInstance(){
		return instance;
	}
	
	//사원의 row개수 구하기
	public int getCountRow(String sh_prjnm) {
		int x= 0;
		String sql = "{call prj_totalrow(?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try{
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, sh_prjnm);
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
	

	public List<PrjDto> selectAllPrjs(int page, int perPageRow, String sh_prjnm, String ss_empno, String manager) {
		String sql = "{call prj_list(?,?,?,?)}";
		List<PrjDto> list = new ArrayList<PrjDto>();

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
			cstmt.setString(3, sh_prjnm);
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(4);			

			while (rs.next()) {
				PrjDto pDto = new PrjDto();
				pDto.setPrjno(rs.getInt("prjno"));
				pDto.setPrjnm(rs.getString("prjnm"));
				pDto.setPrjcontent(rs.getString("prjcontent"));
				pDto.setStartdt(rs.getString("startdt"));
				pDto.setEnddt(rs.getString("enddt"));
				pDto.setCompno(rs.getInt("compno"));
				pDto.setCompnm(rs.getString("compnm"));
				pDto.setEmpno(rs.getString("empno"));
				pDto.setEmpnm(rs.getString("empnm"));

				
				if ( manager != null && (ss_empno !=null && ss_empno.equals(rs.getString("empno"))) ){
					pDto.setUpyn("o");
				} else {
					pDto.setUpyn("x");
				}
				
				list.add(pDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}	
	

	
	public int insertPrj(PrjDto pDto){
		int result = 0;
		String sql="{call prj_insert(?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setString(1, pDto.getPrjnm());
			cstmt.setString(2, pDto.getStartdt());
			cstmt.setString(3, pDto.getEnddt());
			cstmt.setString(4, pDto.getPrjcontent());
			
			if (pDto.getCompno() > 0) {
				cstmt.setInt(5, pDto.getCompno());
			} else {
				cstmt.setNull(5, java.sql.Types.NULL);
			}
			cstmt.setString(6, pDto.getEmpno());
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
	
	public int updatePrj(PrjDto pDto){
		int result = 0;
		String sql="{call prj_update(?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, pDto.getPrjno());
			cstmt.setString(2, pDto.getPrjnm());
			cstmt.setString(3, pDto.getStartdt());
			cstmt.setString(4, pDto.getEnddt());
			cstmt.setString(5, pDto.getPrjcontent());
			
			if (pDto.getCompno() > 0) {
				cstmt.setInt(6, pDto.getCompno());
			} else {
				cstmt.setNull(6, java.sql.Types.NULL);
			}
			
			cstmt.setString(7, pDto.getEmpno());
			cstmt.registerOutParameter(8, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(8);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}
	
}
