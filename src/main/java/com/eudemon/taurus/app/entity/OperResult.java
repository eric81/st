package com.eudemon.taurus.app.entity;

public class OperResult extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = -3462899920938256116L;
	
	public static String CODE_SUCCESS = "200";
	public static String CODE_FAIL = "500";
	
	public String code = CODE_SUCCESS;
	public String message = "success";
	public Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
