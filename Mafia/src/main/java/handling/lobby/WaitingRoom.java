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
	public final static int MIN_PERSON = 5; // �ּ� ���� �ο�
	public final static int MAX_PERSON = 10; // �ִ� ���� �ο�
	private final int id;
	private String name;
	private MafiaClient leader;
	private final ArrayList<MafiaClient> clients = new ArrayList<MafiaClient>();
	private boolean isStart = false; // ������ ���� �� ������
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
			leader.dropMessage(4, "������ ���� �޾ҽ��ϴ�.");
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

	public boolean removeClient(MafiaClient c) { // �濡�� ���� �� ȣ��
		boolean suc = remove(c.getAccId());
		if (suc) {
			c.setWaitingRoom(null);
			if (this.getOnlines() >= 1) { // ������ ���� ����� 1�� �̻��� ��
				if (this.getLeader().getAccId() == c.getAccId()) { // ���� ����� ������ ��
					this.setLeader(this.getClients().get(0)); // ������ �ƹ����׳� �Ѱ���
				}
				System.out.println("1");
				this.broadCast(RoomPacketCreator.exitClient(c.getAccId()));
				System.out.println("2");
				Lobby.broadCast(LobbyPacketCreator.updateRoom(this));
				System.out.println("[WaitingRoom] '" + c.getCharName() + "'���� '" + this.getName() + "'�濡�� �����߽��ϴ�. ( ���� �ο� : " + this.getOnlines() + ")");
			} else { // ������ �� �ƹ��� ������
				this.destroyRoom(c);
				System.out.println("[WaitingRoom] '" + c.getCharName() + "'���� '" + this.getName() + "'���� �ı��߽��ϴ�. ( ���� �ο� : " + this.getOnlines() + ")");
			}
			Manager.manager.addRoomClient(this);
		} else {
			System.out.println("[WaitingRoom] �� �� ���� ������ �濡�� ������ ���߽��ϴ�.");
		}
		return suc;
	}

	public void destroyRoom(MafiaClient c) {
		Lobby.removeRoom(this);
		Lobby.broadCast(LobbyPacketCreator.removeRoom(this.getId())); // �� �������ٰ� Send
		setLeader(null);
		clients.clear();
	}

	public List<MafiaClient> getClients() {
		return clients;
	}

	public boolean isStart() {
		return isStart;
	}

	public boolean exitRoom(MafiaClient c) { /* �� ���� �� ȣ�� */
		return this.removeClient(c);
	}

	public boolean enterRoom(MafiaClient c) { // ���� ���� �� ȣ��
		/*
		 * 1. �ο� �� ���� ���� �κ� ���� 2. �߰��Ǵ� �ο� ���ǿ� ����
		 * 
		 */
		if (getOnlines() == this.getMaxPerson()) {
			System.out.println("����");
			c.dropMessage(4, "�ش� �濡 ���� �� �� �ִ� �ڸ��� �����ϴ�.");
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
			c.dropMessage(4, "�� �� ���� ������ �濡 ������ �Ұ��մϴ�.");
			System.out.println("[WaitingRoom] �� �� ���� ������ �濡 ������ �Ұ����մϴ�.");
		}
		return suc;
	}

	public void startGame() { // ���ӽ��� �� ȣ��
		// �غ� �ƴ��� Ȯ��
		boolean ready = true;
		if (getOnlines() < getMinPerson()) { // �����ο� ���� ��
			this.getLeader().dropMessage(4, "������ �����ϱ� ���� �ο��� ���ڶ��ϴ�.");
			return;
		}
		for (MafiaClient c : clients) {
			if (!c.isReady()) {
				ready = false;
				break;
			}
		}
		if (!ready) { // �غ� �� �� ��� ���� �� ���忡�� �޽��� ����
			this.getLeader().dropMessage(2, "�غ����� ���� �ο��� �ֽ��ϴ�.");  
		} else { // ���� ���� ��
			int id = 0;
			for (MafiaClient c : clients) {
				c.setId(id);
				c.setDead(false);
				c.warp(LocationInformation.GAME_ROOM); // ���������� �̵�
				c.dropMessage(2, "������ �����մϴ�.");
			}
			this.isStart = true;
			this.setGame(new MafiaGame(this));
			Lobby.broadCast(LobbyPacketCreator.updateRoom(this));
			// ���Ǿ� ���� ��ü ����
		}
	}

	public void endGame(boolean mafiaWin) { // ���� ���� �� ȣ��
		this.isStart = false;
		for (MafiaClient c : clients) {
			if (mafiaWin) {
				if (ServerConstants.isMafia(c.getJob())) {
					// ���Ǿ� �¸� ����

				} else {
					// �ù��� �й� ����
				}
			} else {
				if (ServerConstants.isMafia(c.getJob())) {
					// ���Ǿ� �й� ����

				} else {
					// �ù��� �¸� ����
				}

			}
			c.setReady(false); // ��� ���� ��������
			c.setDead(false);
			c.setBlockChat(false);
			c.setJob(0);
			c.warp(LocationInformation.WAITING_ROOM, this);
		}
		Lobby.broadCast(LobbyPacketCreator.updateRoom(this)); // �� ���� ����
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
