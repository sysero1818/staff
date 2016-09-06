package com.kedu.sch.dto;

public class SchDto {
	private int schno;
	private String empno;
	private String startdt;
	private String enddt;
	private String school;
	private String major;
	private String period;
	
	public int getSchno() {
		return schno;
	}
	public void setSchno(int schno) {
		this.schno = schno;
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
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	@Override
	public String toString() {
		return "SchDto [schno=" + schno + ", empno=" + empno + ", startdt=" + startdt + ", enddt=" + enddt + ", school="
				+ school + ", major=" + major + ", period=" + period + "]";
	}

}
