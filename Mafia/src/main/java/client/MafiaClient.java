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
	private String accName;
	private String charName;
	private String email;
	private Channel session;
	private int location = -1; // -1 로그인창 | 0 로비 | 1 대기실 | 2 게임장
	private int grade = 0;
	private int gradePoint = 0;
	private int level;
	private int exp;
	private int job; // 게임 내 직업
	private boolean isReady = false; // 대기실 준비 상태
	private WaitingRoom waitRoom;
	private int citizenVote, doctorVote, policeVote, mafiaVote; // 어떤 직업군에게 받은 투표 수
	private boolean blockChat = false; // 말하기 금지
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

	public void disconnect() {
		if(this.location != -1)
			this.saveDB();
		else if(this.location == LocationInformation.LOBBY) 
			Lobby.removeClient(this);
		else if(this.location == LocationInformation.WAITING_ROOM) 
			this.getWaitingRoom().removeClient(this);
		else if(this.location == LocationInformation.GAME_ROOM) {
			// 마피아 게임중일 때
		}
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
				this.setLevel(level);
				this.setExp(exp);
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
		for(WaitingRoom room : Lobby.getRooms()) {
			this.getSession().writeAndFlush(LobbyPacketCreator.updateRoom(room, true));
			System.out.println(room.getName() + " 보냄");
		}
		this.getSession().writeAndFlush(ClientPacketCreator.userInformation(this));
		//warp(LocationInformation.LOBBY);
	}
	
	public void saveDB() {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DatabaseConnection.getConnection();
			ps = con.prepareStatement("UPDATE accounts SET level = ?, exp = ?, grade = ?, grade_point = ?, charName = ? WHERE id = ?");
			ps.setInt(1, this.getLevel());
			ps.setInt(2, this.getExp());
			ps.setInt(3, this.getGrade());
			ps.setInt(4, this.getGradePoint());
			ps.setString(5, this.getCharName());
			ps.setInt(6, this.getAccId());
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
		if(this.location == location)
			return;
		
		if(this.location == -1) {
			Lobby.addClient(this);
		}
		if(this.location == LocationInformation.LOBBY &&
				location == LocationInformation.WAITING_ROOM) { // 로비 -> 대기실
		}
		if(location == LocationInformation.LOBBY && // 대기실 -> 로비
				this.location == LocationInformation.WAITING_ROOM) {
			Lobby.addClient(this);
			this.getWaitingRoom().removeClient(this);
			if(this.getWaitingRoom().getOnlines() > 1) { // 나가고 남은 사람이 1명 이상일 때
				if(this.getWaitingRoom().getLeader().getAccId() == this.getAccId()) { // 나간 사람이 방장일 때
					this.getWaitingRoom().setLeader(this.getWaitingRoom().getClients().get(0)); // 아무한테나 넘겨줌
				}
			} else {
				this.getWaitingRoom().destroyRoom(); // 방 삭제		
				Lobby.broadCast(LobbyPacketCreator.removeRoom(this.getWaitingRoom().getId()));
				// 방 없어졌다고 Send
			}
			this.setWaitingRoom(null);
		}
		this.location = location;
		int roomId = this.getWaitingRoom() == null ? -1 : this.getWaitingRoom().getId();
		this.getSession().writeAndFlush(ClientPacketCreator.warp(location, roomId));
		
	}
	
	public boolean exitRoom() {
		boolean exit = false;
		return exit;
	}
	
	public boolean enterRoom(int roomId) {
		for(WaitingRoom r : Lobby.getRooms()) {
			if(r.getId() == roomId) {
				this.setWaitingRoom(r);
				this.warp(LocationInformation.LOBBY);
				return true;
			}
		}
		return false;
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

	public void dropMessage(int type, String message) {
		// 메시지 전송
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
	

}
