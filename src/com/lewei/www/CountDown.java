package com.lewei.www;

import java.awt.Label;

public class CountDown implements Runnable {

	private Clock clock;
	private long time;

	public CountDown(Clock clock) {
		this.clock = clock;
		// 将时间换算成秒
		time = clock.getHours() * 60 * 60 + clock.getMin() * 60
				+ clock.getSecond();
	}

	public void run() {
		while (time >= 0) {
			try {
				Thread.sleep(1000);
				time -= 1;// 时间减去一秒
				clock.setHours((int) time / (60 * 60));
				clock.setMin((int) (time / 60) % 60);
				clock.setSecond((int) time % 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// return clock;
	public Clock getTime() {
		return clock;
	}

	public static void main(String[] args) {
		Clock clock = new Clock(0, 10, 0);

		CountDown jishi = new CountDown(clock);
		Display show = new Display(jishi.getTime());
		// 显示 show = new 显示();
		new Thread(show).start();
		new Thread(jishi).start();

	}
}

class Display implements Runnable {
	private Clock clock;
	
	public Display(Clock clock) {
		this.clock = clock;
	}

	public void run() {

		while (clock.getHours() != 0 || clock.getMin() != 0
				|| clock.getSecond() != 0) {
			try {
				System.out.println(String.format("%02d", clock.getHours())
						+ ":" + String.format("%02d", clock.getMin()) + ":"
						+ String.format("%02d", clock.getSecond()));
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}