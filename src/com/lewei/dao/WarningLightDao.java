package com.lewei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.lewei.db.LeweiDBUtils;
import com.lewei.db.MySQLDBUtil;
import com.lewei.db.OracleUtils;
import com.lewei.model.AlarmData;
import com.lewei.model.TPLine;
import com.lewei.model.TPPlan;
import com.lewei.model.Takt;
import com.lewei.model.Warning;
import com.lewei.model.WarningInfo;

public class WarningLightDao {

	/**
	 * ��ȡ��Ϣʱ�䡣
	 * 
	 * @param rangerNum
	 * @return
	 * @throws Exception
	 */
	public String getRestTime(int rangerNum) throws Exception {

		String time = null;

		Connection conn = new MySQLDBUtil().getConn();
		// .ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("select RestTime from tpline where TPLineName = 'DOP2' and Ranger ="
						+ rangerNum
						+ " and Status = 1 ORDER BY StartTime DESC LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			time = rs.getString(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return time;
	}

	/**
	 * ��ȡTPLineID��
	 * 
	 * @param rangerNum
	 * @return
	 * @throws SQLException
	 */
	public int getTPLineIDByName(int rangerNum) throws SQLException {
		int id = 0;
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("select TPLineID from tpline where TPLineName = 'DOP2' and Ranger ="
						+ rangerNum
						+ " and Status = 1 ORDER BY StartTime DESC LIMIT 1");
		while (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return id;
	}

	/**
	 * ��ȡ���ġ�
	 * 
	 * @param tPLineID
	 * @return
	 * @throws SQLException
	 */
	public List<Takt> getTaktByTPLineID(int tPLineID) throws SQLException {
		List<Takt> list = new ArrayList<>();
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("select Takt,Num from tplprod where TPLineID = '"
						+ tPLineID + "' and Status = 1");
		while (rs.next()) {
			Takt t = new Takt();
			t.setTakt(rs.getInt(1));
			t.setNum(rs.getInt(2));
			list.add(t);
		}
		rs.close();
		statement.close();
		conn.close();
		return list;
	}

	/**
	 * ��ȡ����ʱ�䡣
	 * 
	 * @param rangerNum
	 * @return
	 * @throws SQLException
	 */
	public String getChangeTime(int rangerNum) throws SQLException {
		String time = null;

		Connection conn = new MySQLDBUtil().getConn();
		// .ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("select ChangeTime from tpline where TPLineName = 'DOP2' and Ranger ="
						+ rangerNum
						+ " and Status = 1 ORDER BY StartTime DESC LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			time = rs.getString(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return time;
	}

	/**
	 * ��ȡ��ť�¼���
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AlarmData> getNewButtonStat2() throws SQLException {
		List<AlarmData> list = new ArrayList<AlarmData>();
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT  id,bid,keyid,ttime,yn,status FROM(SELECT * FROM t_alarmdata WHERE bid BETWEEN 17 AND 20 ORDER BY ttime DESC LIMIT 2000) a GROUP BY a.bid,a.keyid");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			AlarmData ad = new AlarmData();
			ad.setBid(rs.getString("bid"));
			ad.setId(rs.getInt("id"));
			ad.setKeyid(rs.getString("keyid"));
			ad.setStatus(rs.getInt("status"));
			ad.setYn(rs.getInt("yn"));
			list.add(ad);
		}
		rs.close();
		statement.close();
		conn.close();
		return list;
	}

	/**
	 * ��ذ�ť״̬��
	 */
	public AlarmData getNewButtonStat() throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		AlarmData ad = new AlarmData();
		conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		rs = statement
				.executeQuery("select id,bid,keyid,ttime,yn,status from t_alarmdata order by id desc limit 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			ad.setBid(rs.getString("bid"));
			ad.setId(rs.getInt("id"));
			ad.setKeyid(rs.getString("keyid"));
			ad.setStatus(rs.getInt("status"));
			ad.setYn(rs.getInt("yn"));
		}
		rs.close();
		statement.close();
		conn.close();
		return ad;
	}

	/**
	 * ��ǲ���״̬�Ѷ���
	 * 
	 * @param rangerNum
	 * 
	 * @throws SQLException
	 */
	public void setTPLineStatusTo0(int rangerNum) throws SQLException {
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		statement.executeUpdate("update tpline set Status=0 where TPLineID = "
				+ rangerNum + " AND Status = 1");
		statement.close();
		conn.close();
	}

	/**
	 * ��ȡ����ܲ�����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTotalNum0() throws SQLException {
		String totalNum = "0;0";
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum FROM tpplan WHERE Ranger = 0 AND TPLineName ='DOP2' AND STATUS = 1 ORDER BY CreateTime DESC LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			totalNum = rs.getInt("TotalNum") + ";" + rs.getInt("TPPlanID");
		}
		rs.close();
		statement.close();
		conn.close();
		return totalNum;
	}

	/**
	 * ��ȡ�а��ܲ�����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTotalNum1() throws SQLException {
		String totalNum = "0;0";
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum FROM tpplan WHERE Ranger = 1 AND TPLineName ='DOP2' AND STATUS = 1 ORDER BY CreateTime DESC LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			totalNum = rs.getInt("TotalNum") + ";" + rs.getInt("TPPlanID");
		}
		rs.close();
		statement.close();
		conn.close();
		return totalNum;
	}

	/**
	 * ��ȡ����ܲ�����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTotalNum2() throws SQLException {
		String totalNum = "0;0";
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum FROM tpplan WHERE Ranger = 2 AND TPLineName ='DOP2' AND STATUS = 1  ORDER BY CreateTime DESC LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			totalNum = rs.getInt("TotalNum") + ";" + rs.getInt("TPPlanID");
		}
		rs.close();
		statement.close();
		conn.close();
		return totalNum;
	}

	/**
	 * ��ȡ�ƻ�������
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getPlanIfNum() throws SQLException {
		// TODO Auto-generated method stub
		int planNum = 0;
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("select PlanNum from tpline where TPLineName = 'DOP2' and Status = 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			planNum = rs.getInt("PlanNum");
		}
		rs.close();
		statement.close();
		conn.close();
		return planNum;
	}

	/**
	 * �������ʱ�䡣
	 * 
	 * @param tPLineID
	 * @param lostT
	 * @throws SQLException
	 */
	public void setTPLineLostTime(int tPLineID, String lostT)
			throws SQLException {
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ���±�
		statement.executeUpdate("UPDATE tpline SET LostTime = '" + lostT
				+ "' WHERE TPLineID = " + tPLineID);
		statement.close();
		conn.close();
	}

	/**
	 * ����ʵ�ʲ�����
	 * 
	 * @param tPLineID
	 * @param parseInt
	 * @throws SQLException
	 */
	public void setTPLineRealNum(int tPLineID, int parseInt)
			throws SQLException {
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ���±�
		statement.executeUpdate("UPDATE tpline SET RealNum = " + parseInt
				+ " WHERE TPLineID = " + tPLineID);
		statement.close();
		conn.close();
	}

	/**
	 * ����ƻ�������
	 * 
	 * @param tPLineID
	 * @param parseInt
	 * @throws SQLException
	 */
	public void setTPLinePlanNum(int tPLineID, int parseInt)
			throws SQLException {
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ���±�
		statement.executeUpdate("UPDATE tpline SET PlanNum = " + parseInt
				+ " WHERE TPLineID = " + tPLineID);
		statement.close();
		conn.close();
	}

	/**
	 * ��������ʱ���ȡʵ�ʲ�����
	 * 
	 * @param begin
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 */
	public int getRealNumWithTime(String begin, String start, String end)
			throws SQLException {
		int num = 0;
		Connection connection = OracleUtils.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = null;

		try {
			SimpleDateFormat sdf_ymr = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_Hs = new SimpleDateFormat("HH:mm");
			Date date = sdf_ymr.parse(begin);
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			c.add(c.DATE, 1);
			date = c.getTime();
			// ��һ�� ����
			String next = sdf_ymr.format(date);
			if (sdf_Hs.parse(start).getTime() >= sdf_Hs.parse(end).getTime()) { // ������ڣ���Ϊ���һ�졣
				rs = statement
						.executeQuery("select (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)  as ��λ,sum(case when Crtdate between to_date('"
								+ begin
								+ " "
								+ start
								+ ":00','yyyy-mm-dd hh24:mi:ss') and to_date('"
								+ next
								+ " "
								+ end
								+ ":59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as ���ʱ��  from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='����') and f.CrtDate between to_date('"
								+ begin
								+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
								+ next
								+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)");
			} else {// ����˵��ʱ��Ϊͬһ��
				rs = statement
						.executeQuery("select (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)  as ��λ,sum(case when Crtdate between to_date('"
								+ begin
								+ " "
								+ start
								+ ":00','yyyy-mm-dd hh24:mi:ss') and to_date('"
								+ begin
								+ " "
								+ end
								+ ":59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as ���ʱ��  from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='����') and f.CrtDate between to_date('"
								+ begin
								+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
								+ next
								+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		while (rs.next()) {
			if (rs.getString(1).equals("D15")) {
				num = rs.getInt(2);
			}
		}
		rs.close();
		statement.close();
		connection.close();
		return num;
	}

	/**
	 * ��ȡ���ʵ�ʲ�����
	 * 
	 * @param nowDate
	 * @return
	 * @throws SQLException
	 */
	public int getRealNum_zao() throws SQLException {
		int num = 0;
		Connection connection = OracleUtils.getConnection();
		Statement statement = connection.createStatement();
		Date date = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(c.DATE, 1);
		date = c.getTime();
		SimpleDateFormat sdf_ymr = new SimpleDateFormat("yyyy-MM-dd");
		// ��ǰ����
		String now = sdf_ymr.format(new Date());
		// ��һ�� ����
		String next = sdf_ymr.format(date);

		// select (case when GW='DOP' then (case when CrtAcc in
		// ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10' end) else GW end)
		// as ��λ,'2015-11-05' as ����,round(sum(BQUA),0) as ���� ,sum(case when
		// Crtdate between to_date('2015-11-05 07:15:00','yyyy-mm-dd
		// hh24:mi:ss') and to_date('2015-11-05 15:14:59','yyyy-mm-dd
		// hh24:mi:ss') then BQUA else 0 end) as ��� ,sum(case when Crtdate
		// between to_date('2015-11-05 15:15:00','yyyy-mm-dd hh24:mi:ss') and
		// to_date('2015-11-05 23:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else
		// 0 end) as �а� ,sum(case when Crtdate between to_date('2015-11-05
		// 23:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-11-06
		// 07:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as ��� from
		// baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and
		// ( f.DES1='����') and f.CrtDate between to_date('2015-11-05
		// 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-11-06
		// 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then
		// (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else
		// 'D10' end) else GW end)
		ResultSet rs = statement
				.executeQuery("select (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)  as ��λ,sum(case when Crtdate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ now
						+ " 15:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as ���  from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='����') and f.CrtDate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)");
		while (rs.next()) {
			if (rs.getString(1).equals("D15")) {
				num = rs.getInt(2);
			}
		}
		rs.close();
		statement.close();
		connection.close();
		return num;
	}

	/**
	 * ��ȡ�а�ʵ�ʲ�����
	 * 
	 * @param nowDate
	 * @return
	 * @throws SQLException
	 */
	public int getRealNum_zhong() throws SQLException {
		int num = 0;
		Connection connection = OracleUtils.getConnection();
		Statement statement = connection.createStatement();
		Date date = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(c.DATE, 1);
		date = c.getTime();
		SimpleDateFormat sdf_ymr = new SimpleDateFormat("yyyy-MM-dd");
		// ��ǰ����
		String now = sdf_ymr.format(new Date());
		// ��һ�� ����
		String next = sdf_ymr.format(date);

		ResultSet rs = statement
				.executeQuery("select (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)  as ��λ,sum(case when Crtdate between to_date('"
						+ now
						+ " 15:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ now
						+ " 23:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as �а�  from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='����') and f.CrtDate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)");
		while (rs.next()) {
			if (rs.getString(1).equals("D15")) {
				num = rs.getInt(2);
			}
		}
		rs.close();
		statement.close();
		connection.close();
		return num;
	}

	/**
	 * ��ȡ���ʵ�ʲ�����
	 * 
	 * @param nowDate
	 * @return
	 * @throws SQLException
	 */
	public int getRealNum_wan() throws SQLException {
		int num = 0;
		Connection connection = OracleUtils.getConnection();
		Statement statement = connection.createStatement();
		Date date = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(c.DATE, 1);
		date = c.getTime();
		SimpleDateFormat sdf_ymr = new SimpleDateFormat("yyyy-MM-dd");
		// ��ǰ����
		String now = sdf_ymr.format(new Date());
		// ��һ�� ����
		String next = sdf_ymr.format(date);
		ResultSet rs = statement
				.executeQuery("select (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)  as ��λ,sum(case when Crtdate between to_date('"
						+ now
						+ " 23:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as ���  from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='����') and f.CrtDate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)");
		while (rs.next()) {
			if (rs.getString(1).equals("D15")) {
				num = rs.getInt(2);
			}
		}
		rs.close();
		statement.close();
		connection.close();
		return num;
	}

	/**
	 * ״̬���㡣
	 * 
	 * @param tPLineID
	 * @throws SQLException
	 */
	public void setTPPlanStatusTo0(int tPLineID) throws SQLException {
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		statement.executeUpdate("update tpplan set Status=0 where TPPlanID = "
				+ tPLineID);
		statement.close();
		conn.close();
	}

	/**
	 * ��ȡ���¼ƻ���
	 * 
	 * @return
	 * @throws SQLException
	 */
	public TPPlan getLatestTpplan() throws SQLException {
		TPPlan plan = null;
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum,TPLineName,Ranger FROM tpplan WHERE TPLineName ='DOP2' AND STATUS = 1 ORDER BY CreateTime DESC LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			plan = new TPPlan();
			plan.setTPPlanID(rs.getInt(1));
			plan.setTotalNum(rs.getInt(2));
			plan.setTPLineName(rs.getString(3));
			plan.setRanger(rs.getInt(4));
		}
		rs.close();
		statement.close();
		conn.close();
		return plan;
	}

	/**
	 * ͨ���ƻ�ID��ȡ���²������顣
	 * 
	 * @param tPPlanID
	 * @return
	 * @throws SQLException
	 */
	public TPLine getTplineWithTPPlanID(int tPPlanID) throws SQLException {
		TPLine line = null;
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT TPLineID,TPPlanID,TPLineName,Ranger,RestTime,StartTime,EndTime FROM tpline WHERE TPPlanID ="
						+ tPPlanID + " AND STATUS = 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			line = new TPLine();
			line.setTPLineID(rs.getInt(1));
			line.setTPPlanID(rs.getInt(2));
			line.setTPLineName(rs.getString(3));
			line.setRanger(rs.getInt(4));
			line.setRestTime(rs.getString(5));
			line.setStartTime(rs.getString(6));
			line.setEndTime(rs.getString(7));
		}
		rs.close();
		statement.close();
		conn.close();
		return line;
	}

	/**
	 * ��ȡ�Ӱ��¼ƻ���
	 * 
	 * @param rangerNum
	 * @return
	 * @throws SQLException
	 */
	public TPPlan getUpdateWork(int rangerNum) throws SQLException {
		TPPlan plan = null;
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		// ͨ����ѯ���ؽ��
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum,TPLineName,Ranger FROM tpplan WHERE TPLineName ='DOP2' AND STATUS = 1 AND Ranger = "
						+ rangerNum + " LIMIT 1");
		// ѭ��ȡ�� rs �еĽ��
		while (rs.next()) {
			plan = new TPPlan();
			plan.setTPPlanID(rs.getInt(1));
			plan.setTotalNum(rs.getInt(2));
			plan.setTPLineName(rs.getString(3));
			plan.setRanger(rs.getInt(4));
		}
		rs.close();
		statement.close();
		conn.close();
		return plan;
	}

	/**
	 * Ԥ����Ϣ�������ݿ⡣
	 * 
	 * @param remark
	 * @return int
	 * @throws SQLException
	 */
	public int addWarningInfo(String remark) throws SQLException {
		int row = 0;
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		row = statement
				.executeUpdate("INSERT INTO warninginfo (Content) VALUES ('"
						+ remark + "')");
		statement.close();
		conn.close();
		return row;
	}

	/**
	 * ��ȡ������Ϣ��
	 * 
	 * @return List<String>
	 * @throws SQLException
	 */
	public WarningInfo getWarningInfo() throws SQLException {
		WarningInfo info = null;
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT InfoID,Content FROM warninginfo WHERE Status = 1 ORDER BY InfoID DESC LIMIT 1");
		while (rs.next()) {
			info = new WarningInfo();
			info.setInfoID(rs.getInt(1));
			info.setContent(rs.getString(2));
		}
		rs.close();
		statement.close();
		return info;
	}

	/**
	 * ��ȡ���ź��롣
	 * 
	 * @return List<String>
	 * @throws SQLException
	 */
	public List<String> getPhoneNumber() throws SQLException {
		List<String> numberList = new ArrayList<>();
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT MesNumber FROM message WHERE Status = 1");
		while (rs.next()) {
			numberList.add(rs.getString(1));
		}
		rs.close();
		statement.close();
		conn.close();
		return numberList;
	}

	/**
	 * ��ȡ�ռ������ַ��
	 * 
	 * @return List<String>
	 * @throws SQLException
	 */
	public List<String> getEmailAddress() throws SQLException {
		List<String> emailList = new ArrayList<>();
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT Email FROM email WHERE Status = 1");
		while (rs.next()) {
			emailList.add(rs.getString(1));
		}
		rs.close();
		statement.close();
		conn.close();
		return emailList;
	}

	/**
	 * ���Ԥ����Ϣ�Ѷ���
	 * 
	 * @param infoID
	 * @throws SQLException
	 */
	public int setWarningInfoAsReaded(int infoID) throws SQLException {
		int row = 0;
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		row = statement
				.executeUpdate("UPDATE warninginfo SET Status = 0 WHERE InfoID = "
						+ infoID);
		statement.close();
		return row;
	}

	/**
	 * ������״̬��ʶΪ2��
	 * 
	 * @param tPLineID
	 * @throws SQLException
	 */
	public int setTPLineStatusTo2(int tPLineID) throws SQLException {
		int r = 0;
		Connection conn = new MySQLDBUtil().getConn();
		// ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		r = statement
				.executeUpdate("update tpline set Status = 2 where TPLineID = "
						+ tPLineID);
		statement.close();
		conn.close();
		return r;
	}

	/**
	 * ����ɫ��ť�Ĺ�λ�������ݿ⡣
	 * 
	 * @param tPLineID
	 * @param number
	 * @return
	 */
	public boolean insertWorkStation(int tPLineID, String number)
			throws SQLException {
		int r = 0;
		Connection conn = new MySQLDBUtil().getConn();
		Statement statement = conn.createStatement();
		r = statement
				.executeUpdate("update tpline set WorkStations = CONCAT(WorkStations,'"
						+ number + " ') where TPLineID = " + tPLineID);
		statement.close();
		conn.close();
		return r > 0 ? true : false;
	}

	/**
	 * ���ذ��Ʊ�����Ϣ���͵���ά��������
	 * 
	 * @param w
	 * @throws SQLException
	 */
	public boolean addWittturBtnWarningToLewei(Warning w) throws SQLException {
		int r = 0;
		Connection conn = new LeweiDBUtils().getConn();
		String sql = "INSERT INTO warning_info	(customerCode, warningSite, warningTime, warningType, warningHandler, status) VALUES	(?, ?, NOW(), ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, w.getCustomerCode());
		ps.setString(2, w.getWarningSite());
		ps.setString(3, w.getWarningType());
		ps.setString(4, w.getWarningHandler());
		ps.setInt(5, w.getStatus());
		r = ps.executeUpdate();
		ps.close();
		conn.close();
		return r > 0 ? true : false;
	}

	/**
	 * ���ذ��Ʊ�����Ϣ�Ѵ���״̬���͵���ά��������
	 * 
	 * @param code
	 * @throws SQLException
	 */
	public boolean dealWittturBtnWarningToLewei(String code)
			throws SQLException {
		int r = 0;
		Connection conn = new LeweiDBUtils().getConn();
		Statement statement = conn.createStatement();
		r = statement
				.executeUpdate("UPDATE warning_info SET status = 1 WHERE customerCode = '"
						+ code + "'");
		return r > 0 ? true : false;
	}

}
