package com.kedu.empskill.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.common.DBManager;
import com.kedu.empskill.dto.EmpSkillDto;

public class EmpSkillDao {
	
	private EmpSkillDao(){
		
	}
	
	private static EmpSkillDao instance = new EmpSkillDao();
	
	public static EmpSkillDao getInstance(){
		return instance;
	}

	public int empSkill_getCountRow(String sh_empno) {
		int x= 0;
		String sql = "{call empskill_totalrow(?,?)}";
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
	
	public List<EmpSkillDto> selectAllEmpSkills(int page, int perPageRow, String sh_empno) {
		String sql = "{call empskill_list(?,?,?,?)}";
		List<EmpSkillDto> list = new ArrayList<EmpSkillDto>();

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
				EmpSkillDto esDto = new EmpSkillDto();
				esDto.setEmpno(rs.getString("empno"));
				esDto.setSkillno(rs.getInt("skillno"));
				esDto.setSkillnm(rs.getString("skillnm"));
				esDto.setCtno(rs.getInt("ctno"));
				esDto.setCtnm(rs.getString("ctnm"));
				list.add(esDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}
	
	
	public int insertEmpSkill(EmpSkillDto esDto){
		int result = 0;
		String sql="{call empskill_insert(?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setString(1, esDto.getEmpno());
			cstmt.setInt(2, esDto.getSkillno());
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
	
	
	public int deleteEmpSkill(EmpSkillDto esDto){
		int result = 0;
		String sql="{call empskill_delete(?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			cstmt.setString(1, esDto.getEmpno());
			cstmt.setInt(2, esDto.getSkillno());
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
	
	
	// 스킬만
	public int skill_getCountRow() {
		int x= 0;
		String sql = "{call skill_totalrow(?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;

		try{
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeQuery();
			x = cstmt.getInt(1);

		}catch(Exception ex){
			System.out.println("getListCount 에러: " + ex);			
		}finally{
			DBManager.close(conn, cstmt);
		}
		return x;
	}
	
	public List<EmpSkillDto> selectAllSkills(int page, int perPageRow) {
		String sql = "{call skill_list(?,?,?)}";
		List<EmpSkillDto> list = new ArrayList<EmpSkillDto>();

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
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(3);			

			while (rs.next()) {
				EmpSkillDto esDto = new EmpSkillDto();
				esDto.setSkillno(rs.getInt("skillno"));
				esDto.setSkillnm(rs.getString("skillnm"));
				esDto.setCtno(rs.getInt("ctno"));
				esDto.setCtnm(rs.getString("ctnm"));

				list.add(esDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}	
}
