package com.asdzheng.suitedrecyclerview.bean;

import java.io.Serializable;

/**
 * 登录接口
 * @author hq3
 *
 */

public class BaseResponse<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private String used_time;
	private int code;

	public String getUsed_time() {
		return used_time;
	}

	public void setUsed_time(String used_time) {
		this.used_time = used_time;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
