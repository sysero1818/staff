package com.kedu.prj.dto;

public class PrjDto {
	private int prjno;
	private String prjnm;
	private String prjcontent;
	private String startdt;
	private String enddt;
	private String empno;
	private String empnm;
	private int	compno;
	private String compnm;
	private String regdt;
	private String upyn;
	
	public PrjDto(){
		
	}

	public int getPrjno() {
		return prjno;
	}

	public void setPrjno(int prjno) {
		this.prjno = prjno;
	}

	public String getPrjnm() {
		return prjnm;
	}

	public void setPrjnm(String prjnm) {
		this.prjnm = prjnm;
	}

	public String getPrjcontent() {
		return prjcontent;
	}

	public void setPrjcontent(String prjcontent) {
		this.prjcontent = prjcontent;
	}

	public String getStartdt() {
		return startdt;
	}

	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
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

	public int getCompno() {
		return compno;
	}

	public void setCompno(int compno) {
		this.compno = compno;
	}

	public String getCompnm() {
		return compnm;
	}

	public void setCompnm(String compnm) {
		this.compnm = compnm;
	}

	public String getRegdt() {
		return regdt;
	}

	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}
	
	public String getUpyn() {
		return upyn;
	}

	public void setUpyn(String upyn) {
		this.upyn = upyn;
	}

	@Override
	public String toString() {
		return "PrjDto [prjno=" + prjno + ", prjnm=" + prjnm + ", prjcontent=" + prjcontent + ", startdt=" + startdt
				+ ", enddt=" + enddt + ", empno=" + empno + ", empnm=" + empnm + ", compno=" + compno + ", compnm="
				+ compnm + ", regdt=" + regdt + "]";
	}


	
}
