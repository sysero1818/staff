package com.kedu.cert.dto;

public class CertDto {
	private int cno;
	private String empno;
	private String certno;
	private String certnm;
	private String aqdt;
	private String issuorgan;
	private String renewstdt;
	private String reneweddt;
	private String period;
	
	public CertDto(){
		
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getCertnm() {
		return certnm;
	}
	public void setCertnm(String certnm) {
		this.certnm = certnm;
	}
	public String getAqdt() {
		return aqdt;
	}
	public void setAqdt(String aqdt) {
		this.aqdt = aqdt;
	}
	public String getIssuorgan() {
		return issuorgan;
	}
	public void setIssuorgan(String issuorgan) {
		this.issuorgan = issuorgan;
	}
	public String getRenewstdt() {
		return renewstdt;
	}
	public void setRenewstdt(String renewstdt) {
		this.renewstdt = renewstdt;
	}
	public String getReneweddt() {
		return reneweddt;
	}
	public void setReneweddt(String reneweddt) {
		this.reneweddt = reneweddt;
	}
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	@Override
	public String toString() {
		return "CertDto [cno=" + cno + ", empno=" + empno + ", certno=" + certno + ", certnm=" + certnm + ", aqdt="
				+ aqdt + ", issuorgan=" + issuorgan + ", renewstdt=" + renewstdt + ", reneweddt=" + reneweddt + "]";
	}
	
	
}
