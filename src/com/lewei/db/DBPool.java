package com.lewei.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 连接池
 * 
 * @author djzhao
 * @time 2015年11月3日
 */
public class DBPool {

	Connection con = null;

	// private String testTable = "test";

	// 测试连接是否可用的测试表名，默认没有测试表

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
	 * 初始化连接池
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
	 * 释放链接
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
	 * 关闭连接池
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

		} // 清空监控

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
	 * 获取连接
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

				refreshConnections();// 出异常就刷新连接池

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
	 * 添加数据连接
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
	 * 获取单例对象
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
	 * 读取连接配置
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

			System.out.print("出错了！");

		}

	}

	/**
	 * 
	 * 关闭数据库连接
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

			System.out.println(" 关闭数据库连接出错： " + e.getMessage());

		}

	}

	/**
	 * 
	 * 刷新连接池中所有的连接对象
	 * 
	 * 
	 * 
	 * @throws SQLException
	 */

	public void refreshConnections() throws SQLException {

		// 确保连接池己创新存在

		if (pool == null) {

			addConnection();

			System.out.println(" 连接池不存在，已经重建了!");

			return;

		} else { // 清空所有对象，并且重建 closePool();

			addConnection();

			System.out.println(" 刷新一次了");

		}

	}
}