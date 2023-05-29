package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.packet.ClientPacketCreator;
import handling.packet.LobbyPacketCreator;
import information.LocationInformation;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class MafiaClient {
	public final static AttributeKey<MafiaClient> CLIENTKEY = AttributeKey.valueOf("mafiaclient_netty");
	private int accId;
	private int id; // ���� �� ���� id
	private String accName;
	private String charName;
	private String email;
	private Channel session;
	private int location = -1; // -1 �α���â | 0 �κ� | 1 ���� | 2 ������
	private int grade = 0;
	private int gradePoint = 0;
	private int level;
	private int exp;
	private int job; // ���� �� ����
	private boolean isReady = false; // ���� �غ� ����
	private WaitingRoom waitRoom;
	private int citizenVote, doctorVote, policeVote, mafiaVote; // � ���������� ���� ��ǥ ��
	private int agree; // ó�� ���� ��
	private boolean blockChat = false; // ���ϱ� ����
	private boolean dead = false; // ��� ����
	private boolean isConnected = false;
	private String certification_code;

	public MafiaClient(Channel session) {
		this.session = session;
	}

	public Channel getSession() {
		return this.session;
	}

	public String getInetAddress() {
		return this.getSession().remoteAddress().toString();
	}

	public void disconnect() { // �α׾ƿ� ��
		if (this.location != -1) { // �α��� â�� �ƴϸ�
			this.saveDB();
			
		}
		if (this.location == LocationInformation.LOBBY)
			Lobby.removeClient(this);
		if (this.location == LocationInformation.WAITING_ROOM)
			this.getWaitingRoom().exitRoom(this);
		if (this.location == LocationInformation.GAME_ROOM) {
			this.getWaitingRoom().broadCast(null);
		}
		this.setConnected(false);
		this.changeLoggedin(this.getAccId(), 0);
		System.out.println("[MafiaClient] '" + this.getCharName() + "'���� �α׾ƿ� �߽��ϴ�.");
	}

	public void login(String accName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("SELECT * FROM accounts WHERE accName = ?");
			ps.setString(1, accName);
			rs = ps.executeQuery();
			while (rs.next()) {
				this.setAccId(rs.getInt("id"));
				this.setAccName(accName);
				this.setCharName(rs.getString("charName"));
				this.setLevel(rs.getInt("level"));
				this.setExp(rs.getInt("exp"));
				this.setGrade(rs.getInt("grade"));
				this.setGradePoint(rs.getInt("grade_point"));
				this.setEmail(rs.getString("email"));
			}
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
		changeLoggedin(this.getAccId(), 1);
		this.setConnected(true);
		this.getSession().writeAndFlush(ClientPacketCreator.userInformation(this));
		this.warp(LocationInformation.LOBBY);
		for (WaitingRoom room : Lobby.getRooms()) {
			this.getSession().writeAndFlush(LobbyPacketCreator.updateRoom(room));
		}
	}

	public void changeLoggedin(int accId, int loggedin) {
		/* 
		 * loggedin : 0 �α׾ƿ� 1 ������
		 */
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET loggedin = ? WHERE id = ?");
			ps.setInt(1, loggedin);
			ps.setInt(2, accId);
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
	}
	
	public void saveDB() {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement(
					"UPDATE accounts SET level = ?, exp = ?, grade = ?, grade_point = ?, charName = ?, email = ? WHERE id = ?");
			ps.setInt(1, this.getLevel());
			ps.setInt(2, this.getExp());
			ps.setInt(3, this.getGrade());
			ps.setInt(4, this.getGradePoint());
			ps.setString(5, this.getCharName());
			ps.setInt(6, this.getAccId());
			ps.setString(7, this.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public int getLocation() {
		return location;
	}

	public void warp(int location) {
		this.warp(location, null);
	}

	public void warp(int location, WaitingRoom room) { /*  */
		this.getSession().writeAndFlush(ClientPacketCreator.warp(location, room == null ? -1 : room.getId()));
		System.out.println(this.location + " -> " + location);
		if (location == LocationInformation.LOBBY) { // �κ�� �̵��� ��
			if (this.location == LocationInformation.WAITING_ROOM) { // ���� -> �κ�
				Lobby.addClient(this);
				this.getWaitingRoom().exitRoom(this);
				System.out.println("[MafiaClient] ���ǿ��� �κ�� �̵�");
			} else if (this.location == LocationInformation.GAME_ROOM) { // ������ -> �κ�
				Lobby.addClient(this);

			} else if (this.location == -1) { // �α��� â -> �κ�
				Lobby.addClient(this);
				System.out.println("[MafiaClient] �α��� â���� �κ�� �̵�");
			}
		} else if (location == LocationInformation.WAITING_ROOM) { // ���Ƿ� �̵��� ��
			if (this.location == LocationInformation.LOBBY) { // �κ� -> ����
				if (room.enterRoom(this)) {
					System.out.println("[MafiaClient] �κ񿡼� ���Ƿ� �̵�");
				} else {
					System.out.println("[MafiaClient] �κ� -> ���� �̵� ����");
				}
			} else if (this.location == LocationInformation.GAME_ROOM) { // ������ -> ����
				System.out.println("[MafiaClient] �����忡�� ���Ƿ� �̵�");

			}

		} else if (location == LocationInformation.GAME_ROOM) { // ���������� �̵��� ��
			if (this.location == LocationInformation.WAITING_ROOM) { // ���� -> ������
				System.out.println("[MafiaClient] ���ǿ��� ���������� �̵�");

			}
		}	
		this.location = location;
		System.out.println("�̵� �Ϸ�");
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public WaitingRoom getWaitingRoom() {
		return this.waitRoom;
	}

	public void setWaitingRoom(WaitingRoom room) {
		this.waitRoom = room;
	}

	public int getCitizenVote() {
		return citizenVote;
	}

	public void setCitizenVote(int citizenVote) {
		this.citizenVote = citizenVote;
	}

	public int getDoctorVote() {
		return doctorVote;
	}

	public void setDoctorVote(int doctorVote) {
		this.doctorVote = doctorVote;
	}

	public int getPoliceVote() {
		return policeVote;
	}

	public void setPoliceVote(int policeVote) {
		this.policeVote = policeVote;
	}

	public int getMafiaVote() {
		return mafiaVote;
	}

	public void setMafiaVote(int mafiaVote) {
		this.mafiaVote = mafiaVote;
	}

	public boolean isBlockChat() {
		return blockChat;
	}

	public void setBlockChat(boolean blockChat) {
		this.blockChat = blockChat;
	}

	public void dropMessage(int type, String msg) {
		dropMessage(type, null, msg);
	}

	public void dropMessage(int type, String title, String msg) {
		this.getSession().writeAndFlush(ClientPacketCreator.showMessage(type, title, msg, 0));
	}

	public int getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(int gradePoint) {
		this.gradePoint = gradePoint;
	}

	public String getCertificationCode() {
		return certification_code;
	}

	public void setCertificationCode(String certification_code) {
		this.certification_code = certification_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
		this.setBlockChat(dead);
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public int getAgree() {
		return agree;
	}

	public void setAgree(int agree) {
		this.agree = agree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
