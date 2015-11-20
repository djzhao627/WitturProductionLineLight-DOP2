package com.lewei.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 * ���ӳ�
 * 
 * @author djzhao
 * @time 2015��11��3��
 */
public class DBPool {

	Connection con = null;

	// private String testTable = "test";

	// ���������Ƿ���õĲ��Ա�����Ĭ��û�в��Ա�

	private Vector<Connection> pool;

	private Vector<Connection> poolout;

	private String url;

	private String username;

	private String password;

	private String driverClassName;

	private int poolSize = 5;

	private static DBPool instance = null;

	private DBPool() {
		init();

	}

	// ...

	/**
	 * 
	 * ��ʼ�����ӳ�
	 */

	private void init() {

		pool = null;

		poolout = null;

		readConfig();

		pool = new Vector<Connection>(poolSize);

		poolout = new Vector<Connection>(poolSize);

		addConnection();

	}

	/**
	 * 
	 * �ͷ�����
	 * 
	 * 
	 * 
	 * @param cn
	 */

	public synchronized void release(Connection cn) {

		pool.add(cn);

		poolout.remove(cn);

	}

	/**
	 * 
	 * �ر����ӳ�
	 */

	public synchronized void closePool() {

		if (pool != null && pool.size() > 0) {

			for (int i = 0; i < pool.size(); i++) {

				try {

					((Connection) pool.get(i)).close();

					pool.remove(i);

					pool = null;

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		} // ��ռ��

		if (poolout != null && poolout.size() > 0) {

			for (int i = 0; i < poolout.size(); i++) {

				try {

					((Connection) poolout.get(i)).close();

					poolout.remove(i);

					poolout = null;

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		}

	}

	/**
	 * 
	 * ��ȡ����
	 * 
	 * 
	 * 
	 * @return
	 */

	public synchronized Connection getConnection() {

		Connection conn = null;

		try {

			if (pool != null && pool.size() > 0) {

				while (!testConnection(pool.get(0)) && pool.size() > 0) {

					pool.remove(0);

					if (pool.isEmpty()) {

						addConnection();

					}

				}

				conn = pool.get(0);

				poolout.add(conn);

				pool.remove(conn);
			} else {

				addConnection();

				conn = pool.get(0);

				if (testConnection(conn)) {

					poolout.add(conn);

					pool.remove(conn);

				}

			}

		} catch (Exception e) {

			try {

				refreshConnections();// ���쳣��ˢ�����ӳ�

			} catch (SQLException ex) {

			}

			e.printStackTrace();

		}

		return conn;

	}

	private boolean testConnection(Connection conn) {
		return false;
	}

	/**
	 * 
	 * �����������
	 */

	public void addConnection() {

		Connection conn = null;

		if (pool != null) {

		} else {

			readConfig();

			pool = new Vector<Connection>(poolSize);

			poolout = new Vector<Connection>(poolSize);

		}

		if (pool.isEmpty() && (poolSize - poolout.size() == 0)) {

			try {

				Thread.sleep(5000);

			} catch (InterruptedException e) {

			}

		}

		for (int i = pool.size(); i < (poolSize - poolout.size()); i++) {

			try {

				Class.forName(driverClassName);

				conn = java.sql.DriverManager.getConnection(url, username,
						password);

				pool.add(conn);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}// end for

	}

	/**
	 * 
	 * ��ȡ��������
	 * 
	 * 
	 * 
	 * @return
	 */

	public static DBPool getInstance() {

		if (instance == null) {

			instance = new DBPool();

		}

		return instance;

	}

	/**
	 * 
	 * ��ȡ��������
	 */

	private void readConfig() {

		try {

			this.driverClassName = "com.mysql.jdbc.Driver";

			this.username = "root";

			this.password = "123456";

			this.url = "jdbc:mysql://localhost:3306/dazhe?userUnicode=true&characterEncoding=utf-8&autoReconnect=true";

			this.poolSize = 10;

		} catch (Exception e) {

			e.printStackTrace();

			System.out.print("�����ˣ�");

		}

	}

	/**
	 * 
	 * �ر����ݿ�����
	 * 
	 * 
	 * 
	 * @param conn
	 */

	public void closeConnection(Connection conn) {

		try {

			conn.close();

		} catch (SQLException e) {

			e.printStackTrace();

			System.out.println(" �ر����ݿ����ӳ��� " + e.getMessage());

		}

	}

	/**
	 * 
	 * ˢ�����ӳ������е����Ӷ���
	 * 
	 * 
	 * 
	 * @throws SQLException
	 */

	public void refreshConnections() throws SQLException {

		// ȷ�����ӳؼ����´���

		if (pool == null) {

			addConnection();

			System.out.println(" ���ӳز����ڣ��Ѿ��ؽ���!");

			return;

		} else { // ������ж��󣬲����ؽ� closePool();

			addConnection();

			System.out.println(" ˢ��һ����");

		}

	}
}