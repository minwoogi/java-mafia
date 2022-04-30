package handling.lobby;

import java.util.ArrayList;
import java.util.List;

import client.MafiaClient;
import game.MafiaGame;
import handling.packet.ClientPacketCreator;
import handling.packet.LobbyPacketCreator;
import information.LocationInformation;

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
		//this.setLeader(leader);
		this.isStart = false;
		this.setMaxPerson(maxPerson);
		//this.addClient(leader);
		//Lobby.removeClient(leader);
		//Lobby.broadCast(LobbyPacketCreator.updateRoom(this, true));
	}
	
	public int getAutoIncreaseId() {
		if(++AUTO_INCREASE_ID == Integer.MAX_VALUE) {
			AUTO_INCREASE_ID = 1;
		}
		return AUTO_INCREASE_ID;
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
	
	public boolean addClient(MafiaClient c) {
		Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
		return clients.add(c);
	}
	public boolean removeClient(int accId) {
		for(MafiaClient c : clients) {
			if(c.getAccId() == accId)
				return removeClient(c);
		}
		return false;
	}
	
	public boolean removeClient(MafiaClient c) {
		Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
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
	public void startGame() {
		// 준비 됐는지 확인
		boolean ready = true;
		for(MafiaClient c : clients) {
			if(!c.isReady()) {
				ready = false;
				break;
			}
		}
		if(!ready) { // 준비 안 된 사람 있을 시
			BroadCast(ClientPacketCreator.showMessage(1, "준비하지 않은 인원이 있습니다."));
		} else { // 게임 시작 시 
			for(MafiaClient c : clients) {
				c.warp(LocationInformation.GAME_ROOM);
			}
			this.isStart = true;
			BroadCast(ClientPacketCreator.showMessage(1, "게임을 시작합니다."));
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
			//마피아 게임 객체 생성
		}	
	}
	
	public void endGame() {
		this.isStart = false;
		for(MafiaClient c : clients) {
			c.setReady(false);
		}		
		Lobby.broadCast(LobbyPacketCreator.updateRoom(this, false));
	}
	
	public void BroadCast(byte[] packet) {
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
