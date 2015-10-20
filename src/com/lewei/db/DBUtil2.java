package com.lewei.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil2 {
	
	private static final String URL = "jdbc:oracle:thin:@:1521:baan";
	
	private static final String USER = "proshow";
	
	private static final String PASSWORD = "abc123";
	
	private static Connection conn = null;

	static {
		// 1.加载驱动（反射机制）
				try {
					Class.forName("coracle.jdbc.driver.OracleDriver");
					// 2.获得数据库连接
					conn = DriverManager.getConnection(URL, USER, PASSWORD);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}
	
	public static Connection getConn() {
		return conn;
	}
	
	public static void main(String[] args) throws Exception {

//		conn = DBUtil2.getConn();
		// 3.通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 4.通过查询返回结果
		ResultSet rs = statement.executeQuery("select GW as 岗位,'2015-10-12' as 日期,round(sum(BQUA),0) as 产量 , sum(case when Crtdate between to_date('2015-10-12 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-12 15:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 早班 , sum(case when Crtdate between to_date('2015-10-12 15:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-12 23:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 中班 , sum(case when Crtdate between to_date('2015-10-12 23:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-13 07:14:59','yyyy-mm-dd hh24:mi:ss') then BQUA else 0 end) as 晚班 from baandb.ttiwcn108220 e,baandb.ttiwcn109220 f where e.BOXID=f.BOXID and ( f.DES1='封箱') and f.CrtDate between to_date('2015-10-12 07:15:00','yyyy-mm-dd hh24:mi:ss') and to_date('2015-10-13 07:15:00','yyyy-mm-dd hh24:mi:ss') group by GW");
		// 5.循环去除 rs 中的结果
		while(rs.next()){
			System.out.println(rs.getObject(1));
		}
		
	}

}
