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
import com.lewei.mail.SendMail;
import com.lewei.model.AlarmData;
import com.lewei.model.ChangeTime;
import com.lewei.model.PauseTime;
import com.lewei.model.TPLine;
import com.lewei.model.TPPlan;
import com.lewei.model.Takt;
import com.lewei.model.Warning;
import com.lewei.model.WarningInfo;

public class WarningScreen extends JFrame {

	/** �ƻ�ģ�� */
	TPPlan tpplan = null;
	/** ����ģ�� */
	TPLine tpline = null;

	private static int redBtnCount = 0;
	private static long changeTimeCD = 0;

	private static boolean isChange = false;
	private static boolean isLost = false;
	private static boolean isPause = false;

	private SimpleDateFormat df_Hm = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat df_Hms = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat df_ymr = new SimpleDateFormat("yyyy-MM-dd");

	/** �ϰ�ʱ�� */
	private static String startTime = null;
	/** �°�ʱ�� */
	private static String endTime = null;
	/** ��ǰ���߿�ʼ������ */
	private static String begin = null;

	/** �������ݱ�ʶ */
	private static boolean getData = true;

	private static List<PauseTime> pauseTimeList = new ArrayList<>();
	private static List<ChangeTime> changeTimeList = new ArrayList<>();
	private static List<Takt> taktTimeList = new ArrayList<>();

	/** �����Ԥ����Ϣ */
	private static List<String> addInfoToLewei = new ArrayList<>();
	/** �ı�Ԥ����Ϣ״̬ */
	private static List<String> dealInfoToLewei = new ArrayList<>();

	private Clock clock;
	private static long pauseTime = 0;
	private static long lostTime = 0;
	private static long taktTime = 0;

	private WarningLightDao wld = null;
	/** ���ļ��� */
	private static int taktCount = 0;
	/** ���ĸ��� */
	private static int taktNum = -1;
	/** ��� */
	private static int rangerNum = -1;
	/** ʵ���� */
	private static int realCount = 0;
	/** ���� */
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
	/** ����ID */
	protected int TPLineID = 0;
	/** �ƻ�ID */
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

		line = new JLabel("\u7EBF\u522B:DOP2 Line");
		line.setHorizontalAlignment(SwingConstants.CENTER);
		line.setAlignmentX(Component.RIGHT_ALIGNMENT);
		line.setBounds(219, 40, 774, 70);
		panel_1.add(line);
		line.setFont(new Font("������κ", Font.PLAIN, 60));
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
		greenLight.setBackground(new Color(190, 190, 190));
		greenLight.setBounds(553, 140, 70, 70);
		panel_1.add(greenLight);

		blueLight = new Label("");
		blueLight.setBackground(new Color(0, 51, 255));
		blueLight.setBackground(new Color(190, 190, 190));
		blueLight.setBounds(705, 140, 70, 70);
		panel_1.add(blueLight);

		yellowLight = new Label("");
		yellowLight.setBackground(new Color(255, 255, 0));
		yellowLight.setBounds(629, 140, 70, 70);
		panel_1.add(yellowLight);

		date = new JLabel("");
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setFont(new Font("����", Font.BOLD, 34));
		date.setForeground(new Color(255, 255, 255));
		date.setBounds(10, 140, 396, 70);
		long timemillis = System.currentTimeMillis();
		// ת��������ʾ��ʽ
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		date.setText("���ڣ�" + sdf.format(timemillis));
		panel_1.add(date);

		JLabel datetime = new JLabel("-----------");
		datetime.setHorizontalAlignment(SwingConstants.CENTER);
		datetime.setForeground(Color.WHITE);
		datetime.setFont(new Font("����", Font.BOLD, 34));
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
		total_num.setFont(new Font("������", Font.BOLD, 80));
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
		planIf_num.setFont(new Font("������", Font.BOLD, 80));
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
		real_num.setFont(new Font("������", Font.BOLD, 80));
		real_num.setAlignment(Label.CENTER);
		real_num.setBounds(841, 320, 406, 104);
		panel_1.add(real_num);

		pause = new Label("Pause/\u6682\u505C");
		pause.setForeground(Color.YELLOW);
		pause.setFont(new Font("������", Font.BOLD, 45));
		pause.setAlignment(Label.CENTER);
		pause.setBounds(0, 448, 406, 49);
		panel_1.add(pause);

		pause_time = new Label("00:00:00");
		pause_time.setForeground(Color.YELLOW);
		pause_time.setFont(new Font("������", Font.BOLD, 75));
		pause_time.setAlignment(Label.CENTER);
		pause_time.setBounds(0, 511, 406, 75);
		panel_1.add(pause_time);

		takt = new Label("Takt/\u8282\u62CD");
		takt.setForeground(Color.WHITE);
		takt.setFont(new Font("������", Font.BOLD, 45));
		takt.setAlignment(Label.CENTER);
		takt.setBounds(412, 448, 449, 45);
		panel_1.add(takt);

		takt_time = new Label("00:00:00");
		takt_time.setForeground(Color.WHITE);
		takt_time.setFont(new Font("������", Font.BOLD, 75));
		takt_time.setAlignment(Label.CENTER);
		takt_time.setBounds(412, 511, 449, 75);
		panel_1.add(takt_time);

		lost = new Label("Total Loss/\u635F\u5931");
		lost.setForeground(new Color(204, 51, 51));
		lost.setFont(new Font("������", Font.BOLD, 45));
		lost.setAlignment(Label.CENTER);
		lost.setBounds(841, 448, 396, 49);
		panel_1.add(lost);

		lost_time = new Label("00:00:00");
		lost_time.setForeground(new Color(255, 51, 0));
		lost_time.setFont(new Font("������", Font.BOLD, 75));
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

		// ϵͳʱ����ʾ
		this.setCurrentTimer(datetime);

		separator = new JSeparator();
		separator.setBounds(0, 426, 1237, 18);
		panel_1.add(separator);

		// ʵ����һ��SwingWorker �������ڸ���
		SwingWorker dateTime = dateUpdate();
		dateTime.execute();

		// ʵ����һ��SwingWorker ����UIˢ��
		SwingWorker bgProcess = backgroundProcess();
		bgProcess.execute();

		// ʵ����SwingWorker ��ذ�ť״̬
		SwingWorker button = buttonLister();
		button.execute();

		// ʵ����ʵʱ������ȡSwingWorker
		SwingWorker getReal = getRealNum();
		getReal.execute();

		// �Ӱ��¼��ȡ
		SwingWorker updateWork = updateWork();
		updateWork.execute();

		// �ϴ�Ԥ����Ϣ����ά������
		SwingWorker uploadInfo = uploadWarningInfoToLewei();
		uploadInfo.execute();

		// �����ʼ�����
		// SwingWorker sendInfo = sendWarningInfo();
		// sendInfo.execute();
	}

	/**
	 * ����Timer 1000msʵ��һ�ζ��� ʵ����һ���̡߳�
	 * 
	 * @param time
	 */
	private void setCurrentTimer(JLabel time) {
		final JLabel varTime = time;
		// 1000����ˢ��һ��
		Timer timeAction = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// ת��������ʾ��ʽ
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				// ����UI
				varTime.setText("ʱ�䣺" + df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
	}

	/**
	 * ����ҳ����ֵ��ͣˢ��(��̨�߳�)��<br >
	 * ���߳��ƹ�<b>��Ϣʱ��</b>��<b>����ʱ��</b>��<b>��ʧʱ�䡣</b>
	 * 
	 * @return
	 */
	private SwingWorker backgroundProcess() {
		SwingWorker process = new SwingWorker<Void, Integer>() {
			@Override
			protected Void doInBackground() {

				// ��ʧʱ�䴦��
				taktClock = new Clock(0, 0, 0);
				pauseClock = new Clock(0, 0, 0);
				lostClock = new Clock(0, 0, 0);
				Timer timeAction = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// ui����
						refreshUI();
					}

					/**
					 * UIˢ���̣߳����̸߳��½��ģ���Ϣ����ʧ�����ࡣ
					 * 
					 * @param lostClock
					 * @param taktClock
					 * @param pauseClock
					 */
					private void refreshUI() {

						/** ���ĵ���ʱ */
						// ��ʱ�任�����
						taktTime = taktClock.getHours() * 60 * 60
								+ taktClock.getMin() * 60
								+ taktClock.getSecond();
						// �������ݱ�ʶ
						if (taktTime > 1 && !isPause && !isChange) {
							if (!isLost) {
								greenLight.setBackground(new Color(51, 255, 0));
							}
							taktTime -= 1;// ʱ���ȥһ��
							if (taktTime == 1) {
								// ��������
								taktCount++;
								// ����UI
								planIf_num.setText(taktCount + "");
							}
							// ʱ�任�㣬����model
							taktClock.setHours((int) taktTime / (60 * 60));
							taktClock.setMin((int) (taktTime / 60) % 60);
							taktClock.setSecond((int) taktTime % 60);
							// ����UI
							takt_time.setText(String.format("%02d",
									taktClock.getHours())
									+ ":"
									+ String.format("%02d", taktClock.getMin())
									+ ":"
									+ String.format("%02d",
											taktClock.getSecond()));
						} else if (taktNum > 0 && !isPause && !isChange) {
							// ��Ʒ����������
							taktNum--;
							// ���½�����
							taktClock.setSecond(taktTimeList.get(0).getTakt());
							// ��ȡ�����ݱ�� ��
						} else if (taktNum == 0 && !isPause && !isChange) {
							taktTimeList.remove(0);
							// �ж�list�Ƿ�Ϊ��
							if (taktTimeList.size() > 0) {
								// ��ȡ�����ݱ�� ��
								// ��ȡ�²�������
								taktNum = taktTimeList.get(0).getNum();
								// ���½�����
								taktClock.setSecond(taktTimeList.get(0)
										.getTakt());
							} else {
								greenLight.setBackground(new Color(190, 190,
										190));
								taktNum = -1;
							}
						}

						/** ��Ϣ����ʱ�趨 */
						// ��ʱ�任�����
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
							pauseTime -= 1;// ʱ���ȥһ��
							// ʱ�任�㣬����model
							pauseClock.setHours((int) pauseTime / (60 * 60));
							pauseClock.setMin((int) (pauseTime / 60) % 60);
							pauseClock.setSecond((int) pauseTime % 60);
							// ����UI
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

						/** ��ʧʱ���ʱ */
						// ��ʱ�任�����
						lostTime = lostClock.getHours() * 60 * 60
								+ lostClock.getMin() * 60
								+ lostClock.getSecond();
						if (isLost && taktTime > 0) {
							greenLight.setBackground(new Color(190, 190, 190));
							blueLight.setBackground(new Color(190, 190, 190));
							yellowLight.setBackground(new Color(190, 190, 190));
							redLight.setBackground(new Color(255, 51, 0));
							if (!isChange && !isPause) {
								lostTime += 1;// ʱ�����һ��
								// ʱ�任�㣬����model
								lostClock.setHours((int) lostTime / (60 * 60));
								lostClock.setMin((int) (lostTime / 60) % 60);
								lostClock.setSecond((int) lostTime % 60);
								// ����UI
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

						// /** ���൹��ʱ */
						// if (changeTimeList.size() > 0) {
						// if ((df_Hm.format(new Date()))
						// .equals(changeTimeList.get(0).getTime())) {
						// isChange = true;
						// greenLight.setBackground(new Color(190, 190,
						// 190));
						// if (!isLost) {
						// blueLight.setBackground(new Color(0, 51,
						// 255));
						// }
						// changeTimeCD = changeTimeList.get(0)
						// .getDuration();
						// System.out.println("Duration:"
						// + changeTimeList.get(0).getDuration());
						// changeTimeList.remove(0);
						// }
						// }
						// // ���״̬�ǻ��� ����ʣ��ʱ������� ʱ����� ����ر�״̬
						// if (isChange && changeTimeCD > 0) {
						// changeTimeCD--;
						// if (!isLost) {
						// blueLight.setBackground(new Color(0, 51, 255));
						// }
						// } else {
						// isChange = false;
						// blueLight.setBackground(new Color(190, 190, 190));
						// }

						// ʱ�䵽��ͣ�ߣ���ǰ���ݴ������ݿ�
						if ((df_Hm.format(new Date())).equals(endTime)) {
							System.out.println("time up !");

							wld = new WarningLightDao();
							try {
								if (TPLineID > 0) {
									// ��ȡ��ʧʱ��
									String lostT = lost_time.getText();
									// ��ʧʱ��������ݿ�
									wld.setTPLineLostTime(TPLineID, lostT);

									// ��ȡʵ�ʲ���
									String realPro = real_num.getText();
									// ʵ�ʲ����������ݿ�
									wld.setTPLineRealNum(TPLineID,
											Integer.parseInt(realPro));

									// ��ȡ�ƻ�����
									String planSum = planIf_num.getText();
									// �ƻ������������ݿ�
									wld.setTPLinePlanNum(TPLineID,
											Integer.parseInt(planSum));

									// ����StatusΪ2��2��ʾ���������Ч����
									wld.setTPLineStatusTo2(TPLineID);
									// ����
									TPLineID = 0;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}

							// ���ó���ʧʱ�����������
							resetting();

							// ������ʧʱ��
							lostClock = new Clock(0, 0, 0);
							lost_time.setText("00:00:00");
							// ��ȡ���ݱ�ʶ��Ϊ����ȡ
							getData = true;

						}

						if (getData) {
							wld = new WarningLightDao();
							try {
								// ������࿪ʼ����
								if (tpplan == null) {
									// ʵ����һ�����߼ƻ�
									tpplan = new TPPlan();
									// ��ȡ���²��߼�¼
									tpplan = wld.getLatestTpplan();
									if (tpplan != null) {
										// ��ȡ�ƻ�ID
										TPPlanID = tpplan.getTPPlanID();
										// ʵ����һ������ģ��
										tpline = new TPLine();
										tpline = wld
												.getTplineWithTPPlanID(TPPlanID);
										if (tpline != null) {
											startTime = tpline.getStartTime();
											endTime = tpline.getEndTime();
											long startT = df_Hm
													.parse(startTime).getTime();
											long nowT = df_Hm.parse(
													df_Hm.format((new Date())))
													.getTime();
											long diff = (nowT - startT) / 1000;
											if (diff >= 0) {
												// �趨���
												rangerNum = tpplan.getRanger();
												// ��ȡ����
												t_num = tpplan.getTotalNum();
												// �������»�ȡ
												getData();
											}
										}
									}
								} else {
									if (tpplan.getStatus() == 1) { // tpplan��״̬
										long startT = df_Hm.parse(startTime)
												.getTime();
										long nowT = df_Hm.parse(
												df_Hm.format((new Date())))
												.getTime();
										long diff = (nowT - startT) / 1000;
										if (diff >= 0) {
											// �������»�ȡ
											getData();
										}
									} else {// �ÿ�
										tpplan = null;
									}
								}
							} catch (SQLException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}

				});
				timeAction.start();
				return null;
			}

		};
		return process;
	}

	/**
	 * ��Ϣʱ���ȡ ����ʱ���ȡ ����ʱ�䴦��
	 * 
	 * @throws Exception
	 * @throws ParseException
	 * @throws SQLException
	 */
	private void getData() throws Exception, ParseException, SQLException {
		// ��������
		begin = df_ymr.format(new Date());
		wld = new WarningLightDao();
		// ��ò���ID
		TPLineID = tpline.getTPLineID();
		// ��Ϣʱ���ȡ
		String pauseTimes = tpline.getRestTime();
		if (pauseTimes != null && pauseTimes.length() > 5) {
			for (String s : pauseTimes.split(";")) {
				long duration = (df_Hm.parse(s.split("-")[1]).getTime() - df_Hm
						.parse(s.split("-")[0]).getTime()) / 60000;
				if (duration > 0) {// ����ʱ��������ģ�ͣ�������list
					PauseTime pt = new PauseTime();
					pt.setTime(s.split("-")[0]);
					pt.setDuration(duration);
					pauseTimeList.add(pt);
				}
			}
			System.out.println(pauseTimeList.toString());
		}// end ��Ϣʱ���ȡ

		// ����ʱ���ȡ
		// String changeTime = wld.getChangeTime(rangerNum);
		// if (changeTime != null && changeTime.length() > 5) {
		// for (String s : changeTime.split(";")) {
		// ChangeTime ct = new ChangeTime();
		// ct.setTime(s.split("-")[0]);
		// ct.setDuration((df_Hm.parse(s.split("-")[1])
		// .getTime() - df_Hm.parse(s.split("-")[0])
		// .getTime()) / 1000);
		// changeTimeList.add(ct);
		// }
		// }// end ����ʱ���ȡ

		/** ����ʱ�䴦�� */
		// ��������
		total_num.setText("" + t_num);
		// ��Ǵ˼ƻ���¼�Ѷ�
		wld.setTPPlanStatusTo0(TPPlanID);
		// ��Ǵ˲��߼�¼�Ѷ�
		wld.setTPLineStatusTo0(TPLineID);
		// ��ȡ�����б�
		taktTimeList = wld.getTaktByTPLineID(TPLineID);
		// ��taktNum����ֵ
		taktNum = taktTimeList.get(0).getNum();
		int nowtaktnum = taktTimeList.get(0).getTakt();
		long diffTime = (df_Hm.parse(df_Hm.format((new Date()))).getTime() - df_Hm
				.parse(startTime).getTime()) / 1000;
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
		// ����ʱ�������Ľ���
		planIf_num.setText(taktCount + "");
		// ���ģ��
		tpplan = null;
		tpline = null;
		getData = false;
	}

	/**
	 * ���ó����������ݣ�����������ʧʱ�䡣
	 */
	private void resetting() {
		// ��ı�ǹ�λ
		isLost = false;
		isChange = false;
		isPause = false;

		// ��ɫ��ȫ����Ϊ��ɫ
		redLight.setBackground(new Color(190, 190, 190));
		blueLight.setBackground(new Color(190, 190, 190));
		greenLight.setBackground(new Color(190, 190, 190));
		yellowLight.setBackground(new Color(190, 190, 190));

		// ʵ�ʲ�������
		real_num.setText("0");
		planIf_num.setText("0");
		total_num.setText("0");

		// Clock���
		pauseClock = new Clock(0, 0, 0);
		// lostClock = new Clock(0, 0, 0);
		taktClock = new Clock(0, 0, 0);
		changeClock = new Clock(0, 0, 0);

		// ʱ���λ
		// lost_time.setText("00:00:00");
		pause_time.setText("00:00:00");
		takt_time.setText("00:00:00");

		// list ����
		pauseTimeList.clear();
		taktTimeList.clear();
		changeTimeList.clear();

		// ģ���ÿ�
		tpplan = null;
		tpline = null;

		// ���°�ʱ��
		startTime = null;
		endTime = null;

		// ��Ǳ�����λ
		taktCount = 0;
		realCount = 0;
		rangerNum = -1;
		taktNum = -1;

		// ������λ
		t_num = 0;
		taktTime = 0;
		pauseTime = 0;
		lostTime = 0;
		changeTimeCD = 0;

		// TPPlanID����
		TPPlanID = 0;
		// TPLineID����
		TPLineID = 0;
	}

	/**
	 * ʵʱ��ȡ���ݿ⣬��ȡʵ�ʲ�����
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
						// if (rangerNum == 0) {
						// realCount = wld.getRealNum_zao();
						// } else if (rangerNum == 1) {
						// realCount = wld.getRealNum_zhong();
						// } else if (rangerNum == 2) {
						// realCount = wld.getRealNum_wan();
						// }
						// ��ȡʵ�ʲ���
						realCount = wld.getRealNumWithTime(begin, startTime,
								endTime);
					}
					if (realCount > 0) {// ����UI
						publish(realCount);
					}
					Thread.sleep(30000);
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
	 * ����ʱ��ĸ��¡�
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
					Thread.sleep(30000);
				}
			}

			@Override
			protected void process(List<String> chunks) {
				super.process(chunks);
				date.setText("���ڣ�" + chunks.get(0));
			}

		};
		return background;

	}

	/**
	 * ��ذ�ť״̬��
	 * 
	 * @return
	 */
	private SwingWorker buttonLister() {
		SwingWorker background = new SwingWorker<String, int[]>() {

			@Override
			protected String doInBackground() throws InterruptedException {

				// while (true) {
				// ��ɫ��ť������λ
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

				List<AlarmData> adList = new ArrayList<AlarmData>();
				int id = 0;
				while (true) {
					wld = new WarningLightDao();
					// ��ȡ���°�ť״̬
					try {
						adList = wld.getNewButtonStat2();
					} catch (SQLException e) {
						System.out.println("��ȡ��ťʱ�����쳣");
						e.printStackTrace();
					}
					// ʱ�����״̬
					if (redBtnCount > 0) {// ������������
						isLost = true;
					} else {
						isLost = false;
					}
					// ��ɫ��ť������λ
					redBtnCount = 8;
					for (AlarmData l : adList) {
						int[] s = new int[3];
						s[0] = Integer.parseInt(l.getBid());
						s[1] = Integer.parseInt(l.getKeyid());
						s[2] = l.getYn();
						// ���ݺ�ɫ��ť��״̬����
						if (s[1] == 1 || s[1] == 4) {
							if (s[2] == 1) {
								redBtnCount++;
							} else {
								redBtnCount--;
							}
						}
						Thread.sleep(50);
						publish(s);
					}
					Thread.sleep(300);
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
				// һ�Ű� 1 5 9 13 17
				case 13:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p01.setBackground(new Color(255, 51, 0));
							if (!red1) {
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;1");
									addInfoToLewei.add("DOP2;1");
								}
								red1 = true;
							}
							isLost = true;
						} else {
							if (!green1 && !yellow1) {
								p01.setBackground(Color.DARK_GRAY);
							}
							if (red1) {
								dealInfoToLewei.add("DOP2;1");
							}
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
							if (!red2) {
								red2 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;2");
									addInfoToLewei.add("DOP2;2");
								}
							}
							isLost = true;
						} else {
							if (!green2 && !yellow2) {
								p02.setBackground(Color.DARK_GRAY);
							}
							if (red2) {
								dealInfoToLewei.add("DOP2;2");
							}
							red2 = false;
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
				// ���Ű� 2 6 10 14 18
				case 14:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p03.setBackground(new Color(255, 51, 0));
							if (!red3) {
								red3 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;3");
									addInfoToLewei.add("DOP2;3");
								}
							}
							isLost = true;
						} else {
							if (!green3 && !yellow3) {
								p03.setBackground(Color.DARK_GRAY);
							}
							if (red3) {
								dealInfoToLewei.add("DOP2;3");
							}
							red3 = false;
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
							if (!red4) {
								red4 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;4");
									addInfoToLewei.add("DOP2;4");
								}
							}
							isLost = true;
						} else {
							if (!green4 && !yellow4) {
								p04.setBackground(Color.DARK_GRAY);
							}
							if (red4) {
								dealInfoToLewei.add("DOP2;4");
							}
							red4 = false;
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
				// ���Ű� 3 7 11 15 19
				case 15:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p05.setBackground(new Color(255, 51, 0));
							if (!red5) {
								red5 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;5");
									addInfoToLewei.add("DOP2;5");
								}
							}
							isLost = true;
						} else {
							if (!green5 && !yellow5) {
								p05.setBackground(Color.DARK_GRAY);
							}
							if (red5) {
								dealInfoToLewei.add("DOP2;5");
							}
							red5 = false;
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
							if (!red6) {
								red6 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;6");
									addInfoToLewei.add("DOP2;6");
								}
							}
							isLost = true;
						} else {
							if (!yellow6 && !green6) {
								p06.setBackground(Color.DARK_GRAY);
							}
							if (red6) {
								dealInfoToLewei.add("DOP2;6");
							}
							red6 = false;
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
				// �ĺŰ� 4 8 12 16 20
				case 16:
					switch (btn[1]) {
					case 1:
						if (btn[2] == 1) {
							p07.setBackground(new Color(255, 51, 0));
							if (!red7) {
								red7 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;7");
									addInfoToLewei.add("DOP2;7");
								}
							}
							isLost = true;
						} else {
							if (!yellow7 && !green7) {
								p07.setBackground(Color.DARK_GRAY);
							}
							if (red7) {
								dealInfoToLewei.add("DOP2;7");
							}
							red7 = false;
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
							if (!red8) {
								red8 = true;
								// �����¼�������ݿ�
								if (rangerNum >= 0) {
									addWarningInfo("DOP2;8");
									addInfoToLewei.add("DOP2;8");
								}
							}
							isLost = true;
						} else {
							if (!yellow8 && !green8) {
								p08.setBackground(Color.DARK_GRAY);
							}
							if (red8) {
								dealInfoToLewei.add("DOP2;8");
							}
							red8 = false;
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

			/**
			 * �����¼�������ݿ�
			 * 
			 * @param string
			 */
			private void addWarningInfo(String string) {
				wld = new WarningLightDao();
				String remark = "��ע�⣡�߱�" + string.split(";")[0] + "�� "
						+ string.split(";")[1] + " �Ź�λͣ�ߣ�";
				try {
					// ��ǰ��λ���뵱ǰ����
					wld.insertWorkStation(TPLineID, string.split(";")[1]);
					// Ԥ����Ϣ�������ݿ�
					wld.addWarningInfo(remark);
				} catch (SQLException e) {
					System.out.println("����Ԥ����Ϣ����");
					e.printStackTrace();
				}
			}
		};
		return background;
	}

	/**
	 * ��ȡ���²�����Ϣ��
	 * 
	 * @return
	 */
	private SwingWorker updateWork() {
		SwingWorker update = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				wld = new WarningLightDao();
				TPPlan plan;
				while (true) {
					if (rangerNum >= 0) {
						plan = new TPPlan();
						// ��ȡ�²������Ϣ
						plan = wld.getUpdateWork(rangerNum);
						if (plan != null) { // �������¼�¼ʱ
							System.out.println("�¼�¼�Ѷ�ȡ��");
							// ���ó���
							resetting();

							// �ƻ�ģ�͸���
							tpplan = plan;
							// ����趨
							rangerNum = plan.getRanger();
							// ��ȡ����
							t_num = plan.getTotalNum();
							// ��üƻ�ID
							TPPlanID = plan.getTPPlanID();
							// ��ȡһ��line
							tpline = new TPLine();
							tpline = wld.getTplineWithTPPlanID(TPPlanID);

							if (tpline != null) {
								// ��ʼʱ�����
								startTime = tpline.getStartTime();
								// ����ʱ�����
								endTime = tpline.getEndTime();
								long startT = df_Hm.parse(startTime).getTime();
								long nowT = df_Hm.parse(
										df_Hm.format((new Date()))).getTime();
								long diff = (nowT - startT) / 1000;
								// �����ʼʱ����ڵ��ڵ�ǰʱ��
								if (diff >= 0) {
									// �������»�ȡ
									getData();
								}
							}
						}
					}
					Thread.sleep(10000);
				}
			}

		};
		return update;

	}

	/**
	 * Ԥ����Ϣ���͡�<br>
	 * ͨ�����ź��ʼ���
	 * 
	 * @return
	 */
	private SwingWorker sendWarningInfo() {
		SwingWorker send = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				WarningInfo wInfo = new WarningInfo();
				wld = new WarningLightDao();
				// ʵ�������ն��ŵĺ���list
				List<String> phoneNumberList = new ArrayList<>();
				// ʵ���������ʼ�������list
				List<String> emailList = new ArrayList<>();
				// ʵ�������ڷ�����
				ComputeSmsData sms = new ComputeSmsData();
				SerialToGsm stg;
				// ʵ����javaMail����
				SendMail sm = new SendMail();
				while (true) {
					if (taktNum >= 1) {
						wInfo = wld.getWarningInfo();
						if (wInfo != null) {
							System.out.println("begin send!");
							/** ���Ͷ��� */
							// ��ȡ���ź���
							phoneNumberList = wld.getPhoneNumber();
							// ѭ��list�����Ͷ���
							for (String p : phoneNumberList) {
								stg = new SerialToGsm("COM4");
								System.out.println("phoneNumber��" + p);
								stg.sendSms(p, wInfo.getContent());
								Thread.sleep(2000);
							}
							System.out.println("end message!");
							/** �����ʼ� */
							// ��ȡ�ռ����ַ
							emailList = wld.getEmailAddress();
							for (String e : emailList) {
								System.out.println("emailAddress��" + e);
								sm.sendWarningEmail(e, wInfo.getContent());
								Thread.sleep(2000);
							}
							System.out.println("end mail!");
							/** ���Ԥ���Ѷ� */
							wld.setWarningInfoAsReaded(wInfo.getInfoID());
						}
					}
					Thread.sleep(1000);
				}
			}
		};
		return send;
	}

	/**
	 * Ԥ����Ϣ�ϴ�����ά��������
	 * 
	 * @return
	 */
	private SwingWorker uploadWarningInfoToLewei() {
		SwingWorker sw = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				wld = new WarningLightDao();

				while (true) {
					if (addInfoToLewei.size() > 0) {
						// ���͵���ά������
						Warning w = new Warning();
						w.setCustomerCode(addInfoToLewei.get(0));
						w.setStatus(0);
						w.setWarningHandler("���߰೤");
						w.setWarningSite("����");
						w.setWarningType("���ƾ���");
						wld.addWittturBtnWarningToLewei(w);
						// �Ƴ��Ѵ��������
						addInfoToLewei.remove(0);
					}

					if (dealInfoToLewei.size() > 0) {
						wld.dealWittturBtnWarningToLewei(dealInfoToLewei.get(0));
						// �Ƴ��Ѵ�������
						dealInfoToLewei.remove(0);
					}

					Thread.sleep(30000);
				}
			}
		};
		return sw;
	}

}
