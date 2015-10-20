package com.lewei.model;

public class AlarmData {
	/** 记录id*/
	private int id;
	
	/** 板子id*/
	private String bid;
	
	/** 按键编号*/
	private String keyid;
	
	/** 发生时间*/
	private String ttime;
	
	/** 按键按下状态 1按下 0抬起*/
	private int yn;
	
	/** 状态未*/
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getTtime() {
		return ttime;
	}

	public void setTtime(String ttime) {
		this.ttime = ttime;
	}

	public int getYn() {
		return yn;
	}

	public void setYn(int yn) {
		this.yn = yn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
