package com.kedu.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.kedu.emp.dto.MemberDto;


public class MemberDao {

	private MemberDao() {
	
	}
	
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance(){
		return instance;
	}
	
	private Connection getConnection() throws Exception{
		Connection conn = null;
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/staff");
		conn = ds.getConnection();
		return conn;
	}
	
	public int userCheck(String userid, String pwd){
		int result = -1;
		String sql = "select pwd from member where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString("pwd")!=null && rs.getString("pwd").equals(pwd)){
					result = 1;
				} else {
					result = 0;
				}
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public MemberDto getMember(String userid){
		MemberDto mDto = null;
		String sql = "select * from member where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()){
				mDto = new MemberDto();
				mDto.setName(rs.getString("name"));
				mDto.setUserid(rs.getString("userid"));
				mDto.setPwd(rs.getString("pwd"));
				mDto.setEmail(rs.getString("email"));
				mDto.setPhone(rs.getString("phone"));
				mDto.setAdmin(rs.getInt("admin"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return mDto;
	}
	
	public int confirmID(String userid){
		int result = -1;
		String sql = "select userid from member where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()){
				result = 1;
			} else {
				result = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
	}
	
	public int insertMember(MemberDto mDto){
		int result = -1;
		String sql = "insert into member values (?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mDto.getName());
			pstmt.setString(2, mDto.getUserid());
			pstmt.setString(3, mDto.getPwd());
			pstmt.setString(4, mDto.getEmail());
			pstmt.setString(5, mDto.getPhone());
			pstmt.setInt(6, mDto.getAdmin());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}
	
	public int updateMember(MemberDto mDto){
		int result = -1;
		String sql = "update member set pwd=?, email=?, phone=?, name=? where userid=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mDto.getPwd());
			pstmt.setString(2, mDto.getEmail());
			pstmt.setString(3, mDto.getPhone());
			pstmt.setString(4, mDto.getName())	;			
			pstmt.setString(5, mDto.getUserid());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}	
	
	
	// 전체 행의 숫자 구하기
	public int getCountRow() {
		int x= 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			pstmt=conn.prepareStatement("select count(userid) as cnt from first.member");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				x = rs.getInt("cnt");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return x;
	}	
	
	
	public List<MemberDto> selectAllMembers(int page, int perPageRow) {
		String sql = "select * from "+
				"(select rownum rnum, name, userid, pwd, email, phone, admin "+
				" from "+
				" (select name, userid, pwd, email, phone, admin from first.member order by userid)) "+
				" where rnum>=? and rnum<=?";
		
		List<MemberDto> list = new ArrayList<MemberDto>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
//		읽기 시작할 row 번호.
		int startRow=(page-1)*perPageRow+1; 
		
//		읽을 마지막 row 번호.
		int endRow=startRow+perPageRow-1; 	
		
		try {
			conn = getConnection();	
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberDto mDto = new MemberDto();
				mDto.setName(rs.getString("name"));
				mDto.setUserid(rs.getString("userid"));
				mDto.setPwd(rs.getString("pwd"));
				mDto.setEmail(rs.getString("email"));
				mDto.setPhone(rs.getString("phone"));
				mDto.setAdmin(rs.getInt("admin"));
				list.add(mDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}	
}
