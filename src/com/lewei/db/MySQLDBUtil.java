package com.lewei.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLDBUtil {

	// ����
	private static final String URL = "jdbc:mysql://172.21.2.216:3306/warning_light";

	// ����
	// private static final String URL =
	// "jdbc:mysql://127.0.0.1:3306/warning_light";

	private static final String USER = "root";

	private static final String PASSWORD = "";

	private Connection conn = null;

	DBPool dbp = null;

	// static {
	// // 1.����������������ƣ�
	// try {
	// Class.forName("com.mysql.jdbc.Driver");
	// // 2.������ݿ�����
	// conn = DriverManager.getConnection(URL, USER, PASSWORD);
	//
	// if(conn == null){
	// System.out.println("the conn is closed!");
	// }
	// } catch (ClassNotFoundException e) {
	// System.out.println("sorry,can't find the Driver!");
	// e.printStackTrace();
	// } catch (SQLException e) {
	// System.out.println(conn);
	// e.printStackTrace();
	// }
	// }

	public Connection getConn() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException | ClassNotFoundException e) {
				if (conn == null) {
					Thread.sleep(10000);
					System.out.println("Stop time : " + new Date());
					this.getConn();
					System.out.println("conn :" + conn);
				}
				e.printStackTrace();
			} finally {
				return conn;
			}
		}
		return conn;
	}

	public void main(String[] args) throws Exception {

		// 3.ͨ�����ݿ�����Ӳ������ݿ⣨��ɾ�Ĳ飩
		Statement statement = conn.createStatement();
		System.out.println(conn);
		// 4.ͨ����ѯ���ؽ��
		// ResultSet rs = statement.executeQuery("select * from user");
		// // 5.ѭ��ȥ�� rs �еĽ��
		// while(rs.next()){
		// System.out.println(rs.getObject(3));
		// }
		//
	}

}
