package com.kedu.career.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.career.dto.CareerDto;
import com.kedu.common.DBManager;

public class CareerDao {
	
	private CareerDao(){
		
	}
	
	private static CareerDao instance = new CareerDao();
	
	public static CareerDao getInstance(){
		return instance;
	}	
	
	public int getCountRow(String sh_empno) {
		int x= 0;
		String sql = "{call career_totalrow(?,?)}";
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
	
	
	
	public List<CareerDto> selectAllCareer(int page, int perPageRow, String sh_empno) {
		String sql = "{call career_list(?,?,?,?)}";
		List<CareerDto> list = new ArrayList<CareerDto>();

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
				CareerDto carDto = new CareerDto();
				carDto.setCareerno(rs.getInt("careerno"));
				carDto.setEmpno(rs.getString("empno"));
				carDto.setStartdt(rs.getString("startdt"));
				carDto.setEnddt(rs.getString("enddt"));
				carDto.setPeriod(rs.getString("period"));
				carDto.setCompnm(rs.getString("compnm"));
				carDto.setPositnm(rs.getString("positnm"));
				carDto.setCharge(rs.getString("charge"));
				list.add(carDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}
	
	
	public int insertCareer(CareerDto carDto){
		int result = 0;
		String sql="{call career_insert(?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setString(1, carDto.getEmpno());
			cstmt.setString(2, carDto.getStartdt());
			cstmt.setString(3, carDto.getEnddt());
			cstmt.setString(4, carDto.getCompnm());
			cstmt.setString(5, carDto.getPositnm());
			cstmt.setString(6, carDto.getCharge());			
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
	
	
	public int updateCareer(CareerDto carDto){
		int result = 0;
		String sql="{call career_update(?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, carDto.getCareerno());
			cstmt.setString(2, carDto.getEmpno());
			cstmt.setString(3, carDto.getStartdt());
			cstmt.setString(4, carDto.getEnddt());
			cstmt.setString(5, carDto.getCompnm());
			cstmt.setString(6, carDto.getPositnm());
			cstmt.setString(7, carDto.getCharge());
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
