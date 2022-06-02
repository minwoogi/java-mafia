package handlinig.packet;

import handling.packet.header.SendHeader;
import tools.MafiaPacketWriter;

/**
 * 로비 화면에서 만드는 패킷
 * makeRoomPacket() - 방만들때 방인원과 방이름 정보 패킷 생성
 */

public class LobbyPacket {

	public static byte[] makeRoomPacket(String roomName, int numberOfPeople) { // * 방 만들기 할때 보내는 패킷 * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.MAKE_ROOM);
		writer.writeInt(numberOfPeople);
		writer.writeString(roomName);
		return writer.getPacket();
	}
	
	public static byte[] makeEnterRoomPacket(int roomId) { // * 방 입장할 때 보내는 패킷 * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.ENTER_ROOM);
		writer.writeInt(roomId);
		return writer.getPacket();
	}
	
	public static byte[] makeLogOutPacket(int userId) { // * 로그아웃 정보 만드는 패킷 * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.LOGOUT);
		writer.writeInt(userId);
		return writer.getPacket();
	}
	
	public static byte[] makeGameStartPacket(int start) { // * 게임시작 정보 만드는 패킷 * //
		MafiaPacketWriter writer = new MafiaPacketWriter(SendHeader.START_GAME);
		writer.writeInt(start);
		return writer.getPacket();
	}
	
	

}
