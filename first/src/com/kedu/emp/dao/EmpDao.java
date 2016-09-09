package com.kedu.emp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.common.DBManager;
import com.kedu.common.ResultJson;
import com.kedu.crypt.BCrypt;
import com.kedu.crypt.KISA_SHA256;
import com.kedu.emp.dto.EmpDto;


public class EmpDao {

	private EmpDao(){
		
	}
	
	private static EmpDao instance = new EmpDao();
	
	public static EmpDao getInstance(){
		return instance;
	}
	
	//사원의 row개수 구하기
	public int getCountRow(String sh_empno, String sh_empnm, String sh_indt_st, String sh_indt_ed) {
		int x= 0;
		String sql = "{call emp_count(?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		ResultSet rs = null;

		try{
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, sh_empno);
			cstmt.setString(2, sh_empnm);
			cstmt.setString(3, sh_indt_st);
			cstmt.setString(4, sh_indt_ed);			
			cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(5);
			
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
	
	public List<EmpDto> selectAllEmps(int page, int perPageRow, String sh_empno, String sh_empnm, String sh_indt_st, String sh_indt_ed, String ss_empno, String manager) {
		String sql = "{call emp_list(?,?,?,?,?,?,?)}";
		List<EmpDto> list = new ArrayList<EmpDto>();

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
			cstmt.setString(4, sh_empnm);
			cstmt.setString(5, sh_indt_st);
			cstmt.setString(6, sh_indt_ed);			
			cstmt.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(7);			

			while (rs.next()) {
				EmpDto empDto = new EmpDto();
				empDto.setEmpno(rs.getString("empno"));
				empDto.setEmpnm(rs.getString("empnm"));
				empDto.setPasswd(rs.getString("passwd"));
				empDto.setPic(rs.getString("pic"));
				empDto.setBirth(rs.getString("birth"));
				empDto.setZipseq(rs.getInt("zipseq"));
				empDto.setZipcode(rs.getString("zipcode"));
				empDto.setBasicad(rs.getString("basicad"));				
				empDto.setDetailad(rs.getString("detailad"));
				empDto.setMobile(rs.getString("mobile"));
				empDto.setEmail(rs.getString("email"));
				empDto.setIndt(rs.getString("indt"));
				empDto.setOutdt(rs.getString("outdt"));
				empDto.setEmpgb(rs.getString("empgb"));
				empDto.setRegdt(rs.getString("regdt"));
				empDto.setDeptno(rs.getInt("deptno"));
				empDto.setDeptnm(rs.getString("deptnm"));
				empDto.setPositno(rs.getInt("positno"));	
				empDto.setPositnm(rs.getString("positnm"));
				
				if (ss_empno !=null && ss_empno.equals(rs.getString("empno"))){
					empDto.setPayment(rs.getInt("payment"));
					empDto.setJumin(rs.getString("jumin"));					
					empDto.setUpyn("i");
				} else if (manager != null){
					empDto.setPayment(rs.getInt("payment"));
					empDto.setJumin(rs.getString("jumin"));					
					empDto.setUpyn("o");
				} else {
					empDto.setPayment(0);
					empDto.setJumin(" ");					
					empDto.setUpyn("x");
				}
				list.add(empDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt, rs);
		}
		return list;
	}
	
	public ResultJson<Integer> insertEmp(EmpDto eDto){
		ResultJson<Integer> rj = new ResultJson<Integer>(); 
		int result = 0;
		String sql="{call emp_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		KISA_SHA256 sha = KISA_SHA256.getInstance();

		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);

			String sha_passwd = sha.Sha256Encrypt(eDto.getPasswd().getBytes());
			String bc_passwd = BCrypt.hashpw(sha_passwd, BCrypt.gensalt());

			cstmt.setString(1, eDto.getEmpnm());
			cstmt.setString(2, bc_passwd);
			cstmt.setString(3, eDto.getPic());
			cstmt.setString(4, eDto.getJumin());
			cstmt.setString(5, eDto.getBirth());
			cstmt.setInt(6, eDto.getZipseq());
			cstmt.setString(7, eDto.getDetailad());
			cstmt.setString(8, eDto.getMobile());
			cstmt.setString(9, eDto.getEmail());
			cstmt.setString(10, eDto.getIndt());
			cstmt.setInt(11, eDto.getDeptno());
			cstmt.setInt(12, eDto.getPositno());
			cstmt.setInt(13, eDto.getPayment());
			cstmt.registerOutParameter(14, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
			result = cstmt.getInt(14);
			rj.setResult(result);
			rj.setMsg("");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return rj;
	}

	public int updatePic(String empno, String pic){
		int result = 0;
		String sql="update emp set pic=? where empno=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pic);
			pstmt.setString(2, empno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return result;
	}
	
	public int deletePic(String empno){
		int result = 0;
		String sql="update emp set pic=null where empno=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, empno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return result;
	}	
	
	public ResultJson<Integer> updateEmp(EmpDto eDto, String ip){
		ResultJson<Integer> rj = new ResultJson<Integer>(); 
		int result = 0;
		String expt = "";
		String sql="{call emp_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		KISA_SHA256 sha = KISA_SHA256.getInstance();
     
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			
			String sha_passwd = sha.Sha256Encrypt(eDto.getPasswd().getBytes());
			String bc_passwd = BCrypt.hashpw(sha_passwd, BCrypt.gensalt());
			
			cstmt.setString(1, eDto.getEmpnm());
			cstmt.setString(2, bc_passwd);
			cstmt.setString(3, eDto.getPic());
			cstmt.setString(4, eDto.getJumin());
			cstmt.setString(5, eDto.getBirth());
			cstmt.setInt(6, eDto.getZipseq());
			cstmt.setString(7, eDto.getDetailad());
			cstmt.setString(8, eDto.getMobile());
			cstmt.setString(9, eDto.getEmail());
			cstmt.setString(10, eDto.getIndt());
			cstmt.setInt(11, eDto.getDeptno());
			cstmt.setInt(12, eDto.getPositno());
			cstmt.setInt(13, eDto.getPayment());
			cstmt.setString(14, eDto.getEmpno());			
			cstmt.registerOutParameter(15, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.registerOutParameter(16, oracle.jdbc.OracleTypes.NVARCHAR);
			cstmt.executeUpdate();
			result = cstmt.getInt(15);
			expt = cstmt.getString(16);
			rj.setResult(result);
			rj.setMsg(expt);
			dbacs_insert(eDto.getEmpno(), "emp_update", expt, ip);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return rj;
	}	
	
	public int login(String empno, String passwd, String manager, String mpasswd, String ip){
		int result = 0;
//		String sql = "SELECT e.passwd AS epasswd, m.passwd AS mpasswd FROM EMP e left outer JOIN manage m ON e.empno = m.manager where empno=? and e.outdt is null"; 
		String sql = "{call emp_login(?, ?, ?)}";
		Connection conn = null;
//		PreparedStatement pstmt = null;
		CallableStatement  cstmt = null;
//		ResultSet rs = null;
		
		KISA_SHA256 sha = KISA_SHA256.getInstance();
     
		try {
			conn = DBManager.getConnection();
//			pstmt = conn.prepareStatement(sql);
			cstmt = conn.prepareCall(sql);
			
//			pstmt.setString(1, empno);
			
			cstmt.setString(1, empno);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);
			
			cstmt.executeQuery();

			String db_epasswd = cstmt.getString(2);
			String db_mpasswd = cstmt.getString(3);
			
			String sha_passwd = sha.Sha256Encrypt(passwd.getBytes());
			String sha_mpasswd = sha.Sha256Encrypt(mpasswd.getBytes());
						
//			if(rs.next()){
				if (manager.equals("0")){
//					if (BCrypt.checkpw(sha_passwd, rs.getString("epasswd")) ) {
					if (BCrypt.checkpw(sha_passwd, db_epasswd)) {
						result = 1;
						weblog_insert(empno, ip, "y");
					} else {
						result = 0;
						weblog_insert(empno, ip, "n");
					}					
				} else if (manager.equals("1")){
//					if (BCrypt.checkpw(sha_passwd, rs.getString("epasswd")) && BCrypt.checkpw(sha_mpasswd, rs.getString("mpasswd"))) {
					if (BCrypt.checkpw(sha_passwd, db_epasswd) && BCrypt.checkpw(sha_mpasswd, db_mpasswd)) {
						result = 1;
						weblog_insert(empno, ip, "y");
					} else {
						result = 0;
						weblog_insert(empno, ip, "n");
					}		
				}
//			} else {
//				result = -1;
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}
	
	public void weblog_insert(String empno, String ip, String sfyn){
		String sql = "{call weblog_insert(?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, empno);
			cstmt.setString(2, ip);
			cstmt.setString(3, sfyn);
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
	}

	public void dbacs_insert(String empno, String procnm, String expt, String ip ){
		String sql = "{call dbacs_insert(?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, empno);
			cstmt.setString(2, procnm);			
			cstmt.setString(3, expt);
			cstmt.setString(4, ip);
			cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
			cstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
	}	
	
	public int login_manager(String empno, String passwd, String manager, String mpasswd){
		int result = 0;
		String sql = "select passwd from emp e left outer join manager m on e.empno = m.manager where e.empno=?"; 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		KISA_SHA256 sha = KISA_SHA256.getInstance();
     
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, empno);
			rs = pstmt.executeQuery();

			String sha_passwd = sha.Sha256Encrypt(passwd.getBytes());
						
			if(rs.next()){
				if (BCrypt.checkpw(sha_passwd, rs.getString("passwd")) ) {
					result = 1;
				} else {
					result = 0;
				}
			} else {
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		
		return result;
	}
	
	public EmpDto getEmpOneByEmpno(String empno){
		EmpDto eDto = null;
		String sql = "select * from emp where empno=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, empno);
			rs = pstmt.executeQuery();
			if (rs.next()){
				eDto = new EmpDto();
				eDto.setEmpnm(rs.getString("empnm"));
				eDto.setPasswd(rs.getString("passwd"));
				eDto.setPic(rs.getString("pic"));
				eDto.setJumin(rs.getString("jumin"));
				eDto.setBirth(rs.getString("birth"));
				eDto.setZipseq(rs.getInt("zipseq"));
				eDto.setDetailad(rs.getString("detailad"));
				eDto.setMobile(rs.getString("mobile"));
				eDto.setEmail(rs.getString("email"));
				eDto.setIndt(rs.getString("indt"));
				eDto.setOutdt(rs.getString("outdt"));
				eDto.setEmpgb(rs.getString("empgb"));
				eDto.setRegdt(rs.getString("regdt"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBManager.close(conn, pstmt, rs);
		}
		
		return eDto;
	}	
	
//	 관리자 비번 암호화 처리
	public int updateManager (String manager, String passwd){
		int result = 0;
		String sql="{call manager_update(?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		KISA_SHA256 sha = KISA_SHA256.getInstance();
		
		try {
			conn = DBManager.getConnection();
			cstmt = conn.prepareCall(sql);
			
			String sha_passwd = sha.Sha256Encrypt(passwd.getBytes());
			String bc_passwd = BCrypt.hashpw(sha_passwd, BCrypt.gensalt());
			
			cstmt.setString(1, manager);
			cstmt.setString(2, bc_passwd);
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
