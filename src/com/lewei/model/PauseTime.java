package com.lewei.model;

public class PauseTime {

	/** 开始时间 */
	private String time;

	/** 持续时间(单位：分钟) */
	private long duration;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long l) {
		this.duration = l;
	}
}
