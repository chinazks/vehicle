package com.xforceplus.data.tools;

public class JSONMapResult {
	private int code;
	private String msg;
	private int count;
	private Object[] data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	
	public JSONMapResult build(int code,String msg, int count,Object[] data ) {
		return new JSONMapResult(code,msg,count,data);
	}
	
	public JSONMapResult() {}
	public JSONMapResult(int code, String msg, int count, Object[] data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}
	
	
	
}
