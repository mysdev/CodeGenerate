package com.jl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DBUtil
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2017��6��4�� ����10:27:10
 */
public class DBUtil {
	private String devier;
	/**
	 * ���ݿ������ַ���
	 */
	private String connstr;	
	/**
	 * �û���
	 */
	private String username;
	/**
	 * ����
	 */
	private String password;	
	/**
	 * @fieldName: dbname
	 * @fieldType: String
	 * @Description: ���ݿ��
	 */
	private String dbname;
	public String getDbname() {
		return dbname;
	}

	/**
	 * @fieldName: dbtype
	 * @fieldType: String
	 * @Description: ���ݿ����ͣ�ȫСд
	 */
	private String dbtype;
	
	public DBUtil(){
		initConfig();
	}
	
	/**
	 * ִ��SQL��ѯ��䣬���õ�һ��int�ͽ��
	 * @param sql
	 * @return
	 */
	public Integer QueryToInt(String sql) {
		Integer ret = 0;
		System.out.println(sql);
		Connection conn = null;
		try {
			conn = initConnection();		
			java.sql.PreparedStatement psmt = conn.prepareStatement(sql);
			
			java.sql.ResultSet rs = psmt.executeQuery();			
			if (rs.next()) {
				ret = rs.getInt(1);
			}
			rs.close();
			rs = null;
			psmt.close();
			psmt = null;
			closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(conn);
		}
		return ret;
	}
	
	/**
	 * ִ��SQL��ѯ��䣬���õ�һ��String�ͽ��
	 * 
	 * @param sql
	 * @return
	 */
	public String QueryToStr(String sql) {
		String ret = null;
		System.out.println(sql);
		Connection conn = null;
		try {
			conn = initConnection();
			java.sql.PreparedStatement psmt = conn.prepareStatement(sql);
			java.sql.ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			}
			rs.close();
			rs = null;
			psmt.close();
			psmt = null;
			closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(conn);
		}
		return ret;
	}
	/**
	 * ��ѯ�����ListMap��ʽ
	 * 
	 * @param sql
	 * @param arg
	 * @return
	 */
	public List<Map<String, Object>> QueryTableToListMapObject(String sql) {		
		List<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
		System.out.println(sql);
		Connection conn = null;
		try {
			conn = initConnection();
			java.sql.PreparedStatement psmt = conn.prepareStatement(sql);
			java.sql.ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> data = new HashMap<String, Object>();
				ResultSetMetaData rm = rs.getMetaData();
				for (int i = 0; i < rm.getColumnCount(); i++) {
					data.put(rm.getColumnLabel(i + 1).toLowerCase(),
							rs.getObject(rm.getColumnLabel(i + 1)));
				}

				ret.add(data);
			}
			rs.close();
			rs = null;
			psmt.close();
			psmt = null;
			closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(conn);
		}
		return ret;

	}
	
	/**
	 * ��ѯ�����ListMap��ʽ
	 * 
	 * @param sql
	 * @param arg
	 * @return
	 */
	public HashMap<String, String> QueryTableToMap(String sql) {		
		HashMap<String, String> ret = new HashMap<String, String>();
		System.out.println(sql);
		Connection conn = null;
		try {
			conn = initConnection();
			java.sql.PreparedStatement psmt = conn.prepareStatement(sql);
			java.sql.ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				ret.put(rs.getString(1), rs.getString(2));
			}
			rs.close();
			rs = null;
			psmt.close();
			psmt = null;
			closeConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(conn);
		}
		return ret;

	}
	
	/** 
	* @Title: initConnection 
	* @Description: ��ȡDB���� 
	* @return
	* @throws InstantiationException
	* @throws IllegalAccessException
	* @throws ClassNotFoundException
	* @throws SQLException  Connection    �������� 
	* @throws 
	*/
	public Connection initConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		if (connstr.startsWith("jdbc:jtds")) {
			Class.forName("net.sourceforge.jtds.jdbc.Driver")
					.newInstance();
		} else if (connstr.startsWith("jdbc:oracle")) {
			Class.forName("oracle.jdbc.driver.OracleDriver")
					.newInstance();
		} else if (connstr.startsWith("jdbc:mysql")){
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
		}else if (connstr.startsWith("jdbc.sqlserver")){
			Class.forName(
					"com.microsoft.jdbc.sqlserver.SQLServerDriver")
					.newInstance();
		}else if(devier!=null && devier.length()>0){
			Class.forName(devier).newInstance();
		}
		return DriverManager.getConnection(connstr, username, password);
	}
	
	/** 
	* @Title: closeConnection 
	* @Description: �ر�DB���� 
	* @param conn  void    �������� 
	* @throws 
	*/
	public void closeConnection(Connection conn){
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	
	/** 
	* @Title: initConfig 
	* @Description: �������ò��� 
	* @return void    �������� 
	* @throws 
	*/
	private void initConfig(){
		PropertiesUtil pu = new PropertiesUtil("./resources/code_database.properties");
		devier = pu.readValue("diver_name");
		connstr = pu.readValue("url");
		username = pu.readValue("username");
		password = pu.readValue("password");
		dbname = pu.readValue("database_name");
		if(connstr.startsWith("jdbc:oracle")) {
			dbtype = "oracle";
		} else if (connstr.startsWith("jdbc:mysql")){
			dbtype = "mysql";
		}else if (connstr.startsWith("jdbc:sqlserver")){
			dbtype = "mssql";
		}
		//System.out.println(toString());
	}
	public String toString(){
		return  dbtype+ " devier:"+devier+" connstr:"+connstr
				+" username:"+username+" username:"+username
				+" password:"+password+" dbname:"+dbname;
	}
	
	
	
	public static void main(String[] arg){
		DBUtil dbp = new DBUtil();
		System.out.println(dbp.toString());
	}

	public String getDbtype() {
		return dbtype;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

}
