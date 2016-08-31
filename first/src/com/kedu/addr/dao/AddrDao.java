package com.kedu.addr.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.addr.dto.AddrDto;
import com.kedu.common.DBManager;

public class AddrDao {
	private AddrDao(){
		
	}
	
	private static AddrDao instance = new AddrDao();
	
	public static AddrDao getInstance(){
		return instance;
	}

	//사원의 row개수 구하기
	public int getCountRow(String dong) {
		int x= 0;
		String sql = "{call addr_couter(?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;

		try{
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, dong);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(2);
			
			if(rs.next()){
				x=rs.getInt("cnt");
			}
		}catch(Exception ex){
			System.out.println("getListCount 에러: " + ex);			
		}finally{
			DBManager.close(conn, cstmt, rs);
		}
		return x;
	}	
	
	public List<AddrDto> selectAddrByDong (int page, int perPageRow, String dong) {
		String sql = "{call search_addr(?,?,?,?,?)}";
		List<AddrDto> list = new ArrayList<AddrDto>();
		
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
			cstmt.setString(3, dong);
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(5);			

			while (rs.next()) {
				AddrDto addrDto = new AddrDto();
				addrDto.setSeq(rs.getInt("seq"));
				addrDto.setZipcode(rs.getString("zipcode"));
				addrDto.setAddress1(rs.getString("address1"));
				addrDto.setAddress2(rs.getString("address2"));				
				/*addrDto.setSido(rs.getString("sido"));
				addrDto.setSigungu(rs.getString("sigungu"));
				addrDto.setDong(rs.getString("dong"));
				addrDto.setRi(rs.getString("ri"));
				addrDto.setBldg(rs.getString("bldg"));
				addrDto.setBungi(rs.getString("bungi"));*/
				list.add(addrDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}	
}
