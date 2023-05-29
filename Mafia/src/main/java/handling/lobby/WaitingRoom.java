package handling.lobby;

import java.util.ArrayList;
import java.util.List;

import client.MafiaClient;
import game.MafiaGame;
import handling.packet.ClientPacketCreator;
import handling.packet.LobbyPacketCreator;
import handling.packet.RoomPacketCreator;
import information.LocationInformation;
import information.ServerConstants;
import tools.Manager;

public class WaitingRoom {
	public static int AUTO_INCREASE_ID = 0;
	public final static int MIN_PERSON = 5; // 최소 입장 인원
	public final static int MAX_PERSON = 10; // 최대 입장 인원
	private final int id;
	private String name;
	private MafiaClient leader;
	private final ArrayList<MafiaClient> clients = new ArrayList<MafiaClient>();
	private boolean isStart = false; // 게임이 시작 된 방인지
	private int minPerson = MIN_PERSON;
	private int maxPerson = MAX_PERSON;
	private MafiaGame game;

	public WaitingRoom(String name, MafiaClient leader, int maxPerson) {
		this.id = getAutoIncreaseId();
		this.setName(name);
		this.setLeader(leader);
		this.isStart = false;
		this.setMaxPerson(maxPerson);
	}

	public int getAutoIncreaseId() {
		return (++AUTO_INCREASE_ID == Integer.MAX_VALUE ? (AUTO_INCREASE_ID = 1) : ++AUTO_INCREASE_ID);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MafiaClient getLeader() {
		return leader;
	}

	public void setLeader(MafiaClient leader) {
		/*if(this.getLeader() != null) {
			this.getLeader().getSession().writeAndFlush(ClientPacketCreator.setLeader(false));
			leader.dropMessage(4, "방장을 위임 받았습니다.");
		}
		leader.getSession().writeAndFlush(ClientPacketCreator.setLeader(true));
		this.broadCast(RoomPacketCreator.updateRoom(leader));
		this.broadCast(RoomPacketCreator.updateRoom(this.leader));*/
		this.leader = leader;
	}

	public int getMinPerson() {
		return minPerson;
	}

	public int getMaxPerson() {
		return maxPerson;
	}

	public void setMaxPerson(int maxPerson) {
		this.maxPerson = maxPerson;
	}

	public int getOnlines() {
		return clients.size();
	}

	public boolean remove(int accId) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getAccId() == accId) {
				clients.remove(i);
				return true;
			}
		}
		return false;
		
	}
	public boolean removeClient(int accId) {
		for (MafiaClient c : clients) {
			if (c.getAccId() == accId) {
				return this.removeClient(c);
			}
		}
		return false;
	}

	public boolean removeClient(MafiaClient c) { // 방에서 나갈 때 호출
		boolean suc = remove(c.getAccId());
		if (suc) {
			c.setWaitingRoom(null);
			if (this.getOnlines() >= 1) { // 나가고 남은 사람이 1명 이상일 때
				if (this.getLeader().getAccId() == c.getAccId()) { // 나간 사람이 방장일 때
					this.setLeader(this.getClients().get(0)); // 방장을 아무한테나 넘겨줌
				}
				System.out.println("1");
				this.broadCast(RoomPacketCreator.exitClient(c.getAccId()));
				System.out.println("2");
				Lobby.broadCast(LobbyPacketCreator.updateRoom(this));
				System.out.println("[WaitingRoom] '" + c.getCharName() + "'님이 '" + this.getName() + "'방에서 퇴장했습니다. ( 남은 인원 : " + this.getOnlines() + ")");
			} else { // 나갔을 때 아무도 없으면
				this.destroyRoom(c);
				System.out.println("[WaitingRoom] '" + c.getCharName() + "'님이 '" + this.getName() + "'방을 파괴했습니다. ( 남은 인원 : " + this.getOnlines() + ")");
			}
			Manager.manager.addRoomClient(this);
		} else {
			System.out.println("[WaitingRoom] 알 수 없는 이유로 방에서 나가지 못했습니다.");
		}
		return suc;
	}

	public void destroyRoom(MafiaClient c) {
		Lobby.removeRoom(this);
		Lobby.broadCast(LobbyPacketCreator.removeRoom(this.getId())); // 방 없어졌다고 Send
		setLeader(null);
		clients.clear();
	}

	public List<MafiaClient> getClients() {
		return clients;
	}

	public boolean isStart() {
		return isStart;
	}

	public boolean exitRoom(MafiaClient c) { /* 방 나갈 시 호출 */
		return this.removeClient(c);
	}

	public boolean enterRoom(MafiaClient c) { // 유저 입장 시 호출
		/*
		 * 1. 인원 수 변경 사항 로비에 전달 2. 추가되는 인원 대기실에 전달
		 * 
		 */
		if (getOnlines() == this.getMaxPerson()) {
			System.out.println("ㅇㅇ");
			c.dropMessage(4, "해당 방에 입장 할 수 있는 자리가 없습니다.");
			return false;
		}
		boolean suc = clients.add(c);
		if (suc) {
			c.setWaitingRoom(this);
			c.setReady(false);
			Lobby.removeClient(c);
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this));
			this.broadCast(RoomPacketCreator.updateRoom(c));
			for (MafiaClient client : this.getClients()) {
				if (c.getAccId() != client.getAccId())
					c.getSession().writeAndFlush(RoomPacketCreator.updateRoom(client));
			}
			Manager.manager.addRoomClient(this);
		} else { 
			c.dropMessage(4, "알 수 없는 이유로 방에 입장이 불가합니다.");
			System.out.println("[WaitingRoom] 알 수 없는 이유로 방에 입장이 불가능합니다.");
		}
		return suc;
	}

	public void startGame() { // 게임시작 시 호출
		// 준비 됐는지 확인
		boolean ready = true;
		if (getOnlines() < getMinPerson()) { // 시작인원 부족 시
			this.getLeader().dropMessage(4, "게임을 시작하기 위한 인원이 모자랍니다.");
			return;
		}
		for (MafiaClient c : clients) {
			if (!c.isReady()) {
				ready = false;
				break;
			}
		}
		if (!ready) { // 준비 안 된 사람 있을 시 방장에게 메시지 전송
			this.getLeader().dropMessage(2, "준비하지 않은 인원이 있습니다.");  
		} else { // 게임 시작 시
			int id = 0;
			for (MafiaClient c : clients) {
				c.setId(id);
				c.setDead(false);
				c.warp(LocationInformation.GAME_ROOM); // 게임장으로 이동
				c.dropMessage(2, "게임을 시작합니다.");
			}
			this.isStart = true;
			this.setGame(new MafiaGame(this));
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this));
			// 마피아 게임 객체 생성
		}
	}

	public void endGame(boolean mafiaWin) { // 게임 종료 시 호출
		this.isStart = false;
		for (MafiaClient c : clients) {
			if (mafiaWin) {
				if (ServerConstants.isMafia(c.getJob())) {
					// 마피아 승리 보상

				} else {
					// 시민팀 패배 보상
				}
			} else {
				if (ServerConstants.isMafia(c.getJob())) {
					// 마피아 패배 보상

				} else {
					// 시민팀 승리 보상
				}

			}
			c.setReady(false); // 모든 유저 레디해제
			c.setDead(false);
			c.setBlockChat(false);
			c.setJob(0);
			c.warp(LocationInformation.WAITING_ROOM, this);
		}
		Lobby.broadCast(LobbyPacketCreator.updateRoom(this)); // 방 정보 전송
	}

	public void broadCastMessage(int type, String msg) {
		broadCastMessage(type, null, msg);
	}

	public void broadCastMessage(int type, String title, String msg) {
		for (MafiaClient c : clients)
			c.dropMessage(type, title, msg);
	}

	public void broadCast(byte[] packet) {
		for (MafiaClient c : clients) {
			c.getSession().writeAndFlush(packet);
		}
	}

	public MafiaGame getGame() {
		return game;
	}

	public void setGame(MafiaGame game) {
		this.game = game;
	}
}
