package com.lewei.model;

public class RealNum {
	/** 岗位 */
	private String GW;
	
	/** 日期 */
	private String date;
	
	/** 总产量 */
	private int sum;
	
	/** 早班产量 */
	private int zao;
	
	/** 中班产量 */
	private int zhong;
	
	/** 晚班产量 */
	private int wan;

	public String getGW() {
		return GW;
	}

	public void setGW(String gW) {
		GW = gW;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getZao() {
		return zao;
	}

	public void setZao(int zao) {
		this.zao = zao;
	}

	public int getZhong() {
		return zhong;
	}

	public void setZhong(int zhong) {
		this.zhong = zhong;
	}

	public int getWan() {
		return wan;
	}

	public void setWan(int wan) {
		this.wan = wan;
	}
}
