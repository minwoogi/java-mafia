package handling.lobby;

import java.util.ArrayList;
import java.util.List;

import client.MafiaClient;

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
				return clients.remove(c);
			}
		}
		return false;
	}
	
	public static void broadCast(byte[] packet) {
		for(MafiaClient c : clients) {
			c.getSession().writeAndFlush(packet);
		}
	}
	
	
}
