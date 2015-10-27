package com.lewei.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.lewei.db.MySQLDBUtil;
import com.lewei.db.OracleUtils;
import com.lewei.model.AlarmData;
import com.lewei.model.Takt;

public class WarningLightDao {

	/**
	 * 获取休息时间
	 * 
	 * @param rangerNum
	 * @return
	 * @throws Exception
	 */
	public String getRestTime(int rangerNum) throws Exception {

		String time = null;

		Connection conn = MySQLDBUtil.getConn();
		// .通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("select RestTime from tpline where TPLineName = 'AA' and Ranger ="
						+ rangerNum
						+ " and Status = 1 ORDER BY StartTime DESC LIMIT 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			time = rs.getString(1);
		}
		rs.close();
		statement.close();
		return time;
	}

	/**
	 * 获取TPLineID
	 * 
	 * @param rangerNum
	 * @return
	 * @throws SQLException
	 */
	public int getTPLineIDByName(int rangerNum) throws SQLException {
		int id = 0;
		Connection conn = MySQLDBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("select TPLineID from tpline where TPLineName = 'AA' and Ranger ="
						+ rangerNum
						+ " and Status = 1 ORDER BY StartTime DESC LIMIT 1");
		while (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		statement.close();
		return id;
	}

	/**
	 * 获取节拍
	 * 
	 * @param tPLineID
	 * @return
	 * @throws SQLException
	 */
	public List<Takt> getTaktByTPLineID(int tPLineID) throws SQLException {
		List<Takt> list = new ArrayList<>();
		Connection conn = MySQLDBUtil.getConn();
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
		return list;
	}

	/**
	 * 获取换班时间
	 * 
	 * @param rangerNum
	 * @return
	 * @throws SQLException
	 */
	public String getChangeTime(int rangerNum) throws SQLException {
		String time = null;

		Connection conn = MySQLDBUtil.getConn();
		// .通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("select ChangeTime from tpline where TPLineName = 'AA' and Ranger ="
						+ rangerNum
						+ " and Status = 1 ORDER BY StartTime DESC LIMIT 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			time = rs.getString(1);
		}
		rs.close();
		statement.close();
		return time;
	}

	/**
	 * 获取按钮事件
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<AlarmData> getNewButtonStat2() throws SQLException {
		List<AlarmData> list = new ArrayList<AlarmData>();
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("SELECT  id,bid,keyid,ttime,yn,status FROM(SELECT * FROM t_alarmdata WHERE bid BETWEEN 9 AND 12 ORDER BY ttime DESC LIMIT 2000) a GROUP BY a.bid,a.keyid");
		// 循环取出 rs 中的结果
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
		return list;
	}

	/**
	 * 监控按钮状态
	 */
	public AlarmData getNewButtonStat() throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		AlarmData ad = new AlarmData();
		conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		statement = conn.createStatement();
		// 通过查询返回结果
		rs = statement
				.executeQuery("select id,bid,keyid,ttime,yn,status from t_alarmdata order by id desc limit 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			ad.setBid(rs.getString("bid"));
			ad.setId(rs.getInt("id"));
			ad.setKeyid(rs.getString("keyid"));
			ad.setStatus(rs.getInt("status"));
			ad.setYn(rs.getInt("yn"));
		}
		rs.close();
		statement.close();
		return ad;
	}

	/**
	 * 标记产线状态已读
	 * @param rangerNum 
	 * 
	 * @throws SQLException
	 */
	public void setTPLineStatusTo0(int rangerNum) throws SQLException {
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		statement
				.executeUpdate("update tpline set Status=0 where Ranger = "+rangerNum+" AND TPLineName = 'AA' and Status = 1");
	}

	/**
	 * 获取早班总产量
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTotalNum0() throws SQLException {
		String totalNum = "0;0";
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum FROM tpplan WHERE Ranger = 0 AND TPLineName ='AA' AND STATUS = 1 ORDER BY CreateTime DESC LIMIT 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			totalNum = rs.getInt("TotalNum")+";"+rs.getInt("TPPlanID");
		}
		rs.close();
		statement.close();
		return totalNum;
	}

	/**
	 * 获取中班总产量
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTotalNum1() throws SQLException {
		String totalNum = "0;0";
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum FROM tpplan WHERE Ranger = 1 AND TPLineName ='AA' AND STATUS = 1 ORDER BY CreateTime DESC LIMIT 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			totalNum = rs.getInt("TotalNum")+";"+rs.getInt("TPPlanID");
		}
		rs.close();
		statement.close();
		return totalNum;
	}

	/**
	 * 获取晚班总产量
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getTotalNum2() throws SQLException {
		String totalNum = "0;0";
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("SELECT TPPlanID,TotalNum FROM tpplan WHERE Ranger = 2 AND TPLineName ='AA' AND STATUS = 1  ORDER BY CreateTime DESC LIMIT 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			totalNum = rs.getInt("TotalNum")+";"+rs.getInt("TPPlanID");
		}
		rs.close();
		statement.close();
		return totalNum;
	}

	/**
	 * 获取计划产量
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getPlanIfNum() throws SQLException {
		// TODO Auto-generated method stub
		int planNum = 0;
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement
				.executeQuery("select PlanNum from tpline where TPLineName = 'AA' and Status = 1");
		// 循环取出 rs 中的结果
		while (rs.next()) {
			planNum = rs.getInt("PlanNum");
		}
		rs.close();
		statement.close();
		return planNum;
	}

	/**
	 * 插入损耗时间
	 * 
	 * @param tPLineID
	 * @param lostT
	 * @throws SQLException
	 */
	public void setTPLineLostTime(int tPLineID, String lostT)
			throws SQLException {
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 更新表
		statement.executeUpdate("UPDATE tpline SET LostTime = '" + lostT
				+ "' WHERE TPLineID = " + tPLineID);
		statement.close();
	}

	/**
	 * 插入实际产量
	 * 
	 * @param tPLineID
	 * @param parseInt
	 * @throws SQLException
	 */
	public void setTPLineRealNum(int tPLineID, int parseInt)
			throws SQLException {
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 更新表
		statement.executeUpdate("UPDATE tpline SET RealNum = " + parseInt
				+ " WHERE TPLineID = " + tPLineID);
		statement.close();
	}

	/**
	 * 获取早班实际产量
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
		// 当前日期
		String now = sdf_ymr.format(new Date());
		// 后一日 日期
		String next = sdf_ymr.format(date);

		ResultSet rs = statement
				.executeQuery("select GW, sum(case when Crtdate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ now
						+ " 15:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 早班 from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='封箱') and f.CrtDate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by GW");
		while (rs.next()) {
			if (rs.getString(1).equals("AA挂件")) {
				num = rs.getInt(2);
			}
		}
		rs.close();
		statement.close();
		return num;
	}

	/**
	 * 获取中班实际产量
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
		// 当前日期
		String now = sdf_ymr.format(new Date());
		// 后一日 日期
		String next = sdf_ymr.format(date);

		ResultSet rs = statement
				.executeQuery("select GW,sum(case when Crtdate between to_date('"
						+ now
						+ " 15:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ now
						+ " 23:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 中班 from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='封箱') and f.CrtDate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by GW");
		while (rs.next()) {
			if (rs.getString(1).equals("AA挂件")) {
				num = rs.getInt(2);
				System.out.println(num);
			}
		}
		rs.close();
		statement.close();
		return num;
	}

	/**
	 * 获取晚班实际产量
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
		// 当前日期
		String now = sdf_ymr.format(new Date());
		// 后一日 日期
		String next = sdf_ymr.format(date);

		ResultSet rs = statement
				.executeQuery("select GW,sum(case when Crtdate between to_date('"
						+ now
						+ " 23:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 晚班 from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='封箱') and f.CrtDate between to_date('"
						+ now
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('"
						+ next
						+ " 07:15:00','yyyy-mm-dd hh24:mi:ss') group by GW");
		while (rs.next()) {
			if (rs.getString(1).equals("AA挂件")) {
				num = rs.getInt(2);
			}
		}
		rs.close();
		statement.close();
		return num;
	}

	public void setTPPlanStatusTo0(int tPLineID) throws SQLException {
		Connection conn = MySQLDBUtil.getConn();
		// 通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		statement.executeUpdate("update tpplan set Status=0 where TPPlanID = "+tPLineID);

	}

}
