package com.kedu.emp.dto;

public class CareerDto {
	private int careerno;
	private String empno;
	private String startdt;
	private String enddt;
	private String compnm;
	private String positnm;
	private String charge;
	public int getCareerno() {
		return careerno;
	}
	public void setCareerno(int careerno) {
		this.careerno = careerno;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
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
	public String getCompnm() {
		return compnm;
	}
	public void setCompnm(String compnm) {
		this.compnm = compnm;
	}
	public String getPositnm() {
		return positnm;
	}
	public void setPositnm(String positnm) {
		this.positnm = positnm;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	@Override
	public String toString() {
		return "CareerDto [careerno=" + careerno + ", empno=" + empno + ", startdt=" + startdt + ", enddt=" + enddt
				+ ", compnm=" + compnm + ", positnm=" + positnm + ", charge=" + charge + "]";
	}

	
	
}
