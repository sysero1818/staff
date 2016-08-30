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
	
	public List<AddrDto> selectAddrByDong (String dong) {
		String sql = "{call search_addr(?,?,?)}";
		List<AddrDto> list = new ArrayList<AddrDto>();

		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, dong);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(3);			

			while (rs.next()) {
				AddrDto addrDto = new AddrDto();
				addrDto.setSeq(rs.getInt("seq"));
				addrDto.setZipcode(rs.getString("zipcode"));
				addrDto.setSido(rs.getString("sido"));
				addrDto.setSigungu(rs.getString("sigungu"));
				addrDto.setDong(rs.getString("dong"));
				addrDto.setRi(rs.getString("ri"));
				addrDto.setBldg(rs.getString("bldg"));
				addrDto.setBungi(rs.getString("bungi"));
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
