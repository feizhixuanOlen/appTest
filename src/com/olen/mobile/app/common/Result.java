package com.olen.mobile.app.common;

import java.util.ArrayList;

public class Result<T> {

	private int code;

	private String msg = "";
	private String content;
	private String _ut;

	private T _t;

	private ArrayList<T> data = new ArrayList<T>();

	public Result() {
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String get_ut() {
		return _ut;
	}

	public void set_ut(String _ut) {
		this._ut = _ut;
	}

	public T get_t() {
		return _t;
	}

	public void set_t(T _t) {
		this._t = _t;
	}

	public ArrayList<T> getData() {
		return data;
	}

	public void setData(ArrayList<T> data) {
		this.data = data;
	}
}
