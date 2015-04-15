package com.search.c3p0;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBPooldbtest {
	private static DBPooldbtest dbPool;
	public static ComboPooledDataSource dataSource;
	private static Properties props = null;
	private static Logger log = Logger.getLogger(DBPooldbtest.class);
	static {
		try {
			props = new Properties();
			// String tranfile = "./ini/Custom_pool.properties";
			props.load(DBPooldbtest.class.getClassLoader().getResourceAsStream("dbtest_pool.properties"));
		} catch (Exception ex1) {
			log.error("Exception!!!", ex1);
		}
		dbPool = new DBPooldbtest();
	}

	public static Connection getConnection() throws Exception {
		return DBPooldbtest.getInstance().getConn();
	}

	public final static DBPooldbtest getInstance() {
		if(dbPool==null){
			try {
				props = new Properties();
				// String tranfile = "./ini/Custom_pool.properties";
				props.load(DBPooldbtest.class.getClassLoader().getResourceAsStream("dbtest_pool.properties"));
			} catch (Exception ex1) {
				log.error("Exception!!!", ex1);
			}
			dbPool = new DBPooldbtest();
			
		}
		return dbPool;
	}

	public static void main(String[] args) {
		try {
			Connection con = DBPooldbtest.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select count(*) from wantedanswer");
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception ex) {
			log.error("Exception!!!", ex);
		}
	}

	public DBPooldbtest() {
		try {
			dataSource = new ComboPooledDataSource();
			dataSource.setUser(props.getProperty("User"));
			dataSource.setPassword(props.getProperty("Password"));
			dataSource.setJdbcUrl(props.getProperty("JdbcUrl"));
			// autoReconnect=true&useUnicode=true&(characterEncoding="GB2312 ");
			dataSource.setDriverClass(props.getProperty("DriverClass"));
			dataSource.setInitialPoolSize(Integer.parseInt(props
					.getProperty("InitialPoolSize")));
			dataSource.setMinPoolSize(Integer.parseInt(props
					.getProperty("MinPoolSize")));
			dataSource.setMaxPoolSize(Integer.parseInt(props
					.getProperty("MaxPoolSize")));
			dataSource.setMaxStatements(Integer.parseInt(props
					.getProperty("MaxStatements")));
			dataSource.setMaxStatementsPerConnection(Integer.parseInt(props
					.getProperty("MaxStatementsPerConnection")));
			dataSource.setMaxIdleTime(Integer.parseInt(props
					.getProperty("MaxIdleTime")));
			dataSource.setAcquireIncrement(Integer.parseInt(props
					.getProperty("AcquireIncrement")));
			dataSource.setAutoCommitOnClose(false);
			dataSource.setCheckoutTimeout(Integer.parseInt(props
					.getProperty("CheckoutTimeout")));
			dataSource.setBreakAfterAcquireFailure(false);
			dataSource.setIdleConnectionTestPeriod(Integer.parseInt(props
					.getProperty("IdleConnectionTestPeriod")));
			dataSource.setNumHelperThreads(Integer.parseInt(props
					.getProperty("NumHelperThreads")));
			dataSource.setAcquireRetryAttempts(Integer.parseInt(props
					.getProperty("AcquireRetryAttempts")));
			dataSource.setAcquireRetryDelay(Integer.parseInt(props
					.getProperty("AcquireRetryDelay")));

		} catch (PropertyVetoException e) {
			log.error("Exception!!!", e);
			throw new RuntimeException(e);
		}
	}

	public final Connection getConn() {
		try {
			// log.info("Custom--NumConnections--" +
			// dataSource.getNumConnections());
			// log.info("Custom--NumBusyConnections--"
			// +dataSource.getNumBusyConnections());
			// log.info("Custom--NumIdleConnections--"
			// +dataSource.getNumIdleConnections());
			// log.info("Custom--NumHelperThreads--"
			// +dataSource.getNumHelperThreads());
			// log.info("Custom--AcquireIncrement--"
			// +dataSource.getAcquireIncrement());
			// log.info("Custom--AutoCommitOnClose--"
			// +dataSource.isAutoCommitOnClose());
			// log.info("Custom--NumConnectionsAllUsers--"
			// +dataSource.getNumConnectionsAllUsers());
			// log.info("Custom--NumUnclosedOrphanedConnections--"
			// +dataSource.getNumUnclosedOrphanedConnections());
			// log.info("Custom--NumUnclosedOrphanedConnectionsAllUsers()--"
			// +dataSource.getNumUnclosedOrphanedConnectionsAllUsers());
			// log.info("Custom--NumUnclosedOrphanedConnectionsDefaultUser()--"
			// +dataSource.getNumUnclosedOrphanedConnectionsDefaultUser());
			// log.info("Custom--NumUserPools--" +
			// dataSource.getNumUserPools());
			return dataSource.getConnection();

		} catch (SQLException e) {
			log.error("Exception!!!", e);
			throw new RuntimeException("鏃犳硶浠庢暟鎹簮鑾峰彇杩炴帴 ", e);
		}
//		finally {
//			try {
//				log.info("Custom--NumConnections--"
//						+ dataSource.getNumConnections());
//				log.info("Custom--NumBusyConnections--"
//						+ dataSource.getNumBusyConnections());
//				log.info("Custom--NumIdleConnections--"
//						+ dataSource.getNumIdleConnections());
//			} catch (SQLException ex) {
//				log.error("Exception!!!", ex);
//			}
//
//		}
	}
}
