package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.packet.ClientPacketCreator;
import handling.packet.GamePacketCreator;
import handling.packet.LobbyPacketCreator;
import handling.packet.RoomPacketCreator;
import information.LocationInformation;
import information.ServerConstants;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import tools.Manager;

public class MafiaClient {
	public final static AttributeKey<MafiaClient> CLIENTKEY = AttributeKey.valueOf("mafiaclient_netty");
	public final static ArrayList<MafiaClient> clients = new ArrayList<MafiaClient>();
	public static int AUTO_INCREASE_MSG_ID = 1;
	private int accId;
	private int id; // 게임 내 고유 id
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
	private int vote = 0; // 투표 가능 횟수
	private int citizenVote, doctorVote, policeVote, mafiaVote; // 어떤 직업군에게 받은 투표 수
	private int agree; // 처형 찬성 수
	private boolean blockChat = false; // 말하기 금지
	private boolean dead = false; // 사망 여부
	private boolean isConnected = false;
	private String certification_code;
	private int msgId = -1;
	private int msgValue = -1;
	private boolean showMsg = false;

	public MafiaClient(Channel session) {
		this.session = session;
	}

	public Channel getSession() {
		return this.session;
	}

	public String getInetAddress() {
		return this.getSession().remoteAddress().toString();
	}

	public void disconnect() { // 로그아웃 시
		if (this.location != -1) { // 로그인 창이 아니면
			this.saveDB();
		}
		if (this.location == LocationInformation.LOBBY)
			Lobby.removeClient(this);
		if (this.location == LocationInformation.WAITING_ROOM)
			this.getWaitingRoom().exitRoom(this);
		if (this.location == LocationInformation.GAME_ROOM) {
			this.getWaitingRoom().getGame().chat(6, this.getId() + "번 님께서 접속을 종료했습니다.");
			this.getWaitingRoom().getGame().killPlayer(this);
			this.getWaitingRoom().exitRoom(this);
		}
		this.setConnected(false);
		this.changeLoggedin(this.getAccId(), 0);
		System.out.println("[MafiaClient] '" + this.getCharName() + "'님이 로그아웃 했습니다.");
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
		this.clients.add(this);
	}

	public void changeLoggedin(int accId, int loggedin) {
		/* 
		 * loggedin : 0 로그아웃 1 접속중
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
			ps.setString(6, this.getEmail());
			ps.setInt(7, this.getAccId());
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
		int before_location = this.location;
		this.location = location;
		this.getSession().writeAndFlush(ClientPacketCreator.warp(location, room == null ? -1 : room.getId()));
		System.out.println(before_location + " -> " + location);
		if (location == LocationInformation.LOBBY) { // 로비로 이동할 때
			if (before_location == LocationInformation.WAITING_ROOM) { // 대기실 -> 로비
				Lobby.addClient(this);
				this.getWaitingRoom().exitRoom(this);
				System.out.println("[MafiaClient] 대기실에서 로비로 이동");
			} else if (before_location == LocationInformation.GAME_ROOM) { // 게임장 -> 로비
				Lobby.addClient(this);
			} else if (before_location == -1) { // 로그인 창 -> 로비
				Lobby.addClient(this);
				System.out.println("[MafiaClient] 로그인 창에서 로비로 이동");
			}
			for (WaitingRoom r : Lobby.getRooms()) {
				this.getSession().writeAndFlush(LobbyPacketCreator.updateRoom(r));
			}
		} else if (location == LocationInformation.WAITING_ROOM) { // 대기실로 이동할 때
			if (before_location == LocationInformation.LOBBY) { // 로비 -> 대기실
				if(!room.enterRoom(this)) {
					this.location = before_location;
					this.warp(before_location);
				}
				
			} else if (before_location == LocationInformation.GAME_ROOM) { // 게임장 -> 대기실
				for (MafiaClient client : room.getClients()) {
					this.getSession().writeAndFlush(RoomPacketCreator.updateRoom(client, room.getLeader().getAccId()));
				}
				System.out.println("[MafiaClient] 게임장에서 대기실로 이동");

			}

		} else if (location == LocationInformation.GAME_ROOM) { // 게임장으로 이동할 때
			if (before_location == LocationInformation.WAITING_ROOM) { // 대기실 -> 게임장
				System.out.println("[MafiaClient] 대기실에서 게임장으로 이동");

			}
		}	
		System.out.println("이동 완료");
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
		int msgId = this.createMsgId();
		this.getSession().writeAndFlush(ClientPacketCreator.showMessage(type, title, msg, msgId));
		if(type == 8 || type == 9) {
			if(this.isShowMsg())
				this.closeMessage();
			this.setMsgId(msgId);
			this.setMsgValue(-1);
			this.setShowMsg(true);
		}
	}

	
	public void showJobCard(int job) {
		if(this.getLocation() == LocationInformation.GAME_ROOM)
			this.getSession().writeAndFlush(GamePacketCreator.showJobCard(job));
		else
			System.out.println("[MafiaClient] 게임장 외에는 띄울 수 없습니다.");
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

	public void levelUp() {
		this.level += 1;
		this.setExp(0);
		this.dropMessage(4, this.getCharName() + "님 " + (this.getLevel() - 1) + " -> "+ this.getLevel() +" 레벨업을 축하드립니다!");
	}
	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
	
	public void gainExp(int exp) {
		int needExp = ServerConstants.needExp(this.getLevel());
		this.exp += exp;
		if(needExp <= this.exp) {
			int before_exp = this.exp;
			this.levelUp();
			this.exp += before_exp - needExp;
		}
		this.sendInformation();			
	}
	
	public void sendInformation() {
		this.getSession().writeAndFlush(ClientPacketCreator.userInformation(this));
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
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

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}
	
	public void clearVote() {
		this.setVote(0);
		this.setCitizenVote(0);
		this.setDoctorVote(0);
		this.setMafiaVote(0);
		this.setPoliceVote(0);
		this.setAgree(0);
	}
	
	public int createMsgId() {
		if(AUTO_INCREASE_MSG_ID == Integer.MAX_VALUE) 
			AUTO_INCREASE_MSG_ID = 0;
		return ++AUTO_INCREASE_MSG_ID;
	}
	
	public void closeMessage() {
		if(this.isShowMsg())
			this.getSession().writeAndFlush(ClientPacketCreator.closeMessage(this.getMsgId()));
		this.setMsgValue(-1);
		this.setMsgId(-1);
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
		this.setShowMsg(true);
	}

	public int getMsgValue() {
		return msgValue;
	}

	public void setMsgValue(int msgValue) {
		this.msgValue = msgValue;
	}

	public boolean isShowMsg() {
		return showMsg;
	}

	public void setShowMsg(boolean showMsg) {
		this.showMsg = showMsg;
	}

}
