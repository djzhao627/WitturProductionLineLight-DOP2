package com.lewei.www;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.lewei.dao.WarningLightDao;
import com.lewei.model.AlarmData;
import com.lewei.model.ChangeTime;
import com.lewei.model.PauseTime;
import com.lewei.model.Takt;

public class WarningScreen extends JFrame {

	private static int redBtnCount = 0;
	private static long changeTimeCD = 0;

	private static boolean isChange = false;
	private static boolean isLost = false;
	private static boolean isPause = false;

	private SimpleDateFormat df_Hm = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat df_Hms = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat df_ymr = new SimpleDateFormat("yyyy-MM-dd");

	private static List<PauseTime> pauseTimeList = new ArrayList<>();
	private static List<ChangeTime> changeTimeList = new ArrayList<>();
	private static List<Takt> taktTimeList = new ArrayList<>();

	private Clock clock;
	private static long pauseTime = 0;
	private static long lostTime = 0;
	private static long taktTime = 0;

	private WarningLightDao wld = null;
	/** 节拍计数 */
	private static int taktCount = 0;
	/** 节拍个数 */
	private static int taktNum = -1;
	/** 班次 */
	private static int rangerNum = -1;
	/** 实际量 */
	private static int realCount = 0;
	/** 总量 */
	private static int t_num = 0;

	private static Clock taktClock = null;
	private static Clock pauseClock = null;
	private static Clock lostClock = null;
	private static Clock changeClock = null;

	private boolean red1 = false;
	private boolean red2 = false;
	private boolean red3 = false;
	private boolean red4 = false;
	private boolean red5 = false;
	private boolean red6 = false;
	private boolean red7 = false;
	private boolean red8 = false;

	private boolean yellow1 = false;
	private boolean yellow2 = false;
	private boolean yellow3 = false;
	private boolean yellow4 = false;
	private boolean yellow5 = false;
	private boolean yellow6 = false;
	private boolean yellow7 = false;
	private boolean yellow8 = false;

	private boolean green1 = false;
	private boolean green2 = false;
	private boolean green3 = false;
	private boolean green4 = false;
	private boolean green5 = false;
	private boolean green6 = false;
	private boolean green7 = false;
	private boolean green8 = false;

	private JPanel contentPane;
	private Label total;
	private JLabel line;
	private Label redLight;
	private Label greenLight;
	private Label blueLight;
	private Label yellowLight;
	private Label total_num;
	private Label planIf;
	private Label planIf_num;
	private Label real;
	private final Label real_num;
	private Label pause;
	private final Label pause_time;
	private Label takt;
	private final Label takt_time;
	private Label lost;
	private final Label lost_time;
	private Label p01;
	private Label p02;
	private Label p03;
	private Label p04;
	private Label p05;
	private Label p06;
	private Label p07;
	private Label p08;

	private Constant constant;
	private JSeparator separator;
	/** 产线ID */
	protected int TPLineID = 0;
	/** 计划ID */
	protected int TPPlanID = 0;
	private JLabel date;

	// private int zaoBan = Integer.pdf_Hm.parse("07:15");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarningScreen frame = new WarningScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WarningScreen() {
		setAlwaysOnTop(true);
		setUndecorated(true);
		constant = new Constant();
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1365, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 10, 1345, 748);
		contentPane.add(panel);
		panel.setLayout(null);

		Panel panel_1 = new Panel();
		panel_1.setForeground(new Color(255, 255, 255));
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(44, 0, 1332, 748);
		panel.add(panel_1);
		panel_1.setLayout(null);

		line = new JLabel("\u7EBF\u522B:AA hanger Line");
		line.setHorizontalAlignment(SwingConstants.CENTER);
		line.setAlignmentX(Component.RIGHT_ALIGNMENT);
		line.setBounds(219, 40, 774, 70);
		panel_1.add(line);
		line.setFont(new Font("华文新魏", Font.PLAIN, 60));
		line.setForeground(Color.WHITE);

		ImageIcon icon = new ImageIcon(getClass().getResource(
				"/images/Wittur_Logo.gif"));
		icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),
				icon.getIconHeight(), Image.SCALE_DEFAULT));

		JLabel logo_r = new JLabel("");
		logo_r.setIcon(icon);
		logo_r.setBounds(1145, 15, 102, 95);
		panel_1.add(logo_r);

		redLight = new Label("");
		redLight.setBackground(new Color(255, 51, 0));
		redLight.setBounds(477, 140, 70, 70);
		panel_1.add(redLight);

		greenLight = new Label("");
		greenLight.setBackground(new Color(51, 255, 0));
		greenLight.setBounds(553, 140, 70, 70);
		panel_1.add(greenLight);

		blueLight = new Label("");
		blueLight.setBackground(new Color(0, 51, 255));
		blueLight.setBounds(705, 140, 70, 70);
		panel_1.add(blueLight);

		yellowLight = new Label("");
		yellowLight.setBackground(new Color(255, 255, 0));
		yellowLight.setBounds(629, 140, 70, 70);
		panel_1.add(yellowLight);

		date = new JLabel("");
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setFont(new Font("楷体", Font.BOLD, 34));
		date.setForeground(new Color(255, 255, 255));
		date.setBounds(10, 140, 396, 70);
		long timemillis = System.currentTimeMillis();
		// 转换日期显示格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		date.setText("日期：" + sdf.format(timemillis));
		panel_1.add(date);

		JLabel datetime = new JLabel("-----------");
		datetime.setHorizontalAlignment(SwingConstants.CENTER);
		datetime.setForeground(Color.WHITE);
		datetime.setFont(new Font("楷体", Font.BOLD, 34));
		datetime.setBounds(831, 140, 406, 70);
		panel_1.add(datetime);

		total = new Label("Total/\u603B\u91CF");
		total.setAlignment(Label.CENTER);
		total.setForeground(Color.WHITE);
		total.setFont(new Font("Dialog", Font.BOLD, 45));
		total.setBounds(0, 255, 376, 66);
		panel_1.add(total);

		total_num = new Label("0");
		total_num.setAlignment(Label.CENTER);
		total_num.setForeground(Color.WHITE);
		total_num.setFont(new Font("新宋体", Font.BOLD, 80));
		total_num.setBounds(10, 327, 376, 89);
		panel_1.add(total_num);

		planIf = new Label("Theoretical/\u7406\u8BBA\u4EA7\u91CF");
		planIf.setAlignment(Label.CENTER);
		planIf.setForeground(Color.WHITE);
		planIf.setFont(new Font("Dialog", Font.BOLD, 45));
		planIf.setBounds(366, 255, 495, 66);
		panel_1.add(planIf);

		planIf_num = new Label("0");
		planIf_num.setAlignment(Label.CENTER);
		planIf_num.setForeground(Color.WHITE);
		planIf_num.setFont(new Font("新宋体", Font.BOLD, 80));
		planIf_num.setBounds(412, 320, 449, 104);
		panel_1.add(planIf_num);

		real = new Label("Actual/\u5B9E\u9645\u4EA7\u91CF");
		real.setAlignment(Label.CENTER);
		real.setForeground(Color.WHITE);
		real.setFont(new Font("Dialog", Font.BOLD, 45));
		real.setBounds(841, 255, 406, 54);
		panel_1.add(real);

		real_num = new Label("0");
		real_num.setForeground(Color.WHITE);
		real_num.setFont(new Font("新宋体", Font.BOLD, 80));
		real_num.setAlignment(Label.CENTER);
		real_num.setBounds(841, 320, 406, 104);
		panel_1.add(real_num);

		pause = new Label("Pause/\u6682\u505C");
		pause.setForeground(Color.YELLOW);
		pause.setFont(new Font("新宋体", Font.BOLD, 45));
		pause.setAlignment(Label.CENTER);
		pause.setBounds(0, 448, 406, 49);
		panel_1.add(pause);

		pause_time = new Label("00:00:00");
		pause_time.setForeground(Color.YELLOW);
		pause_time.setFont(new Font("新宋体", Font.BOLD, 75));
		pause_time.setAlignment(Label.CENTER);
		pause_time.setBounds(0, 511, 406, 75);
		panel_1.add(pause_time);

		takt = new Label("Takt/\u8282\u62CD");
		takt.setForeground(Color.WHITE);
		takt.setFont(new Font("新宋体", Font.BOLD, 45));
		takt.setAlignment(Label.CENTER);
		takt.setBounds(412, 448, 449, 45);
		panel_1.add(takt);

		takt_time = new Label("00:00:00");
		takt_time.setForeground(Color.WHITE);
		takt_time.setFont(new Font("新宋体", Font.BOLD, 75));
		takt_time.setAlignment(Label.CENTER);
		takt_time.setBounds(412, 511, 449, 75);
		panel_1.add(takt_time);

		lost = new Label("Total Loss/\u635F\u5931");
		lost.setForeground(new Color(204, 51, 51));
		lost.setFont(new Font("新宋体", Font.BOLD, 45));
		lost.setAlignment(Label.CENTER);
		lost.setBounds(841, 448, 396, 49);
		panel_1.add(lost);

		lost_time = new Label("00:00:00");
		lost_time.setForeground(new Color(255, 51, 0));
		lost_time.setFont(new Font("新宋体", Font.BOLD, 75));
		lost_time.setAlignment(Label.CENTER);
		lost_time.setBounds(841, 511, 396, 73);
		panel_1.add(lost_time);

		p01 = new Label("S1");
		p01.setFont(new Font("Arial", Font.ITALIC, 37));
		p01.setAlignment(Label.CENTER);
		p01.setBackground(Color.DARK_GRAY);
		p01.setForeground(new Color(153, 153, 153));
		p01.setBounds(0, 637, 85, 85);
		panel_1.add(p01);

		p02 = new Label("S2");
		p02.setForeground(new Color(153, 153, 153));
		p02.setFont(new Font("Arial", Font.ITALIC, 37));
		p02.setBackground(Color.DARK_GRAY);
		p02.setAlignment(Label.CENTER);
		p02.setBounds(154, 637, 85, 85);
		panel_1.add(p02);

		p03 = new Label("S3");
		p03.setForeground(new Color(153, 153, 153));
		p03.setFont(new Font("Arial", Font.ITALIC, 37));
		p03.setBackground(Color.DARK_GRAY);
		p03.setAlignment(Label.CENTER);
		p03.setBounds(311, 637, 85, 85);
		panel_1.add(p03);

		p04 = new Label("S4");
		p04.setForeground(new Color(153, 153, 153));
		p04.setFont(new Font("Arial", Font.ITALIC, 37));
		p04.setBackground(Color.DARK_GRAY);
		p04.setAlignment(Label.CENTER);
		p04.setBounds(472, 637, 85, 85);
		panel_1.add(p04);

		p05 = new Label("S5");
		p05.setForeground(new Color(153, 153, 153));
		p05.setFont(new Font("Arial", Font.ITALIC, 37));
		p05.setBackground(Color.DARK_GRAY);
		p05.setAlignment(Label.CENTER);
		p05.setBounds(639, 637, 85, 85);
		panel_1.add(p05);

		p06 = new Label("S6");
		p06.setForeground(new Color(153, 153, 153));
		p06.setFont(new Font("Arial", Font.ITALIC, 37));
		p06.setBackground(Color.DARK_GRAY);
		p06.setAlignment(Label.CENTER);
		p06.setBounds(814, 637, 85, 85);
		panel_1.add(p06);

		p07 = new Label("S7");
		p07.setForeground(new Color(153, 153, 153));
		p07.setFont(new Font("Arial", Font.ITALIC, 37));
		p07.setBackground(Color.DARK_GRAY);
		p07.setAlignment(Label.CENTER);
		p07.setBounds(997, 637, 85, 85);
		panel_1.add(p07);

		p08 = new Label("S8");
		p08.setForeground(new Color(153, 153, 153));
		p08.setFont(new Font("Arial", Font.ITALIC, 37));
		p08.setBackground(Color.DARK_GRAY);
		p08.setAlignment(Label.CENTER);
		p08.setBounds(1172, 637, 85, 85);
		panel_1.add(p08);

		// 系统时间显示
		this.setCurrentTimer(datetime);

		separator = new JSeparator();
		separator.setBounds(0, 426, 1237, 18);
		panel_1.add(separator);

		// 实例化一个SwingWorker 控制日期更新
		SwingWorker dateTime = dateUpdate();
		dateTime.execute();

		// 实例化一个SwingWorker 控制UI刷新
		SwingWorker bgProcess = backgroundProcess();
		bgProcess.execute();

		// 实例化SwingWorker 监控按钮状态
		SwingWorker button = buttonLister();
		button.execute();

		// 实例化实时产量读取SwingWorker
		SwingWorker getReal = getRealNum();
		getReal.execute();

	}

	/**
	 * 设置Timer 1000ms实现一次动作 实际是一个线程
	 * 
	 * @param time
	 */
	private void setCurrentTimer(JLabel time) {
		final JLabel varTime = time;
		// 1000毫秒刷新一次
		Timer timeAction = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// 转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				// 更新UI
				varTime.setText("时间：" + df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}

	/**
	 * 设置页面数值不停刷新(后台线程).<br >
	 * 此线程掌管<b>休息时间</b>，<b>节拍时间</b>和<b>损失时间</b>
	 * 
	 * @return
	 */
	private SwingWorker backgroundProcess() {
		SwingWorker process = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() throws Exception {

				// 损失时间处理
				taktClock = new Clock(0, 0, 0);
				pauseClock = new Clock(0, 0, 0);
				lostClock = new Clock(0, 0, 0);
				Timer timeAction = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// ui更新
						refreshUI(lostClock, taktClock, pauseClock);

					}

					/**
					 * UI刷新线程，此线程更新节拍，休息，损失，换班。
					 * 
					 * @param lostClock
					 * @param taktClock
					 * @param pauseClock
					 */
					private void refreshUI(Clock lostClock, Clock taktClock,
							Clock pauseClock) {
						// 时间损耗状态
						if (redBtnCount > 0) {
							isLost = true;
						} else {
							isLost = false;
						}

						/** 节拍倒计时 */
						// 将时间换算成秒
						taktTime = taktClock.getHours() * 60 * 60
								+ taktClock.getMin() * 60
								+ taktClock.getSecond();
						boolean getData = true;
						if (taktTime > 1 && !isPause && !isChange) {
							getData = false;
							if (!isLost) {
								greenLight.setBackground(new Color(51, 255, 0));
							}
							taktTime -= 1;// 时间减去一秒
							if (taktTime == 1) {
								// 节拍增加
								taktCount++;
								// 更新UI
								planIf_num.setText(taktCount + "");
							}
							// 时间换算，存入model
							taktClock.setHours((int) taktTime / (60 * 60));
							taktClock.setMin((int) (taktTime / 60) % 60);
							taktClock.setSecond((int) taktTime % 60);
							// 更新UI
							takt_time.setText(String.format("%02d",
									taktClock.getHours())
									+ ":"
									+ String.format("%02d", taktClock.getMin())
									+ ":"
									+ String.format("%02d",
											taktClock.getSecond()));
						} else if (taktNum > 0 && !isPause && !isChange) {
							// 产品节拍数减少
							taktNum--;
							// 更新节拍数
							taktClock.setSecond(taktTimeList.get(0).getTakt());
							// 获取新数据标记 关
							getData = false;
						} else if (taktNum == 0 && !isPause && !isChange) {
							taktTimeList.remove(0);
							// 判断list是否为空
							if (taktTimeList.size() > 0) {
								// 获取新数据标记 关
								getData = false;
								// 获取新产节拍数
								taktNum = taktTimeList.get(0).getNum();
								// 更新节拍数
								taktClock.setSecond(taktTimeList.get(0)
										.getTakt());
							} else {
								greenLight.setBackground(new Color(190, 190,
										190));
								taktNum = -1;
							}
						}

						// 时间到，停线
						if ((df_Hms.format(new Date())).equals("06:59:59")
								|| (df_Hms.format(new Date()))
										.equals("14:59:59")
								|| (df_Hms.format(new Date()))
										.equals("22:59:59")) {
							System.out.println("time up !");

							// 损耗标记归位
							isLost = false;
							isChange = false;
							isPause = false;

							// 四色灯全部变为灰色
							redLight.setBackground(new Color(190, 190, 190));
							blueLight.setBackground(new Color(190, 190, 190));
							greenLight.setBackground(new Color(190, 190, 190));
							yellowLight.setBackground(new Color(190, 190, 190));

							// 实际产量清零
							real_num.setText("0");
							planIf_num.setText("0");
							total_num.setText("0");

							// Clock清空
							pauseClock = new Clock(0, 0, 0);
							lostClock = new Clock(0, 0, 0);
							taktClock = new Clock(0, 0, 0);
							changeClock = new Clock(0, 0, 0);

							// 时间归位
							lost_time.setText("00:00:00");
							pause_time.setText("00:00:00");
							takt_time.setText("00:00:00");

							// list 清零
							pauseTimeList.clear();
							taktTimeList.clear();
							changeTimeList.clear();

							// 标记变量归位
							taktCount = 0;
							realCount = 0;
							rangerNum = -1;
							taktNum = -1;

							// 计数归位
							taktTime = 0;
							pauseTime = 0;
							lostTime = 0;
							changeTimeCD = 0;

							// TPPlanID归零
							TPPlanID = 0;
							// TPLineID归零
							TPLineID = 0;

						}

						if (taktTimeList.size() <= 0 && getData) {

							wld = new WarningLightDao();
							greenLight.setBackground(new Color(190, 190, 190));

							try {
								if (TPLineID > 0) {
									// 获取损失时间
									String lostT = lost_time.getText();
									// 损失时间插入数据库
									wld.setTPLineLostTime(TPLineID, lostT);

									// 获取实际产量
									String realPro = real_num.getText();
									// 实际产量插入数据库
									wld.setTPLineRealNum(TPLineID,
											Integer.parseInt(realPro));
									// 重置
									TPLineID = 0;
								}

								// 早中晚班开始运行
								t_num = 0;
								long nowHour = df_Hm.parse(
										df_Hm.format(new Date())).getTime();

								if (nowHour >= df_Hm.parse("07:00").getTime()
										&& nowHour <= df_Hm.parse("14:59")
												.getTime()) {
									// 设定为早班
									rangerNum = 0;
									// 获取总量
									String twoNum = wld.getTotalNum0();
									t_num = Integer.parseInt(twoNum.split(";")[0]);
									TPPlanID = Integer.parseInt(twoNum
											.split(";")[1]);
								} else if (nowHour >= df_Hm.parse("15:00")
										.getTime()
										&& nowHour <= df_Hm.parse("22:59")
												.getTime()) {
									// 设定为中班
									rangerNum = 1;
									// 获取总量
									String twoNum = wld.getTotalNum1();
									t_num = Integer.parseInt(twoNum.split(";")[0]);
									TPPlanID = Integer.parseInt(twoNum
											.split(";")[1]);
								} else if ((nowHour >= df_Hm.parse("23:00")
										.getTime() && nowHour <= df_Hm.parse(
										"23:59").getTime())
										|| (nowHour >= df_Hm.parse("00:00")
												.getTime() && nowHour <= df_Hm
												.parse("06:59").getTime())) {
									// 设定为晚班
									rangerNum = 2;
									// 获取总量
									String twoNum = wld.getTotalNum2();
									t_num = Integer.parseInt(twoNum.split(";")[0]);
									TPPlanID = Integer.parseInt(twoNum
											.split(";")[1]);
								}
								if (t_num > 0) {
									// 数据重新获取
									getData();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						/** 休息倒计时设定 */
						// 将时间换算成秒
						pauseTime = pauseClock.getHours() * 60 * 60
								+ pauseClock.getMin() * 60
								+ pauseClock.getSecond();
						if (pauseTime > 0) {
							isPause = true;
							greenLight.setBackground(new Color(190, 190, 190));
							if (!isLost) {
								yellowLight
										.setBackground(new Color(255, 255, 0));
							}
							pauseTime -= 1;// 时间减去一秒
							// 时间换算，存入model
							pauseClock.setHours((int) pauseTime / (60 * 60));
							pauseClock.setMin((int) (pauseTime / 60) % 60);
							pauseClock.setSecond((int) pauseTime % 60);
							// 更新UI
							pause_time.setText(String.format("%02d",
									pauseClock.getHours())
									+ ":"
									+ String.format("%02d", pauseClock.getMin())
									+ ":"
									+ String.format("%02d",
											pauseClock.getSecond()));
						} else {
							isPause = false;
							yellowLight.setBackground(new Color(190, 190, 190));
						}
						if (pauseTimeList.size() > 0
								&& (df_Hm.format(new Date()))
										.equals(pauseTimeList.get(0).getTime())) {
							pauseClock.setMin((int) (pauseTimeList.get(0)
									.getDuration()));
							pauseTimeList.remove(0);
						}

						/** 损失时间计时 */
						// 将时间换算成秒
						lostTime = lostClock.getHours() * 60 * 60
								+ lostClock.getMin() * 60
								+ lostClock.getSecond();
						if (isLost && taktTime > 0) {
							greenLight.setBackground(new Color(190, 190, 190));
							blueLight.setBackground(new Color(190, 190, 190));
							yellowLight.setBackground(new Color(190, 190, 190));
							redLight.setBackground(new Color(255, 51, 0));
							if (!isChange && !isPause) {
								lostTime += 1;// 时间加上一秒
								// 时间换算，存入model
								lostClock.setHours((int) lostTime / (60 * 60));
								lostClock.setMin((int) (lostTime / 60) % 60);
								lostClock.setSecond((int) lostTime % 60);
								// 更新UI
								lost_time.setText(String.format("%02d",
										lostClock.getHours())
										+ ":"
										+ String.format("%02d",
												lostClock.getMin())
										+ ":"
										+ String.format("%02d",
												lostClock.getSecond()));
							}
						} else {
							redLight.setBackground(new Color(190, 190, 190));
						}

						/** 换班倒计时 */
						if (changeTimeList.size() > 0) {
							if ((df_Hm.format(new Date()))
									.equals(changeTimeList.get(0).getTime())) {
								isChange = true;
								greenLight.setBackground(new Color(190, 190,
										190));
								if (!isLost) {
									blueLight.setBackground(new Color(0, 51,
											255));
								}
								changeTimeCD = changeTimeList.get(0)
										.getDuration();
								System.out.println("Duration:"
										+ changeTimeList.get(0).getDuration());
								changeTimeList.remove(0);
							}
						}
						// 如果状态是换班 并且剩余时间大于零 时间减少 否则关闭状态
						if (isChange && changeTimeCD > 0) {
							changeTimeCD--;
							if (!isLost) {
								blueLight.setBackground(new Color(0, 51, 255));
							}
						} else {
							isChange = false;
							blueLight.setBackground(new Color(190, 190, 190));
						}
					}
				});
				timeAction.start();
				return null;
			}

			/**
			 * 休息时间获取 换班时间获取 节拍时间处理
			 * 
			 * @throws Exception
			 * @throws ParseException
			 * @throws SQLException
			 */
			private void getData() throws Exception, ParseException,
					SQLException {
				wld = new WarningLightDao();
				// 获得产线ID
				TPLineID = wld.getTPLineIDByName(rangerNum);
				if (TPLineID > 0) {
					// 休息时间获取
					String pauseTimes = wld.getRestTime(rangerNum);
					if (pauseTimes != null && pauseTimes.length() > 5) {
						for (String s : pauseTimes.split(";")) {
							PauseTime pt = new PauseTime();
							pt.setTime(s.split("-")[0]);
							pt.setDuration((df_Hm.parse(s.split("-")[1])
									.getTime() - df_Hm.parse(s.split("-")[0])
									.getTime()) / 60000);
							pauseTimeList.add(pt);
						}
						System.out.println(pauseTimeList);
					}// end 休息时间获取

					// 换班时间获取
					String changeTime = wld.getChangeTime(rangerNum);
					if (changeTime != null && changeTime.length() > 5) {
						for (String s : changeTime.split(";")) {
							ChangeTime ct = new ChangeTime();
							ct.setTime(s.split("-")[0]);
							ct.setDuration((df_Hm.parse(s.split("-")[1])
									.getTime() - df_Hm.parse(s.split("-")[0])
									.getTime()) / 1000);
							changeTimeList.add(ct);
						}
					}// end 换班时间获取

					/** 节拍时间处理 */
					// 更新总量
					total_num.setText("" + t_num);
					// 标记此计划记录已读
					wld.setTPPlanStatusTo0(TPPlanID);
					// 标记此产线记录已读
					wld.setTPLineStatusTo0(rangerNum);
					// 获取节拍列表
					taktTimeList = wld.getTaktByTPLineID(TPLineID);
					// 给taktNum赋初值
					taktNum = taktTimeList.get(0).getNum();
					int nowtaktnum = taktTimeList.get(0).getTakt();
					if (rangerNum == 0) {// 早班情况
						long diffTime = (df_Hm
								.parse(df_Hm.format((new Date()))).getTime() - df_Hm
								.parse("07:00").getTime()) / 1000;
						while (diffTime > 0) {
							if (taktNum == 0) {
								taktTimeList.remove(0);
								taktNum = taktTimeList.get(0).getNum();
								nowtaktnum = taktTimeList.get(0).getTakt();
							}
							diffTime -= nowtaktnum;
							taktNum--;
							taktCount++;
						}
					} else if (rangerNum == 1) {// 中班情况
						long diffTime = (df_Hm
								.parse(df_Hm.format((new Date()))).getTime() - df_Hm
								.parse("15:00").getTime()) / 1000;
						while (diffTime > 0) {
							if (taktNum == 0) {
								taktTimeList.remove(0);
								taktNum = taktTimeList.get(0).getNum();
								nowtaktnum = taktTimeList.get(0).getTakt();
							}
							diffTime -= nowtaktnum;
							taktNum--;
							taktCount++;
						}
					} else if (rangerNum == 2) {// 晚班情况
						long diffTime = 0;
						long now = df_Hm.parse(df_Hm.format((new Date())))
								.getTime();
						if (now < 57600) {
							diffTime = (now - df_Hm.parse("23:00").getTime()) / 1000;
						} else {
							diffTime = (now - df_Hm.parse("00:00").getTime()) / 1000 + 3600;
						}
						while (diffTime > 0) {
							if (taktNum == 0) {
								taktTimeList.remove(0);
								taktNum = taktTimeList.get(0).getNum();
								nowtaktnum = taktTimeList.get(0).getTakt();
							}
							diffTime -= nowtaktnum;
							taktNum--;
							taktCount++;
						}
					}
					planIf_num.setText(taktCount + "");
				}
			}
		};
		return process;
	}

	/**
	 * 实时读取数据库，获取实际产量
	 * 
	 * @return
	 */
	private SwingWorker getRealNum() {
		SwingWorker background = new SwingWorker<String, Integer>() {

			@Override
			protected String doInBackground() throws Exception {
				wld = new WarningLightDao();
				while (true) {
					if (taktCount > 0) {
						if (rangerNum == 0) {
							realCount = wld.getRealNum_zao();
						} else if (rangerNum == 1) {
							realCount = wld.getRealNum_zhong();
						} else if (rangerNum == 2) {
							realCount = wld.getRealNum_wan();
						}
					}
					if (realCount > 0) {
						publish(realCount);
					}
					Thread.sleep(60000);
				}
			}

			@Override
			protected void process(List<Integer> chunks) {
				super.process(chunks);
				real_num.setText(chunks.get(0) + "");
			}

		};
		return background;
	}

	/**
	 * 日期时间的更新
	 * 
	 * @return
	 */
	private SwingWorker dateUpdate() {
		SwingWorker background = new SwingWorker<String, String>() {

			@Override
			protected String doInBackground() throws Exception {
				while (true) {
					Date d = new Date();
					if ("00:00".equals(df_Hm.format(d))
							|| "00:01".equals(df_Hm.format(d))) {
						publish(df_ymr.format(d));
					}
					Thread.sleep(60000);
				}
			}

			@Override
			protected void process(List<String> chunks) {
				super.process(chunks);
				date.setText("日期：" + chunks.get(0));
			}

		};
		return background;

	}

	/**
	 * 监控按钮状态
	 * 
	 * @return
	 */
	private SwingWorker buttonLister() {
		SwingWorker background = new SwingWorker<String, int[]>() {

			@Override
			protected String doInBackground() throws Exception {

				wld = new WarningLightDao();
				// while (true) {
				// 红色按钮计数复位
				// if (redBtnCount < 0) {
				// redBtnCount = 0;
				// }
				// AlarmData ad = new AlarmData();
				// ad = wld.getNewButtonStat();
				// if (ad.getId() == id) {
				// Thread.sleep(20);
				// continue;
				// } else {
				// int[] s = new int[3];
				// s[0] = Integer.parseInt(ad.getBid());
				// s[1] = Integer.parseInt(ad.getKeyid());
				// s[2] = ad.getYn();
				// id = ad.getId();
				// publish(s);
				// }
				// Thread.sleep(20);

				wld = new WarningLightDao();
				List<AlarmData> adList = new ArrayList<AlarmData>();
				int id = 0;
				while (true) {
					// 获取最新按钮状态
					adList = wld.getNewButtonStat2();
					// 红色按钮计数复位
					if (redBtnCount < 0) {
						redBtnCount = 0;
					}
					for (AlarmData l : adList) {
						int[] s = new int[3];
						s[0] = Integer.parseInt(l.getBid());
						s[1] = Integer.parseInt(l.getKeyid());
						s[2] = l.getYn();
						Thread.sleep(50);
						publish(s);
					}
					Thread.sleep(500);
				}

			}

			@Override
			protected void process(List<int[]> chunks) {
				super.process(chunks);
				int[] btn = chunks.get(0);
				switch (btn[0]) {
				/**
				 * new Color(255, 51, 0) red new new Color(255, 255, 0) yello
				 * new Color(51, 255, 0) green
				 */
				// 一号板 1 5 9 13 17
				case 9:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p01.setBackground(new Color(255, 51, 0));
							isLost = true;
							red1 = true;
							redBtnCount++;
						} else {
							if (!green1 && !yellow1) {
								p01.setBackground(Color.DARK_GRAY);
							}
							redBtnCount--;
							red1 = false;
						}
						break;
					case 2:
						if (btn[2] == 1) {
							if (!red1) {
								p01.setBackground(new Color(255, 255, 0));
							}
							yellow1 = true;
						} else {
							if (!green1 && !red1) {
								p01.setBackground(Color.DARK_GRAY);
							}
							yellow1 = false;
						}
						break;
					case 3:
						if (btn[2] == 1) {
							if (!red1 && !yellow1) {
								p01.setBackground(new Color(51, 255, 0));
							}
							green1 = true;
						} else {
							if (!red1 && !yellow1) {
								p01.setBackground(Color.DARK_GRAY);
							}
							green1 = false;
						}
						break;
					case 4:
						if (btn[2] == 1) {
							p02.setBackground(new Color(255, 51, 0));
							isLost = true;
							red2 = true;
							redBtnCount++;
						} else {
							if (!green2 && !yellow2) {
								p02.setBackground(Color.DARK_GRAY);
							}
							red2 = false;
							redBtnCount--;
						}
						break;
					case 5:
						if (btn[2] == 1) {
							if (!red2) {
								p02.setBackground(new Color(255, 255, 0));
							}
							yellow2 = true;
						} else {
							if (!red2 && !green2) {
								p02.setBackground(Color.DARK_GRAY);
							}
							yellow2 = false;
						}
						break;
					case 6:
						if (btn[2] == 1) {
							if (!red2 && !yellow2) {
								p02.setBackground(new Color(51, 255, 0));
							}
							green2 = true;
						} else {
							if (!red2 && !yellow2) {
								p02.setBackground(Color.DARK_GRAY);
							}
							green2 = false;
						}
						break;

					default:
						break;
					}
					break;
				// 二号板 2 6 10 14 18
				case 10:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p03.setBackground(new Color(255, 51, 0));
							isLost = true;
							red3 = true;
							redBtnCount++;
						} else {
							if (!green3 && !yellow3) {
								p03.setBackground(Color.DARK_GRAY);
							}
							red3 = false;
							redBtnCount--;
						}
						break;
					case 2:
						if (btn[2] == 1) {
							if (!red3) {
								p03.setBackground(new Color(255, 255, 0));
							}
							yellow3 = true;
						} else {
							if (!red3 && !green3) {
								p03.setBackground(Color.DARK_GRAY);
							}
							yellow3 = false;
						}
						break;
					case 3:
						if (btn[2] == 1) {
							if (!red3 && !yellow3) {
								p03.setBackground(new Color(51, 255, 0));
							}
							green3 = true;
						} else {
							if (!red3 && !yellow3) {
								p03.setBackground(Color.DARK_GRAY);
							}
							green3 = false;
						}
						break;
					case 4:
						if (btn[2] == 1) {
							p04.setBackground(new Color(255, 51, 0));
							isLost = true;
							red4 = true;
							redBtnCount++;
						} else {
							if (!green4 && !yellow4) {
								p04.setBackground(Color.DARK_GRAY);
							}
							red4 = false;
							redBtnCount--;
						}
						break;
					case 5:
						if (btn[2] == 1) {
							if (!red4) {
								p04.setBackground(new Color(255, 255, 0));
							}
							yellow4 = true;
						} else {
							if (!red4 && !green4) {
								p04.setBackground(Color.DARK_GRAY);
							}
							yellow4 = false;
						}
						break;
					case 6:
						if (btn[2] == 1) {
							if (!red4 && !yellow4) {
								p04.setBackground(new Color(51, 255, 0));
							}
							green4 = true;
						} else {
							if (!red4 && !yellow4) {
								p04.setBackground(Color.DARK_GRAY);
							}
							green4 = false;
						}
						break;

					default:
						break;
					}
					break;
				// 三号板 3 7 11 15 19
				case 11:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p05.setBackground(new Color(255, 51, 0));
							isLost = true;
							red5 = true;
							redBtnCount++;
						} else {
							if (!green5 && !yellow5) {
								p05.setBackground(Color.DARK_GRAY);
							}
							red5 = false;
							redBtnCount--;
						}
						break;
					case 2:
						if (btn[2] == 1) {
							if (!red5) {
								p05.setBackground(new Color(255, 255, 0));
							}
							yellow5 = true;
						} else {
							if (!red5 && !green5) {
								p05.setBackground(Color.DARK_GRAY);
							}
							yellow5 = false;
						}
						break;
					case 3:
						if (btn[2] == 1) {
							if (!red5 && !yellow5) {
								p05.setBackground(new Color(51, 255, 0));
							}
							green5 = true;
						} else {
							if (!red5 && !yellow5) {
								p05.setBackground(Color.DARK_GRAY);
							}
							green5 = false;
						}
						break;
					case 4:
						if (btn[2] == 1) {
							p06.setBackground(new Color(255, 51, 0));
							isLost = true;
							red6 = true;
							redBtnCount++;
						} else {
							if (!yellow6 && !green6) {
								p06.setBackground(Color.DARK_GRAY);
							}
							red6 = false;
							redBtnCount--;
						}
						break;
					case 5:
						if (btn[2] == 1) {
							if (!red6) {
								p06.setBackground(new Color(255, 255, 0));
							}
							yellow6 = true;
						} else {
							if (!red6 && !green6) {
								p06.setBackground(Color.DARK_GRAY);
							}
							yellow6 = false;
						}
						break;
					case 6:
						if (btn[2] == 1) {
							if (!red6 && !yellow6) {
								p06.setBackground(new Color(51, 255, 0));
							}
							green6 = true;
						} else {
							if (!red6 && !yellow6) {
								p06.setBackground(Color.DARK_GRAY);
							}
							green6 = false;
						}
						break;

					default:
						break;
					}
					break;
				// 四号板 4 8 12 16 20
				case 12:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p07.setBackground(new Color(255, 51, 0));
							isLost = true;
							red7 = true;
							redBtnCount++;
						} else {
							if (!yellow7 && !green7) {
								p07.setBackground(Color.DARK_GRAY);
							}
							red7 = false;
							redBtnCount--;
						}
						break;
					case 2:
						if (btn[2] == 1) {
							if (!red7) {
								p07.setBackground(new Color(255, 255, 0));
							}
							yellow7 = true;
						} else {
							if (!red7 && !green7) {
								p07.setBackground(Color.DARK_GRAY);
							}
							yellow7 = false;
						}
						break;
					case 3:
						if (btn[2] == 1) {
							if (!red7 && !yellow7) {
								p07.setBackground(new Color(51, 255, 0));
							}
							green7 = true;
						} else {
							if (!red7 && !yellow7) {
								p07.setBackground(Color.DARK_GRAY);
							}
							green7 = false;
						}
						break;
					case 4:
						if (btn[2] == 1) {
							p08.setBackground(new Color(255, 51, 0));
							isLost = true;
							red8 = true;
							redBtnCount++;
						} else {
							if (!yellow8 && !green8) {
								p08.setBackground(Color.DARK_GRAY);
							}
							red8 = false;
							redBtnCount--;
						}
						break;
					case 5:
						if (btn[2] == 1) {
							if (!red8) {
								p08.setBackground(new Color(255, 255, 0));
							}
							yellow8 = true;
						} else {
							if (!red8 && !green8) {
								p08.setBackground(Color.DARK_GRAY);
							}
							yellow8 = false;
						}
						break;
					case 6:
						if (btn[2] == 1) {
							if (!red8 && !yellow8) {
								p08.setBackground(new Color(51, 255, 0));
							}
							green8 = true;
						} else {
							if (!red8 && !yellow8) {
								p08.setBackground(Color.DARK_GRAY);
							}
							green8 = false;
						}
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			}
		};
		return background;
	}
}
