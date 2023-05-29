package handling.login.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import client.MafiaClient;
import database.DatabaseConnection;

public class LoginHandler {
	public static int canLogin(String id, String password, MafiaClient c) {
		int failCode = -1; // ����
		if(!checkIDPassword(id, password)) // id pw�� �´��� 
			return 1; 
		if(!checkLoggedin(id)) // �α������� �������� 
			return 2;
		return failCode;
	}
	
	public static boolean checkIDPassword(String id, String password) {
		boolean loginOk = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE accName = ? AND password = ?");
			ps.setString(1, id);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if(rs.next())
				loginOk = true;			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(rs != null) rs.close();} catch (SQLException e) {}
			try { if(ps != null) ps.close();} catch (SQLException e) {}
			try { if(con != null) con.close();} catch (SQLException e) {}
		}
		return loginOk;
	}
	
	public static boolean checkLoggedin(String id) {
		boolean loginOk = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE accName = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) 
				loginOk = rs.getInt("loggedin") == 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(rs != null) rs.close();} catch (SQLException e) {}
			try { if(ps != null) ps.close();} catch (SQLException e) {}
			try { if(con != null) con.close();} catch (SQLException e) {}
		}
		return loginOk;
	}
	
	public static void disconnectAll() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET loggedin = ? WHERE id > 0");
			ps.setInt(1, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		System.out.println("[LoginHandler] ���� �������� ������ ��� ���� ���׽��ϴ�.");
	}
}
