package com.kedu.empskill.dto;

public class EmpSkillDto {
	private String empno;
	private int skillno;
	private String skillnm;
	private int ctno;
	private String ctnm;
	
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public int getSkillno() {
		return skillno;
	}
	public void setSkillno(int skillno) {
		this.skillno = skillno;
	}
	public String getSkillnm() {
		return skillnm;
	}
	public void setSkillnm(String skillnm) {
		this.skillnm = skillnm;
	}
	public int getCtno() {
		return ctno;
	}
	public void setCtno(int ctno) {
		this.ctno = ctno;
	}
	public String getCtnm() {
		return ctnm;
	}
	public void setCtnm(String ctnm) {
		this.ctnm = ctnm;
	}

	@Override
	public String toString() {
		return "EmpSkillDto [empno=" + empno + ", skillno=" + skillno + ", skillnm=" + skillnm + ", ctno=" + ctno
				+ ", ctnm=" + ctnm + "]";
	}

}
