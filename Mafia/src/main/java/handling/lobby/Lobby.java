package handling.lobby;

import java.util.ArrayList;
import java.util.List;

import client.MafiaClient;
import handling.packet.LobbyPacketCreator;
import information.LocationInformation;

public class Lobby {
	private static final List<MafiaClient> clients = new ArrayList<MafiaClient>();
	private static final List<WaitingRoom> rooms = new ArrayList<WaitingRoom>();
	
	public static List<MafiaClient> getClients() {
		return clients;
	}
	
	public static List<WaitingRoom> getRooms() {
		return rooms;
	}
	
	public static int getConnections() {
		return clients.size();
	}
	
	public static boolean addRoom(WaitingRoom room) {
		removeClient(room.getLeader());
		broadCast(LobbyPacketCreator.updateRoom(room, true));
		System.out.println("[Lobby] '" + room.getName() + "' 방 생성 완료");
		return rooms.add(room);
	}
	
	public static boolean addClient(MafiaClient client) {
		return clients.add(client);
	}
	
	public static boolean removeClient(MafiaClient client) {
		return clients.remove(client);
	}
	
	public static boolean removeClient(int id) {
		for(MafiaClient c : clients) {
			if(c.getAccId() == id) {
				return removeClient(c);
			}
		}
		return false;
	}

	public static void broadCaseMessage(int type, String msg) {
		for(MafiaClient c : clients) 
			c.dropMessage(type, null, msg);		
	}
	
	public static void broadCaseMessage(int type, String title, String msg) {
		for(MafiaClient c : clients) 
			c.dropMessage(type, title, msg);		
	}
	
	public static void broadCast(byte[] packet) {
		System.out.println("[Lobby] 접속자 수 : " + getConnections());
		for(MafiaClient c : clients) {
			System.out.println("[" + c.getCharName() + "] 에게 패킷을 전송했습니다. (Lobby)");
			c.getSession().writeAndFlush(packet);
		}
	}
	
	
}
