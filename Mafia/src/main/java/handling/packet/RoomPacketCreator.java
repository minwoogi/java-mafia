package handling.packet;

import client.MafiaClient;
import handling.lobby.WaitingRoom;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class RoomPacketCreator {
	/*
	 *  ���ǿ� �ִ� ����鿡�� ������ ��Ŷ
	 * 
	 *  
	 * 1. ���� ���� ���� �� ��
	 * 2. ���� ���� �� ����, Ƽ� ����� ��
	 * 
	 */
	
	public static byte[] updateRoom(MafiaClient c) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.ROOM_UPDATE);
		System.out.println("updateRoom");
		packet.writeInt(c.getAccId()); // id( Ű �� )
		packet.writeInt(c.getWaitingRoom().getLeader().getAccId()); // ���� id
		packet.writeString(c.getCharName());
		packet.writeBoolean(c.isReady()); // �غ� ����
		packet.writeInt(c.getLevel()); // ���� 
		packet.writeInt(c.getGrade()); // Ƽ��
		System.out.println("updateRoom");
		return packet.getPacket();
	}
	
	public static byte[] exitClient(int accId) {
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.EXIT_ROOM);
		packet.writeInt(accId);
		return packet.getPacket();				
	}
	
	public static byte[] roomUserInformation(boolean enter, WaitingRoom room) { // �̻��
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.ENTER_ROOM);
		packet.writeBoolean(enter); // ���� ���� ����
		if(enter && room != null) {
			int users = room.getOnlines();
			packet.writeInt(users); // ���� ��
			for(MafiaClient c : room.getClients()) {
				packet.writeInt(c.getAccId()); // accid
				packet.writeString(c.getCharName()); // charName
				packet.writeBoolean(c.isReady()); // �غ� ����
				packet.writeInt(c.getLevel()); // ����
				packet.writeInt(c.getGrade()); // Ƽ��
			}
 		}
		return packet.getPacket();
	
	}
	
}
