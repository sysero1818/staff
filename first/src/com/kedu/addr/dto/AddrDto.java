package com.kedu.addr.dto;

public class AddrDto {
	private int seq;
	private String zipcode;
	private String sido;
	private String sigungu;
	private String dong;
	private String ri;
	private String bldg;
	private String bungi;
	
	public AddrDto(){
		
	}
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getRi() {
		return ri;
	}
	public void setRi(String ri) {
		this.ri = ri;
	}
	public String getBldg() {
		return bldg;
	}
	public void setBldg(String bldg) {
		this.bldg = bldg;
	}
	public String getBungi() {
		return bungi;
	}
	public void setBungi(String bungi) {
		this.bungi = bungi;
	}
	@Override
	public String toString() {
		return "AddrDto [seq=" + seq + ", zipcode=" + zipcode + ", sido=" + sido + ", sigungu=" + sigungu + ", dong="
				+ dong + ", ri=" + ri + ", bldg=" + bldg + ", bungi=" + bungi + "]";
	}
	
	
}
