package handling.lobby;

import java.util.ArrayList;
import java.util.List;

import client.MafiaClient;
import game.MafiaGame;
import handling.packet.ClientPacketCreator;
import handling.packet.LobbyPacketCreator;
import information.LocationInformation;
import information.ServerConstants;

public class WaitingRoom {
	public static int AUTO_INCREASE_ID = 0;
	public final static int MIN_PERSON = 5; // 최소 입장 인원
	public final static int MAX_PERSON = 10; // 최대 입장 인원
	private final int id;
	private String name;
	private MafiaClient leader;
	private final List<MafiaClient> clients = new ArrayList<MafiaClient>();
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
	
	public boolean addClient(MafiaClient c) { // 방에 들어 올 때 호출
		if(getOnlines() == this.getMaxPerson()) {
			c.dropMessage(4, "해당 방에 입장 할 수 있는 자리가 없습니다.");
			return false;
		}
		boolean warp = clients.add(c);
		if(warp) {
			c.setWaitingRoom(this);
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
		}
		return warp;
	}
	
	public boolean removeClient(int accId) {
		for(MafiaClient c : clients) {
			if(c.getAccId() == accId) {
				return this.removeClient(c);
			}
		}
		return false;
	}
	
	public boolean removeClient(MafiaClient c) { // 방에서 나갈 때 호출
		if(this.getOnlines() > 1) { // 나가고 남은 사람이 1명 이상일 때
			if(this.getLeader().getAccId() == c.getAccId()) { // 나간 사람이 방장일 때
				this.setLeader(this.getClients().get(0)); // 방장을 아무한테나 넘겨줌
			}
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
		} else { // 나갔을 때 아무도 없으면
			Lobby.broadCast(LobbyPacketCreator.removeRoom(this.getId())); // 방 없어졌다고 Send
			this.destroyRoom(); // 방 삭제		
		}
		return clients.remove(c);
	}
	
	public void destroyRoom() {
		clients.clear();
		setLeader(null);
	}
	
	public List<MafiaClient> getClients() {
		return clients;
	}

	public boolean isStart() {
		return isStart;
	}
	
	public void enterRoom(MafiaClient c) {

	}
	public void startGame() { // 게임시작 시 호출
		// 준비 됐는지 확인
		boolean ready = true;
		if(getOnlines() < getMinPerson()) // 시작인원 부족 시
			return;
		for(MafiaClient c : clients) {
			if(!c.isReady()) {
				ready = false;
				break;
			}
		}
		if(!ready) { // 준비 안 된 사람 있을 시 방장에게 메시지 전송
			this.getLeader().getSession().writeAndFlush(ClientPacketCreator.showMessage(1, "알림", "준비하지 않은 인원이 있습니다."));
		} else { // 게임 시작 시 
			for(MafiaClient c : clients) {
				c.setDead(false);
				c.setBlockChat(false);
				c.warp(LocationInformation.GAME_ROOM); // 게임장으로 이동
				c.dropMessage(2, "게임을 시작합니다.");
			}
			this.isStart = true;
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
			//마피아 게임 객체 생성
		}	
	}
	
	public void endGame(boolean mafiaWin) { // 게임 종료 시 호출
		this.isStart = false;
		for(MafiaClient c : clients) {
			if(mafiaWin) {
				if(ServerConstants.isMafia(c.getJob())) {
					// 마피아 승리 보상
					
				} else {
					// 시민팀 패배 보상
				}
			} else {
				if(ServerConstants.isMafia(c.getJob())) {
					// 마피아 패배 보상
					
				} else {
					// 시민팀 승리 보상
				}
				
			}
			c.setReady(false); // 모든 유저 레디해제
			c.setDead(false);
			c.setBlockChat(false);
			c.setJob(0);
		}		
		Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false)); // 방 정보 전송
	}

	public void broadCastMessage(int type, String msg) {
		broadCastMessage(type, null, msg);
	}	

	public void broadCastMessage(int type, String title, String msg) {
		for(MafiaClient c : clients) 
			c.dropMessage(type, title, msg);
	}
	
	public void broadCast(byte[] packet) {
		for(MafiaClient c : clients) {
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
