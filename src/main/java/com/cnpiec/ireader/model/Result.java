package com.cnpiec.ireader.model;

public class Result {
	private String status;
	private String msg;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

}
