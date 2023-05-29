package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	final private static String IP = "localhost";
	final private static int PORT = 3306;
	final private static String SCHEMA = "mafia";
	final private static String DB_ID = "root";
	final private static String DB_PASSWORD = "root";
	final private static String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + SCHEMA + "?serverTimezone=Asia/Seoul";

	static Connection con = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void connectDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�");
		}
	}
	
	public static Connection getConnection() throws SQLException {
		con = DriverManager.getConnection(URL, DB_ID, DB_PASSWORD);
		return con;
	}
}
