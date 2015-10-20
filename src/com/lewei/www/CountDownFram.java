package com.lewei.www;
import java.util.Calendar;

import java.util.Date;

import java.util.Timer;

import java.util.TimerTask;


import javax.swing.JFrame;

import javax.swing.JLabel;

/**

 * 

 * @author wesley

 * @date 2015年1月28日

 *

 */

public class CountDownFram {

 

 private long longTime;

 private long currentTime;

 private long distTime;

 private long day, hour, minutes, seconds;

  

 public CountDownFram() {

 CDown();

 }

  

 public void CDown() {

  Timer timer = new Timer();

  JFrame jf = new JFrame();

  final JLabel jl = new JLabel();

  

  jf.add(jl);

  jf.setVisible(true);

  jf.setSize(350, 100);

  jf.setTitle("倒计时软件");

  jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  

  timer.schedule(new TimerTask() {

   @Override

   public void run() {

    Calendar cal = Calendar.getInstance();

    //设置预定的时间

    cal.set(2015, 0, 28, 17, 30, 0);//0代表1月 ，11代表12月

    // 返回历元到指定时间的毫秒数。

    longTime = cal.getTimeInMillis();

    // 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。

    currentTime = new Date().getTime();

    //距离的时间

    distTime = longTime - currentTime;

    //得到天数

    day = ((distTime / 1000) / (3600 * 24));

    //得到小时数

    hour = ((distTime / 1000) - day * 86400) / 3600;

    //得到分钟数

    minutes = ((distTime / 1000) - day * 86400 - hour * 3600) / 60;

    //得到秒数

    seconds = (distTime / 1000) - day * 86400 - hour * 3600

      - minutes * 60;

    jl.setText("倒计时" + day + " 天 " + hour + "小时 :" + minutes

      + "分钟 :" + seconds + "秒");

   }

  }, 0, 1000);

 }

  

 public static void main(String[] args) {

 new CountDownFram();

 }

}