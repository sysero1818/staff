package com.kedu.notice.dto;

public class NotidatDto {
	private int seq;
	private int dseq;
	private String dcontent;
	private String dregdtt;
	private String dgubun;
	private String empno;
	private String manager;
	private int result;
	
	public NotidatDto() {

	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getDseq() {
		return dseq;
	}

	public void setDseq(int dseq) {
		this.dseq = dseq;
	}

	public String getDcontent() {
		return dcontent;
	}

	public void setDcontent(String dcontent) {
		this.dcontent = dcontent;
	}

	public String getDregdtt() {
		return dregdtt;
	}

	public void setDregdtt(String dregdtt) {
		this.dregdtt = dregdtt;
	}

	public String getDgubun() {
		return dgubun;
	}

	public void setDgubun(String dgubun) {
		this.dgubun = dgubun;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "NotidatDto [seq=" + seq + ", dseq=" + dseq + ", dcontent=" + dcontent + ", dregdtt=" + dregdtt
				+ ", dgubun=" + dgubun + ", empno=" + empno + ", manager=" + manager + "]";
	}

	
}
