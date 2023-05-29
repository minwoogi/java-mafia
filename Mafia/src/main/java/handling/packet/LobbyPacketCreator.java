package handling.packet;

import client.MafiaClient;
import handling.lobby.Lobby;
import handling.lobby.WaitingRoom;
import handling.packet.header.SendHeader;
import tools.packet.MafiaPacketWriter;

public class LobbyPacketCreator {
	
	public static byte[] updateRoom(WaitingRoom room) {
		// Ŭ�������� �޾Ƶ��� �� ID�� �ش� ���� ������ ������Ʈ �����ش�.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOBBY_UPDATE);
		packet.writeInt(room.getId()); // �� ID
		packet.writeString(room.getName()); // ���̸�
		packet.writeInt(room.getOnlines()); // ����
		packet.writeInt(room.getMaxPerson()); // �ѿ�
		packet.writeBoolean(room.isStart()); // �� ���� : true ���� / false ���
		return packet.getPacket();
	}
	
	public static byte[] removeRoom(int roomId) {
		// Ŭ�������� �޾Ƶ��� �� ID�� �ش� ���� ȭ�鿡�� �����.
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOBBY_REMOVE_ROOM);
		packet.writeInt(roomId);
		return packet.getPacket();		
	}
	
	public static byte[] loadUsers() {
		// �ʴ��ϱ� ��ư Ŭ�� �� ����
		MafiaPacketWriter packet = new MafiaPacketWriter(SendHeader.LOBBY_USERS);
		packet.writeInt(Lobby.getClients().size());
		for(MafiaClient c : Lobby.getClients()) {
			packet.writeInt(c.getAccId()); // id ��
			packet.writeString(c.getCharName()); // �г���
			packet.writeInt(c.getLevel()); // ����
		}
		return packet.getPacket();
	}
	
}
