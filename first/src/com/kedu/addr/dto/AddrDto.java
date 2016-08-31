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
	private String address1;
	private String address2;
	
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Override
	public String toString() {
		return "AddrDto [seq=" + seq + ", zipcode=" + zipcode + ", sido=" + sido + ", sigungu=" + sigungu + ", dong="
				+ dong + ", ri=" + ri + ", bldg=" + bldg + ", bungi=" + bungi + "]";
	}
	
	
}
