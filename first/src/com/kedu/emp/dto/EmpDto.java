package com.kedu.emp.dto;

public class EmpDto {
	private String empno;
	private String empnm;
	private String passwd;
	private String pic;
	private String jumin;
	private String birth;
	private int zipseq;
	private String zipcode;
	private String basicad;
	private String detailad;
	private String mobile;
	private String email;
	private String indt;
	private String outdt;
	private String empgb;
	private String regdt;
	private int deptno;
	private String deptnm;
	private int positno;
	private String positnm;
	private int payment;
	private String upyn;
	
	
	public EmpDto() {

	}


	public String getEmpno() {
		return empno;
	}


	public void setEmpno(String empno) {
		this.empno = empno;
	}


	public String getEmpnm() {
		return empnm;
	}


	public void setEmpnm(String empnm) {
		this.empnm = empnm;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public String getPic() {
		return pic;
	}


	public void setPic(String pic) {
		this.pic = pic;
	}


	public String getJumin() {
		return jumin;
	}


	public void setJumin(String jumin) {
		this.jumin = jumin;
	}


	public String getBirth() {
		return birth;
	}


	public void setBirth(String birth) {
		this.birth = birth;
	}


	public int getZipseq() {
		return zipseq;
	}


	public void setZipseq(int zipseq) {
		this.zipseq = zipseq;
	}


	public String getZipcode() {
		return zipcode;
	}


	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getBasicad() {
		return basicad;
	}


	public void setBasicad(String basicad) {
		this.basicad = basicad;
	}


	public String getDetailad() {
		return detailad;
	}


	public void setDetailad(String detailad) {
		this.detailad = detailad;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getIndt() {
		return indt;
	}


	public void setIndt(String indt) {
		this.indt = indt;
	}


	public String getOutdt() {
		return outdt;
	}


	public void setOutdt(String outdt) {
		this.outdt = outdt;
	}


	public String getEmpgb() {
		return empgb;
	}


	public void setEmpgb(String empgb) {
		this.empgb = empgb;
	}


	public String getRegdt() {
		return regdt;
	}


	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}


	public int getDeptno() {
		return deptno;
	}


	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}


	public String getDeptnm() {
		return deptnm;
	}


	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}


	public int getPositno() {
		return positno;
	}


	public void setPositno(int positno) {
		this.positno = positno;
	}


	public String getPositnm() {
		return positnm;
	}


	public void setPositnm(String positnm) {
		this.positnm = positnm;
	}


	public int getPayment() {
		return payment;
	}


	public void setPayment(int payment) {
		this.payment = payment;
	}


	public String getUpyn() {
		return upyn;
	}

	public void setUpyn(String upyn) {
		this.upyn = upyn;
	}


	@Override
	public String toString() {
		return "EmpDto [empno=" + empno + ", empnm=" + empnm + ", passwd=" + passwd + ", pic=" + pic + ", jumin="
				+ jumin + ", birth=" + birth + ", zipseq=" + zipseq + ", detailad=" + detailad + ", mobile=" + mobile
				+ ", email=" + email + ", indt=" + indt + ", outdt=" + outdt + ", empgb=" + empgb + ", regdt=" + regdt
				+ ", deptno=" + deptno + ", deptnm=" + deptnm + ", positno=" + positno + ", positnm=" + positnm
				+ ", payment=" + payment + "]";
	}

	
	
}
