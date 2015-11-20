package com.lewei.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OracleUtils {

	private static final String driverUrl = "oracle.jdbc.driver.OracleDriver";

	private static final String url = "jdbc:oracle:thin:@172.21.3.131:1521:baan";

	private static final String username = "proshow";

	private static final String password = "abc123";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(driverUrl);
			connection = DriverManager.getConnection(url, username, password);
			// connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("Oracle Stop time : " + new Date());
			OracleUtils.getConnection();
			e.printStackTrace();
		}
		return connection;
	}

	// 测试Oracle连接是否成功
	public static void main(String[] args) throws SQLException {
		Connection connection = OracleUtils.getConnection();
		System.out.println("连接成功：" + connection);
		Statement statement = connection.createStatement();
		// DOP线为分离
		// ResultSet rs =
		// statement.executeQuery("select GW as 岗位,'2015-10-20' as 日期,round(sum(BQUA),0) as 产量 , sum(case when Crtdate between to_date('2015-10-20 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-20 15:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 早班 , sum(case when Crtdate between to_date('2015-10-20 15:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-20 23:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 中班 , sum(case when Crtdate between to_date('2016-10-20 23:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-21 07:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 晚班 from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='封箱') and f.CrtDate between to_date('2015-10-20 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-21 07:15:00','yyyy-mm-dd hh24:mi:ss') group by GW");
		// DOP线已分离
		ResultSet rs = statement
				.executeQuery("select (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)  as 岗位,'2015-11-05' as 日期,round(sum(BQUA),0) as 产量 ,sum(case when Crtdate between to_date('2015-11-05 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-11-05 15:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 早班 ,sum(case when Crtdate between to_date('2015-11-05 15:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-11-05 23:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 中班 ,sum(case when Crtdate between to_date('2015-11-05 23:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-11-06 07:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 晚班  from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='封箱') and f.CrtDate between to_date('2015-11-05 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-11-06 07:15:00','yyyy-mm-dd hh24:mi:ss') group by (case when GW='DOP' then (case when CrtAcc in ('SCDOP1','SCDOP2','SCDOP3') then 'D15' else 'D10'  end)  else GW end)");
		while (rs.next()) {
			System.out.println("岗位：" + rs.getObject(1));
			System.out.println("日期" + rs.getObject(2));
			System.out.println("产量" + rs.getObject(3));
			System.out.println(rs.getObject(4));
			System.out.println(rs.getObject(5));
			System.out.println(rs.getObject(6));
			System.out.println("---------------");
		}
	}

}