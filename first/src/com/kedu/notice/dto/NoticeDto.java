package com.kedu.notice.dto;

public class NoticeDto {
	private int seq;
	private String title;
	private String content;
	private int cnt;
	private String manager;
	private String regdtt;
	private String upyn;
	private int result;
	
	public NoticeDto(){
		
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getRegdtt() {
		return regdtt;
	}
	public void setRegdtt(String regdtt) {
		this.regdtt = regdtt;
	}
	public String getUpyn() {
		return upyn;
	}
	public void setUpyn(String upyn) {
		this.upyn = upyn;
	}
	
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "NoticeDto [seq=" + seq + ", title=" + title + ", content=" + content + ", cnt=" + cnt + ", manager="
				+ manager + "]";
	}
	
	
	
}
