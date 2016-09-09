package com.kedu.common;

public class ResultJson<T> {
	private T result;
	private String msg;
	
	public ResultJson() {
	}
	
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "ResultJson [result=" + result + ", msg=" + msg + "]";
	}
	
}
