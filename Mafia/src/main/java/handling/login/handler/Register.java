package handling.login.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import database.DatabaseConnection;

public class Register {
	public static String getCertificationCode() {
		Random rand = new Random();
		int nums[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		int size = 6;
		String code = "";
		for(int i = 0; i < size; i++) {
			code += nums[rand.nextInt(nums.length)]; 
		}
		return code;
	}
	public static boolean isOverlapID(String id) {
		boolean overlap = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE accName = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next())
				overlap = true;			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(rs != null) rs.close();} catch (SQLException e) {}
			try { if(ps != null) ps.close();} catch (SQLException e) {}
			try { if(con != null) con.close();} catch (SQLException e) {}
		}
		return overlap;		
	}

	public static boolean isOverlapNickName(String name) {
		boolean overlap = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE charName = ?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			if(rs.next())
				overlap = true;			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(rs != null) rs.close();} catch (SQLException e) {}
			try { if(ps != null) ps.close();} catch (SQLException e) {}
			try { if(con != null) con.close();} catch (SQLException e) {}
		}
		return overlap;		
	}
	public static boolean isOverlapEmail(String email) {
		boolean overlap = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE email = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next())
				overlap = true;			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if(rs != null) rs.close();} catch (SQLException e) {}
			try { if(ps != null) ps.close();} catch (SQLException e) {}
			try { if(con != null) con.close();} catch (SQLException e) {}
		}
		return overlap;		
	}
	
	public static boolean register(String accName, String password, String charName, String email) {
		boolean suc = false;
		if(isOverlapID(accName) || isOverlapNickName(charName)) {
			System.out.println("register 실패. 사유. ID또는 닉네임 중복");
			return false;
		}
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("INSERT INTO accounts(accName, password, charName, level, exp, grade, grade_point, email) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, accName);
			ps.setString(2, password);
			ps.setString(3, charName);
			ps.setInt(4, 1);
			ps.setInt(5, 0);
			ps.setInt(6, 0);
			ps.setInt(7, 0);
			ps.setString(8, email);
			ps.executeUpdate();
			suc = true;
		} catch (SQLException e) {
			System.out.println("register 실패. 사유. DB 오류");
			e.printStackTrace();
		} finally {
			try { if(ps != null) ps.close();} catch (SQLException e) {}
			try { if(con != null) con.close();} catch (SQLException e) {}			
		}
		return suc;		
	}
}
