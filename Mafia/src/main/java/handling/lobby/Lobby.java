package handling.lobby;

import java.util.ArrayList;
import java.util.List;

import client.MafiaClient;
import handling.packet.LobbyPacketCreator;
import information.LocationInformation;
import tools.Manager;

public class Lobby {
	private static final ArrayList<MafiaClient> clients = new ArrayList<MafiaClient>();
	private static final ArrayList<WaitingRoom> rooms = new ArrayList<WaitingRoom>();
	
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
		Manager.manager.addRoom(room);
		return rooms.add(room);
	}
	
	public static boolean removeRoom(WaitingRoom room) {
		Manager.manager.removeRoom(room);
		return rooms.remove(room);
	}
	
	public static boolean addClient(MafiaClient client) {
		Manager.manager.addLobbyClient(client);
		System.out.println("[Lobby] " + client.getCharName() + " 님이 로비로 왔습니다.");
		return clients.add(client);
	}
	
	public static boolean removeClient(MafiaClient client) {
		Manager.manager.removeLobbyClient(client);
		System.out.println("[Lobby] " + client.getCharName() + " 님이 로비를 떠났습니다.");
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
	
	public static WaitingRoom getRoom(int roomId) {
		for(WaitingRoom room : rooms) {
			if(room.getId() == roomId) {
				return room;
			}
		}
		return null;
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
		for(MafiaClient c : clients) {
			c.getSession().writeAndFlush(packet);
		}
	}
	
	
}
