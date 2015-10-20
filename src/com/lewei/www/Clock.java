package com.lewei.www;

public class Clock {

	// –° ±
	private int hours;

	// ∑÷÷”
	private int min;

	// √Î
	private int second;

	public Clock(int hours, int min, int second) {
		this.hours = hours;
		this.min = min;
		this.second = second;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

}
