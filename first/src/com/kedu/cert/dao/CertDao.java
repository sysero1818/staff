package com.kedu.cert.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.cert.dto.CertDto;
import com.kedu.common.DBManager;

public class CertDao {
	
	private CertDao(){
		
	}
	
	private static CertDao instance = new CertDao();
	
	public static CertDao getInstance(){
		return instance;
	}	
	
	public int getCountRow(String sh_empno) {
		int x= 0;
		String sql = "{call cert_totalrow(?,?)}";
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
	
	
	
	public List<CertDto> selectAllCert(int page, int perPageRow, String sh_empno) {
		String sql = "{call cert_list(?,?,?,?)}";
		List<CertDto> list = new ArrayList<CertDto>();

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
				CertDto cDto = new CertDto();
				cDto.setCno(rs.getInt("cno"));
				cDto.setEmpno(rs.getString("empno"));
				cDto.setCertno(rs.getString("certno"));
				cDto.setCertnm(rs.getString("certnm"));
				cDto.setAqdt(rs.getString("aqdt"));
				cDto.setIssuorgan(rs.getString("issuorgan"));
				cDto.setRenewstdt(rs.getString("renewstdt"));
				cDto.setReneweddt(rs.getString("reneweddt"));
				cDto.setPeriod(rs.getString("period"));
				list.add(cDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}
	
	
	public int insertCert(CertDto cDto){
		int result = 0;
		String sql="{call cert_insert(?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setString(1, cDto.getEmpno());
			cstmt.setString(2, cDto.getCertno());
			cstmt.setString(3, cDto.getCertnm());
			cstmt.setString(4, cDto.getAqdt());
			cstmt.setString(5, cDto.getIssuorgan());
			cstmt.setString(6, cDto.getRenewstdt());
			cstmt.setString(7, cDto.getReneweddt());
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
	
	
	public int updateCert(CertDto cDto){
		int result = 0;
		String sql="{call cert_update(?,?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setInt(1, cDto.getCno());
			cstmt.setString(2, cDto.getEmpno());
			cstmt.setString(3, cDto.getCertno());
			cstmt.setString(4, cDto.getCertnm());
			cstmt.setString(5, cDto.getAqdt());
			cstmt.setString(6, cDto.getIssuorgan());
			cstmt.setString(7, cDto.getRenewstdt());
			cstmt.setString(8, cDto.getReneweddt());
			cstmt.registerOutParameter(9, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(9);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}	
}
