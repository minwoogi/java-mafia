package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

/**
 * �κ� ȭ�鿡�� ����� ��Ŷ
 * makeRoomPacket() - �游�鶧 ���ο��� ���̸� ���� ��Ŷ ����
 */

public class LobbyPacket {

	public static byte[] makeRoomPacket(String roomName, int numberOfPeople) { // * �� ����� �Ҷ� ������ ��Ŷ * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		writer.writeInt(numberOfPeople);
		writer.writeString(roomName);
		return writer.getPacket();
	}
	
	public static byte[] makeEnterRoomPacket(int roomId) { // * �� ������ �� ������ ��Ŷ * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.ENTER_ROOM);
		writer.writeInt(roomId);
		return writer.getPacket();
	}
	
	public static byte[] makeLogOutPacket(int userId) { // * �α׾ƿ� ���� ����� ��Ŷ * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.LOGOUT);
		writer.writeInt(userId);
		return writer.getPacket();
	}
	
	public static byte[] makeGameStartPacket(int start) { // * ���ӽ��� ���� ����� ��Ŷ * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.START_GAME);
		writer.writeInt(start);
		return writer.getPacket();
	}
	
	

}
