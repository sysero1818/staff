package com.kedu.emp.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kedu.common.DBManager;
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
	public int getCountRow() {
		int x= 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			conn=DBManager.getConnection();
			//System.out.println("getConnection");
			pstmt=conn.prepareStatement("select count(empno) from emp");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				x=rs.getInt(1);
			}
		}catch(Exception ex){
			System.out.println("getListCount 에러: " + ex);			
		}finally{
			DBManager.close(conn, pstmt);
		}
		return x;
	}
	
	public List<EmpDto> selectAllEmps(int page, int perPageRow, String sh_empno, String sh_empnm, String sh_indt_st, String sh_indt_ed) {
		String sql = "{call emp_list(?,?,?,?,?,?,?)}";
		List<EmpDto> list = new ArrayList<EmpDto>();

		int startrow=(page - 1) * perPageRow + 1;
		int endrow=startrow + perPageRow - 1;

		Connection conn = null;
		CallableStatement  pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareCall(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			pstmt.setString(3, sh_empno);
			pstmt.setString(4, sh_empnm);
			pstmt.setString(5, sh_indt_st);
			pstmt.setString(6, sh_indt_ed);			
			pstmt.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR);
			pstmt.executeQuery();
			rs = (ResultSet) pstmt.getObject(7);			

			while (rs.next()) {
				EmpDto empDto = new EmpDto();
				empDto.setEmpno(rs.getString("empno"));
				empDto.setEmpnm(rs.getString("empnm"));
				empDto.setPasswd(rs.getString("passwd"));
				empDto.setPic(rs.getString("pic"));
				empDto.setJumin(rs.getString("jumin"));
				empDto.setBirth(rs.getString("birth"));
				empDto.setZipseq(rs.getInt("zipseq"));
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
				empDto.setPayment(rs.getInt("payment"));
				list.add(empDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	
	public int insertEmp(EmpDto eDto){
		int result = 0;
		String sql="{call emp_insert(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		KISA_SHA256 sha = KISA_SHA256.getInstance();
		
/*      p_empnm    	IN	emp.empnm%type
      , p_passwd	IN	emp.passwd%type
      , p_pic		IN	emp.pic%type
      , p_jumin		IN	emp.jumin%type
      , p_birth		IN	emp.birth%type
      , p_zipseq	IN	emp.zipseq%type
      , p_detailad	IN	emp.detailad%type
      , p_mobile	IN	emp.mobile%type
      , p_email		IN	emp.email%type
      , p_indt		IN	emp.indt%type
      , p_deptno	IN	dept.deptno%type
      , p_positno	IN	posit.positno%type
      , p_payment	IN	payhist.payment%type
*/
		
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
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
	
	public int updateEmp(EmpDto eDto){
		int result = 0;
		String sql="{call emp_update(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement  cstmt = null;
		KISA_SHA256 sha = KISA_SHA256.getInstance();
		
/*      p_empnm    	IN	emp.empnm%type
      , p_passwd	IN	emp.passwd%type
      , p_pic		IN	emp.pic%type
      , p_jumin		IN	emp.jumin%type
      , p_birth		IN	emp.birth%type
      , p_zipseq	IN	emp.zipseq%type
      , p_detailad	IN	emp.detailad%type
      , p_mobile	IN	emp.mobile%type
      , p_email		IN	emp.email%type
      , p_indt		IN	emp.indt%type
      , p_deptno	IN	dept.deptno%type
      , p_positno	IN	posit.positno%type
      , p_payment	IN	payhist.payment%type
*/
      
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
			cstmt.executeUpdate();
			result = cstmt.getInt(15);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, cstmt);
		}
		
		return result;
	}	
	
	public int login(String empno, String passwd, String manager, String mpasswd){
		int result = 0;
		String sql = "SELECT e.passwd AS epasswd, m.passwd AS mpasswd FROM EMP e left outer JOIN manage m ON e.empno = m.manager where empno=?"; 
		
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
			String sha_mpasswd = sha.Sha256Encrypt(mpasswd.getBytes());
						
			if(rs.next()){
				if (manager.equals("0")){
					if (BCrypt.checkpw(sha_passwd, rs.getString("epasswd")) ) {
						result = 1;
					} else {
						result = 0;
					}					
				} else if (manager.equals("1")){
					if (BCrypt.checkpw(sha_passwd, rs.getString("epasswd")) && BCrypt.checkpw(sha_mpasswd, rs.getString("mpasswd"))) {
						result = 1;
					} else {
						result = 0;
					}		
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
